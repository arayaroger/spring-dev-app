package com.pfcti.spring.dev.app.beans;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.spring.dev.app.dto.enums.ClienteQueryType;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdministradorClientesTest {

    @Autowired
    ClienteRepository clienteRepository;
    @Test
    void obtenerListaClientePorCriterio() {
        AdministradorClientes administradorClientes = new AdministradorClientes(clienteRepository,ClienteQueryType.NOMBRES);
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);

        List<ClienteDto> clienteDtos = administradorClientes.obtenerListaClientePorCriterio(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> System.out.println("Cliente: " + clienteDto.getApellidos()));
        assertTrue(clienteDtos.size() == 1);

    }
}