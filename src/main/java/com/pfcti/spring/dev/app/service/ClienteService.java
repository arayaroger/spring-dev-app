package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.*;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClienteService {
    private ClienteRepository clienteRepository;
    private CuentaRepository cuentaRepository;
    private TarjetaRepository tarjetaRepository;
    private InversionRepository inversionRepository;
    private DireccionRepository direccionRepository;

    public void insertarCliente(ClienteDto clienteDto){
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());

        clienteRepository.save(cliente);
    }

    // public ClienteDto buscarClientePorId(int id){
    //     clienteRepository.findById(id);
    // }

    public ClienteDto obtenerCliente(int clienteId){
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->{
                    throw new RuntimeException("Cliente No existe");
                });

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setApellidos(cliente.getApellidos());
        clienteDto.setCedula(cliente.getCedula());
        clienteDto.setTelefono(cliente.getTelefono());

        return clienteDto;
    }

    public void actualizarCliente(ClienteDto clienteDto){

        Cliente cliente = clienteRepository.findById(clienteDto.getId())
                .orElseThrow(() ->{
                    throw new RuntimeException("Cliente No existe");
                });

        cliente.setId(clienteDto.getId());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());

        clienteRepository.save(cliente);
    }

    private ClienteDto fromClienteToClienteDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        /*clienteDto.setId(cliente.getId());*/
        /*clienteDto.setNombre(cliente.getNombre());*/
        /*clienteDto.setApellidos(cliente.getApellidos());*/
        /*clienteDto.setCedula(cliente.getCedula());*/
        /*clienteDto.setTelefono(cliente.getTelefono());*/

        return clienteDto;
    }

    public List<ClienteDto> obtenerClientes(){
        List<ClienteDto>  clienteReturn = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach( cliente ->{
            clienteReturn.add(fromClienteToClienteDto(cliente));
                }
        );
        return clienteReturn;
    }

    public List<ClienteDto> obtenerClientesPorCodigoISOPaisYCuentasActivas(String codigoISO){
        List<Cliente> clientes = clienteRepository.findClientesByPaisAndCuentas_ActivaIsTrue(codigoISO);
        List<ClienteDto> clientesDto = new ArrayList<>();

        clientes.forEach(cliente ->{
            clientesDto.add(fromClienteToClienteDto(cliente));
        });

        return clientesDto;
    }

    public void eliminarCliente(Integer clienteId){
        tarjetaRepository.deleteAllByCliente_Id(clienteId);
        inversionRepository.deleteAllByCliente_Id(clienteId);
        direccionRepository.deleteAllByCliente_Id(clienteId);
        cuentaRepository.deleteAllByCliente_Id(clienteId);
        clienteRepository.deleteById(clienteId);
    }

    public List<ClienteDto> buscarPorApellidos(String apellidos){
        List<ClienteDto> clientesDtos = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.buscarPorApellidos(apellidos);
        clientes.forEach(cliente ->{
            clientesDtos.add(fromClienteToClienteDto(cliente));
        });

        return clientesDtos;
    }

    public List<ClienteDto> buscarPorApellidosNative(String apellidos){
        List<ClienteDto> clientesDtos = new ArrayList<>();
        List<Tuple> tuplas = clienteRepository.buscarPorApellidosNative(apellidos);
        tuplas.forEach(tupla ->{
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setId((Integer)tupla.get("Id"));
            clienteDto.setNombre((String) tupla.get("nombre"));
            clienteDto.setApellidos((String) tupla.get("apellidos"));
            clienteDto.setCedula((String) tupla.get("cedula"));
            clienteDto.setTelefono((String) tupla.get("telefono"));
            clienteDto.setPais((String)tupla.get("pais"));
            clientesDtos.add(clienteDto);
        });

        return clientesDtos;
    }

    public void actualizarNombrePorApellido(String nombre, String apellidos){
        clienteRepository.updateClienteByQuery(nombre, apellidos);
    }
}
