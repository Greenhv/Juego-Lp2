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
    private int width;

    /*
     * Alto de un laberinto
     */
    private int height;

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
            laberinto.add(new ArrayList<>(Collections.nCopies(N, new Celda())));
        }
        this.setWidth(M);
        this.setHeight(N);
    }
    
    public void draw()
    {

        for (int x = 0; x < this.getWidth(); ++x) {
            for (int y = 0; y < this.getHeight(); ++y) {
                laberinto.get(x).get(y).draw();
            }
            System.out.println();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Celda get(int x, int y)
    {
        return this.laberinto.get(x).get(y);
    }
}
