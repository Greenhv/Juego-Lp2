package com.mycompany.juegolp2;

public class PocionCuracion extends Artefacto
{
    private int puntos_vida;

    public PocionCuracion(int puntos_vida)
    {
        super();
        if (puntos_vida <= 0)
            throw new IllegalArgumentException("A potion's health points can't be lower than zero");
        this.puntos_vida = puntos_vida;
    }

    public int getHP()
    {
        return this.puntos_vida;
    }

    @Override
    public Artefacto.Tipo getType() {
        return Artefacto.Tipo.POCION;
    }
}
