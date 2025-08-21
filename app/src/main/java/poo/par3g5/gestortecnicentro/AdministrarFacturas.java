package poo.par3g5.gestortecnicentro;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import modelo.Cliente;
import modelo.Factura;
import modelo.RepositorioPruebaAvance;

public class AdministrarFacturas extends AppCompatActivity {

    private List<Factura> facturas;
    private RecyclerView rvFacturas;

    public void mostrarFormularioCliente(View v) {
        Intent intento = new Intent(this, FormularioNuevoCliente.class);
        startActivity(intento);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rvFacturas.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_facturas);

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
            // Crear CardView
            CardView cardView = new CardView(parent.getContext());
            int margin = (int) (8 * parent.getContext().getResources().getDisplayMetrics().density);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(margin, margin, margin, margin);
            cardView.setLayoutParams(layoutParams);
            cardView.setRadius(16); // Esquinas redondeadas
            cardView.setCardElevation(8); // Sombra

            // Crear LinearLayout como contenido del CardView
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            int paddingPx = (int) (16 * parent.getContext().getResources().getDisplayMetrics().density);
            layout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

            // Crear TextViews con márgenes
            LinearLayout.LayoutParams paramsMargen = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsMargen.setMargins(0, 0, 0, (int) (8 * parent.getContext().getResources().getDisplayMetrics().density));

            TextView tvFecha = new TextView(parent.getContext());
            tvFecha.setLayoutParams(paramsMargen);

            TextView tvCliente = new TextView(parent.getContext());
            tvCliente.setLayoutParams(paramsMargen);

            TextView tvTotal = new TextView(parent.getContext());
            tvTotal.setLayoutParams(paramsMargen);

            TextView tvOrdenes = new TextView(parent.getContext());
            tvOrdenes.setLayoutParams(paramsMargen);

            // Añadir TextViews al layout
            layout.addView(tvFecha);
            layout.addView(tvCliente);
            layout.addView(tvTotal);
            layout.addView(tvOrdenes);

            // Añadir layout al CardView
            cardView.addView(layout);

            return new AdaptadorFacturaHolder(cardView, tvFecha, tvCliente, tvTotal, tvOrdenes);
        }


        @Override
        public void onBindViewHolder(@NonNull AdaptadorFacturaHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return facturas.size();
        }

        // --- ViewHolder ---
        class AdaptadorFacturaHolder extends RecyclerView.ViewHolder {
            TextView tvFecha, tvCliente, tvTotal, tvOrdenes;

            public AdaptadorFacturaHolder(@NonNull View itemView, TextView tvFecha, TextView tvCliente, TextView tvTotal, TextView tvOrdenes) {
                super(itemView);
                this.tvFecha = tvFecha;
                this.tvCliente = tvCliente;
                this.tvTotal = tvTotal;
                this.tvOrdenes = tvOrdenes;
            }

            public void imprimir(int position) {
                Factura factura = facturas.get(position);
                Cliente cliente = factura.getCliente();

                tvFecha.setText("Fecha: " + factura.getFecha().toString());
                tvCliente.setText("Cliente: " + cliente.getUsername() + " (" + cliente.getId() + ")");
                tvTotal.setText("Total: $" + factura.getTotal());
                tvOrdenes.setText("Órdenes: " + factura.getOrdenes().size());
            }
        }
    }
}