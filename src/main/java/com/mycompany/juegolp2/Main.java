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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Juego juego = Juego.getInstance();
        juego.intro();
        juego.init();
        
        Juego.Result res = juego.play();
        switch (res) {
            case QUIT:
                System.out.print("Has decidido terminar el juego. ");
                System.out.println("Gracias por jugar.");
                break;
            case WIN:
                System.out.println("Has ganado !");
                break;
            case LOSE:
                System.out.println("Has perdido :(");
                break;
        }
    }
}
