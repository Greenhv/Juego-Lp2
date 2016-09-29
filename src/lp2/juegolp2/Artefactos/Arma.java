package lp2.juegolp2.Artefactos;

/**
 * Created by pmvb on 01/09/16.
 */
public class Arma extends Artefacto
{
    public static Arma[] armasDisp;
    static {
        armasDisp = new Arma[2];
        armasDisp[0] = new Arma(1, 5, "Daga");
        armasDisp[1] = new Arma(4, 8, "Espada");
    }
    private int dmg_min;
    private int dmg_max;

    public Arma(int dmg_min, int dmg_max)
    {
        super();
        if (dmg_min > dmg_max)
            throw new ArithmeticException(
                "El daño máximo de un arma no puede ser menor que el mínimo");
        this.dmg_min = dmg_min;
        this.dmg_max = dmg_max;
    }
    
    public Arma(int dmg_min, int dmg_max, String nombre)
    {
        this(dmg_min, dmg_max);
        this.setNombre(nombre);
    }

    public int damage()
    {
        return (int) (Math.random() * (dmg_max-dmg_min+1) + dmg_min);
    }

    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.ARMA;
    }
    
    @Override
    public String toString()
    {
        // Se imprime el daño promedio
        String str = getNombre() + " - " + Integer.toString((dmg_min+dmg_max)/2);
        return str;
    }
}
