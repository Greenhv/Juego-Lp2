/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

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
    
    public Entidad(String nombre)
    {
        this.nombre = nombre;
    }
    
    public Entidad(String nombre, Position pos)
    {
        this.nombre = nombre;
        this.setPosition(pos);
        this.setPosition(new Position(1, 1));
    }
    
    public void move(Direction dir)
    {
        this.getPosition().move(dir);
    }

    public Position getPosition()
    {
        return this.position;
    }
    
    public void setPosition(Position pos)
    {
        this.position = pos;
    }
    
    public int getCurrentHP()
    {
        return this.currHP;
    }
    
    public void damage(int dmg)
    {
        if ((this.currHP - dmg) < 0)
            this.currHP = 0;
        else 
            this.currHP -= dmg;
    }
    
    private void setMaxHP(int max)
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
}
