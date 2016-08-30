/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

enum GameResult
{
    QUIT,
    LOSE,
    WIN
}

/**
 *
 * @author pmvb
 */
public class Juego {
    
    private Avatar jugador;
    private GestorLaberinto mapa;
    
    private Juego()
    {
    }
    
    public static Juego getInstance()
    {
        return JuegoHolder.INSTANCE;
    }
    
    private static class JuegoHolder {

        private static final Juego INSTANCE = new Juego();
    }
    
    // Inicia datos del jugador y lo necesario para jugar
    public void init()
    {
        // Obten datos y Crea jugador
        this.createPlayer();
        //
    }
    
    public void play()
    {
        
    }
    
    public GameResult result()
    {   
        return GameResult.WIN;
    }
    
    private void createPlayer()
    {
        this.jugador = new Avatar("Nombre");
    }
}
