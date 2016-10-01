package lp2.juegolp2.Interfaz;

import lp2.juegolp2.Mundo.Avatar;
import lp2.juegolp2.Mundo.Position;
import lp2.juegolp2.Mundo.Laberinto;
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
        System.out.println(jugador);
        int X = jugador.getX();
        int Y = jugador.getY();
        laberinto.actualizarJugador(X, Y);
        int xIni = (X - this.altoVisible) < 0 ? 0 : (X - this.altoVisible);
        int xFin = (X + this.altoVisible) >= laberinto.getAlto() ? (laberinto.getAlto()-1) : (X + this.altoVisible);
        int yIni = (Y - this.anchoVisible) < 0 ? 0 : (Y - this.anchoVisible);
        int yFin = (Y + this.anchoVisible) >= laberinto.getAncho() ?( laberinto.getAncho()-1) : (Y + this.anchoVisible);
        
        for(int i = xIni; i <= xFin; i++){
            for(int j = yIni; j <= yFin;j++){
                System.out.print(laberinto.get(i, j).getContenido().asChar());
            }
            System.out.println();
        }
//        System.out.print("\n\n\n");
    }
    
    public void dibujarInfoJugador(Avatar jugador)
    {
        System.out.println(jugador);
    }
    
    public void showError(String err)
    {
        System.out.println(err);
    }
    
    public void showMessage(String msg)
    {
        System.out.println(msg);
    }
}
