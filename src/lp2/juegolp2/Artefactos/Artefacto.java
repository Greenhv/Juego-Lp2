package lp2.juegolp2.Artefactos;

import lp2.juegolp2.Facilidades.ImageLoader;
import lp2.juegolp2.Facilidades.WorldObject;

/**
 * Created by pmvb on 01/09/16.
 */
public abstract class Artefacto extends WorldObject
{
    String nombre;
    
    public Artefacto(String nombre, ImageLoader imgLoader)
    {
        super(imgLoader);
        this.nombre = nombre;
    }
    
    public String getNombre()
    {
        return this.nombre;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public abstract Artefacto.Tipo type();

    public enum Tipo
    {
        ARMADURA,
        POCION,
        ARMA
    }
    
    public static Artefacto random(ImageLoader imgLoader)
    {
        int tipo = (int) (Math.random() * Artefacto.Tipo.values().length);
        Artefacto.Tipo type = Artefacto.Tipo.values()[tipo];
        Artefacto artefacto = null;
        switch (type) {
            case POCION:
                artefacto = PocionCuracion.random(imgLoader);
                break;
            case ARMA:
                artefacto = Arma.random(imgLoader);
                break;
            case ARMADURA:
                artefacto = Armadura.random(imgLoader);
                break;
        }
        return artefacto;
    }
    
    public abstract String toString();
}
