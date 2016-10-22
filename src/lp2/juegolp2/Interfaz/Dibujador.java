package lp2.juegolp2.Interfaz;

import java.util.Scanner;
import lp2.juegolp2.Mundo.*;
import lp2.juegolp2.Entidades.*;
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
        System.out.print(new String(new char[30]).replace('\0', '\n'));
    }
    
    public void dibujarLaberinto(Laberinto laberinto, Position jugador)
    {
        this.flush();
        //laberinto.draw(); //Metodo de debug para revisar si el mapa es correctamente Dibujado
        int X = jugador.getX();
        int Y = jugador.getY();
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
//        System.out.print("\n\n\n");
    }
    
    public void dibujarInfoJugador(Avatar jugador)
    {
        System.out.println("*********************--------**********************");
        System.out.println(jugador);
        System.out.println("*********************--------**********************");
    }
    
    public void showError(String err)
    {
        System.out.println(err);
    }
    
    public void showMessage(String msg)
    {
        System.out.println(msg);
    }
    
    public void showBattleInterface(Avatar jugador, Entidad enemigo)
    {
        System.out.print("Heroe: " + jugador.getNombre());
        System.out.print(" - Vida Actual: " + jugador.getCurrentHP());
        System.out.print(" \t\t vs \t\tEnemigo: " + enemigo.getNombre());
        System.out.println(" - Vida Actual: " + enemigo.getCurrentHP() + 
                " - Defensa: " + 
                ((enemigo.getArmadura() == null) ? 0 : enemigo.getArmadura().getDefensa()));
    }
    
    public void showPrompt(String prompt)
    {
        System.out.print(prompt);
    }
    
    public void showPauseScreen()
    {
        System.out.println("Presione Enter para continuar...");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}
