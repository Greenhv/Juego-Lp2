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
    // El enemigo tambien deberia hacer danho. Armas ? Danho Fisico ? Otro tipo de danho ?
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
        Arma armaBasica = new Arma(nivel,nivel*2);
        Armadura armaduraBasica = new Armadura(nivel*2);
        this.setArma(armaBasica);
        this.setArmadura(armaduraBasica);
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel_enemigo;
    }
    
    public static Enemigo random(int nivel)
    {
        String nombre = enemy_names[(int) (Math.random()) % enemy_names.length];
        Enemigo enemigo = new Enemigo(nombre, nivel);
        enemigo.setArma(Arma.armasDisp[nivel%2]);
        enemigo.setArmadura(Armadura.armadurasDisp[nivel%2]);
        return enemigo;
    }
}
