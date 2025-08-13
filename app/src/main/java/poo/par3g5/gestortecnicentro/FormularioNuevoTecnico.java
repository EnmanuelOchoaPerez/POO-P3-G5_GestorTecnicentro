package poo.par3g5.gestortecnicentro;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import modelo.Tecnico;

public class FormularioNuevoTecnico extends AppCompatActivity {

    EditText etIdTecnico, etTelefonoTecnico, etNombreTecnico, etEspecialidadTecnico;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_nuevo_tecnico);

        etIdTecnico = findViewById(R.id.nuevoIdTecnico);
        etTelefonoTecnico = findViewById(R.id.nuevoTelefonoTecnico);
        etNombreTecnico = findViewById(R.id.nuevoNombreTecnico);
        etEspecialidadTecnico = findViewById(R.id.nuevaEspecialidadTecnico);


    }


    //Metodo del boton guardar el nuevo Tecnico
    public void GuardarNuevoTecnico(View v) {
        String id = etIdTecnico.getText().toString();
        String telefono = etTelefonoTecnico.getText().toString();
        String nombre = etNombreTecnico.getText().toString();
        String especialidad = etEspecialidadTecnico.getText().toString();


        //validar que los campos estén llenos
        if (id.isEmpty() || telefono.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto tipo Tecnico
        Tecnico nuevoTecnico = new Tecnico(
                id,
                telefono,
                nombre,
                especialidad
        );

        // Guardar en la lista del AdministrarTecnicos usando el repositorio interno
        boolean agregar = AdministrarTecnicos.TecnicosRepository
                .getInstance()
                .agregarTecnico(nuevoTecnico);

        if (!agregar) {
            Toast.makeText(this, "El técnico con ese ID ya existe", Toast.LENGTH_SHORT).show();
        } else {
            finish(); // Cierra el formulario y vuelve atrás
        }
    }
}