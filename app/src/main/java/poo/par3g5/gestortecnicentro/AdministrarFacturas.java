package poo.par3g5.gestortecnicentro;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import modelo.Cliente;
import modelo.DetalleServicio;
import modelo.Factura;
import modelo.Orden;
import modelo.RepositorioPruebaAvance;
import modelo.Servicio;

public class AdministrarFacturas extends AppCompatActivity {

    private List<Factura> facturas;
    private RecyclerView rvFacturas;

    @Override
    protected void onResume() {
        super.onResume();
        rvFacturas.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_facturas);
        TextView titulo = findViewById(R.id.tvTextoEncabezado);
        titulo.setText("Administrar Facturas");

        // Obtener facturas desde el repositorio global
        facturas = RepositorioPruebaAvance.getInstance().getFacturas();

        // Configurar el RecyclerView
        rvFacturas = findViewById(R.id.rvFacturas);
        rvFacturas.setLayoutManager(new LinearLayoutManager(this));
        rvFacturas.setAdapter(new AdaptadorFactura());
    }

    // --- Adaptador de Facturas ---
    private class AdaptadorFactura extends RecyclerView.Adapter<AdaptadorFactura.AdaptadorFacturaHolder> {

        @NonNull
        @Override
        public AdaptadorFacturaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_base_cardview, parent, false);
            return new AdaptadorFacturaHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorFacturaHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return facturas.size();
        }

        // ---------------- VIEW HOLDER ----------------
        class AdaptadorFacturaHolder extends RecyclerView.ViewHolder {
            LinearLayout cardContent;
            boolean expandido = false;

            public AdaptadorFacturaHolder(@NonNull View itemView) {
                super(itemView);
                cardContent = itemView.findViewById(R.id.cardContent);

                // Manejar clic para expandir/colapsar
                itemView.setOnClickListener(v -> {
                    expandido = !expandido;
                    imprimir(getAdapterPosition());
                });
            }

            public void imprimir(int position) {
                cardContent.removeAllViews();
                Factura factura = facturas.get(position);
                Cliente cliente = factura.getCliente();
                Context context = itemView.getContext();
                float density = context.getResources().getDisplayMetrics().density;

                LinearLayout.LayoutParams paramsMargen = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                paramsMargen.setMargins(0, 0, 0, (int) (8 * density));

                // Cabecera compacta (siempre visible)
                TextView tvFecha = new TextView(context);
                tvFecha.setText("Fecha: " + factura.getFecha().toString());
                tvFecha.setLayoutParams(paramsMargen);

                TextView tvCliente = new TextView(context);
                tvCliente.setText("Cliente: " + cliente.getUsername() + " (" + cliente.getId() + ")");
                tvCliente.setLayoutParams(paramsMargen);

                TextView tvTotal = new TextView(context);
                tvTotal.setText("Total: $" + factura.getTotal());
                tvTotal.setLayoutParams(paramsMargen);

                TextView tvOrdenes = new TextView(context);
                tvOrdenes.setText("Órdenes: " + factura.getOrdenes().size());
                tvOrdenes.setLayoutParams(paramsMargen);

                cardContent.addView(tvFecha);
                cardContent.addView(tvCliente);
                cardContent.addView(tvTotal);
                cardContent.addView(tvOrdenes);

                // Si no está expandido, termina aquí
                if (!expandido) return;

                // --------------------------------------------
                // Mostrar detalles de la factura
                // --------------------------------------------

                // Encabezado de tabla
                LinearLayout encabezado = new LinearLayout(context);
                encabezado.setOrientation(LinearLayout.HORIZONTAL);
                encabezado.setLayoutParams(paramsMargen);

                encabezado.addView(crearCelda(context, "Servicio", 1f, true));
                encabezado.addView(crearCelda(context, "Precio", 1f, true));
                encabezado.addView(crearCelda(context, "Cantidad", 1f, true));
                encabezado.addView(crearCelda(context, "Subtotal", 1f, true));
                cardContent.addView(encabezado);

                for (Orden orden : factura.getOrdenes()) {
                    for (DetalleServicio ds : orden.getServicios()) {
                        Servicio servicio = ds.getServicio();
                        int cantidad = ds.getCantidad();
                        double precioUnitario = servicio.getPrecio();
                        double subtotal = cantidad * precioUnitario;

                        LinearLayout fila = new LinearLayout(context);
                        fila.setOrientation(LinearLayout.HORIZONTAL);
                        fila.setLayoutParams(paramsMargen);

                        fila.addView(crearCelda(context, servicio.getNombre(), 1f, false));
                        fila.addView(crearCelda(context, String.format(Locale.getDefault(), "$ %.2f", precioUnitario), 1f, false));
                        fila.addView(crearCelda(context, String.valueOf(cantidad), 1f, false));
                        fila.addView(crearCelda(context, String.format(Locale.getDefault(), "$ %.2f", subtotal), 1f, false));

                        cardContent.addView(fila);
                    }
                }

                // Línea separadora
                View separator = new View(context);
                separator.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (2 * density)
                ));
                separator.setBackgroundColor(Color.LTGRAY);
                cardContent.addView(separator);

                // Membresía mensual
                LinearLayout membresia = new LinearLayout(context);
                membresia.setOrientation(LinearLayout.HORIZONTAL);
                membresia.setLayoutParams(paramsMargen);
                membresia.addView(crearCelda(context, "Membresía mensual", 3f, true));
                membresia.addView(crearCelda(context, "$50.00", 1f, false));
                cardContent.addView(membresia);

                // Total final
                LinearLayout totalFinal = new LinearLayout(context);
                totalFinal.setOrientation(LinearLayout.HORIZONTAL);
                totalFinal.setLayoutParams(paramsMargen);
                totalFinal.addView(crearCelda(context, "Total", 3f, true));
                totalFinal.addView(crearCelda(context,
                        String.format(Locale.getDefault(), "$ %.2f", factura.getTotal()), 1f, false));
                cardContent.addView(totalFinal);
            }

            private TextView crearCelda(Context context, String texto, float peso, boolean negrita) {
                TextView tv = new TextView(context);
                tv.setText(texto);
                tv.setLayoutParams(new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, peso
                ));
                tv.setTextSize(14);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START); // Alineado a la izquierda
                tv.setPadding(8, 8, 8, 8);
                if (negrita) tv.setTypeface(null, Typeface.BOLD);
                return tv;
            }
        }
    }

    public void generarNuevaFactura(View view){
        startActivity(new Intent(this, FormularioNuevaFactura.class));
    }
}