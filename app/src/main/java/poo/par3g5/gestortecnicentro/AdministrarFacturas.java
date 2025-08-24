package poo.par3g5.gestortecnicentro;
import android.content.Context;
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

import modelo.Cliente;
import modelo.Factura;
import modelo.RepositorioPruebaAvance;

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

            public AdaptadorFacturaHolder(@NonNull View itemView) {
                super(itemView);
                cardContent = itemView.findViewById(R.id.cardContent);
            }

            public void imprimir(int position) {
                // Limpiar vistas anteriores
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

                // Crear y agregar TextViews dinámicamente
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
                tvOrdenes.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                // Agregar al layout del CardView
                cardContent.addView(tvFecha);
                cardContent.addView(tvCliente);
                cardContent.addView(tvTotal);
                cardContent.addView(tvOrdenes);
            }
        }
    }
}