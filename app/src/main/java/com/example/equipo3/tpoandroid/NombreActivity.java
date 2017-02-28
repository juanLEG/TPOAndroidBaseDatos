package com.example.equipo3.tpoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import java.io.Serializable;
import java.sql.Struct;

public class NombreActivity extends AppCompatActivity {

    private EditText txtNombre;
    private Button btnAceptar;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);

        //Recuperamos la base de datos pasada por parametro
        final DataBaseManager manager=Comunicador.getBase();

        //Obtenemos una referencia a los controles de la interfaz
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);

        //Implementamos el evento click del botón
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!manager.buscarJugador(txtNombre.getText().toString()).moveToFirst()) {
                    String[] args =new String[]{txtNombre.toString()};
                    manager.insertar(txtNombre.getText().toString(),0,0,0,0.0);
                }

                Comunicador.setBase(manager);
                finish();
                //Creamos el Intent
                Intent intent =
                        new Intent(NombreActivity.this, JugarActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }
}
