package lp2.juegolp2.Entidades;

import lp2.juegolp2.Artefactos.*;
import lp2.juegolp2.Mundo.Direction;
import lp2.juegolp2.Mundo.Position;

/**
 *
 * @author pmvb
 */
public abstract class Entidad
{
    private String nombre;
    private int maxHP;
    private int currHP;
    private Position position;
    private Direction facingDir;
    private Arma arma;
    private Armadura armadura;
    
    public Entidad(String nombre)
    {
        this.nombre = nombre;
        this.facingDir = Direction.LEFT;
    }
    
    public Entidad(String nombre, Position pos)
    {
        this(nombre);
        this.setPosition(pos);
        this.facingDir = Direction.LEFT;
    }
    
    public void move(Direction dir)
    {
        this.getPosition().move(dir);
        this.setFacingDir(dir);
    }

    public Position getPosition()
    {
        return this.position;
    }
    
    public void setPosition(Position pos)
    {
        this.position = pos;
    }
    
    protected void initHP()
    {
        this.currHP = maxHP;
    }
    
    public int getCurrentHP()
    {
        return this.currHP;
    }
    
    public void damage(int dmg)
    {
        if (this.getArmadura() != null) {
            int def = this.getArmadura().getDefensa();
            if ((def - dmg) > 0) {
                this.getArmadura().setDefensa(def-dmg);
            } else {
                this.setArmadura(null);
                dmg -= def;
            }
        }
        if ((this.currHP - dmg) < 0)
            this.currHP = 0;
        else 
            this.currHP -= dmg;
    }
    
    protected void setMaxHP(int max)
    {
        if (max <= 0)
            throw new IllegalArgumentException(
                "La vida máxima no puede ser menor que cero (max: "
                + Integer.toString(max) + ")");
        this.maxHP = max;
    }
    
    public int getMaxHP()
    {
        return this.maxHP;
    }
    
    public String getNombre()
    {
        return this.nombre;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public Direction getFacingDir()
    {
        return this.facingDir;
    }
    
    public void setFacingDir(Direction dir)
    {
        this.facingDir = dir;
    }
    
    public abstract int getNivel();
    
    public void heal(int hp)
    {
        currHP += hp;
        if (currHP > maxHP)
            currHP = maxHP;
    }
    
    public void heal(PocionCuracion pocion)
    {
        this.heal(pocion.getHP());
    }
    
    /**
     * @return the arma
     */
    public Arma getArma() {
        return arma;
    }

    /**
     * @param arma the arma to set
     */
    public void setArma(Arma arma) {
        this.arma = arma;
    }

    /**
     * @return the armadura
     */
    public Armadura getArmadura() {
        return armadura;
    }

    /**
     * @param armadura the armadura to set
     */
    public void setArmadura(Armadura armadura) {
        this.armadura = armadura;
    }
}
