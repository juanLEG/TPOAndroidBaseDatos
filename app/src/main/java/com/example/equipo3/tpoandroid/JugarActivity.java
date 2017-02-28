package com.example.equipo3.tpoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JugarActivity extends AppCompatActivity {
    private tateti juego;
    private Button colBotones[];
    private Button volverJugar;
    private Button salir;

    private TextView info;
    private TextView contJugador;
    private TextView contEmpate;
    private TextView contIA;

    private String nombre;

    private DataBaseManager manager;

    private int contadorJugador;//sacar de la base de datos
    private int contadorEmpate;
    private int contadorIA;

    private boolean jugadorPrimero = true;
    private boolean termino = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        //Recuperamos el nombre del jugador
        Bundle b=this.getIntent().getExtras();
        String nom=b.getString("NOMBRE");
        this.setNombre(nom);
        System.out.println("El nombre de la persona es: "+nombre);

        //Recuperamos la base de datos pasada por parametro
        this.setBase(Comunicador.getBase());

        colBotones = new Button[juego.getTamañoTablero()];
        colBotones[0] = (Button) findViewById(R.id.uno);
        colBotones[1] = (Button) findViewById(R.id.dos);
        colBotones[2] = (Button) findViewById(R.id.tres);
        colBotones[3] = (Button) findViewById(R.id.cuatro);
        colBotones[4] = (Button) findViewById(R.id.cinco);
        colBotones[5] = (Button) findViewById(R.id.seis);
        colBotones[6] = (Button) findViewById(R.id.siete);
        colBotones[7] = (Button) findViewById(R.id.ocho);
        colBotones[8] = (Button) findViewById(R.id.nueve);

        Cursor c = manager.recuperarValores(nombre);
        c.moveToFirst();
        contadorJugador =c.getInt(0);
        contadorEmpate =c.getInt(1);
        contadorIA = c.getInt(2);

        info = (TextView) findViewById(R.id.informacion);
        contJugador = (TextView) findViewById(R.id.contadorJugador);
        contEmpate = (TextView) findViewById(R.id.contadorEmpate);
        contIA = (TextView) findViewById(R.id.contadorIA);

        contJugador.setText(Integer.toString(contadorJugador));
        contEmpate.setText(Integer.toString(contadorEmpate));
        contIA.setText(Integer.toString(contadorIA));

        //Obtenemos una referencia a los controles de la interfaz
        volverJugar = (Button) findViewById(R.id.botonVolverJugar);
        salir = (Button) findViewById(R.id.botonSalir);

        //Implementamos el evento click del botón volverJugar
        volverJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Creamos el Intent
                Intent intent =
                        new Intent(JugarActivity.this, JugarActivity.class);

                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("NOMBRE", nombre);

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        //Implementamos el evento click del botón salir
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        juego = new tateti();
        startNewGame();

    }

    private void startNewGame()
    {
        juego.clearBoard();

        for (int i = 0; i < colBotones.length; i++)
        {
            colBotones[i].setText("");
            colBotones[i].setEnabled(true);
            colBotones[i].setOnClickListener(new ButtonClickListener(i));
        }

        if (jugadorPrimero)
        {
            info.setText(R.string.jugadorPrimero);
            jugadorPrimero = false;
        }
        else
        {
            info.setText(R.string.turnoIA);
            int move = juego.getMovimientoIA();
            setMovimiento(juego.ia, move);
            jugadorPrimero = true;
        }
    }

    private class ButtonClickListener implements View.OnClickListener
    {
        int indice;

        public ButtonClickListener(int indice)
        {

            this.indice = indice;
        }

        public void onClick(View view)
        {
            if (!termino)
            {
                if (colBotones[indice].isEnabled())
                {
                    setMovimiento(juego.humano, indice);

                    int ganador = juego.verificarGanador();

                    if (ganador == 0)
                    {
                        info.setText(R.string.turnoIA);
                        int move = juego.getMovimientoIA();
                        setMovimiento(juego.ia, move);
                        ganador = juego.verificarGanador();
                    }

                    if (ganador == 0)
                        info.setText(R.string.turnoJugador);
                    else if (ganador == 1) {
                        info.setText(R.string.resultadoEmpate);
                        contadorEmpate++;
                        int cantJugados = contadorJugador + contadorIA + contadorEmpate;
                        manager.modificar(nombre,contadorJugador,contadorEmpate,contadorIA,(((double) contadorJugador / cantJugados)*100));
                        contEmpate.setText(Integer.toString(contadorEmpate));
                        termino = true;
                    }
                    else if (ganador == 2) {
                        info.setText(R.string.resultadoGanoJugador);
                        contadorJugador++;
                        int cantJugados = contadorJugador + contadorIA + contadorEmpate;
                        manager.modificar(nombre,contadorJugador,contadorEmpate,contadorIA,(((double) contadorJugador / cantJugados)*100));
                        contJugador.setText(Integer.toString(contadorJugador));
                        termino = true;
                    }
                    else {
                        info.setText(R.string.resultadoGanoIA);
                        contadorIA++;
                        int cantJugados = contadorJugador + contadorIA + contadorEmpate;
                        manager.modificar(nombre,contadorJugador,contadorEmpate,contadorIA,(((double) contadorJugador / cantJugados)*100));
                        contIA.setText(Integer.toString(contadorIA));
                        termino = true;
                    }
                }
            }

        }
    }

    private void setMovimiento(char jugador, int indice) {
        juego.setMovimiento(jugador, indice);
        colBotones[indice].setEnabled(false);
        colBotones[indice].setText(String.valueOf(jugador));
        if (jugador == juego.humano)
            colBotones[indice].setTextColor(Color.rgb(7,141,151));
        else
            colBotones[indice].setTextColor(Color.rgb(174,7,203));
    }

    private void setNombre(String n) {
        nombre=n;
    }

    private void setBase(DataBaseManager base){
        manager = base;
    }
}
