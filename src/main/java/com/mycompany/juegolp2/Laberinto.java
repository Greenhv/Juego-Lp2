package com.mycompany.juegolp2;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;

import java.util.*;

/**
 *
 * @author pmvb
 */
public class Laberinto implements Drawable
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
    
    public Laberinto(int M, int N)
    {
        this.setAncho(M);
        this.setAlto(N);
        laberinto = new Celda[alto][ancho];
        for (int i = 0; i < alto; i++){
          for (int j = 0; j < ancho; j++){
                laberinto[i][j] = new Celda(i,j);
            }
        }
        inicializar_laberinto();
        generar_ruta();

    }
    
    /* Generador de Camino para el Laberinto */
        private void inicializar_laberinto(){
        boolean fila_flag = true;
        boolean columna_flag = false;
        for (int i = 0; i < alto; i++){
            for (int j = 0; j < ancho; j++){
                if((i != 0) && (j != 0) && (j != ancho-1) && (i != alto-1) && columna_flag){
                    if(fila_flag){
                        laberinto[i][j].mark_as_outside();
                        fila_flag = false;
                    }
                    else
                        fila_flag = true;
                    }
            }
            columna_flag = !columna_flag;
            fila_flag = true;
        }
    }
    
    private void generar_ruta(){
        Stack cells_stack = new Stack();
        Celda starting_cell = laberinto[1][1]; //just a test starting point
        starting_cell.mark_as_inside();
        cells_stack.push(starting_cell);
        while(!cells_stack.empty()){
            Celda current_cell = (Celda)cells_stack.peek();
            
            HashMap<Integer, Celda> close_cells_hash = new HashMap<Integer, Celda>();
            if(labyrinth_free_space(current_cell.getFila(),current_cell.getColumna()-2)){
                close_cells_hash.put(0,laberinto[current_cell.getFila()][current_cell.getColumna()-2]);
            }
            if(labyrinth_free_space(current_cell.getFila()-2,current_cell.getColumna())){
                close_cells_hash.put(1,laberinto[current_cell.getFila()-2][current_cell.getColumna()]);
            }
            if(labyrinth_free_space(current_cell.getFila(),current_cell.getColumna()+2)){
                close_cells_hash.put(2,laberinto[current_cell.getFila()][current_cell.getColumna()+2]);
            }
            if(labyrinth_free_space(current_cell.getFila()+2,current_cell.getColumna())){ 
                close_cells_hash.put(3,laberinto[current_cell.getFila()+2][current_cell.getColumna()]);
            }
            
            //if(nearby_empty_cell(current_cell.getRow(),current_cell.getColumn())){//if current_cell has adjacent cells that are ADENTRO
            if(nearby_empty_cell(close_cells_hash)){//if current_cell has adjacent cells that are ADENTRO
                //pick a random_cell
                Celda random_cell = new Celda();
                random_cell = get_random_cell(close_cells_hash);
                //make a path from current to random cell
                make_path(current_cell, random_cell);
                random_cell.mark_as_inside();
                cells_stack.push(random_cell);
            }
            else
                cells_stack.pop();
        }
    }
    
    
    private boolean labyrinth_free_space(int fila, int columna){
        if((fila < 0)||(columna < 0)||(fila >= alto)||(columna >= ancho)){
            return false;
        }
        else return laberinto[fila][columna].is_free(alto, ancho);
    }
    private boolean nearby_empty_cell(HashMap<Integer, Celda> close_cells_hash){
        if(close_cells_hash.size()>0)
            return true;
        return false;
    }
    
    private Celda get_random_cell(HashMap<Integer, Celda> close_cells_hash){
        Random randomGenerator = new Random();
        while(true){
            int random_number = randomGenerator.nextInt(4);
            if(close_cells_hash.get(random_number) != null)
                return close_cells_hash.get(random_number);
            //return null;
        }
    }
    
    private void make_path(Celda start_cell, Celda end_cell){
        if(start_cell.getFila() == end_cell.getFila()){
            if(start_cell.getColumna() < end_cell.getColumna())
                laberinto[start_cell.getFila()][start_cell.getColumna()+1].mark_as_inside();
            else
                laberinto[start_cell.getFila()][start_cell.getColumna()-1].mark_as_inside();
        }
        if(start_cell.getColumna() == end_cell.getColumna()){
            if(start_cell.getFila() < end_cell.getFila())
                laberinto[start_cell.getFila()+1][start_cell.getColumna()].mark_as_inside();
            else
                laberinto[start_cell.getFila()-1][start_cell.getColumna()].mark_as_inside();
        }
    }    

    /* Fin del generador de laberinto */
    
    public void draw()
    {
//        String topBorder = " " + new String(new char[this.getWidth()]).replace("\0", " _  ");
        for (int i = 0; i < this.getAlto(); ++i) {
//            System.out.println(topBorder + "\n");
            for (int j = 0; j < this.getAncho(); ++j) {
//                System.out.print("| ");
                laberinto[i][j].draw();
//                System.out.print(' ');
            }
            System.out.println();
        }
//        System.out.println(topBorder);
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }
}
