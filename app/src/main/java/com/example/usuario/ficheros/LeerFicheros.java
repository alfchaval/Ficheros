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

public class LeerFicheros extends AppCompatActivity {

    EditText edtRaw, edtAssets, edtMemoriaInterna, edtMemoriaExterna;
    TextView txvMensaje, txvResultado;
    Button btnSumar;
    Memoria miMemoria;
    public static final String OPERANDO_RAW = "numero";
    public static final String OPERANDO_ASSETS = "valor.txt";
    public static final String OPERANDO_MEMORIA_INTERNA = "dato.txt";
    public static final String OPERANDO_MEMORIA_EXTERNA = "dato_sd.txt";
    public static final String OPERACIONES = "operaciones.txt";
    public static final String CODIFICACION = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_ficheros);

        iniciar();
    }

    private void iniciar() {
        Resultado resultado;

        miMemoria = new Memoria(getApplicationContext());

        edtRaw = (EditText) findViewById(R.id.edtRaw);
        edtAssets = (EditText) findViewById(R.id.edtAssets);
        edtMemoriaInterna = (EditText) findViewById(R.id.edtMemoriaInterna);
        edtMemoriaExterna = (EditText) findViewById(R.id.edtMemoriaExterna);
        txvMensaje = (TextView) findViewById(R.id.txvMensaje);
        txvResultado = (TextView) findViewById(R.id.txvResultado);
        btnSumar = (Button) findViewById(R.id.btnSumar);
        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1 = edtRaw.getText().toString();
                String op2 = edtAssets.getText().toString();
                String op3 = edtMemoriaInterna.getText().toString();
                String op4 = edtMemoriaExterna.getText().toString();
                int cantidad = 0;
                String operacion, mensaje;

                try {
                    cantidad = Integer.parseInt(op1) + Integer.parseInt(op2) + Integer.parseInt(op3) + Integer.parseInt(op4);
                    operacion = op1 + " + " + op2 + " + " +op3 + " + " +op4 + " = " + String.valueOf(cantidad);
                } catch (NumberFormatException e) {
                    Log.e("Error", e.getMessage());
                    operacion = "0";
                    mensaje = e.getMessage();
                }
                txvMensaje.setVisibility(View.VISIBLE);
                txvResultado.setText(operacion);
                if(miMemoria.disponibleEscritura()) {
                    if(miMemoria.escribirExterna(OPERACIONES, operacion + '\n', true, CODIFICACION)) {
                        mensaje = "Operación escrita correctamente";
                    }
                    else {
                        mensaje = "Error al escribir en la memoria externa";
                    }
                }
                else {
                    mensaje = "Memoria externa no disponible";
                }
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        //RAW
        resultado = miMemoria.leerRaw(OPERANDO_RAW);
        if(resultado.getCodigo()) {
            edtRaw.setText(resultado.getContenido());
        }
        else {
            Toast.makeText(this, "Error al leer " + OPERANDO_RAW + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }
        //ASSETS
        resultado = miMemoria.leerAsset(OPERANDO_ASSETS);
        if(resultado.getCodigo()) {
            edtAssets.setText(resultado.getContenido());
        }
        else {
            Toast.makeText(this, "Error al leer " + OPERANDO_ASSETS + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }
        //INTERNA
        if(miMemoria.escribirInterna(OPERANDO_MEMORIA_INTERNA, "8", false, CODIFICACION)) {
            resultado = miMemoria.leerInterna(OPERANDO_MEMORIA_INTERNA, CODIFICACION);
            if(resultado.getCodigo()) {
                edtMemoriaInterna.setText(resultado.getContenido());
            }
            else {
                Toast.makeText(this, "Error al leer " + OPERANDO_MEMORIA_INTERNA + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Error al escribir " + OPERANDO_MEMORIA_INTERNA, Toast.LENGTH_SHORT).show();
        }
        //EXTERNA
        if(miMemoria.disponibleEscritura()) {
            if(miMemoria.escribirExterna(OPERANDO_MEMORIA_EXTERNA, "9", false, CODIFICACION)) {
                resultado = miMemoria.leerExterna(OPERANDO_MEMORIA_EXTERNA, CODIFICACION);
                if(resultado.getCodigo()) {
                    edtMemoriaExterna.setText(resultado.getContenido());
                }
                else {
                    Toast.makeText(this, "Error al leer " + OPERANDO_MEMORIA_EXTERNA+ " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Error al escribir " + OPERANDO_MEMORIA_EXTERNA, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Memoria externa no disponible", Toast.LENGTH_SHORT).show();
        }
    }
}
