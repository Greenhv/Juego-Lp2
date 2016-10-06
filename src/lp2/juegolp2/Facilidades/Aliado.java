/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp2.juegolp2.Facilidades;

import java.util.*;
import lp2.juegolp2.Entidades.Avatar;
import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.*;

/**
 *
 * @author alulab
 */
public class Aliado extends Avatar
{
    private ArrayList<Consejo> consejos;
    
    public Aliado(String nombre)
    {
        super(nombre);
        consejos = new ArrayList<>();
    }
    
    public Aliado(String nombre, Position pos)
    {
        super(nombre, pos);
        consejos = new ArrayList<>();
    }
    
    public String getConsejo()
    {
        int index = (int) (Math.random() * consejos.size());
        return consejos.get(index).getConsejo();
    }
    
    public void addConsejo(Consejo consejo)
    {
        this.consejos.add(consejo);
    }
}
