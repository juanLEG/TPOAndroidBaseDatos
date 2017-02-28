package com.example.equipo3.tpoandroid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class RankingActivity extends AppCompatActivity {

    private DataBaseManager manager;
    private Cursor c;
    private ListView lista;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        manager=Comunicador.getBase();
        lista=(ListView)findViewById(R.id.listView);
        c=manager.cargarCursorJugadores();


        String[] from= new String[]{manager.CN_NOMBRE,manager.CN_PROMEDIO};
        int[] to= new int[]{android.R.id.text1,android.R.id.text2};
        adapter= new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, c, from, to,0);
        lista.setAdapter(adapter);

    }
}
