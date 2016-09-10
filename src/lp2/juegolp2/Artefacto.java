package lp2.juegolp2;

/**
 * Created by pmvb on 01/09/16.
 */
public abstract class Artefacto
{
    public Artefacto()
    {

    }

    public abstract Artefacto.Tipo type();

    public enum Tipo
    {
        ARMADURA,
        POCION,
        ARMA
    }
    
    public abstract String toString();
}
