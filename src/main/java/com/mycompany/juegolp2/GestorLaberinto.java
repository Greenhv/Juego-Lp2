package com.mycompany.juegolp2;

import java.util.*;

/**
 *
 * @author pmvb
 */
public class GestorLaberinto
{
    List<Laberinto> laberintos;
    
    public GestorLaberinto()
    {
        this.laberintos = new ArrayList<Laberinto>();
    }
    
    public Laberinto Crear(int M, int N)
    {
        Laberinto lab = new Laberinto(2*M+1, 2*N+1);
        //configLaberinto(lab);
        return lab;
    }

    public void configLaberinto(Laberinto lab)
    {
        Deque<Position> visited = new ArrayDeque<>();

        for (int i = 1; i < lab.getWidth(); i += 2) {
            for (int j = 1; j < lab.getHeight(); j += 2) {
                int x = (int) (Math.random() * 100) < 50 ? 0 : 1;
                int y = (int) (Math.random() * 100) < 50 ? 0 : 1;

            }
        }
    }
}
