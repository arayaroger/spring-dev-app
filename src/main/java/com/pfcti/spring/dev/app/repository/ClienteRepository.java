package com.pfcti.spring.dev.app.repository;

import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    List<Cliente> findClientesByPaisAndCuentas_ActivaIsTrue(String pais);
    @Query(value = "select c from Cliente c where c.apellidos = :apellidos")
    List<Cliente> buscarPorApellidos(String apellidos);

    List<Cliente> findClientesByApellidos(String apellidos);
    @Query(value = "select nombre, apellidos, cedula, telefono, id, pais from Cliente where apellidos =:apellidos", nativeQuery = true)
    List<Tuple> buscarPorApellidosNative(String apellidos);

    @Modifying
    @Query(value="update Cliente c set c.nombre =:nombre where c.apellidos =:apellidos")
    void updateClienteByQuery(String nombre,String apellidos);
}
