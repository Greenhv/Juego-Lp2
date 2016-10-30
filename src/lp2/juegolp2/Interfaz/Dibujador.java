package lp2.juegolp2.Interfaz;

import java.util.Scanner;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import lp2.juegolp2.Mundo.*;
import lp2.juegolp2.Entidades.*;
import lp2.juegolp2.Facilidades.ImageLoader;

/**
 *
 * @author pmvb
 */
public class Dibujador
{   
    private int anchoVisible;
    private int altoVisible;
    public static int tileSize = 32;
    private GameWindow window;
    private BufferStrategy bs;
    private Point mapCenter;
    private ImageLoader imgLoader;
    
    public Dibujador()
    {
        this.anchoVisible = 5;
        this.altoVisible = 10;
        this.window = new GameWindow(altoVisible, anchoVisible);
        this.bs = window.getBufferStrategy();
        JPanel mapPanel = this.window.getMapPanel();
        this.mapCenter = new Point(mapPanel.getWidth()/2, mapPanel.getHeight()/2);
        this.imgLoader = new ImageLoader();
    }
    
    public void flush()
    {   
        System.out.print(new String(new char[30]).replace('\0', '\n'));
    }
    
    public void dibujarLaberinto(Laberinto laberinto, Position jugador)
    {
        // Obtiene graficos
        Graphics g = this.bs.getDrawGraphics();
        
        this.flush();
        //laberinto.draw(g, 0, 0); //Metodo de debug para revisar si el mapa es correctamente Dibujado
        
        // Coordenadas de celdas
        int X = jugador.getX();
        int Y = jugador.getY();
        int xCeldaIni = (X - this.anchoVisible) < 0 ? 0 : (X - this.anchoVisible);
        int xCeldaFin = (X + this.anchoVisible) >= laberinto.getAncho() ? (laberinto.getAncho()-1) : (X + this.anchoVisible);
        int yCeldaIni = (Y - this.altoVisible) < 0 ? 0 : (Y - this.altoVisible);
        int yCeldaFin = (Y + this.altoVisible) >= laberinto.getAlto() ? (laberinto.getAlto()-1) : (Y + this.altoVisible);
        
        // Coordenadas del mapa desde las cuales se dibuja
        int xMapIni = mapCenter.x - tileSize/2 - (xCeldaFin-xCeldaIni)*tileSize;
        xMapIni = (xMapIni < 0) ? 0 : xMapIni;
        int yMapIni = mapCenter.y - tileSize/2 - (yCeldaFin-yCeldaIni)*tileSize;
        yMapIni = (yMapIni < 0) ? 0 : yMapIni;
        System.out.println("Centro: " + mapCenter.toString());
        System.out.println("xMapIni: " + xMapIni + ", yMapIni: " + yMapIni);
        for(int i = yCeldaIni; i <= yCeldaFin; i++){
            for(int j = xCeldaIni; j <= xCeldaFin;j++){
                int xImg = xMapIni + j*tileSize;
                int yImg = yMapIni + i*tileSize;
                laberinto.get(j, i).draw(g, xImg, yImg);
            }
            System.out.println();
        }
    }
    
    public void dibujarInfoJugador(Avatar jugador)
    {
        String info = "Nombre: " + jugador.getNombre() + "\n";
        info += "HP: " + jugador.getCurrentHP() + "/" + jugador.getMaxHP() + "\n";
        info += "Nivel: " + jugador.getNivel() + "\n";
        info += "Arma: " + ((jugador.getArma() == null) ? "Ninguna" : jugador.getArma().toString()) + "\n";
        info += "Armadura: " + ((jugador.getArmadura() == null) ? "Ninguna" : jugador.getArmadura().toString()) + "\n";
        info += "Saco: \n";
        info += ((jugador.getSaco().empty()) ? "Vacío" : jugador.getSaco().toString());
        this.window.getSideBar().setPlayerInfo(jugador.toString());
    }
    
    public void showError(String err)
    {
        this.showMessage("Error", err, JOptionPane.ERROR_MESSAGE);
    }
    
    public void showMessage(String msg)
    {
        this.showMessage("Mensaje", msg);
    }
    
    public void showMessage(String title, String msg)
    {
        this.showMessage(title, msg, JOptionPane.PLAIN_MESSAGE);
    }
    
    public void showMessage(String title, String msg, int JOptionMsgType)
    {
        JOptionPane.showMessageDialog(this.window, msg, title, JOptionMsgType);
    }
    
    public void showBattleInterface(Avatar jugador, Entidad enemigo)
    {
        this.window.getSideBar().showBattleArea();
        
        String data = "Heroe: " + jugador.getNombre();
        data += " - Vida Actual: " + jugador.getCurrentHP();
        data += " - Defensa: " + 
                ((jugador.getArmadura() == null) ? 0 : jugador.getArmadura().getDefensa());
        data += " \nvs\n";
        data += "Enemigo: " + enemigo.getNombre();
        data += " - Vida Actual: " + enemigo.getCurrentHP() + " - Defensa: " + 
                ((enemigo.getArmadura() == null) ? 0 : enemigo.getArmadura().getDefensa());
        
        this.window.getSideBar().setBattleInterface(data);
    }
    
    public void hideBattleInterface()
    {
        this.window.getSideBar().hideBattleArea();
    }
    
    /**
     * Este método será modificado para que se muestre en la barra lateral.
     * Por ahora solo delega a {@link #showInputPopup}
     * 
     * @param prompt El mensaje a mostrar al usuario
     * @return Lo ingresado por el usuario
     * 
     * @see showInputPopup(String)
     */
    public String showInputPrompt(String prompt)
    {
        return this.showInputPopup(prompt);
    }
    
    /**
     * Este método muestra una ventana de diálogo donde el jugador ingresará
     * sus datos mediante {@link JOptionPane.showInputDialog}
     * 
     * @param prompt El mensaje a mostrar al usuario
     * @return Lo ingresado por el usuario
     */
    public String showInputPopup(String prompt)
    {
        String input = JOptionPane.showInputDialog(this.window, prompt, "", JOptionPane.PLAIN_MESSAGE);
        return input;
    }
    
    public void showPauseScreen()
    {
        System.out.println("Presione Enter para continuar...");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
    
    public void showStory(String story, String playerName)
    {
        story = story.replaceAll("\\{playerName\\}", playerName);
        JFrame ventana = new JFrame();
        JOptionPane.showMessageDialog(ventana, story, "Historia", JOptionPane.PLAIN_MESSAGE);
        ventana.dispose();
    }
    
    public void closeWindow()
    {
        this.window.dispose();
    }
    
    public GameWindow getWindow()
    {
        return this.window;
    }
    
    public ImageLoader getImageLoader()
    {
        return this.imgLoader;
    }
}
