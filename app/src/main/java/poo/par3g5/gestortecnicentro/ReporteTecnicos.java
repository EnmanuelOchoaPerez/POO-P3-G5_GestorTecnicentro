package poo.par3g5.gestortecnicentro;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import modelo.Orden;
import modelo.RepositorioPruebaAvance;
import modelo.Tecnico;

public class ReporteTecnicos extends AppCompatActivity {

    Spinner spinnerMeses;
    EditText editTextAño;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_tecnicos);
        spinnerMeses = findViewById(R.id.inputMeses);
        editTextAño = findViewById(R.id.inputAño);

        TextView tvEncabezado = findViewById(R.id.tvTextoEncabezado);
        if (tvEncabezado != null) {
            tvEncabezado.setText("Reportes de Atenciones por Tecnico");
        }

        String[] mesesArray = DateFormatSymbols.getInstance().getMonths();

        List<String> meses = new ArrayList<>();
        meses.add("Escoja un mes");
        for (int i = 0; i < 12; i++) {
            meses.add(mesesArray[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                meses
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(adapter);
    }

    //Metodo para GenerarReporteGananciaTecnicos
    public void GenerarRat(View v) {
        String año = editTextAño.getText().toString();
        String mes = spinnerMeses.getSelectedItem().toString();

        if (año.isEmpty() || mes.equals("Escoja un mes")) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Toast.makeText(this, "Versión de Android no soportada", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Parsear YearMonth del mes y año
        Locale locale = Locale.getDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale);
        YearMonth selectedYM = YearMonth.parse(mes + " " + año, formatter);

        // 2. Obtener el contenedor (ya definido en XML)
        LinearLayout contenedor = findViewById(R.id.contenedorReporte);
        View cardView = getLayoutInflater().inflate(R.layout.item_base_cardview, null);
        contenedor.addView(cardView,0);
        LinearLayout cardContent = cardView.findViewById(R.id.cardContent);

        // --- AGREGAR ENCABEZADO ---

        // Título principal
        TextView titulo = new TextView(this);
        titulo.setText(String.format("Reporte %s %s", mes, año));
        titulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titulo.setTypeface(null, Typeface.BOLD);
        titulo.setPadding(0, 0, 0, 8);  // espacio debajo del texto
        cardContent.addView(titulo);

        // Divisor negro debajo del título
        View divisorTitulo = new View(this);
        LinearLayout.LayoutParams divisorTituloParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())
        );
        divisorTitulo.setLayoutParams(divisorTituloParams);
        divisorTitulo.setBackgroundColor(Color.BLACK);
        cardContent.addView(divisorTitulo);

        // Segunda línea encabezado (Tenico y Recaudo)
        LinearLayout encabezadoFila = new LinearLayout(this);
        encabezadoFila.setOrientation(LinearLayout.HORIZONTAL);
        encabezadoFila.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView serviciosEncabezado = new TextView(this);
        serviciosEncabezado.setText("Tecnico");
        serviciosEncabezado.setTypeface(null, Typeface.BOLD);
        serviciosEncabezado.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView recaudoEncabezado = new TextView(this);
        recaudoEncabezado.setText("Ganancia");
        recaudoEncabezado.setTypeface(null, Typeface.BOLD);
        recaudoEncabezado.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        recaudoEncabezado.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        encabezadoFila.addView(serviciosEncabezado);
        encabezadoFila.addView(recaudoEncabezado);
        cardContent.addView(encabezadoFila);

        // --- FIN ENCABEZADO ---

        // 3. Recorrer órdenes y agregar contenido
        boolean hayResultados = false;
        for (Orden o : RepositorioPruebaAvance.getInstance().getOrdenes()) {
            YearMonth ordenYM = YearMonth.from(o.getFechaServicio());

            if (selectedYM.equals(ordenYM)) {
                hayResultados = true;

                Tecnico tecnico = o.getTecnico();
                    // Fila horizontal
                    LinearLayout fila = new LinearLayout(this);
                    fila.setOrientation(LinearLayout.HORIZONTAL);
                    fila.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    // Nombre del tecnico
                    TextView nombreTecnico = new TextView(this);
                    nombreTecnico.setText(tecnico.getUsername());
                    nombreTecnico.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    // Ganancia
                    TextView recaudo = new TextView(this);
                    recaudo.setText(String.format(Locale.getDefault(), "$ %.2f", tecnico.getGanancia(ordenYM)));
                    recaudo.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                    recaudo.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                    fila.addView(nombreTecnico);
                    fila.addView(recaudo);
                    cardContent.addView(fila);

                    // Separador horizontal entre filas
                    View separator = new View(this);
                    LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()
                            )
                    );
                    separatorParams.setMargins(0, 1, 0, 8);
                    separator.setLayoutParams(separatorParams);
                    separator.setBackgroundColor(Color.LTGRAY);
                    cardContent.addView(separator);

            }
        }

        // 4. Si no hubo resultados
        if (!hayResultados) {
            final TextView sinResultados = new TextView(this);
            sinResultados.setText("No se encontraron servicios para ese mes y año.");
            sinResultados.setTextColor(Color.DKGRAY);
            sinResultados.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cardContent.addView(sinResultados);
        }
    }
}
