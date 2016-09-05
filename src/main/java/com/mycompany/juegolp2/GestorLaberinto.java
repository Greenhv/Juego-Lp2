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
        return lab;
    }
}
