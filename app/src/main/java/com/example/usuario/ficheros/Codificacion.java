package com.example.usuario.ficheros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Codificacion extends AppCompatActivity {

    EditText edtLeer, edtGuardar, edtContenido;
    Button btnLeer, btnGuardar, btnExplorar;
    RadioButton rbtnUTF8, rbtnUTF16, rbtnISO;

    Memoria miMemoria;

    private static final int ABRIRFICHERO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificacion);

        inicializar();
    }

    private void inicializar() {
        edtLeer = (EditText) findViewById(R.id.edtLeer);
        edtGuardar = (EditText) findViewById(R.id.edtGuardar);
        edtContenido = (EditText) findViewById(R.id.edtContenido);
        rbtnUTF8 = (RadioButton) findViewById(R.id.rbtnUTF8);
        rbtnUTF16 = (RadioButton) findViewById(R.id.rbtnUTF16);
        rbtnISO = (RadioButton) findViewById(R.id.rbtnISO);

        miMemoria = new Memoria(getApplicationContext());

        btnLeer = (Button) findViewById(R.id.btnLeer);
        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeerFichero();
            }
        });

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EscribirFichero();
            }
        });

        btnExplorar = (Button) findViewById(R.id.btnExplorar);
        btnExplorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExplorarFichero();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == ABRIRFICHERO_REQUEST_CODE)
            if (resultCode == RESULT_OK) {
                String ruta = data.getData().getPath();
                edtLeer.setText(ruta.substring(ruta.lastIndexOf(":") + 1));
                LeerFichero();
            }
            else {
                Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();
            }
    }

    private String codificacion() {
        if(rbtnUTF8.isChecked()) return "UTF-8";
        if(rbtnUTF16.isChecked()) return "UTF-16";
        if(rbtnISO.isChecked()) return "ISO-8859-15";
        return "";
    }

    private void LeerFichero() {
        String mensaje = "";
        Resultado resultado = miMemoria.leerExterna(edtLeer.getText().toString(), codificacion());
        if(resultado.getCodigo()) {
            edtContenido.setText(resultado.getContenido());
            mensaje = "Fichero leído correctamente";
        }
        else {
            mensaje = "Error leyendo el fichero" + edtLeer.getText().toString();
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void EscribirFichero() {
        String mensaje = "";
        if(miMemoria.disponibleEscritura()) {
            if(miMemoria.escribirExterna(edtGuardar.getText().toString(), edtContenido.getText().toString(), false, codificacion())) {
                mensaje = "Fichero escrito correctamente";
            }
            else {
                mensaje = "Error al escribir en el fichero " + edtGuardar.getText().toString();
            }
        }
        else {
            mensaje = "Memoria externa no disponible";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void ExplorarFichero() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, ABRIRFICHERO_REQUEST_CODE);
        }
        else {
            Toast.makeText(getBaseContext(), "No hay aplicación para manejar ficheros", Toast.LENGTH_SHORT).show();
        }
    }
}
