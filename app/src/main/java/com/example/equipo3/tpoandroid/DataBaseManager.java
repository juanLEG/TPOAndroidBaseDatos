package com.example.equipo3.tpoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DataBaseManager  {

    public static final String TABLE_NAME = "jugadores";
    public static final String CN_ID = "_id";
    public static final String CN_NOMBRE = "nombre";
    public static final String CN_GANO = "gano";
    public static final String CN_EMPATO = "empato";
    public static final String CN_PERDIO = "perdio";
    public static final String CN_PROMEDIO = "promedio";



    public static final String CREATE_TABLE = "create table " +TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NOMBRE + " text not null,"
            + CN_GANO + " integer not null,"
            + CN_EMPATO + " integer not null,"
            + CN_PERDIO + " integer not null,"
            + CN_PROMEDIO + " double not null);";

    private DbHelper helper;
    private SQLiteDatabase db;
    private SQLiteDatabase db1;

    public DataBaseManager(Context context) {
        //Creamos la bases de datos
         helper = new DbHelper(context);
         db = helper.getWritableDatabase();
         db1= helper.getReadableDatabase();
    }

    private ContentValues generarContentValues(String nombre, int gano, int empato, int perdio, double promedio) {
        ContentValues valores = new ContentValues();
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_GANO, gano);
        valores.put(CN_EMPATO, empato);
        valores.put(CN_PERDIO, perdio);
        valores.put(CN_PROMEDIO, promedio);

        return valores;
    }

    public void insertar(String nombre, int gano, int empato, int perdio, double promedio) {
        db.insert(TABLE_NAME,null,generarContentValues(nombre, gano, empato, perdio, promedio));

    }

    public void modificar(String nombre,int gano, int empato, int perdio, double promedio) {
        db.update(TABLE_NAME,generarContentValues(nombre,gano, empato,perdio, promedio),CN_NOMBRE+"=?", new String[]{nombre});
    }


    public Cursor cargarCursorJugadores() {
        String [] columnas= new String[]{CN_ID,CN_NOMBRE,CN_GANO,CN_EMPATO,CN_PERDIO, CN_PROMEDIO};
        return db.query(TABLE_NAME, columnas, null, null, null, null,null);

    }

    public Cursor buscarJugador(String nombre) {
        String [] columnas= new String[]{CN_ID,CN_NOMBRE,CN_GANO,CN_EMPATO,CN_PERDIO, CN_PROMEDIO};
        return  db.query(TABLE_NAME,columnas,CN_NOMBRE+"=?", new String[]{nombre}, null, null,null );
    }



    public Cursor recuperarValores(String nombre){
        String[] args = new String[] {nombre};
        Cursor c = db.rawQuery("SELECT gano,empato,perdio,promedio FROM jugadores WHERE nombre=?", args);
        return c;
    }

}
