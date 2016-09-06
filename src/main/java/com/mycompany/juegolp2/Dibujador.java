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
    
    public void flush()
    {
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(new String(new char[30]).replace('\0', '\n'));
    }
    
    public void dibujarLaberinto(Laberinto laberinto, Position jugador)
    {
        this.flush();
        //laberinto.draw(); //Metodo de debug para revisar si el mapa es correctamente Dibujado
        System.out.println(jugador);
        int X = jugador.getX();
        int Y = jugador.getY();
        laberinto.actualizarJugador(X,Y);
        int xIni = (X - this.altoVisible) < 0 ? 0 : (X - this.altoVisible);
        int xFin = (X + this.altoVisible) >= laberinto.getAlto() ? (laberinto.getAlto()-1) : (X + this.altoVisible);
        int yIni = (Y - this.anchoVisible) < 0 ? 0 : (Y - this.anchoVisible);
        int yFin = (Y + this.anchoVisible) >= laberinto.getAncho() ?( laberinto.getAncho()-1) : (Y + this.anchoVisible);
        
        for(int i = xIni; i <= xFin; i++){
            for(int j = yIni; j <= yFin;j++){
                laberinto.get(i, j).draw();
            }
            System.out.println();
        }
        System.out.print("\n\n\n");
    }
    
    public void dibujarInfoJugador(Avatar jugador)
    {
        System.out.println("Jugador: " + jugador.getNombre());
        System.out.println("Nivel: " + jugador.getNivel());
        String arma = (jugador.getArma() != null) ? jugador.getArma().toString() : "Ninguna";
        System.out.println("Arma: " + arma);
        String armadura = (jugador.getArmadura() != null) ? jugador.getArmadura().toString() : "Ninguna";
        System.out.println("Armadura: " + armadura);
        System.out.println("Saco: ");
        System.out.println(jugador.getSaco());
    }
    
    public void showError(String err)
    {
        System.err.println(err);
    }
}
