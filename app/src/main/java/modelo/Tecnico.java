/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Esta clase hija de Persona representan a l@s que trabajan en el taller y
 * guarda los datos de interes correspondientes.
 *
 * @author Rafael Cosmo
 */
public class Tecnico extends Persona {

    /**
     * Estos atributos son los datos de interes especificios de esta clase
     * ademas de los heredados, representan el tipo de trabajo que se le da
     * mejor a cada tecnico y cuanto ha sido recaudado gracias a este.
     */
    private String especialidad;
    private double ganancia;

    /**
     * Este constructor de la clase es un constructor basico que invoca al
     * constructor de la clase padre para no repetir codigo y ademas fija el
     * valor inicial del atributo ganancia en 0 hasta que el tecnico haga su
     * primer trabajo.
     *
     * @param id
     * @param telefono
     * @param nombre
     * @param especialidad
     */
    public Tecnico(String id, String telefono, String nombre, String especialidad) {
        super(id, telefono, nombre);
        this.especialidad = especialidad;
        this.ganancia = 0;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema
     */
    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia += ganancia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Este metodo sobreescribe y usa el metodo de la clase padre para retornar
     * todos los datos necesarios del tecnico.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tecnico : ");
        sb.append(super.toString());
        sb.append(", ").append(especialidad);
        return sb.toString();
    }
}
