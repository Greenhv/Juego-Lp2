package com.mycompany.juegolp2;

/**
 * Created by pmvb on 01/09/16.
 */
public abstract class Artefacto
{
    public Artefacto()
    {

    }

    public abstract Artefacto.Tipo getType();

    public enum Tipo
    {
        ARMADURA,
        POCION,
        ARMA
    }
}
