/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Esta clase representa los insumos disponibles en el taller. Cada insumo tiene
 * una descripción, un proveedor asociado y una cantidad en stock.
 *
 * @author Rafael Cosmo
 */
public class Insumo {

    /**
     * El proveedor que suministra este insumo.
     */
    private Proveedor proveedor;

    /**
     * Descripción del insumo (por ejemplo: "Aceite 10W-30").
     */
    private String descripcion;

    /**
     * Cantidad disponible del insumo en inventario.
     */
    private int cantidad;

    /**
     * Constructor básico que recibe la descripción y el proveedor del insumo.
     * La cantidad inicial se establece en 0.
     *
     * @param descripcion descripción del insumo
     * @param proveedor proveedor asociado al insumo
     */
    public Insumo(String descripcion, Proveedor proveedor) {
        this.descripcion = descripcion;
        this.cantidad = 0;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema.
     */
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
