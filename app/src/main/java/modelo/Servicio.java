/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 * Esta clase se encarga de guardar los datos necesarios de los servicios
 * disponibles.
 *
 * @author Rafael Cosmo
 */
public class Servicio {

    /**
     * Estos datos son los datos que representan a los servicios del sistema. el
     * codio es unico y esta dictaminado por el contador que lleva una cuenta de
     * todos los servicios del sistema desde el primero. El nombre por el que
     * sera reconocido el servicio, el precio del mismo asi como su respectivo
     * regesitro que es un historial de precios. El recaudo representa el monto
     * total que se ha recaudado en el taller con cada servicio.
     *
     */
    private static int contadorId = 1;
    private int codigo;
    private String nombre;
    private double precio;
    private double recaudo;

    /**
     * Este constructor de la clase recibe unicamente el nombre, el precio
     * inicial y la fecha en que fue creado el servicio, se asegura de agregar
     * esta primera informacion en el historial al momento de ser creado el
     * servicio.
     *
     * @param nombre
     * @param precio
     * @param fecha
     */
    public Servicio(String nombre, double precio, LocalDate fecha) {
        this.codigo = contadorId++;
        this.nombre = nombre;
        this.precio = precio;
        this.recaudo = 0;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema
     */
    public double getRecaudo() {
        return recaudo;
    }

    public void setRecaudo(double recaudo) {
        this.recaudo += recaudo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    /**
     * Este metodo setter tambien se encarga de registrar los cambios en el
     * historial por lo que tambien recibe una fecha.
     *
     * @param precio
     * @param fecha
     */
    public void setPrecio(double precio, LocalDate fecha) {
        this.precio = precio;
    }

    /**
     * Este metodo es para tener los datos mas relevantes que posteriormente
     * seran mostrados por consola.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo de servicio : ");
        sb.append("").append(codigo);
        sb.append(", ").append(nombre);
        sb.append(", $").append(precio);
        return sb.toString();
    }
}
