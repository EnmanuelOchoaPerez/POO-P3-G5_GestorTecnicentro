/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Esta clase es la encargada de guardar los datos de los vehiculos con los que
 * se han trabajado.
 *
 * @author Rafael Cosmo
 */
public class Vehiculo {

    /**
     * Estos atributos son los datos que se guardan en esta clase, representan
     * la placa que es unica para cada vehiculo y un enum que representa si el
     * vehiculo es un auto, una moto, o un autobus.
     */
    private String placa;
    private TipoVehiculo tipo;

    /**
     * Constructor de la clase que ademas asegura que todas las letras de la
     * placa deben estar en mayusculas.
     *
     * @param placa
     * @param tipo
     */
    public Vehiculo(String placa, TipoVehiculo tipo) {
        this.placa = placa.toUpperCase();
        this.tipo = tipo;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema.
     */
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }

}
