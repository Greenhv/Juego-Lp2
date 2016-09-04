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
        
        while (true) {
            juego.play();

            Juego.Result res = juego.result();
            if (res == Juego.Result.QUIT) {
                System.out.println("Has decidido terminar el juego");
                break;
            } else if (res == Juego.Result.WIN) {
                System.out.println("Has ganado !");
                break;
            } else if (res == Juego.Result.LOSE) {
                System.out.println("Has perdido :(");
                break;
            }
        }
    }
    
}
