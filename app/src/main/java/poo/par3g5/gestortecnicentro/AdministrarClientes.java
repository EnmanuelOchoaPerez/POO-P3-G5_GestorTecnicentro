package poo.par3g5.gestortecnicentro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.RepositorioPruebaAvance;
import modelo.TipoCliente;


public class AdministrarClientes extends AppCompatActivity {
    ArrayList<Cliente> clientes;
    RecyclerView rvClientes;
    public void mostrarFormularioCliente(View v){
        Intent intento = new Intent(this, FormularioNuevoCliente.class);
        startActivity(intento);
    }
    protected void onResume() {
        super.onResume();
        rvClientes.getAdapter().notifyDataSetChanged();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_clientes);

        // Obtener clientes desde el repositorio global
        clientes = RepositorioPruebaAvance.getInstance().getClientes();

        // Configurar el RecyclerView
        rvClientes = findViewById(R.id.rvClientes);
        rvClientes.setLayoutManager(new LinearLayoutManager(this));
        rvClientes.setAdapter(new AdministrarClientes.AdaptadorClientes());
    }

    //clase interna adapter
    private class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.AdaptadorClientesHolder> {
        TextView tvIdCliente, tvNombreCliente, tvDireccionCliente, tvTelefonoCliente, tvTipoCliente;


        @NonNull
        @Override
        public AdaptadorClientesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorClientesHolder(getLayoutInflater().inflate(R.layout.item_cliente, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorClientesHolder holder, int position) {
            holder.imprimir(position);

        }

        @Override
        public int getItemCount() {
            return clientes.size();
        }

        //view holder
        private class AdaptadorClientesHolder extends RecyclerView.ViewHolder{
            public AdaptadorClientesHolder(@NonNull View itemView) {
                super(itemView);
                tvIdCliente = itemView.findViewById(R.id.tvIdCliente);
                tvTelefonoCliente = itemView.findViewById(R.id.tvTelefonoCliente);
                tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
                tvDireccionCliente = itemView.findViewById(R.id.tvDireccionCliente);
                tvTipoCliente = itemView.findViewById(R.id.tvTipoCliente);


            }

            public void imprimir(int position) {
                Cliente cliente = clientes.get(position);
                tvIdCliente.setText(String.valueOf(cliente.getId()));
                tvTelefonoCliente.setText(cliente.getTelefono());
                tvNombreCliente.setText(cliente.getUsername());
                tvDireccionCliente.setText(cliente.getDireccion());
                tvTipoCliente.setText(cliente.getTipoCliente().toString());

            }
        }
    }
}