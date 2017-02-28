package com.example.equipo3.tpoandroid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private Button botonJugar;
    private Button botonSalir;
    private Button botonMusica;
    private Button botonRanking;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DataBaseManager manager = new DataBaseManager(this);

        //Obtenemos una referencia a los controles de la interfaz
        botonJugar = (Button)findViewById(R.id.botonJugar);
        botonRanking = (Button)findViewById(R.id.botonRanking);
        botonSalir = (Button)findViewById(R.id.botonSalir);
        botonMusica = (Button)findViewById(R.id.botonMusica);


        //Implementamos el evento click del botón
        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comunicador.setBase(manager);
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, NombreActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        botonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comunicador.setBase(manager);
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, RankingActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        botonMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botonMusica.getText().toString().equalsIgnoreCase("Musica ON"))
                {
                    playMP();
                    botonMusica.setText("Musica OFF");
                }
                else
                {
                    stopMP();
                    botonMusica.setText("Musica ON");
                }
            }
        });
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //resetear variables al salir
                if(botonMusica.getText().toString().equalsIgnoreCase("Musica OFF"))
                {
                    stopMP();
                }

                    finish();
            }
        });
    }
    private void playMP()
    {
        mp=MediaPlayer.create(this,R.raw.sp);
        mp.setLooping(true);
        mp.start();

    }
    private void stopMP()
    {
        mp.stop();
    }
}
