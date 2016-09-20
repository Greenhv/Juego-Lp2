package lp2.juegolp2.Mundo;

/**
 *
 * @author pmvb
 */
public class Enemigo extends Entidad
{
    private static String[] enemy_names = {
        "Duende",
        "Ogro",
        "Harpía"
    };
    
    private int nivel_enemigo;
    public Enemigo(String nombre)
    // El enemigo tambien deberia hacer danho. Armas ? Danho Fisico ? Otro tipo de danho ?
    {
        super(nombre);
        nivel_enemigo = 1;
    }
    
    public Enemigo(String nombre, int nivel)
    {
        this(nombre);
        this.nivel_enemigo = nivel;
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel_enemigo;
    }
    
    public static Enemigo random(int nivel)
    {
        String nombre = enemy_names[(int) (Math.random()) % enemy_names.length];
        return new Enemigo(nombre, nivel);
    }
}
