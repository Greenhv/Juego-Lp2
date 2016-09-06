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
        Juego juego = new Juego();
        juego.intro();
        juego.init();
        
        OUTER:
        while (true) {
            Juego.Result res = juego.play();
            if (res != null) {
                switch (res) {
                    case QUIT:
                        System.out.println("Has decidido terminar el juego");
                        break OUTER;
                    case WIN:
                        System.out.println("Has ganado !");
                        break OUTER;
                    case LOSE:
                        System.out.println("Has perdido :(");
                        break OUTER;
                    default:
                        break;
                }
            }
        }
    }
}
