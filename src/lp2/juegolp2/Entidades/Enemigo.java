package lp2.juegolp2.Entidades;

import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Facilidades.ImageLoader;
import lp2.juegolp2.Mundo.Celda;
/**
 *
 * @author pmvb
 */
public class Enemigo extends Entidad
{
    private static String[] enemy_names = {
        "Skeleton",
        "Orc",
        "Imp"
    };
    
    private int nivel_enemigo;
    
    public Enemigo(String nombre, ImageLoader imgLoader)
    {
        super(nombre, imgLoader);
        nivel_enemigo = 1;
        init();
    }
    
    public Enemigo(String nombre, int nivel, ImageLoader imgLoader)
    {
        this(nombre, imgLoader);
        this.nivel_enemigo = nivel;
        init();
    }
    
    private void init()
    {
        super.setMaxHP(nivel_enemigo*10);
        super.initHP();
        this.setArma(Arma.random(this.getImageLoader()));
        this.setArmadura(Armadura.random(this.getImageLoader()));
        this.sprite.setImage(getNombre().toLowerCase() + "StopDown");
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel_enemigo;
    }
    
    public static Enemigo random(int nivel, ImageLoader imgLoader)
    {
        String nombre = enemy_names[(int) (Math.random() * enemy_names.length)];
        return new Enemigo(nombre, nivel, imgLoader);
    }
    
    @Override
    public String toString()
    {
        String str = "Nombre: " + getNombre() + " - HP: " + getCurrentHP() + "/" + getMaxHP() + "\n";
        str += "Nivel: " + getNivel() + "\n";
        str += "Arma: " + ((getArma() == null) ? "Ninguna" : getArma().toString()) + "\n";
        str += "Armadura: " + ((getArmadura() == null) ? "Ninguna" : getArmadura().toString()) + "\n";
        
        return str;
    }
    
    @Override
    public Celda.Contenido getContenidoCelda()
    {
        return Celda.Contenido.ENEMIGO;
    }
}
