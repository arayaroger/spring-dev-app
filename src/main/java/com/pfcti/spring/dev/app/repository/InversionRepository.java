package com.pfcti.spring.dev.app.repository;

import com.pfcti.spring.dev.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InversionRepository extends JpaRepository<Cliente,Integer> {

}