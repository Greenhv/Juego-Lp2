/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

enum TipoCelda
{
    ADENTRO,
    ANTERIOR,
    SIGUIENTE,
    PARED
};

enum ContenidoCelda
{
    
}

/**
 *
 * @author pmvb
 */
public class Celda implements Drawable
{
    private TipoCelda tipo;
    private char contenido;
    
    public Celda(TipoCelda tipo)
    {
        this.tipo = tipo;
        this.contenido = ' ';
    }
    
    public void setTipo(TipoCelda tipo)
    {
        this.tipo = tipo;
    }
    
    public TipoCelda getTipo()
    {
        return this.tipo;
    }
    
    public void ocupar(char contenido)
    {
        this.contenido = contenido;
    }
    
    public char getContenido()
    {
        return this.contenido;
    }
    
    public void visitar()
    {
        
    }
    
    public void draw()
    {
        System.out.print(this.contenido);
    }
}
