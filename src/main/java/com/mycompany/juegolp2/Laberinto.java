/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

import java.util.*;

/**
 *
 * @author pmvb
 */
public class Laberinto implements Drawable
{
    /*
     * Ancho de un laberinto
     */
    private int M;
    /*
     * Alto de un laberinto
     */
    private int N;
    /*
     * El laberinto mismo
     */
    ArrayList<ArrayList<Celda>> laberinto;
    /*
     * La probabilidad de que un enemigo aparezca en una celda
     */
    private double pct_enemigo;
    /*
     * Niveles posibles de enmigos en el laberinto
     */
    private int[] niveles;
    
    public Laberinto(int M, int N)
    {
        this.laberinto = new ArrayList<>(M);
        for (int i = 0; i < M; ++i) {
            laberinto.add(new ArrayList<>(N));
        }
    }
    
    public void draw()
    {
        for (int x = 0; x < M; ++x) {
            for (int y = 0; y < N; ++y) {
                laberinto.get(x).get(y).draw();
            }
            System.out.println();
        }
    }
}
