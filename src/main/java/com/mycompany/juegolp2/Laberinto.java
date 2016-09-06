package com.mycompany.juegolp2;

import java.util.*;

/**
 *
 * @author pmvb
 */
public class Laberinto
{
    /*
     * Ancho de un laberinto
     */
    private int ancho;

    /*
     * Alto de un laberinto
     */
    private int alto;

    /*
     * El laberinto mismo
     */
    public Celda[][] laberinto;

    /*
     * La probabilidad de que un enemigo aparezca en una celda
     */
    private double pct_enemigo;

    /*
     * Niveles posibles de enmigos en el laberinto
     */
    private int[] niveles;
    
    /**
     * Posicion de la celda para acceder al siguiente laberinto
     * 
     */
    private Position anterior;
    
    /**
     * Posicion de la celda para acceder al siguiente laberinto
     * 
     */
    private Position siguiente;
    
    public Laberinto(int ancho, int alto)
    {
        this.setAncho(ancho);
        this.setAlto(alto);
        laberinto = new Celda[alto][ancho];
        for (int i = 0; i < alto; i++){
          for (int j = 0; j < ancho; j++){
                laberinto[i][j] = new Celda(i,j);
            }
        }
        this.init();
        generar_ruta();

    }
    
    /* Generador de Camino para el Laberinto */
    private void init()
    {
        boolean fila_flag = true;
        boolean columna_flag = false;
        for (int i = 1; i < alto-1; i++){
            for (int j = 1; j < ancho-1; j++){
                if(fila_flag){
                    laberinto[i][j].markAsOutside();
                }
                fila_flag = !fila_flag;
            }
            columna_flag = !columna_flag;
            fila_flag = true;
        }
    }
    
    private void generar_ruta()
    {
        Stack<Celda> cells_stack = new Stack<>();
        Celda starting_cell = laberinto[1][1]; //just a test starting point
        starting_cell.markAsInside();
        cells_stack.push(starting_cell);
        
        while(!cells_stack.empty()) {
            Celda current_cell = cells_stack.peek();
            
            HashMap<Integer, Celda> close_cells = new HashMap<>();
            int fila = current_cell.getFila();
            int columna = current_cell.getColumna();
            if(labyrinth_free_space(fila, columna-2)){
                close_cells.put(0, laberinto[fila][columna-2]);
            }
            if(labyrinth_free_space(fila-2, columna)){
                close_cells.put(1, laberinto[fila-2][columna]);
            }
            if(labyrinth_free_space(fila, columna+2)){
                close_cells.put(2, laberinto[fila][columna+2]);
            }
            if(labyrinth_free_space(fila+2, columna)){ 
                close_cells.put(3, laberinto[fila+2][columna]);
            }
            
            //if current_cell has adjacent cells that are ADENTRO
            if(nearby_empty_cell(close_cells)) {
                //pick a random_cell
                Celda random_cell = get_random_cell(close_cells);
                //make a path from current to random cell
                make_path(current_cell, random_cell);
                random_cell.markAsInside();
                cells_stack.push(random_cell);
            }
            else
                cells_stack.pop();
        }
    }
    
    
    private boolean labyrinth_free_space(int fila, int columna)
    {
        if( (fila < 0) || (columna < 0) || (fila >= alto) || (columna >= ancho)){
            return false;
        }
        return laberinto[fila][columna].isFree();
    }
    
    private boolean nearby_empty_cell(HashMap<Integer, Celda> close_cells)
    {
        return close_cells.size() > 0;
    }
    
    private Celda get_random_cell(HashMap<Integer, Celda> close_cells)
    {
        while(true){
            int random_number = (int) (Math.random() * 10) % 4;
            if(close_cells.get(random_number) != null)
                return close_cells.get(random_number);
            //return null;
        }
    }
    
    private void make_path(Celda start_cell, Celda end_cell)
    {
        if(start_cell.getFila() == end_cell.getFila()){
            if(start_cell.getColumna() < end_cell.getColumna())
                laberinto[start_cell.getFila()][start_cell.getColumna()+1].markAsInside();
            else
                laberinto[start_cell.getFila()][start_cell.getColumna()-1].markAsInside();
        }
        if(start_cell.getColumna() == end_cell.getColumna()){
            if(start_cell.getFila() < end_cell.getFila())
                laberinto[start_cell.getFila()+1][start_cell.getColumna()].markAsInside();
            else
                laberinto[start_cell.getFila()-1][start_cell.getColumna()].markAsInside();
        }
    }    

    /* Fin del generador de laberinto */
    
    public void draw()
    {
        for (int i = 0; i < this.getAlto(); ++i) {
            for (int j = 0; j < this.getAncho(); ++j) {
                laberinto[i][j].draw();
            }
            System.out.println();
        }
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        if (ancho <= 0)
            throw new IllegalArgumentException("Ancho debe ser mayor que cero");
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        if (alto <= 0)
            throw new IllegalArgumentException("Alto debe ser mayor que cero");
        this.alto = alto;
    }
    
    public Celda get(Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        // Por como se manejan los arreglos, 'x' corresponde al alto y 'y' al ancho
        if (!this.inBounds(x, y))
            throw new IndexOutOfBoundsException(
                "Coordenadas fuera de rango en Laberinto.get(Position)"
                + "pos: " + pos.toString());
        return laberinto[x][y];
    }
    
    public Celda get(int x, int y)
    {
        // Por como se manejan los arreglos, 'x' corresponde al alto y 'y' al ancho
        if (!this.inBounds(x, y))
            throw new IndexOutOfBoundsException(
                "Coordenadas fuera de rango en Laberinto.get(int, int): "
                + "x: " + Integer.toString(x) + ", y: " + Integer.toString(y));
        return laberinto[x][y];
    }

    /**
     * @return the anterior
     */
    public Position getAnterior() 
    {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Position anterior) 
    {
        this.anterior = anterior;
        this.get(anterior).setContenido(ContenidoCeldas.ANTERIOR.asChar());
    }

    /**
     * @return the siguiente
     */
    public Position getSiguiente() 
    {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(Position sig)
    {
        this.siguiente = sig;
        this.get(sig).setContenido(ContenidoCeldas.SIGUIENTE.asChar());
    }
    
    public void actualizarJugador(int X, int Y)
    {
        this.get(X, Y).setContenido(ContenidoCeldas.JUGADOR);
    }
    
    public boolean inBounds(int x, int y)
    {
        return !(x < 0 || y < 0 || x >= getAlto() || y >= getAncho());
    }
    
    public boolean inBounds(Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        return !(x < 0 || y < 0 || x >= getAlto() || y >= getAncho());
    }
    
    public boolean validPlayerPosition(int x, int y)
    {
        if (x < 1 || y < 1 || x >= getAlto()-2 || y >= getAncho()-2)
            return false;
        System.out.println(this.get(x, y).getTipo());
        if (this.get(x, y).getTipo() == Celda.TipoCelda.PARED)
            return false;
        return true;
    }
    
    public boolean validPlayerPosition(Position pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        return validPlayerPosition(x, y);
    }
}