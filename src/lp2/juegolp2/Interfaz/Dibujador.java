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
public class Dibujador
{   
    private int anchoVisible;
    private int altoVisible;
    public static int tileSize = 32;
    private GameWindow window;
    private BufferStrategy bs;
    private ImageLoader imgLoader;
    private Juego juego;
    private Juego.GameShared gameShared;
    private EnemyThread enemies;
    private ArtefactThread artefacts;

    public Dibujador(Juego juego)
    {
        this.anchoVisible = 5;
        this.altoVisible = 10;
        this.window = new GameWindow(altoVisible, anchoVisible, this);
        this.bs = window.getBufferStrategy();
        this.imgLoader = new ImageLoader("assets/sprites", "loader");
        this.imgLoader.startLoader();
        this.juego = juego;
        this.juego.giveKeyTo(this);
        this.enemies = new EnemyThread(this);
        this.artefacts = new ArtefactThread(this);
    }
    
    public void flush()
    {   
        System.out.print(new String(new char[30]).replace('\0', '\n'));
    }
    
    private void dibujarLaberinto(Graphics g, Laberinto laberinto)
    {
        JPanel mapPanel = this.window.getMapPanel();
        Rectangle mapBounds = mapPanel.getBounds();
        
        this.flush();
        
        // Coordenadas de celdas
        int xPlayer = laberinto.getPlayer().getPosition().getX();
        int yPlayer = laberinto.getPlayer().getPosition().getY();
        int xCeldaIni = (xPlayer - this.anchoVisible) < 0 ? 0 : (xPlayer - this.anchoVisible);
        int xCeldaFin = (xPlayer + this.anchoVisible) >= laberinto.getAncho() ? (laberinto.getAncho()-1) : (xPlayer + this.anchoVisible);
        int yCeldaIni = (yPlayer - this.altoVisible) < 0 ? 0 : (yPlayer - this.altoVisible);
        int yCeldaFin = (yPlayer + this.altoVisible) >= laberinto.getAlto() ? (laberinto.getAlto()-1) : (yPlayer + this.altoVisible);
        
        // Coordenadas del mapa desde las cuales se dibuja
        Point mapCenter = new Point(mapBounds.x + mapBounds.width/2, mapBounds.y + mapBounds.height/2);
        int xMapIni = mapCenter.x - (xCeldaFin-xCeldaIni-1)*tileSize/2;
        xMapIni = (xMapIni < 0) ? 0 : xMapIni;
        int yMapIni = mapCenter.y - (yCeldaFin-yCeldaIni-1)*tileSize/2;
        yMapIni = (yMapIni < 0) ? 0 : yMapIni;
        
        laberinto.drawRegion(g, new Position(xMapIni, yMapIni), 
                            new Position(xCeldaIni, yCeldaIni), 
                            new Position(xCeldaFin, yCeldaFin));
    }
    
    public void dibujarInfoJugador()
    {
        this.window.getSideBar().repaint();
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
        String enemyData = "Enemigo: " + enemigo.getNombre() + "\n"
                        + "Vida Actual: " + enemigo.getCurrentHP() + "\n"
                        + "Defensa: " + 
                        ((enemigo.getArmadura() == null) ? 0 : enemigo.getArmadura().getDefensa());
        
        this.window.getSideBar().setBattleInterface(enemyData);
        this.window.getSideBar().showBattleArea();
        this.window.getSideBar().repaint();
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
        JOptionPane.showMessageDialog(this.window, story, "Historia", JOptionPane.PLAIN_MESSAGE);
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
        this.enemies.start();
        this.artefacts.start();
        this.window.setVisible(true);
        //this.dibujarLaberinto(this.gameShared.getLaberintoActual());
    }
    
    public void setCommandInput(String input)
    {
        this.gameShared.processInput(input);
    }
    
    public void dibujarLaberinto()
    {
        //this.dibujarLaberinto(this.gameShared.getLaberintoActual());
        this.window.getMapPanel().repaint();
    }
    
    public void dibujarLaberinto(Graphics g)
    {
        this.dibujarLaberinto(g, this.gameShared.getLaberintoActual());
    }
    
    public void endGame()
    {
        this.gameShared.endGame();
        try {
            this.enemies.join();
            this.artefacts.join();
        } catch (InterruptedException ex) {
            
        }
    }
    
    public void receiveGameKey(Juego.GameShared shared)
    {
        this.gameShared = shared;
    }
    
    public Juego getJuego()
    {
        return this.juego;
    }
    
    public String getPlayerInfo()
    {
        Avatar jugador = this.gameShared.getJugador();
        String info = "Nombre: " + jugador.getNombre() + "\n"
                    + "HP: " + jugador.getCurrentHP() + "/" + jugador.getMaxHP() + "\n"
                    + "Nivel: " + jugador.getNivel() + "\n"
                    + "Arma: " + ((jugador.getArma() == null) ? "Ninguna" : jugador.getArma().toString()) + "\n"
                    + "Armadura: " + ((jugador.getArmadura() == null) ? "Ninguna" : jugador.getArmadura().toString()) + "\n"
                    + "Saco: \n"
                    + ((jugador.getSaco().empty()) ? "Vacío" : jugador.getSaco().toString());
        
        return info;
    }
    
    public void keyPressed(java.awt.event.KeyEvent evt)
    {
        this.gameShared.keyPressed(evt);
    }
    
    public void quitGameAction()
    {
        this.gameShared.processInput("salir");
    }
    
    public void useItemAction()
    {
        this.gameShared.processInput("usar");
    }
    
    public void interactAction()
    {
        this.gameShared.processInput("interactuar");
    }
    
    public synchronized void updateStage()
    {
        this.dibujarLaberinto();
        this.dibujarInfoJugador();
    }
    
    class EnemyThread extends Thread
    {
        Dibujador dib;
        public EnemyThread(Dibujador dib)
        {
            this.dib = dib;
        }
        
        @Override
        public void run()
        {
            while (!dib.gameShared.gameOver()) {
                dib.gameShared.moverEntidades();
                dib.updateStage();
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
    
    class ArtefactThread extends Thread
    {
        Dibujador dib;
        public ArtefactThread(Dibujador dib)
        {
            this.dib = dib;
        }
        @Override
        public void run()
        {
            while(!dib.gameShared.gameOver()) {
                dib.gameShared.moverArtefactos();
                dib.updateStage();
                
                try {
                    sleep(200);
                } catch (InterruptedException ex) {
                    
                }
            }
        }
    }
}
