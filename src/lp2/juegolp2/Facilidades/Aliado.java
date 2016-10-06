/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp2.juegolp2.Facilidades;

import lp2.juegolp2.Entidades.Avatar;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.*;

/**
 *
 * @author alulab
 */
public class Aliado extends Avatar
{
    private static String[] consejos;
    static {
        consejos = new String[5];
        consejos[0] = "No te acerques a los enemigos si no estás preparado";
        consejos[1] = "Trata de recoger artefactos antes de pelear con un enemigo";
        consejos[2] = "Si el laberinto es muy difícil, regresa al anterior";
        consejos[3] = "No dejes que los enemigos te acorralen";
        consejos[4] = "Si tienes baja vida, huye de los enemigos";
    }
    
    public Aliado(String nombre, Position pos)
    {
        super(nombre, pos);
    }
    
    public String getConsejo()
    {
        int index = (int) (Math.random() * consejos.length);
        return consejos[index];
    }
}
