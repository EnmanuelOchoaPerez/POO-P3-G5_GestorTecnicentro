package poo.par3g5.gestortecnicentro;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import modelo.Proveedor;


public class FormularioNuevoProveedor extends AppCompatActivity {

    EditText etIdProveedor, etTelefonoProveedor, etNombreProveedor, etDescripcionProveedor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_nuevo_proveedor);

        etIdProveedor = findViewById(R.id.nuevoIdProveedor);
        etTelefonoProveedor = findViewById(R.id.nuevoTelefonoProveedor);
        etNombreProveedor = findViewById(R.id.nuevoNombreProveedor);
        etDescripcionProveedor = findViewById(R.id.nuevaDescripcionProveedor);


    }


    //Metodo del boton guardar
    public void GuardarNuevoProveedor(View v) {
        String id = etIdProveedor.getText().toString();
        String telefono = etTelefonoProveedor.getText().toString();
        String nombre = etNombreProveedor.getText().toString();
        String descripcion = etDescripcionProveedor.getText().toString();


        //validar que los campos estén llenos
        if (id.isEmpty() || telefono.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto tipo Proveedor
        Proveedor nuevoProveedor = new Proveedor(
                id,
                telefono,
                nombre,
                descripcion
        );

        // Guardar en la lista del AdministrarProveedores usando el repositorio interno
        boolean agregar = AdministrarProveedores.ProveedoresRepository
                .getInstance()
                .agregarProveedor(nuevoProveedor);

        if (!agregar) {
            Toast.makeText(this, "El proveedor con ese ID ya existe", Toast.LENGTH_SHORT).show();
        } else {
            finish(); // Cierra el formulario y vuelve atrás
        }
    }
}