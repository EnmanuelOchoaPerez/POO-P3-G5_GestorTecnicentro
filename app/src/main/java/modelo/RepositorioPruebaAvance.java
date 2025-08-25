package modelo;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepositorioPruebaAvance implements Serializable {
    private static RepositorioPruebaAvance instance;

    private ArrayList<Cliente> clientes;
    private ArrayList<Tecnico> tecnicos;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Servicio> servicios;
    private ArrayList<Orden> ordenes;

    private ArrayList<Factura> facturas;

    @SuppressLint("NewApi")
    private RepositorioPruebaAvance() {
        clientes = new ArrayList<>();
        tecnicos = new ArrayList<>();
        proveedores = new ArrayList<>();
        servicios = new ArrayList<>();
        ordenes = new ArrayList<>();
        facturas = new ArrayList<>();

        // --- CLIENTES ---
        clientes.add(new Cliente("0824937125", "0994871623", "Juan Pérez", "Calle Los Álamos  N45-180, La Floresta, Guayas", TipoCliente.PERSONAL));
        clientes.add(new Cliente("0637592816001", "0912748690", "Empresa XYZ", "Calle 12 de Octubre E6-320, Edificio Torre Azul, Guayas", TipoCliente.EMPRESA));
        clientes.add(new Cliente("0741068357", "0931820475", "María Gómez", "Av. El Inca Oe7-221, Urbanización Naranjos, Guayas", TipoCliente.PERSONAL));
        clientes.add(new Cliente("0519283742001", "0975086214", "Constructora Delta", "Pasaje San Martín S3-115, Pinar Alto, Guayas", TipoCliente.EMPRESA));
        clientes.add(new Cliente("0927593816001", "0975086214", "Grupo HG S.A", "Calle Robles N12-45, Urbanización La Colina, Guayas", TipoCliente.EMPRESA));

        // --- SERVICIOS --
        servicios.add(new Servicio("Cambio de aceite", 50.0));
        servicios.add(new Servicio("Cambio de llantas", 20.0));
        servicios.add(new Servicio("Alineación y balanceo", 35.0));
        servicios.add(new Servicio("Lavado de motor en seco", 30.0));
        servicios.add(new Servicio("Revisión de frenos", 45.0));
        servicios.add(new Servicio("Diagnóstico electrónico", 60.0));
        guardarServiciosEnArchivo();

        // --- TÉCNICOS ---
        tecnicos.add(new Tecnico("0458926173", "0967812049", "Carlos López", "Mecánico general"));
        tecnicos.add(new Tecnico("0367819402", "0924031687", "Andrea Ramírez", "Especialista en suspensión"));

        // --- PROVEEDORES ---
        proveedores.add(new Proveedor("0741067125001", "0954728136", "Filtros y Accesorios", "AutoFrenos Quito"));
        proveedores.add(new Proveedor("0824992816001", "0973461820", "Distribuidora Eléctrica", "TecnoVolt Guayaquil"));

        // --- ÓRDENES ---
        ArrayList<DetalleServicio> detalles = new ArrayList<>();
        detalles.add(new DetalleServicio(servicios.get(1), 4));
        detalles.add(new DetalleServicio(servicios.get(0), 3));
        detalles.add(new DetalleServicio(servicios.get(3), 1));
        ordenes.add(new Orden(
                getClienteById("0927593816001"),
                getTecnicoById("0367819402"),
                LocalDate.of(2025, 5, 14),
                new Vehiculo("GUB-1603", TipoVehiculo.BUS),
                new ArrayList<>(detalles)));
        detalles.clear();
        detalles.add(new DetalleServicio(servicios.get(1), 1));
        detalles.add(new DetalleServicio(servicios.get(0), 1));
        ordenes.add(new Orden(
                getClienteById("0824937125"),
                getTecnicoById("0367819402"),
                LocalDate.of(2025, 1, 20),
                new Vehiculo("Gk195P", TipoVehiculo.MOTOCICLETA),
                new ArrayList<>(detalles)));
        detalles.clear();
        detalles.add(new DetalleServicio(servicios.get(4), 2));
        detalles.add(new DetalleServicio(servicios.get(0), 2));
        detalles.add(new DetalleServicio(servicios.get(3), 1));
        ordenes.add(new Orden(
                getClienteById("0741068357"),
                getTecnicoById("0458926173"),
                LocalDate.of(2025, 3, 5),
                new Vehiculo("Ghc-1856", TipoVehiculo.AUTO),
                new ArrayList<>(detalles)));
        detalles.clear();
        detalles.add(new DetalleServicio(servicios.get(5), 2));
        detalles.add(new DetalleServicio(servicios.get(0), 1));
        detalles.add(new DetalleServicio(servicios.get(1), 2));
        ordenes.add(new Orden(
                getClienteById("0637592816001"),
                getTecnicoById("0458926173"),
                LocalDate.of(2025, 7, 24),
                new Vehiculo("Gpo575l", TipoVehiculo.MOTOCICLETA),
                new ArrayList<>(detalles)));
        guardarOrdenesEnArchivo();

        facturas.clear(); // Limpia cualquier factura anterior
        facturas.add(generarFactura("0927593816001", YearMonth.parse("2025-05")));
        guardarFacturasEnArchivo();
    }

    public static RepositorioPruebaAvance getInstance() {
        if (instance == null) {
            instance = new RepositorioPruebaAvance();
        }
        return instance;
    }

    // --- Getters públicos ---
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public ArrayList<Orden> getOrdenes() {
        return ordenes;
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    // --- Nuevos métodos de búsqueda estándar ---
    public Cliente getClienteById(String id) {
        return clientes.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Cliente> getEmpresas() {
        return (ArrayList<Cliente>) clientes.stream()
                .filter(c -> c.getTipoCliente().equals(TipoCliente.EMPRESA))
                .collect(Collectors.toList());  // Devuelve una lista de todas las empresas
    }


    public Tecnico getTecnicoById(String id) {
        return tecnicos.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public Servicio getServicioByIndex(int index) {
        return (index >= 0 && index < servicios.size()) ? servicios.get(index) : null;
    }

    public Proveedor getProveedorById(String id) {
        return proveedores.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    //Factura: generar, guardar y serializar
    public Factura generarFactura(String clienteId, YearMonth fecha) {
        Cliente cliente = getClienteById(clienteId);
        ArrayList<Orden> ordenesProcesadas = new ArrayList<>();

        if (cliente == null || cliente.getTipoCliente() != TipoCliente.EMPRESA) {
            return null;
        }
        for (Orden o : ordenes) {
            if (!ordenesProcesadas.contains(o) &&
                    o.getCliente().getId().equals(clienteId) &&
                    YearMonth.from(o.getFechaServicio()).equals(fecha)) {
                ordenesProcesadas.add(o);
            }
        }

        return ordenesProcesadas.isEmpty() ? null : new Factura(fecha, cliente, ordenesProcesadas);
    }

    public void guardarFacturasEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("facturas.ser"))) {
            oos.writeObject(facturas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarFactura(@NonNull Factura factura) {
        for (Factura f : facturas) {
            if (f.equals(factura)) {
                return;
            }
        }
        facturas.add(factura);
    }

    //Orden: serializar
    public void guardarOrdenesEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("facturas.ser"))) {
            oos.writeObject(ordenes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Servicio: serializar
    public void guardarServiciosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("facturas.ser"))) {
            oos.writeObject(servicios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Cliente agregar y serializar
    public boolean agregarCliente(@NonNull Cliente cliente) {
        if (getClienteById(cliente.getId()) == null) {
            clientes.add(cliente);
            guardarClientesEnArchivo(); // Serializar automáticamente
            return true;
        }
        return false;
    }

    public void guardarClientesEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clientes.ser"))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarClientesDesdeArchivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes.ser"))) {
            clientes = (ArrayList<Cliente>) ois.readObject();
        } catch (FileNotFoundException e) {
            clientes = new ArrayList<>(); // archivo no existe, se crea lista vacía
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //  Técnicos agregar y serializar
    public boolean agregarTecnico(@NonNull Tecnico tecnico) {
        if (getTecnicoById(tecnico.getId()) == null) {
            tecnicos.add(tecnico);
            guardarTecnicosEnArchivo();
            return true;
        }
        return false;
    }

    // serialización
    public void guardarTecnicosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tecnicos.ser"))) {
            oos.writeObject(tecnicos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // deserialización
    public void cargarTecnicosDesdeArchivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tecnicos.ser"))) {
            tecnicos = (ArrayList<Tecnico>) ois.readObject();
        } catch (FileNotFoundException e) {
            tecnicos = new ArrayList<>(); // archivo no existe, se crea lista vacía
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //eliminar Técnicos por posición
    public boolean eliminarTecnico(int position) {
        if (position >= 0 && position < tecnicos.size()) {
            tecnicos.remove(position);
            return true;
        }
        return false;
    }

    // --- Proveedores ---
    public boolean agregarProveedor(@NonNull Proveedor proveedor) {
        if (getProveedorById(proveedor.getId()) == null) {
            proveedores.add(proveedor);
            guardarProveedoresEnArchivo();
            return true;
        }
        return false;
    }

    // serialización
    public void guardarProveedoresEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("proveedores.ser"))) {
            oos.writeObject(proveedores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // deserialización
    public void cargarProveedoresDesdeArchivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("proveedores.ser"))) {
            proveedores = (ArrayList<Proveedor>) ois.readObject();
        } catch (FileNotFoundException e) {
            proveedores = new ArrayList<>(); // archivo no existe, se crea lista vacía
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

