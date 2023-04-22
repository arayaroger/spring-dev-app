package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CuentaServiceTest {

    @Autowired
    private CuentaService cuentaService;
    @Test
    void buscarDinamicamentePorCriterios() {

        List<CuentaDto> cuentaDtos = cuentaService.buscarDinamicamentePorCriterios(new CuentaDto());

        assertFalse(cuentaDtos.isEmpty());
        cuentaDtos.forEach(cliente -> System.out.println(">>>>> CUENTA EXISTENTE filtro 1: " + cliente.getTipo() + " " + cliente.getNumero() + " - Estado "+ cliente.getActiva() ));
        assertTrue(cuentaDtos.size() >= 4);

        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setActiva(false);
        cuentaDtos = cuentaService.buscarDinamicamentePorCriterios(cuentaDto);

        assertFalse(cuentaDtos.isEmpty());
        cuentaDtos.forEach(cliente -> System.out.println(">>>>> CUENTA EXISTENTE filtro 2: " + cliente.getTipo() + " " + cliente.getNumero() + " - Estado "+ cliente.getActiva() ));
        assertTrue(cuentaDtos.size() >= 1);

//        ClienteDto clienteDto = new ClienteDto();
//        clienteDto.setApellidos("SANCHEZ");
//        cuentaDtos = cuentaService.buscarDinamicamentePorCriterios(CuentaDto);
//        cuentaDtos.forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE CON FILTRO: " + cliente.getApellidos()));
//        assertTrue(cuentaDtos.size() >= 4);
    }
}