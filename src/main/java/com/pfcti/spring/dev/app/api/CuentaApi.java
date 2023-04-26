package com.pfcti.spring.dev.app.api;

import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.service.CuentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/cuenta")
public class CuentaApi {

    @Autowired
    CuentaService cuentaService;

    @PostMapping
    public void guardarCuenta(@RequestBody CuentaDto cuentaDto){
        log.info("insertar cuenta: {}", cuentaDto);
        System.out.println("TUVO Q GUARDAR");
        cuentaService.insertarCuentaPorCliente(cuentaDto);
    }

    @GetMapping("/cliente/{id}")
    public List<CuentaDto> buscarCuentasPorCliente(@PathVariable int id){
        log.info("Busqueda de cuentas por cliente: {}",id);
        System.out.println("TUVO Q consultar");
        return cuentaService.obtenerCuentasPorCliente(id);

    }

    @PutMapping("/inactivar/cliente/{id}")
    public void inactivarCuentasxCliente(@PathVariable int id){
        log.info("actualizar de cuenta : {}", id);
        cuentaService.inactivarCuentasCliente(id);

    }

}
