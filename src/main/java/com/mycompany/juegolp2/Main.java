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
        Juego.getInstance().init();
        
        while (true) {
            Juego.getInstance().play();
            
            GameResult res = Juego.getInstance().result();
            if (res == GameResult.QUIT) {
                System.out.println("Has decidido terminar el juego");
            } else if (res == GameResult.WIN) {
                System.out.println("Has ganado !");
                break;
            } else if (res == GameResult.LOSE) {
                System.out.println("Has perdido :(");
                break;
            }
        }
    }
    
}
