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
    private Dibujador dibujador;
    private int currentLab;
    
    public Juego()
    {
        this.gestorLaberinto = new GestorLaberinto();
        this.dibujador = new Dibujador();
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

    public Result play()
    {
        Scanner scan = new Scanner(System.in);
        Laberinto laberintoActual = this.gestorLaberinto.laberintoPosicionN(this.currentLab);
        while(true){
            this.dibujador.dibujarLaberinto(laberintoActual,this.jugador.getPosition());
            System.out.print("Ingrese su siguiente movimiento: ");
            String movimiento = scan.nextLine();
            if(movimiento.contentEquals("interactuar")) {
                // Interactua con el artefacto mas cercano;
                return Result.PLAYING;
            }
            else if(movimiento.contains("mover")) {
                // Mover al Avatar y Comprobar que se encuentra en una celda valida
                this.moverAvatar(movimiento, laberintoActual);
            }
            else if(movimiento.contentEquals("salir")) {
                // Sale del Juego
                this.dibujador.myFlush();
                return Result.QUIT;
            }
            else{
                dibujador.myFlush();
            }
        }
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
        this.jugador = new Avatar(nombre, this.gestorLaberinto.laberintoPosicionN(this.currentLab).getAnterior());
        this.gestorLaberinto.agregaPlayer(jugador);
    }

    private void initMap()
    {
        int numLaberintos = (int) (Math.random() * 6) + 5;
        this.gestorLaberinto.crearLaberintos(numLaberintos);
    }
    
    private Result moverAvatar(String movimiento, Laberinto laberintoActual) 
    {
        String words[] = movimiento.split(" ");
        if(words.length >= 2){
            String comando = words[1];
            Direction dir = Direction.valueOf(comando);
            this.jugador.move(dir);
            System.out.println(this.jugador.getPosition().getX() + " " + this.jugador.getPosition().getY());
            this.revisarPosicionJugador(laberintoActual,dir);
        }
        else {
            this.dibujador.myFlush();
        }
        return Result.PLAYING;
    }
    
    private void revisarPosicionJugador(Laberinto laberintoActual, Direction dir)
    {
        if(laberintoActual.get(this.jugador.getPosition()).esPared()){
            switch (dir) {
                case U:
                    this.jugador.move(Direction.D);
                    break;
                case R:
                    this.jugador.move(Direction.L);
                    break;
                case D:
                    this.jugador.move(Direction.U);
                    break;
                case L:
                    this.jugador.move(Direction.R);
                    break;
            }
        }
    }
    
    public enum Result
    {
        QUIT,
        LOSE,
        WIN,
        PLAYING
    }
}
