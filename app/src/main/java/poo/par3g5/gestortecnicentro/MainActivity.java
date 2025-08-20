package poo.par3g5.gestortecnicentro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //lanzar la actividad Administrar Clientes
    public void mostrarAdministrarClientes(View v){
        Intent intent= new Intent(this, AdministrarClientes.class);
        startActivity(intent);
    }

    //lanzar la actividad Administrar Tecnicos
    public void mostrarAdministrarTecnicos(View v){
        Intent intent= new Intent(this, AdministrarTecnicos.class);
        startActivity(intent);
    }

    //lanzar la actividad Administrar Tecnicos
    public void mostrarAdministrarProveedores(View v){
        Intent intent= new Intent(this, AdministrarProveedores.class);
        startActivity(intent);
    }


}