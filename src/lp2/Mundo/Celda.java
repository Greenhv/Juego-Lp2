package lp2.Mundo;

import lp2.Artefactos.*;
/**
 *
 * @author pmvb
 */
public class Celda
{
    private int fila;
    private int columna;
    private Tipo tipo;
    private char contenido;
    private Enemigo enemigo;
    private Artefacto artefacto;

    public Celda()
    {
        this.tipo = Tipo.PARED;
        this.contenido = Contenido.PARED.asChar();
    }
    
    public Celda(int fila, int columna)
    {
        this.fila = fila;
        this.columna = columna;
        this.tipo = Tipo.PARED;
        this.contenido = Contenido.PARED.asChar();
    }

    public Celda(Tipo tipo, Contenido contenido)
    {
        this.tipo = tipo;
        this.contenido = contenido.asChar();
    }
    
    public void setTipo(Tipo tipo)
    {
        this.tipo = tipo;
    }
    
    public Tipo getTipo()
    {
        return this.tipo;
    }
    
    public void setContenido(char contenido)
    {
        this.contenido = contenido;
    }

    public void setContenido(Contenido contenido)
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
        this.setTipo(Tipo.ADENTRO);
        this.setContenido(Contenido.LIBRE.asChar());
    }
    
    public void markAsOutside()
    {
        this.setTipo(Tipo.AFUERA);
    }
    
    public boolean isFree()
    {
        return this.getTipo() == Tipo.AFUERA;
    }
    
    public boolean esPared()
    {
        return this.getTipo() == Tipo.PARED;
    }
    
    public Enemigo getEnemigo()
    {
        return this.enemigo;
    }
    
    public void setEnemigo(Enemigo ene)
    {
        if (this.getContenido() == Contenido.ENEMIGO.asChar())
            this.enemigo = ene;
    }
    
    public void removeEnemigo()
    {
        if (this.getContenido() == Contenido.ENEMIGO.asChar()) {
            this.enemigo = null;
            this.setContenido(Contenido.LIBRE.asChar());
        }
    }
    
    public Artefacto getArtefacto()
    {
        return this.artefacto;
    }
    
    public void setArtefacto(Artefacto art)
    {
        if (this.getContenido() == Contenido.ARTEFACTO.asChar())
            this.artefacto = art;
    }
    
    public void removeArtefacto()
    {
        if (this.getContenido() == Contenido.ARTEFACTO.asChar()) {
            this.artefacto = null;
            this.setContenido(Contenido.LIBRE.asChar());
        }
    }

    /**
     * Contenidos posibles de una celda
     *
     */
    public enum Contenido {
        PARED('#'),
        LIBRE(' '), 
        ARTEFACTO('A'), 
        ENEMIGO('E'), 
        SIGUIENTE('+'),
        ANTERIOR('-'), 
        JUGADOR('*');

        public char asChar() {
            return asChar;
        }
        private final char asChar;

        private Contenido(char asChar) {
            this.asChar = asChar;
        }
    }
    
    public enum Tipo
    {
        PARED,
        ADENTRO,
        AFUERA,
        ANTERIOR,
        SIGUIENTE,
        
    }
}
