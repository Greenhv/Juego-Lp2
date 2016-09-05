/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

import java.util.ArrayList;

/**
 *
 * @author pmvb
 */
public class Juego {
    
    private Avatar jugador;
    private GestorLaberinto gestorLaberinto;
    private ArrayList<Laberinto> mapa;
    
    public Juego()
    {
        this.gestorLaberinto = new GestorLaberinto();
        this.mapa = new ArrayList<>();
    }

    // Introducción al juego
    public void intro()
    {
        System.out.println("JUEGO");
    }

    // Configura lo necesario para jugar
    public void init()
    {
        this.createMap();
        // Obten datos y crea jugador
        this.createPlayer();
        //
    }

    public void play()
    {
        this.jugador.move(Direction.DOWN);
        this.jugador.getPosition().print();
        this.mapa.get(0).draw();
    }

    public Result result()
    {
        return Result.WIN;
    }
    
    private void createPlayer()
    {
        this.jugador = new Avatar("Nombre");
        //this.jugador.setPosition();
    }

    private void createMap()
    {
        // M y N  entre 20 y 30
        int M = (int) ((Math.random()*10) + 20);
        int N = (int) ((Math.random()*10) + 20);
        System.out.println(Integer.toString(M) + " - " + Integer.toString(N));
        this.mapa.add(this.gestorLaberinto.Crear(M, N));
    }

    public enum Result
    {
        QUIT,
        LOSE,
        WIN
    }
}
