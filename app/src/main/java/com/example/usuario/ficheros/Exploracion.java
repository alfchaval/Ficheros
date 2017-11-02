package com.example.usuario.ficheros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Exploracion extends AppCompatActivity implements View.OnClickListener {
    private static final int ABRIRFICHERO_REQUEST_CODE = 1;
    private Button botonAbrir;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exploracion);
        botonAbrir = (Button) findViewById(R.id.botonAbrir);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        botonAbrir.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, ABRIRFICHERO_REQUEST_CODE);
        else {
            //informar que no hay ninguna aplicación para manejar ficheros
            Toast.makeText(this, "No hay aplicación para manejar ficheros", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == ABRIRFICHERO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Mostramos en la etiqueta la ruta del archivo seleccionado
                String ruta = data.getData().getPath();
                txtInfo.setText(ruta);
            }
            else {
                Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
