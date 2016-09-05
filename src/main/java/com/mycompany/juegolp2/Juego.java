/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pmvb
 */
public class Juego {
    
    private Avatar jugador;
    private GestorLaberinto gestorLaberinto;
    private ArrayList<Laberinto> mapa;
    private int currentLab;
    
    public Juego()
    {
        this.gestorLaberinto = new GestorLaberinto();
        this.mapa = new ArrayList<>();
        this.currentLab = 0;
    }

    // Introducción al juego
    public void intro()
    {
        System.out.println("JUEGO");
    }

    // Configura lo necesario para jugar
    public void init()
    {
        this.initMap();
        // Obten datos y crea jugador
        this.initPlayer();
        //
    }

    public void play()
    {
        this.mapa.get(0).draw();
    }

    public Result result()
    {
        return Result.WIN;
    }
    
    private void initPlayer()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String nombre = scan.nextLine();
        this.jugador = new Avatar(nombre, mapa.get(0).getAnterior());
        this.mapa.get(0).get(this.jugador.getPosition()).setContenido(ContenidoCeldas.JUGADOR.asChar());
    }

    private void initMap()
    {
        int numLaberintos = (int) (Math.random() * 6) + 5;
        for (int i = 0; i < numLaberintos; ++i) {
            // M y N  entre 20 y 30
            int M = (int) ((Math.random()*11) + 20);
            int N = (int) ((Math.random()*11) + 20);
            this.mapa.add(this.gestorLaberinto.Crear(M, N));
        }
    }

    public enum Result
    {
        QUIT,
        LOSE,
        WIN
    }
}
