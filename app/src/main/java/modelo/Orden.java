/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Esta clase representa una orden de servicio en el taller. Contiene toda la información
 * necesaria para registrar y calcular el trabajo realizado: cliente, técnico, fecha,
 * vehículo, lista de servicios y el total de la orden.
 * 
 * @author Rafael Cosmo
 */
public class Orden {

    /**
     * Atributos de la orden:
     * - tecnico: técnico encargado de realizar el servicio.
     * - cliente: cliente que solicitó el servicio.
     * - fechaServicio: fecha en la que se realiza el servicio.
     * - vehiculo: vehículo que recibió el servicio.
     * - servicios: servicios realizados y la cantidad correspondiente.
     * - total: monto total de la orden calculado con base en servicios.
     */
    private Tecnico tecnico;
    private Cliente cliente;
    private LocalDate fechaServicio;
    private Vehiculo vehiculo;
    private ArrayList<DetalleServicio> servicios;
    private double total;

    /**
     * Constructor principal que inicializa todos los datos de la orden,
     * calcula el total y actualiza la ganancia del técnico asignado.
     *
     * @param cliente 
     * @param tecnico 
     * @param fecha 
     * @param vehiculo 
     * @param servicios 
     */
    public Orden(Cliente cliente, Tecnico tecnico, LocalDate fecha, Vehiculo vehiculo, ArrayList<DetalleServicio> servicios) {
        this.tecnico = tecnico;
        this.cliente = cliente;
        this.fechaServicio = fecha;
        this.vehiculo = vehiculo;
        this.servicios = servicios;
        this.total = this.calcularTotal();
        this.tecnico.setGanancia(total);
    }

    /**
     * Método privado que calcula el total de la orden sumando
     * los subtotales de todos los servicios realizados.
     * Este metodo esta destinado a ser usado unicamente en el constructor.
     * 
     * @return total acumulado de los servicios
     */
    private double calcularTotal() {
        double total = 0;
        for (DetalleServicio s : servicios) {
            total += s.calcularSubtotal();
        }
        return total;
    }

    /**
     * Getters y Setters necesarios para la clase en el sistema.
     *
     * @return los datos correspondientes a cliente, técnico, fecha, vehículo, servicios y total
     */
    public Cliente getCliente() {
        return cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public double getTotal() {
        return total;
    }

    public LocalDate getFechaServicio() {
        return fechaServicio;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public ArrayList<DetalleServicio> getServicios() {
        return servicios;
    }
}