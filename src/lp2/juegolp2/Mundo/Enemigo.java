package lp2.juegolp2.Mundo;

import lp2.juegolp2.Artefactos.*;
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
    {
        super(nombre);
        nivel_enemigo = 1;
    }
    
    public Enemigo(String nombre, int nivel)
    {
        this(nombre);
        this.nivel_enemigo = nivel;
        super.setMaxHP(nivel*10);
        super.initHP();
        this.setArma(Arma.random());
        this.setArmadura(Armadura.random());
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel_enemigo;
    }
    
    public static Enemigo random(int nivel)
    {
        String nombre = enemy_names[(int) (Math.random() * enemy_names.length)];
        return new Enemigo(nombre, nivel);
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
}
