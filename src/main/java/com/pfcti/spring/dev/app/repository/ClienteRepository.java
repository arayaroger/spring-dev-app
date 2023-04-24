package com.pfcti.spring.dev.app.repository;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>, JpaSpecificationExecutor<Cliente> {
    List<Cliente> findClientesByPaisAndCuentas_ActivaIsTrue(String pais);

//    @Query(value="select c1_0.id,c1_0.nombre,c1_0.apellidos,c1_0.cedula,c1_0.telefono,c1_0.pais from Cliente c1_0 left join Tarjeta c2_0 on c1_0.id=c2_0.cliente.id where c1_0.pais<>:paisNacional and not c2_0.activa")
//    List<Cliente> buscarClientesExtranjerosPorEstadoTarjeta(String paisNacional);

    List<Cliente> findClientesByPaisNotAndTarjetas_ActivaIsFalse(String pais);

    @Query(value="select c1_0.id,c1_0.nombre,c1_0.apellidos,c1_0.cedula,c1_0.telefono,c1_0.pais from Cliente c1_0 left join Tarjeta c2_0 on c1_0.id=c2_0.cliente_id where c1_0.pais!=:paisNacional and c2_0.activa = false",nativeQuery = true)
    List<Cliente> buscarClientesExtranjerosPorEstadoTarjeta(String paisNacional);

    @Query(value = "select c from Cliente c where c.apellidos = :apellidos")
    List<Cliente> buscarPorApellidos(String apellidos);

    List<Cliente> findClientesByApellidos(String apellidos);
    @Query(value = "select nombre, apellidos, cedula, telefono, id, pais from Cliente where apellidos =:apellidos", nativeQuery = true)
    List<Tuple> buscarPorApellidosNative(String apellidos);

    @Modifying
    @Query(value="update Cliente c set c.nombre =:nombre where c.apellidos =:apellidos")
    void updateClienteByQuery(String nombre,String apellidos);

    List<Cliente> findClientesByApellidosAndNombre(String apellidos, String nombre);

    List<Cliente> findClientesByCedula(String cedula);

    List<Cliente> findClientesByNombreOrApellidos(String nombres, String apellidos);

    List<Cliente> findClientesByNombreContainingIgnoreCaseOrNombreContainingIgnoreCase(String nombres, String apellidos);




}
