package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        Cliente cliente = new Cliente();

        cliente.setId(clienteDto.getId());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());

        clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerClientes(){
        return clienteRepository.findAll();
    }
}
