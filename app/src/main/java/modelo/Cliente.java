/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 * Esta clase hija de Persona representa a los clientes del taller. Almacena
 * datos específicos como la dirección y el tipo de cliente.
 *
 * @author Rafael Cosmo
 */
public class Cliente extends Persona implements Serializable {

    /**
     * Representa el tipo de cliente (Personal, Empresa).
     */
    private TipoCliente tipoCliente;

    /**
     * Dirección física del cliente.
     */
    private String direccion;

    /**
     * Constructor que recibe todos los datos necesarios para crear un cliente,
     * incluyendo los heredados y sus atributos específicos.
     *
     * @param id identificador único del cliente
     * @param telefono número de contacto del cliente
     * @param nombre nombre del cliente
     * @param direccion dirección física del cliente
     * @param tipoCliente tipo o categoría del cliente
     */
    public Cliente(String id, String telefono, String nombre, String direccion, TipoCliente tipoCliente) {
        super(id, telefono, nombre);
        this.tipoCliente = tipoCliente;
        this.direccion = direccion;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema.
     */
    
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Este método sobreescribe y usa el método toString de la clase padre para
     * incluir la dirección y tipo del cliente.
     *
     * @return representación en texto del cliente
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente : ");
        sb.append(super.toString());
        sb.append(", ").append(direccion);
        sb.append(", ").append(tipoCliente);
        return sb.toString();
    }
}
