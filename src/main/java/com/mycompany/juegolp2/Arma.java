package com.mycompany.juegolp2;

/**
 * Created by pmvb on 01/09/16.
 */
public class Arma extends Artefacto
{
    private int dmg_min;
    private int dmg_max;

    public Arma(int dmg_min, int dmg_max)
    {
        super();
        if (dmg_min > dmg_max)
            throw new ArithmeticException("Minimum weapon damage can't be lower than maximum damage");
        this.dmg_min = dmg_min;
        this.dmg_max = dmg_max;
    }

    public int damage()
    {
        return (int) (Math.random() * (dmg_max-dmg_min) + dmg_min);
    }

    @Override
    public Artefacto.Tipo getType() {
        return Artefacto.Tipo.ARMA;
    }
}
