package com.pfcti.spring.dev.app.beans;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.spring.dev.app.dto.enums.ClienteQueryType;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuscadorClienteBDDTest {

    @Autowired
    private BuscadorClientes baseDeDatos;

    @Autowired
    @Qualifier("baseDeDatos")
    private BuscadorClientes buscadorClientesBaseDatos;

    @Autowired
    @Qualifier("sistemaExterno")
    private BuscadorClientes buscadorClientesConQualifier;
    @Test
    void obtenerListaClientes() {

        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);

        List<ClienteDto> clienteDtos = baseDeDatos.obtenerListaClientes(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> System.out.println("Cliente: " + clienteDto.getApellidos()));
        assertTrue(clienteDtos.size() == 1);
    }

    @Test
    void obtenerListaClientesBDQualifier() {

        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);

        List<ClienteDto> clienteDtos = buscadorClientesBaseDatos.obtenerListaClientes(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> System.out.println("Cliente: " + clienteDto.getApellidos()));
        assertTrue(clienteDtos.size() == 1);
    }
    @Test
    void obtenerListaClientesSistemaExterno() {

        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);

        List<ClienteDto> clienteDtos = buscadorClientesConQualifier.obtenerListaClientes(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> System.out.println("Cliente: " + clienteDto.getApellidos()));
        assertTrue(clienteDtos.size() == 0);
    }
}
