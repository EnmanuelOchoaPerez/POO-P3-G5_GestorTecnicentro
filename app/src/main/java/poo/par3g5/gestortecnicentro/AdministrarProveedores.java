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

    // Clase interna Singleton
    public static class ProveedoresRepository {
        private static AdministrarProveedores.ProveedoresRepository instance;
        private ArrayList<Proveedor> proveedores;

        private ProveedoresRepository() {
            proveedores = new ArrayList<>();
            // datos de ejemplo
            proveedores.add(new Proveedor("0936", "09852", "Aceites Lucía", "Aceites y lubricantes"));
            proveedores.add(new Proveedor("0937", "09853", "Frenos S.A.", "Repuestos y accesorios de frenos"));
            proveedores.add(new Proveedor("0938", "09854", "Motor Parts", "Partes de motor y transmisiones"));
            proveedores.add(new Proveedor("0939", "09855", "Luces LED", "Iluminación automotriz"));
            proveedores.add(new Proveedor("0940", "09856", "Neumáticos del Sur", "Llantas y neumáticos"));
        }

        public static AdministrarProveedores.ProveedoresRepository getInstance() {
            if (instance == null) {
                instance = new AdministrarProveedores.ProveedoresRepository();
            }
            return instance;
        }

        public ArrayList<Proveedor> getProveedores() {
            return  proveedores;
        }

        public boolean agregarProveedor(Proveedor proveedor) {
            //validar si el proveedor por agregar ya existe
            for (Proveedor p : proveedores) {
                if (p.getId().equals(proveedor.getId())) {
                    return false; // No agregado porque ya existe
                }
            }
            proveedores.add(proveedor);
            return true; // Se agregó con éxito
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_proveedores);

        //Inicializar lista de proveedores desde el repositorio
        proveedores = AdministrarProveedores.ProveedoresRepository.getInstance().getProveedores();

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