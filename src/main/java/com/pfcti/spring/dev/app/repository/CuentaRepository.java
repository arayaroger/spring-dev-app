package com.pfcti.spring.dev.app.repository;

import com.pfcti.spring.dev.app.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta,Integer>, JpaSpecificationExecutor<Cuenta> {
    void deleteAllByCliente_Id(int clienteId);

    List<Cuenta> findCuentasByActiva(Boolean activa);

    List<Cuenta> findCuentasByCliente_Id(int id);

    @Modifying
    @Query("update Cuenta c set c.activa = false where c.cliente.id =:clienteId")
    void setInactiveCuentaByClienteId(int clienteId);

}
