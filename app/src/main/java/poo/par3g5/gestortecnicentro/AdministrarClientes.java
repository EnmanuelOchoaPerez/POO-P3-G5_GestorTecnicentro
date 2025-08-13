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
import modelo.TipoCliente;


public class AdministrarClientes extends AppCompatActivity {
    public void mostrarFormularioCliente(View v){
        Intent intento = new Intent(this, FormularioNuevoCliente.class);
        startActivity(intento);
    }
    protected void onResume() {
        super.onResume();
        rvClientes.getAdapter().notifyDataSetChanged();
    }
    ArrayList<Cliente> clientes;
    RecyclerView rvClientes;

    // Clase interna Singleton
    public static class ClienteRepository {
        private static ClienteRepository instance;
        private ArrayList<Cliente> clientes;

        private ClienteRepository() {
            clientes = new ArrayList<>();
            // datos de ejemplo
            clientes.add(new Cliente("0998", "09994", "Marcelo Castro", "Guayaquil", TipoCliente.PERSONAL));
            clientes.add(new Cliente("0102", "09876", "Ana Torres", "Quito", TipoCliente.EMPRESA));
            clientes.add(new Cliente("0234", "09543", "Luis Pérez", "Cuenca", TipoCliente.PERSONAL));
            clientes.add(new Cliente("0456", "09765", "María Gómez", "Ambato", TipoCliente.EMPRESA));
            clientes.add(new Cliente("0678", "09654", "Sofía Morales", "Manta", TipoCliente.PERSONAL));
        }

        public static ClienteRepository getInstance() {
            if (instance == null) {
                instance = new ClienteRepository();
            }
            return instance;
        }

        public ArrayList<Cliente> getClientes() {
            return clientes;
        }

        public boolean agregarCliente(Cliente cliente) {
            //validar si el cliente por agregar ya existe
            for (Cliente c : clientes) {
                if (c.getId().equals(cliente.getId())) {
                    return false; // No agregado porque ya existe
                }
            }
            clientes.add(cliente);
            return true; // Se agregó con éxito
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_clientes);

        //Inicializar lista de clientes desde el repositorio
        clientes = ClienteRepository.getInstance().getClientes();

        //configuración del recycler
        rvClientes =findViewById(R.id.rvClientes);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rvClientes.setLayoutManager(linearLayoutManager);
        rvClientes.setAdapter(new AdaptadorClientes());



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