/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Esta clase es la clase padre de todas demas clases que representan personas
 * en la aplicacion, tales como; Clientes, Tecnicos y Proveedores. Esta clase no
 * puede y no debe ser instanciada directamente.
 *
 * @author Rafael Cosmo
 */
public abstract class Persona {

    /**
     * Estos atributos son los datos que se guardan de cada persona, el id
     * deberia ser unico para cada uno, el telefono representa el numero de
     * contacto y el username representa el nombre de la persona.
     */
    protected String id;
    protected String telefono;
    protected String username;

    /**
     * Este constructor de la base es un constructor basico que recibe todos los
     * datos.
     *
     * @param id
     * @param telefono
     * @param username
     */
    public Persona(String id, String telefono, String username) {
        this.id = id;
        this.telefono = telefono;
        this.username = username;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema.
     */
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Este metodo es para tener los datos mas relevantes que posteriormente
     * seran mostrados por consola, esta destinado a ser usado y sobreescrito
     * por las clases hijas de Persona.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(", ").append(username);
        sb.append(", ").append(telefono);
        return sb.toString();
    }

}
