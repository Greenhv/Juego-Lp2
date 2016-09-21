package lp2.juegolp2.Artefactos;

public class PocionCuracion extends Artefacto
{
    public static PocionCuracion[] pocionesDisp;
    static {
        pocionesDisp = new PocionCuracion[2];
        pocionesDisp[0] = new PocionCuracion(5, "Pocion");
        pocionesDisp[1] = new PocionCuracion(10, "Super Pocion");
    }
    private int puntos_vida;
    
    public PocionCuracion(int puntos_vida)
    {
        if (puntos_vida <= 0)
            throw new IllegalArgumentException("A potion's health points can't be lower than zero");
        this.puntos_vida = puntos_vida;
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
