package com.example.equipo3.tpoandroid;



public class Comunicador {
    private static DataBaseManager base=null;

    public static void setBase(DataBaseManager base1)
    {
        base=base1;
    }
    public static DataBaseManager getBase()
    {
        return base;
    }
}
