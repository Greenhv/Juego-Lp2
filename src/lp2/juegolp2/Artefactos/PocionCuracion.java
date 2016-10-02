package lp2.juegolp2.Artefactos;

import java.util.*;

public class PocionCuracion extends Artefacto
{
    public static ArrayList<PocionCuracion> pocionesDisp;
    static {
        pocionesDisp = new ArrayList<>();
        pocionesDisp.add(new PocionCuracion(5, "Pocion"));
        pocionesDisp.add(new PocionCuracion(10, "Super Pocion"));
        pocionesDisp.add(new PocionCuracion(20, "Hyper Pocion"));
        pocionesDisp.add(new PocionCuracion(50, "Max Pocion"));
    }
    private int puntos_vida;
    
    public PocionCuracion(int puntos_vida)
    {
        this.setHP(puntos_vida);
    }
    
    public PocionCuracion(int puntos_vida, String nombre)
    {
        this(puntos_vida);
        setNombre(nombre);
    }

    public int getHP()
    {
        return this.puntos_vida;
    }
    
    public void setHP(int puntos_vida)
    {
        if (puntos_vida <= 0)
            throw new IllegalArgumentException(
                "Los puntos de vida de una poción no pueden ser negativos"
            );
        this.puntos_vida = puntos_vida;
    }
    
    public static PocionCuracion random()
    {
        return pocionesDisp.get((int) (Math.random() * pocionesDisp.size()));
    }
    
    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.POCION;
    }
    
    @Override
    public String toString()
    {
        String str = getNombre() + " - " + Integer.toString(puntos_vida) + "HP";
        return str;
    }
}
