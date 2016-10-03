package lp2.juegolp2.Artefactos;

/**
 * Created by pmvb on 01/09/16.
 */
public abstract class Artefacto
{
    String nombre;
    
    public Artefacto()
    {
        
    }
    
    public Artefacto(String nombre)
    {
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
    
    public static Artefacto random()
    {
        int tipo = (int) (Math.random() * Artefacto.Tipo.values().length);
        Artefacto.Tipo type = Artefacto.Tipo.values()[tipo];
        Artefacto artefacto = null;
        switch (type) {
            case POCION:
                artefacto = PocionCuracion.random();
                break;
            case ARMA:
                artefacto = Arma.random();
                break;
            case ARMADURA:
                artefacto = Armadura.random();
                break;
        }
        return artefacto;
    }
    
    public abstract String toString();
}
