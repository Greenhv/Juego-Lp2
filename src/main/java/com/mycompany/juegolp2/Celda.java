/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

;

/**
 *
 * @author pmvb
 */
public class Celda
{
    private int fila;
    private int columna;
    private TipoCelda tipo;
    private char contenido;
    private Enemigo enemigo;
    private Artefacto artefacto;

    public Celda()
    {
        this.tipo = TipoCelda.PARED;
        this.contenido = ContenidoCeldas.PARED.asChar();
    }
    
    public Celda(int fila, int columna)
    {
        this.fila = fila;
        this.columna = columna;
        this.tipo = TipoCelda.PARED;
        this.contenido = ContenidoCeldas.PARED.asChar();
    }

    public Celda(TipoCelda tipo, ContenidoCeldas contenido)
    {
        this.tipo = tipo;
        this.contenido = contenido.asChar();
    }
    
    public void setTipo(TipoCelda tipo)
    {
        this.tipo = tipo;
    }
    
    public TipoCelda getTipo()
    {
        return this.tipo;
    }
    
    public void setContenido(char contenido)
    {
        this.contenido = contenido;
    }

    public void setContenido(ContenidoCeldas contenido)
    {
        this.contenido = contenido.asChar();
    }
    
    public char getContenido()
    {
        return this.contenido;
    }
    
    public void draw()
    {
        System.out.print(this.contenido);
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna)
    {
        this.columna = columna;
    }
    
    public void markAsInside()
    {
        this.setTipo(TipoCelda.ADENTRO);
        this.setContenido(ContenidoCeldas.LIBRE.asChar());
    }
    
    public void markAsOutside()
    {
        this.setTipo(TipoCelda.AFUERA);
    }
    
    public boolean isFree()
    {
        return this.getTipo() == TipoCelda.AFUERA;
    }
    
    public boolean esPared()
    {
        return this.getTipo() == TipoCelda.PARED;
    }
    
    public Enemigo getEnemigo()
    {
        return this.enemigo;
    }
    
    public void setEnemigo(Enemigo ene)
    {
        if (this.getContenido() == ContenidoCeldas.ENEMIGO.asChar())
            this.enemigo = ene;
    }
    
    public void removeEnemigo()
    {
        if (this.getContenido() == ContenidoCeldas.ENEMIGO.asChar()) {
            this.enemigo = null;
            this.setContenido(ContenidoCeldas.LIBRE.asChar());
        }
    }
    
    public Artefacto getArtefacto()
    {
        return this.artefacto;
    }
    
    public void setArtefacto(Artefacto art)
    {
        if (this.getContenido() == ContenidoCeldas.ARTEFACTO.asChar())
            this.artefacto = art;
    }
    
    public void removeArtefacto()
    {
        if (this.getContenido() == ContenidoCeldas.ARTEFACTO.asChar()) {
            this.artefacto = null;
            this.setContenido(ContenidoCeldas.LIBRE.asChar());
        }
    }
    
    public enum TipoCelda
    {
        PARED,
        ADENTRO,
        AFUERA,
        ANTERIOR,
        SIGUIENTE,
        
    }
}
