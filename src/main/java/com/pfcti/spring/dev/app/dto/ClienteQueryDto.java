package com.pfcti.spring.dev.app.dto;
import com.pfcti.spring.dev.app.dto.enums.*;
import com.pfcti.spring.dev.app.model.Cliente;
import lombok.Data;

import java.util.List;

@Data
public class ClienteQueryDto {

    private String textoBusqueda;
    private ClienteQueryType clienteQueryType;


}