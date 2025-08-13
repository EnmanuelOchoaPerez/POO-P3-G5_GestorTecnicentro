package poo.par3g5.gestortecnicentro;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import modelo.Cliente;
import modelo.TipoCliente;

public class FormularioNuevoCliente extends AppCompatActivity {
    EditText etIdCliente, etTelefonoCliente, etNombreCliente, etDireccionCliente;
    Spinner spnuevoTipoCliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_nuevo_cliente);

        etIdCliente = findViewById(R.id.nuevoIdCliente);
        etTelefonoCliente = findViewById(R.id.nuevoTelefonoCliente);
        etNombreCliente = findViewById(R.id.nuevoNombreCliente);
        etDireccionCliente = findViewById(R.id.nuevaDireccionCliente);
        spnuevoTipoCliente = findViewById(R.id.spnuevoTipoCliente);

        //  Spinner con los tipos de cliente
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"PERSONAL", "EMPRESA"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnuevoTipoCliente.setAdapter(adapter);
    }


    //Metodo del boton guardar
    public void GuardarNuevoCliente(View v) {
        String id = etIdCliente.getText().toString();
        String telefono = etTelefonoCliente.getText().toString();
        String nombre = etNombreCliente.getText().toString();
        String direccion = etDireccionCliente.getText().toString();
        String tipoSeleccionado = spnuevoTipoCliente.getSelectedItem().toString();

        //validar que los campos estén llenos
        if (id.isEmpty() || telefono.isEmpty() || nombre.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto tipo cliente
        Cliente nuevoCliente = new Cliente(
                id,
                telefono,
                nombre,
                direccion,
                TipoCliente.valueOf(tipoSeleccionado)
        );

        // Guardar en la lista del AdministrarClientes usando el repositorio interno
        boolean agregado = AdministrarClientes.ClienteRepository
                .getInstance()
                .agregarCliente(nuevoCliente);

        if (!agregado) {
            Toast.makeText(this, "El cliente con ese ID ya existe", Toast.LENGTH_SHORT).show();
        } else {
            finish(); // Cierra el formulario y vuelve atrás
        }
    }
}