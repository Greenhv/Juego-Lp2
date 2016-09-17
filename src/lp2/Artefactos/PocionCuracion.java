package lp2.Artefactos;

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
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.POCION;
    }
    
    @Override
    public String toString()
    {
        String str = "Poción de Curación: \n";
        str += "Recupera: " + Integer.toString(puntos_vida) + "HP\n";
        return str;
    }
}
