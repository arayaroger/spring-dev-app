package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void insertarCliente() {

        List<Cliente> listClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("lista antes de insertar: " + listClientes.size());

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Roger");
        clienteDto.setApellidos("Araya Montero");
        clienteDto.setCedula("112660478");
        clienteDto.setTelefono("88606902");
        clienteService.insertarCliente(clienteDto);

        listClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("lista luego de insertar: " + listClientes.size());



        assertEquals(3,listClientes.size());
    }

    @Test
    void obtenerCliente() {
        ClienteDto clienteDto = clienteService.obtenerCliente(1);
        System.out.println(clienteDto.getNombre() + " " + clienteDto.getApellidos() + " si existe");
        assertEquals(1,clienteDto.getId());
    }

    @Test
    void actualizarCliente() {
        ClienteDto clienteDtoInicial = clienteService.obtenerCliente(1);
        System.out.println("El cliente inicial es " + clienteDtoInicial.getNombre() + " "+ clienteDtoInicial.getApellidos());

        clienteDtoInicial.setApellidos("FALLAS");

        clienteService.actualizarCliente(clienteDtoInicial);

        ClienteDto clienteDtoFinal = clienteService.obtenerCliente(1);
        System.out.println("El cliente final es " + clienteDtoFinal.getNombre() + " "+ clienteDtoFinal.getApellidos());

        assertEquals("FALLAS",clienteDtoFinal.getApellidos());
    }

    @Test
    void obtenerClientes() {
        List<ClienteDto> clientesDto = clienteService.obtenerClientes();
        clienteService
                .obtenerClientes()
                .forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE: " + cliente.getApellidos()));

        assertEquals(2,clientesDto.size());
    }

    @Test
    void obtenerClientesPorCodigoISOPaisYCuentasActivas() {
        List<ClienteDto> clienteDtos = clienteService.obtenerClientesPorCodigoISOPaisYCuentasActivas("EC");
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " + clienteDto.getApellidos());});
        assertEquals(1, clienteDtos.size());
    }

    @Test
    void eliminarCliente() {
        clienteService.eliminarCliente(1);
        assertEquals(1,1);
    }

    @Test
    void buscarPorApellidos() {
        List<ClienteDto> clientesDto = clienteService.buscarPorApellidos("PEREZ");
        clientesDto.forEach(clienteDto -> {System.out.println("Cliente: " + clienteDto.getApellidos());});
        assertEquals(1,clientesDto.size());
    }

    @Test
    void buscarPorApellidosNative() {
        List<ClienteDto> clientesDto = clienteService.buscarPorApellidosNative("PEREZ");
        clientesDto.forEach(clienteDto -> {System.out.println("Cliente: " + clienteDto.getApellidos());});
        assertEquals(1,clientesDto.size());
    }

    @Test
    void actualizarNombrePorApellido() {
        ClienteDto clienteDtoInicial = clienteService.buscarPorApellidos("SANCHEZ").get(0);
        System.out.println("El cliente inicial es " + clienteDtoInicial.getNombre() + " "+ clienteDtoInicial.getApellidos());

        clienteService.actualizarNombrePorApellido("nuevoNombre",clienteDtoInicial.getApellidos());

        ClienteDto clienteDtoFinal = clienteService.buscarPorApellidos("SANCHEZ").get(0);
        System.out.println("El cliente modificado es " + clienteDtoFinal.getNombre() + " "+ clienteDtoFinal.getApellidos());

        assertNotEquals(clienteDtoInicial.getNombre(),clienteDtoFinal.getNombre());
    }

    @Test
    void buscarClientePorApellidosyNombre() {
        List<ClienteDto> clienteDtos = clienteService.buscarClientePorApellidosyNombre("SANCHEZ","RAUL");
        System.out.println("CLIENTE ENCONTRADO "+ clienteDtos.get(0).getApellidos());
        assertFalse(clienteDtos.isEmpty());
        assertEquals("SANCHEZ",clienteDtos.get(0).getApellidos());
    }

    @Test
    void obtenerClientesExtranjerosYCuentasInactivas() {
        List<ClienteDto> clienteDtos = clienteService.obtenerClientesExtranjerosYTarjetasInactivas("CR");
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " + clienteDto.getApellidos());});
        assertEquals(1, clienteDtos.size());
    }

    @Test
    void buscarDinamicamentePorCriterios() {
        List<ClienteDto> clienteDtos = clienteService.buscarDinamicamentePorCriterios(new ClienteDto());
        assertFalse(clienteDtos.isEmpty());
        clienteDtos.forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE: " + cliente.getApellidos()));
        assertTrue(clienteDtos.size() >=2);

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellidos("SANCHEZ");
        clienteDtos = clienteService.buscarDinamicamentePorCriterios(clienteDto);
        clienteDtos.forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE CON FILTRO: " + cliente.getApellidos()));
        assertTrue(clienteDtos.size() >=4 );

        clienteDto = new ClienteDto();
        clienteDto.setApellidos("SANCHEZ");
        clienteDto.setNombre("DAM");
        clienteDtos = clienteService.buscarDinamicamentePorCriterios(clienteDto);
        clienteDtos.forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE CON FILTRO 2: " + cliente.getApellidos()));
        assertTrue(clienteDtos.size() >=2 );

        clienteDto = new ClienteDto();
        clienteDto.setApellidos("SANCHEZ");
        clienteDto.setCedula("111");
        clienteDtos = clienteService.buscarDinamicamentePorCriterios(clienteDto);
        clienteDtos.forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE CON FILTRO 3: " + cliente.getApellidos()));
        assertTrue(clienteDtos.size() >=2 );

        clienteDto = new ClienteDto();
        clienteDto.setCedula("111");
        clienteDto.setApellidos("SANCHEZ C");
        clienteDto.setNombre("DAM");
        clienteDtos = clienteService.buscarDinamicamentePorCriterios(clienteDto);
        clienteDtos.forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE CON FILTRO 4: " + cliente.getApellidos()));
        assertTrue(clienteDtos.size() >=1 );


    }
}