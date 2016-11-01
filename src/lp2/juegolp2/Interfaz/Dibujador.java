package lp2.juegolp2.Interfaz;

import java.util.Scanner;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import lp2.juegolp2.Mundo.*;
import lp2.juegolp2.Entidades.*;
import lp2.juegolp2.Facilidades.ImageLoader;
import lp2.juegolp2.Juego.Juego;

/**
 *
 * @author pmvb
 */
public class Dibujador implements Runnable
{   
    private int anchoVisible;
    private int altoVisible;
    public static int tileSize = 32;
    private GameWindow window;
    private BufferStrategy bs;
    private ImageLoader imgLoader;
    private Thread animator;
    private Juego juego;

    public Dibujador(Juego juego)
    {
        this.anchoVisible = 5;
        this.altoVisible = 10;
        this.window = new GameWindow(altoVisible, anchoVisible);
        this.bs = window.getBufferStrategy();
        this.imgLoader = new ImageLoader("assets/sprites", "loader");
        this.imgLoader.startLoader();
        this.juego = juego;
    }
    
    public void flush()
    {   
        System.out.print(new String(new char[30]).replace('\0', '\n'));
    }
    
    public void dibujarLaberinto(Laberinto laberinto)
    {
        JPanel mapPanel = this.window.getMapPanel();
        Rectangle mapBounds = mapPanel.getBounds();
        // Obtiene graficos
        Graphics g = this.bs.getDrawGraphics();
        g.fillRect(mapBounds.x, mapBounds.y, mapBounds.width, mapBounds.height);
        this.flush();
        //laberinto.draw(g, 0, 0); //Metodo de debug para revisar si el mapa es correctamente Dibujado
        
        // Coordenadas de celdas
        int xPlayer = laberinto.getPlayer().getPosition().getX();
        int yPlayer = laberinto.getPlayer().getPosition().getY();
        int xCeldaIni = (xPlayer - this.anchoVisible) < 0 ? 0 : (xPlayer - this.anchoVisible);
        int xCeldaFin = (xPlayer + this.anchoVisible) >= laberinto.getAncho() ? (laberinto.getAncho()-1) : (xPlayer + this.anchoVisible);
        int yCeldaIni = (yPlayer - this.altoVisible) < 0 ? 0 : (yPlayer - this.altoVisible);
        int yCeldaFin = (yPlayer + this.altoVisible) >= laberinto.getAlto() ? (laberinto.getAlto()-1) : (yPlayer + this.altoVisible);
        
        // Coordenadas del mapa desde las cuales se dibuja
        Point mapCenter = new Point(mapBounds.x + mapBounds.width/2, mapBounds.y + mapBounds.height/2);
        int xMapIni = 0;//mapCenter.x - (xCeldaFin-xCeldaIni)*tileSize/2;
        xMapIni = (xMapIni < 0) ? 0 : xMapIni;
        int yMapIni = 0;//mapCenter.y - (yCeldaFin-yCeldaIni)*tileSize/2;
        yMapIni = (yMapIni < 0) ? 0 : yMapIni;
        
        System.out.println("Centro: " + mapCenter.toString());
        System.out.println("xMapIni: " + xMapIni + ", yMapIni: " + yMapIni);
        
        laberinto.drawRegion(g, new Position(xMapIni, yMapIni), 
                            new Position(xCeldaIni, yCeldaIni), 
                            new Position(xCeldaFin, yCeldaFin));
        if (bs.contentsLost()) {
            System.out.println("Buffer contents lost");
        } else {
            bs.show();
            this.window.getSideBar().repaint();
        }
        g.dispose();
    }
    
    public void dibujarInfoJugador(Avatar jugador)
    {
        String info = "Nombre: " + jugador.getNombre() + "\n"
                    + "HP: " + jugador.getCurrentHP() + "/" + jugador.getMaxHP() + "\n"
                    + "Nivel: " + jugador.getNivel() + "\n"
                    + "Arma: " + ((jugador.getArma() == null) ? "Ninguna" : jugador.getArma().toString()) + "\n"
                    + "Armadura: " + ((jugador.getArmadura() == null) ? "Ninguna" : jugador.getArmadura().toString()) + "\n"
                    + "Saco: \n"
                    + ((jugador.getSaco().empty()) ? "Vacío" : jugador.getSaco().toString());
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
        
        String data = "Heroe: " + jugador.getNombre()
                    + " - Vida Actual: " + jugador.getCurrentHP()
                    + " - Defensa: " + 
                    ((jugador.getArmadura() == null) ? 0 : jugador.getArmadura().getDefensa())
                    + " \nvs\n"
                    + "Enemigo: " + enemigo.getNombre()
                    + " - Vida Actual: " + enemigo.getCurrentHP() + " - Defensa: " + 
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
        this.window.getSideBar().setCommandPrompt(prompt);
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
    
    public void startGame()
    {
        this.window.setVisible(true);
    }
    
    public void run()
    {

    }
}
