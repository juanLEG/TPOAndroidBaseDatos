package com.example.equipo3.tpoandroid;

/**
 * Created by Equipo3 on 22/02/2017.
 */

import java.util.Random;

public class tateti {

    private char tablero[];
    private final static int tamañoTablero = 9;

    public static final char humano = 'X';
    public static final char ia = '0';
    public static final char espacio = ' ';

    private Random rand;

    public static int getTamañoTablero() {
        return tamañoTablero;
    }

    public tateti(){

        tablero = new char[tamañoTablero];

        for (int i = 0; i < tamañoTablero; i++)
            tablero[i] = espacio;

        rand = new Random();
    }

    public void clearBoard()
    {
        for (int i = 0; i < tamañoTablero; i++)
        {
            tablero[i] = espacio;
        }
    }

    public void setMovimiento(char jugador, int indice)
    {
        tablero[indice] = jugador;
    }

    public int getMovimientoIA()
    {
        int movimiento;

        for (int i = 0; i < getTamañoTablero(); i++)
        {
            if (tablero[i] != humano && tablero[i] != ia)
            {
                char aux = tablero[i];
                tablero[i] = ia;
                if (verificarGanador() == 3)
                {
                    setMovimiento(ia, i);
                    return i;
                }
                else
                    tablero[i] = aux;
            }
        }

        for (int i = 0; i < getTamañoTablero(); i++)
        {
            if (tablero[i] != humano && tablero[i] != ia)
            {
                char aux = tablero[i];
                tablero[i] = humano;
                if (verificarGanador() == 2)
                {
                    setMovimiento(ia, i);
                    return i;
                }
                else
                    tablero[i] = aux;
            }
        }

        do
        {
            movimiento = rand.nextInt(getTamañoTablero());
        } while (tablero[movimiento] == humano || tablero[movimiento] == ia);

        setMovimiento(ia, movimiento);
        return movimiento;
    }

    public int verificarGanador()
    {
        for (int i = 0; i <= 6; i += 3)
        {
            if (tablero[i] == humano &&
                    tablero[i+1] == humano &&
                    tablero[i+2] == humano)
                return 2;
            if (tablero[i] == ia &&
                    tablero[i+1] == ia &&
                    tablero[i+2] == ia)
                return 3;
        }

        for (int i = 0; i <= 2; i++)
        {
            if (tablero[i] == humano &&
                    tablero[i+3] == humano &&
                    tablero[i+6] == humano)
                return 2;
            if (tablero[i] == ia &&
                    tablero[i+3] == ia &&
                    tablero[i+6] == ia)
                return 3;
        }

        if ((tablero[0] == humano &&
                tablero[4] == humano &&
                tablero[8] == humano) ||
                tablero[2] == humano &&
                        tablero[4] == humano &&
                        tablero[6] == humano)
            return 2;
        if ((tablero[0] == ia &&
                tablero[4] == ia &&
                tablero[8] == ia) ||
                tablero[2] == ia &&
                        tablero[4] == ia &&
                        tablero[6] == ia)
            return 3;

        for (int i = 0; i < getTamañoTablero(); i++)
        {
            if (tablero[i] != humano && tablero[i] != ia)
                return 0;
        }

        return 1;
    }
}  
