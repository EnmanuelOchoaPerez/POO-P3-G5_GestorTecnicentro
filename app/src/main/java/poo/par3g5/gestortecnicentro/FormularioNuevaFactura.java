package poo.par3g5.gestortecnicentro;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import modelo.Cliente;
import modelo.DetalleServicio;
import modelo.Factura;
import modelo.Orden;
import modelo.RepositorioPruebaAvance;
import modelo.Servicio;

public class FormularioNuevaFactura extends AppCompatActivity {

    Spinner spinnerMeses;
    EditText editTextAño;
    AutoCompleteTextView actvEmpresa;
    ArrayList<Cliente> empresas = RepositorioPruebaAvance.getInstance().getEmpresas();
    private Cliente empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nueva_factura);
        spinnerMeses = findViewById(R.id.inputMeses);
        editTextAño = findViewById(R.id.inputAño);
        actvEmpresa = findViewById(R.id.actvEmpresa);

        TextView tvEncabezado = findViewById(R.id.tvTextoEncabezado);
        if (tvEncabezado != null) {
            tvEncabezado.setText("Formulario para nueva factura");
        }

        String[] mesesArray = DateFormatSymbols.getInstance().getMonths();

        List<String> meses = new ArrayList<>();
        meses.add("Escoja un mes");
        for (int i = 0; i < 12; i++) {
            meses.add(mesesArray[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                meses
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(adapter);

        ArrayAdapter<Cliente> actvAdapter = new ArrayAdapter<Cliente>(
                this, android.R.layout.simple_dropdown_item_1line, empresas) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view.findViewById(android.R.id.text1))
                        .setText(getItem(position).getUsername());
                return view;
            }
        };
        actvEmpresa.setOnClickListener(v -> actvEmpresa.showDropDown()); // Mostrar sugerencias al hacer clic
        actvEmpresa.setAdapter(actvAdapter);
        actvEmpresa.setOnItemClickListener((parent, view, position, id) -> {
            empresa = (Cliente) parent.getItemAtPosition(position);
            actvEmpresa.setText(empresa.getUsername());

        });
    }

    public void GenerarFactura(View v) {
        try {
            String año = editTextAño.getText().toString().trim();
            String mes = spinnerMeses.getSelectedItem().toString();
            String empresaNombre = actvEmpresa.getText().toString().trim();

            if (año.isEmpty() || mes.equals("Escoja un mes") || empresaNombre.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!año.matches("\\d{4}")) {
                Toast.makeText(this, "Ingrese un año válido de 4 dígitos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                Toast.makeText(this, "Versión de Android no soportada", Toast.LENGTH_SHORT).show();
                return;
            }

            empresa = empresa != null && empresa.getUsername().equalsIgnoreCase(empresaNombre)
                    ? empresa
                    : empresas.stream()
                    .filter(c -> c.getUsername().equalsIgnoreCase(empresaNombre))
                    .findFirst()
                    .orElse(null);

            if (empresa == null) {
                Toast.makeText(this, "Empresa no válida", Toast.LENGTH_SHORT).show();
                return;
            }

            Locale locale = Locale.getDefault();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale);
            YearMonth selectedYM = YearMonth.parse(mes + " " + año, formatter);

            LinearLayout contenedor = findViewById(R.id.contenedorReporte);
            contenedor.removeAllViews(); // Limpiar resultados previos

            boolean hayResultados = false;

            // Obtener las órdenes que coincidan
            List<Orden> ordenesFiltradas = RepositorioPruebaAvance.getInstance().getOrdenes().stream()
                    .filter(o -> YearMonth.from(o.getFechaServicio()).equals(selectedYM)
                            && o.getCliente().getId().equalsIgnoreCase(empresa.getId()))
                    .collect(Collectors.toList());

            if (ordenesFiltradas.isEmpty()) {
                TextView sinResultados = new TextView(this);
                sinResultados.setText("No se encontraron servicios para ese mes y año.");
                sinResultados.setTextColor(Color.DKGRAY);
                sinResultados.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                contenedor.addView(sinResultados);
                return;
            }

            hayResultados = true;

            for (Orden orden : ordenesFiltradas) {
                // Inflar cardView para cada orden
                View cardView = getLayoutInflater().inflate(R.layout.item_base_cardview, null);
                LinearLayout cardContent = cardView.findViewById(R.id.cardContent);

                // 1. Encabezado con columnas
                LinearLayout header = new LinearLayout(this);
                header.setOrientation(LinearLayout.HORIZONTAL);
                header.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                String[] titulos = {"Servicio", "Precio", "Cantidad", "Subtotal"};
                for (String titulo : titulos) {
                    TextView tv = new TextView(this);
                    tv.setText(titulo);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    header.addView(tv);
                }
                cardContent.addView(header);

                // Separador debajo del header
                View separatorHeader = new View(this);
                LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())
                );
                sepParams.setMargins(0, 8, 0, 8);
                separatorHeader.setLayoutParams(sepParams);
                separatorHeader.setBackgroundColor(Color.DKGRAY);
                cardContent.addView(separatorHeader);

                // 2. Filas de servicios con datos
                for (DetalleServicio ds : orden.getServicios()) {
                    Servicio servicio = ds.getServicio();
                    int cantidad = ds.getCantidad();
                    double precioUnitario = servicio.getPrecio();
                    double subtotal = precioUnitario * cantidad;

                    LinearLayout fila = new LinearLayout(this);
                    fila.setOrientation(LinearLayout.HORIZONTAL);
                    fila.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    // Servicio (nombre)
                    TextView tvServicio = new TextView(this);
                    tvServicio.setText(servicio.getNombre());
                    tvServicio.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    // Precio unitario
                    TextView tvPrecio = new TextView(this);
                    tvPrecio.setText(String.format(Locale.getDefault(), "$ %.2f", precioUnitario));
                    tvPrecio.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tvPrecio.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    // Cantidad
                    TextView tvCantidad = new TextView(this);
                    tvCantidad.setText(String.valueOf(cantidad));
                    tvCantidad.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tvCantidad.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    // Subtotal
                    TextView tvSubtotal = new TextView(this);
                    tvSubtotal.setText(String.format(Locale.getDefault(), "$ %.2f", subtotal));
                    tvSubtotal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tvSubtotal.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    fila.addView(tvServicio);
                    fila.addView(tvPrecio);
                    fila.addView(tvCantidad);
                    fila.addView(tvSubtotal);

                    cardContent.addView(fila);
                }

                // Separador debajo de servicios
                View separatorServicios = new View(this);
                LinearLayout.LayoutParams sepServiciosParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())
                );
                sepServiciosParams.setMargins(0, 12, 0, 12);
                separatorServicios.setLayoutParams(sepServiciosParams);
                separatorServicios.setBackgroundColor(Color.LTGRAY);
                cardContent.addView(separatorServicios);

                // 3. Membresía mensual (fijo $50.0)
                LinearLayout filaMembresia = new LinearLayout(this);
                filaMembresia.setOrientation(LinearLayout.HORIZONTAL);
                filaMembresia.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView tvMembresiaLabel = new TextView(this);
                tvMembresiaLabel.setText("Membresía mensual");
                tvMembresiaLabel.setTypeface(null, Typeface.BOLD);
                tvMembresiaLabel.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));

                TextView tvMembresiaPrecio = new TextView(this);
                double costoMembresia = 50.0; // fijo
                tvMembresiaPrecio.setText(String.format(Locale.getDefault(), "$ %.2f", costoMembresia));
                tvMembresiaPrecio.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                tvMembresiaPrecio.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                filaMembresia.addView(tvMembresiaLabel);
                filaMembresia.addView(tvMembresiaPrecio);
                cardContent.addView(filaMembresia);

                // 4. Total factura (incluye membresía)
                LinearLayout filaTotal = new LinearLayout(this);
                filaTotal.setOrientation(LinearLayout.HORIZONTAL);
                filaTotal.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView tvTotalLabel = new TextView(this);
                tvTotalLabel.setText("Total");
                tvTotalLabel.setTypeface(null, Typeface.BOLD);
                tvTotalLabel.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));

                // Obtener factura para la orden y cliente
                Factura factura = RepositorioPruebaAvance.getInstance().generarFactura(empresa.getId(), selectedYM);

                TextView tvTotalPrecio = new TextView(this);
                tvTotalPrecio.setText(String.format(Locale.getDefault(), "$ %.2f", factura.getTotal()));
                tvTotalPrecio.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                tvTotalPrecio.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                filaTotal.addView(tvTotalLabel);
                filaTotal.addView(tvTotalPrecio);
                cardContent.addView(filaTotal);

                // Agregar CardView al contenedor
                contenedor.addView(cardView);
            }

            // Guardar factura (opcional, aquí se podría adaptar si quieres guardar solo una vez o por cada orden)
            RepositorioPruebaAvance.getInstance().agregarFactura(
                    RepositorioPruebaAvance.getInstance().generarFactura(empresa.getId(), selectedYM)
            );
            RepositorioPruebaAvance.getInstance().guardarFacturasEnArchivo();

        } catch (Exception e) {
            Toast.makeText(this, "Error al generar factura: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
