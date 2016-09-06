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
    
    public Laberinto get(int n)
    {
        return this.laberintos.get(n);
    }
    
    public Laberinto Crear(int M, int N)
    {
        Laberinto lab = new Laberinto(2*M+1, 2*N+1);
        configLaberinto(lab);
        return lab;
    }
    
    public void crearLaberintos(int numLaberintos)
    {
        for (int i = 0; i < numLaberintos; ++i) {
            // M y N  entre 20 y 30
            int M = (int) ((Math.random()*11) + 20);
            int N = (int) ((Math.random()*11) + 20);
            this.laberintos.add(this.Crear(M, N));
        }
    }
    /**
     * TODO: Agregar Artefactos y Enemigos
     * 
     * Agrega lo necesario para completar el laberinto (celdas anterior, siguiente,
     * artefactos y enemigos).
     * Crea lista de celdas libres y luego elige donde poner lo necesario
     * aleatoriamente.
     * 
     * @param lab Laberinto
     */
    private void configLaberinto(Laberinto lab)
    {
        // Crea lista de celdas libres
        ArrayList<Position> libres = new ArrayList<>();
        for (int i = 1; i < lab.getAlto()-1; ++i) {
            for (int j = 1; j < lab.getAncho()-1; ++j) {
                // Si está libre, la añado a la lista
                if (lab.get(i, j).getContenido() == ContenidoCeldas.LIBRE.asChar()) {
                    libres.add(new Position(i, j));
                }
            }
        }
        
        // Agrega anterior
        int index = (int) (Math.random()*libres.size());
        lab.setAnterior(libres.get(index));
        libres.remove(index);
        
        // Agrega siguiente
        index = (int) (Math.random()*100) % libres.size();
        lab.setSiguiente(libres.get(index));
        libres.remove(index);
        
        // Agrega artefactos
        
        // Agrega enemigos
    }
    
    public void agregaPlayer(Avatar jugador)
    {
        Laberinto laberinto = this.laberintos.get(0);
        laberinto.get(jugador.getPosition()).setContenido(ContenidoCeldas.JUGADOR.asChar());
    }
    
    public int size()
    {
        return laberintos.size();
    }
}
