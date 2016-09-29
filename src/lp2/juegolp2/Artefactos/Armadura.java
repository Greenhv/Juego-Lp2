package lp2.juegolp2.Artefactos;

/**
 * Created by pmvb on 01/09/16.
 */
public class Armadura extends Artefacto
{
    public static Armadura[] armadurasDisp;
    static {
        armadurasDisp = new Armadura[2];
        armadurasDisp[0] = new Armadura(4, "Armadura de Cuero");
        armadurasDisp[1] = new Armadura(8, "Armadura de Metal");
    }
    private int defensa;

    public Armadura(int def)
    {
        super();
        this.defensa = def;
    }
    
    public Armadura(int def, String nombre)
    {
        this(def);
        setNombre(nombre);
    }
    
    public int getDefensa()
    {
        return defensa;
    }

    @Override
    public Artefacto.Tipo type()
    {
        return Artefacto.Tipo.ARMADURA;
    }
    
    @Override
    public String toString()
    {
        String str = getNombre() + " - " + Integer.toString(defensa);
        return str;
    }
}
