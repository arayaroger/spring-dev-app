package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.NotificationDto;
import com.pfcti.spring.dev.app.jms.publishers.NotificationPubSubSender;
import com.pfcti.spring.dev.app.jms.senders.NotificationSender;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.model.Cuenta;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import com.pfcti.spring.dev.app.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import org.springframework.integration.support.MessageBuilder;

import com.pfcti.spring.dev.app.jms.senders.NotificationSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CuentaService {
    private NotificationPubSubSender notificationPubSubSender;
    private CuentaRepository cuentaRepository;
    private ClienteRepository clienteRepository;

    private CuentaSpecification cuentaSpecification;

    private NotificationSender notificationSender;
    private ClienteService clienteService;

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

    public void creacionDeCuentaYNotificacion(CuentaDto cuentaDto) {
        insertarCuentaPorCliente(cuentaDto);
        sendNotification(cuentaDto);
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

//    public void sendNotification(CuentaDto cuentaDto) {
//        ClienteDto clienteDto = clienteService.obtenerCliente(cuentaDto.getIdCliente());
//        NotificationDto notificacionDto = new NotificationDto();
//        notificacionDto.setPhoneNumber(clienteDto.getTelefono());
//        notificacionDto.setMailBody("Estimado " + clienteDto.getNombre() + " " + clienteDto.getApellidos() + " tu cuenta número “ + cuentaDto.getNumero() + " se a creado con éxito."); this.notificationSender.sendSms(notificacionDto);
//    }

    public void sendNotification(CuentaDto cuentaDto) {
        ClienteDto clienteDto = clienteService.obtenerCliente(cuentaDto.getClienteId());
        NotificationDto notificacionDto = new NotificationDto();
        notificacionDto.setPhoneNumber(clienteDto.getTelefono());
        notificacionDto.setMailBody("Estimado " + clienteDto.getNombre() + " " + clienteDto.getApellidos() + " tu cuenta número " + cuentaDto.getNumero() + " se ha creado con éxito.");
        this.notificationSender.sendMail(notificacionDto);
    }

    private static String getMailBody(CuentaDto cuentaDto, ClienteDto clienteDto) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Estimado ");
        bodyBuilder.append(clienteDto.getNombre());
        bodyBuilder.append(" ");
        bodyBuilder.append(clienteDto.getApellidos());
        bodyBuilder.append(" tu cuenta número ");
        bodyBuilder.append(cuentaDto.getNumero());
        bodyBuilder.append(" se ha creado con éxito.");
        return bodyBuilder.toString();

    }

    public void sendCreateAccountNotification(CuentaDto cuentaDto) {
        //log.info("Empezando envío de notificaciones");
        Message<CuentaDto> message = MessageBuilder.withPayload(cuentaDto).build();
        Message<String> result = notificationPubSubSender.sendNotification(message);
        log.info("Resultado envío notificación: {}", result.getPayload());
    }

    public void creacionDeCuentaPublishSub(CuentaDto cuentaDto) {
        insertarCuentaPorCliente(cuentaDto);
        sendCreateAccountNotification(cuentaDto);
    }




}