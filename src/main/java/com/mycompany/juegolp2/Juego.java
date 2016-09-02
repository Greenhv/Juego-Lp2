/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Juego {
    
    private Avatar jugador;
    private GestorLaberinto gestorLaberinto;
    
    public Juego()
    {
    }

    // Introducción al juego
    public void intro()
    {
        System.out.println("JUEGO");
    }

    // Inicia datos del jugador y lo necesario para jugar
    public void init()
    {
        this.createMap();
        // Obten datos y crea jugador
        this.createPlayer();
        //
    }

    public void play()
    {
        this.jugador.getPosition().print();
        this.jugador.move(Direction.DOWN);
        this.jugador.getPosition().print();
    }

    public Result result()
    {
        return Result.WIN;
    }
    
    private void createPlayer()
    {
        this.jugador = new Avatar("Nombre");
    }

    private void createMap()
    {
        int M = (int) ((Math.random()*100) % 31 + 20);
        int N = (int) ((Math.random()*100) % 31 + 20);
        this.gestorLaberinto.Crear(M, N);
    }

    public enum Result
    {
        QUIT,
        LOSE,
        WIN
    }
}
