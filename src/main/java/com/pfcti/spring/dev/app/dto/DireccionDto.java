package com.pfcti.spring.dev.app.dto;

import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class DireccionDto {
    private int id;
    private String direccion;
    private String nomenclatura;
    private int clienteId;
}
