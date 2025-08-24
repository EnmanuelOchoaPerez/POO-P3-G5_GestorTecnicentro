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


import modelo.Proveedor;
import modelo.RepositorioPruebaAvance;


public class AdministrarProveedores extends AppCompatActivity {
    public void mostrarFormularioProveedor(View v){
        Intent intent = new Intent(this, FormularioNuevoProveedor.class);
        startActivity(intent);
    }
    protected void onResume() {
        super.onResume();
        rvProveedores.getAdapter().notifyDataSetChanged();
    }
    ArrayList<Proveedor> proveedores;
    RecyclerView rvProveedores;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_proveedores);

        //Inicializar lista de proveedores desde el repositorio
        proveedores = RepositorioPruebaAvance.getInstance().getProveedores();

        //configuración del recycler
        rvProveedores =findViewById(R.id.rvProveedores);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rvProveedores.setLayoutManager(linearLayoutManager);
        rvProveedores.setAdapter(new AdministrarProveedores.AdaptadorProveedores());



    }

    //clase interna adapter
    private class AdaptadorProveedores extends RecyclerView.Adapter<AdministrarProveedores.AdaptadorProveedores.AdaptadorProveedoresHolder> {
        TextView tvIdProveedor, tvNombreProveedor,  tvTelefonoProveedor, tvDescripcionProveedor;


        @NonNull
        @Override
        public AdministrarProveedores.AdaptadorProveedores.AdaptadorProveedoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdministrarProveedores.AdaptadorProveedores.AdaptadorProveedoresHolder(getLayoutInflater().inflate(R.layout.item_proveedor, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdministrarProveedores.AdaptadorProveedores.AdaptadorProveedoresHolder holder, int position) {
            holder.imprimirProveedores(position);

        }

        @Override
        public int getItemCount() {
            return proveedores.size();
        }

        private class AdaptadorProveedoresHolder extends RecyclerView.ViewHolder{
            public AdaptadorProveedoresHolder(@NonNull View itemView) {
                super(itemView);
                tvIdProveedor = itemView.findViewById(R.id.tvIdProveedor);
                tvTelefonoProveedor = itemView.findViewById(R.id.tvTelefonoProveedor);
                tvNombreProveedor = itemView.findViewById(R.id.tvNombreProveedor);
                tvDescripcionProveedor = itemView.findViewById(R.id.tvDescripcionProveedor);



            }

            public void imprimirProveedores(int position) {
                Proveedor proveedor = proveedores.get(position);
                tvIdProveedor.setText(String.valueOf(proveedor.getId()));
                tvTelefonoProveedor.setText(proveedor.getTelefono());
                tvNombreProveedor.setText(proveedor.getUsername());
                tvDescripcionProveedor.setText(proveedor.getDescripcion());


            }
        }
    }
}