package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.model.Cuenta;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import com.pfcti.spring.dev.app.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CuentaService {
    private CuentaRepository cuentaRepository;
    private ClienteRepository clienteRepository;

    private CuentaSpecification cuentaSpecification;

    private CuentaDto fromCuentaToCuentaDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);
        cuentaDto.setClienteId(cuenta.getCliente().getId());
        return cuentaDto;
    }
    public List<CuentaDto> buscarDinamicamentePorCriterios(CuentaDto cuentaDtoFilter){
        return cuentaRepository
                .findAll(cuentaSpecification.buildFilter(cuentaDtoFilter))
                .stream()
                .map(this::fromCuentaToCuentaDto)
                .collect(Collectors.toList());
    }

    public void insertarCuentaPorCliente(CuentaDto cuentaDto){

        Cliente cliente = clienteRepository.findById(cuentaDto.getClienteId())
                .orElseThrow(() ->{
                    throw new RuntimeException("Cliente No existe");
                });

        Cuenta cuenta = new Cuenta();
        cuenta.setActiva(cuentaDto.getActiva());
        cuenta.setTipo(cuentaDto.getTipo());
        cuenta.setNumero(cuentaDto.getNumero());
        cuenta.setCliente(cliente);

        cuentaRepository.save(cuenta);
    }

    public void inactivarCuentasCliente(int clienteId){

        cuentaRepository.setInactiveCuentaByClienteId(clienteId);

    }

    public List<CuentaDto> obtenerCuentasPorCliente(int id){
        List<CuentaDto>  cuentaReturn = new ArrayList<>();
        List<Cuenta> cuentas = cuentaRepository.findCuentasByCliente_Id(id);
        cuentas.forEach( cuenta ->{
                    cuentaReturn.add(fromCuentaToCuentaDto(cuenta));
                }
        );
        return cuentaReturn;
    }


}