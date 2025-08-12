/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Esta clase hija de Persona representa a los proveedores del taller. Guarda
 * información de contacto y una descripción de los productos o servicios que
 * ofrecen.
 *
 * @author Rafael Cosmo
 */
public class Proveedor extends Persona {

    /**
     * Este atributo representa una breve descripción del proveedor, como el
     * tipo de suministros o servicios que ofrece.
     */
    private String descripcion;

    /**
     * Constructor que recibe todos los datos necesarios para crear un
     * proveedor, incluyendo los heredados y su descripción específica.
     *
     * @param id el identificador único del proveedor
     * @param contacto el número de contacto del proveedor
     * @param username el nombre del proveedor
     * @param descripcion descripción del proveedor o sus servicios
     */
    public Proveedor(String id, String contacto, String username, String descripcion) {
        super(id, contacto, username);
        this.descripcion = descripcion;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema.
     */
    public String getContacto() {
        return super.getTelefono();
    }

    public void setContacto(String contacto) {
        super.setTelefono(contacto);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Este método sobreescribe el método toString de la clase padre para
     * incluir también la descripción del proveedor.
     *
     * @return representación en texto del proveedor
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Proveedor : ");
        sb.append(super.toString());
        sb.append(", ").append(descripcion);
        return sb.toString();
    }
}
