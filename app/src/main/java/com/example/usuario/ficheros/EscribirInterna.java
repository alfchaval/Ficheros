package com.example.usuario.ficheros;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by usuario on 17/10/17.
 */

public class EscribirInterna extends AppCompatActivity {

    EditText operando1, operando2;
    TextView resultado, propiedades;
    Button boton;

    public final static String NOMBRE_FICHERO = "resultado.txt";

    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escritura_interna);

        operando1 = (EditText) findViewById(R.id.edt_operando1);
        operando2 = (EditText) findViewById(R.id.edt_operando2);
        resultado = (TextView) findViewById(R.id.txv_resultado);
        propiedades = (TextView) findViewById(R.id.txv_propiedades);
        boton = (Button) findViewById(R.id.btn_mas);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result;
                String op1 = operando1.getText().toString();
                String op2 = operando2.getText().toString();
                String texto = "NaN";

                try {
                    result = Integer.parseInt(op1) + Integer.parseInt(op2);
                    texto = String.valueOf(result);
                } catch (NumberFormatException e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                resultado.setText(texto);
                if(miMemoria.escribirInterna(NOMBRE_FICHERO, texto, false, "UTF-8")) {
                    propiedades.setText(miMemoria.mostrarPropiedadesInterna(NOMBRE_FICHERO));
                }
                else {
                    propiedades.setText("Error al escribir en el fichero " + NOMBRE_FICHERO);
                }
            }
        });

        miMemoria = new Memoria(getApplicationContext());
    }

}
