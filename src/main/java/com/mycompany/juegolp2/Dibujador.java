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
public class Dibujador
{   
    int anchoVisible;
    int altoVisible;
    
    public Dibujador() 
    {
        this.anchoVisible = 5;
        this.altoVisible = 10;
    }
    
    public void myFlush()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    public void dibujarLaberinto(Laberinto laberinto, Position jugador)
    {
        this.myFlush();
        System.out.println(jugador.getX() + "  " +jugador.getY());
        int X = jugador.getX();
        int Y = jugador.getY();
        laberinto.actualizarJugador(X,Y);
        int posIX = (X - this.altoVisible) < 0 ? 0 : (X - this.altoVisible) ;
        int posFX = (X + this.altoVisible) >= laberinto.getAlto() ? (laberinto.getAlto()-1) : (X + this.altoVisible);
        int posIY = (Y - this.anchoVisible) < 0 ? 0 : (Y - this.anchoVisible);
        int posFY = (Y + this.anchoVisible) >= laberinto.getAncho() ?( laberinto.getAncho()-1) : (Y + this.anchoVisible);
        
        for(int i = posIX; i<posFX; i++){
            for(int j = posIY; j<posFY;j++){
                laberinto.get(i, j).draw();
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
//        laberinto.draw(); //Metodo de debug para revisar si el mapa es correctamente Dibujado
    }
}
