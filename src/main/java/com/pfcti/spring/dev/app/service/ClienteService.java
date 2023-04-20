package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {
    private ClienteRepository clienteRepository;

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
}
