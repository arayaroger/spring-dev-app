//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package io.spring.guides.gs_producing_web_service;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the io.spring.guides.gs_producing_web_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.spring.guides.gs_producing_web_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetClienteRequest }
     * 
     * @return
     *     the new instance of {@link GetClienteRequest }
     */
    public GetClienteRequest createGetClienteRequest() {
        return new GetClienteRequest();
    }

    /**
     * Create an instance of {@link GetClienteResponse }
     * 
     * @return
     *     the new instance of {@link GetClienteResponse }
     */
    public GetClienteResponse createGetClienteResponse() {
        return new GetClienteResponse();
    }

    /**
     * Create an instance of {@link Cliente }
     * 
     * @return
     *     the new instance of {@link Cliente }
     */
    public Cliente createCliente() {
        return new Cliente();
    }

}
