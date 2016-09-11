package lp2.juegolp2;

/**
 *
 * @author pmvb
 */
public class Enemigo extends Entidad
{
    private int nivel_enemigo;
    public Enemigo(Position pos)
    // El enemigo tambien deberia hacer danho. Armas ? Danho Fisico ? Otro tipo de danho ?
    {
        super(pos);
        nivel_enemigo = 1;        
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel_enemigo;
    }
}
