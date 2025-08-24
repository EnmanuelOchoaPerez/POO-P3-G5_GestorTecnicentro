package poo.par3g5.gestortecnicentro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import modelo.RepositorioPruebaAvance;
import modelo.Tecnico;

public class AdministrarTecnicos extends AppCompatActivity {
        ArrayList<Tecnico> tecnicos;
        RecyclerView rvTecnicos;
    public void mostrarFormularioTecnico(View v){
        Intent intent = new Intent(this, FormularioNuevoTecnico.class);
        startActivity(intent);
    }
    protected void onResume() {
        super.onResume();
        rvTecnicos.getAdapter().notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_tecnicos);

        //Inicializar lista de proveedores desde el repositorio
        tecnicos = RepositorioPruebaAvance.getInstance().getTecnicos();

        //configuración del recycler
        rvTecnicos =findViewById(R.id.rvTecnicos);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rvTecnicos.setLayoutManager(linearLayoutManager);
        rvTecnicos.setAdapter(new AdministrarTecnicos.AdaptadorTecnicos());



    }

    //clase interna adapter
    private class AdaptadorTecnicos extends RecyclerView.Adapter<AdministrarTecnicos.AdaptadorTecnicos.AdaptadorTecnicosHolder> {
        TextView tvIdTecnico, tvNombreTecnico,  tvTelefonoTecnico, tvEspecialidadTecnico;


        @NonNull
        @Override
        public AdministrarTecnicos.AdaptadorTecnicos.AdaptadorTecnicosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdministrarTecnicos.AdaptadorTecnicos.AdaptadorTecnicosHolder(getLayoutInflater().inflate(R.layout.item_tecnico, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdministrarTecnicos.AdaptadorTecnicos.AdaptadorTecnicosHolder holder, int position) {
            holder.imprimirTecnicos(position);

        }

        @Override
        public int getItemCount() {
            return tecnicos.size();
        }

        private class AdaptadorTecnicosHolder extends RecyclerView.ViewHolder{
            TextView tvIdTecnico, tvNombreTecnico, tvTelefonoTecnico, tvEspecialidadTecnico;
            Button btnEliminar;  //  botón para eliminar técnico
            public AdaptadorTecnicosHolder(@NonNull View itemView) {
                super(itemView);
                tvIdTecnico = itemView.findViewById(R.id.tvIdTecnico);
                tvTelefonoTecnico = itemView.findViewById(R.id.tvTelefonoTecnico);
                tvNombreTecnico = itemView.findViewById(R.id.tvNombreTecnico);
                tvEspecialidadTecnico = itemView.findViewById(R.id.tvEspecialidadTecnico);
                btnEliminar = itemView.findViewById(R.id.btnEliminar);

                // configuración del botón eliminar
                btnEliminar.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mostrarConfirmacionEliminar(position);
                    }
                });


            }

            public void imprimirTecnicos(int position) {
                Tecnico tecnico = tecnicos.get(position);
                tvIdTecnico.setText(String.valueOf(tecnico.getId()));
                tvTelefonoTecnico.setText(tecnico.getTelefono());
                tvNombreTecnico.setText(tecnico.getUsername());
                tvEspecialidadTecnico.setText(tecnico.getEspecialidad());


            }
        }
        // Método para mostrar diálogo de confirmación antes de la eliminación
        private void mostrarConfirmacionEliminar(int position) {
            new AlertDialog.Builder(AdministrarTecnicos.this)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Está seguro que desea eliminar este técnico?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarTecnico(position);
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        // Método que elimina el técnico de la lista y actualiza el RecyclerView
        private void eliminarTecnico(int position) {
            tecnicos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tecnicos.size());
            Toast.makeText(AdministrarTecnicos.this, "Técnico eliminado", Toast.LENGTH_SHORT).show();
        }
    }
}