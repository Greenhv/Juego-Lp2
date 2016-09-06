package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Avatar extends Entidad
{
    private int nivel;
    private Arma arma;
    private Armadura armadura;
    private Saco saco;
    private Direction facingDir;
    
    public Avatar(String nombre, Position pos)
    {
        super(nombre, pos);
        this.nivel = 1;
        this.saco = new Saco();
        this.facingDir = Direction.LEFT;
        setMaxHP(nivel*10);
        super.initHP();
    }
    
    public void pickupItem(Artefacto item)
    {
        if (item.type() == Artefacto.Tipo.ARMA)
            this.arma = (Arma) item;
        else if (item.type() == Artefacto.Tipo.ARMADURA)
            this.armadura = (Armadura) item;
        else
            this.saco.addItem(item);
    }
    
    @Override
    public int getNivel()
    {
        return this.nivel;
    }
    
    public Arma getArma()
    {
        return this.arma;
    }
    
    public Armadura getArmadura()
    {
        return this.armadura;
    }
    
    public Saco getSaco()
    {
        return this.saco;
    }
    
    public Direction getFacingDir()
    {
        return this.facingDir;
    }
    
    public void setFacingDir(Direction dir)
    {
        this.facingDir = dir;
    }
    
    @Override
    public String toString()
    {
        String str = "Nombre: " + getNombre() + " - HP: " + getCurrentHP() + "/" + getMaxHP() + "\n";
        str += "Mirando hacia: " + Direction.direcciones.get(facingDir) + "\n";
        str += "Nivel: " + getNivel() + "\n";
        str += "Arma: " + ((getArma() == null) ? "Ninguna" : getArma().toString()) + "\n";
        str += "Armadura: " + ((getArmadura() == null) ? "Ninguna" : getArmadura().toString()) + "\n";
        str += "Saco: \n";
        str += ((getSaco().empty()) ? "Vacío" : getSaco().toString());
        
        return str;
    }
}

