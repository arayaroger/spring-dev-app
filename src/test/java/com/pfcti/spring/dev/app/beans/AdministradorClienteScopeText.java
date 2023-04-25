package com.pfcti.spring.dev.app.beans;


import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdministradorClienteScopeText {

    @Autowired
    @Qualifier("defaultNombres")
    private AdministradorClientes instancia1;

    @Autowired
    @Qualifier("defaultNombres")
    private AdministradorClientes instancia2;

    @Autowired
    @Qualifier("defaultNombresSingleton")
    private AdministradorClientes instancia3;

    @Autowired
    @Qualifier("defaultNombresSingleton")
    private AdministradorClientes instancia4;

    @Test
    void instances(){
        System.out.println("Prototipo: " + instancia1);
        System.out.println("Prototipo: " + instancia2);
        System.out.println("Singleton: " + instancia3);
        System.out.println("Singleton: " + instancia4);

        assertTrue(true);
    }

}
