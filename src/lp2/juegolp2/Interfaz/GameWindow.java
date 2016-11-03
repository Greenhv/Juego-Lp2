package lp2.juegolp2.Interfaz;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author pmvb
 */
public class GameWindow extends JFrame
{
    private JPanel mapPanel;
    private Sidebar sidebar;
    private int altoMapa;
    private int anchoMapa;
    private Dibujador dibujador;
    
    public GameWindow(int altoMapa, int anchoMapa, Dibujador dibujador)
    {
        this.altoMapa = altoMapa;
        this.anchoMapa = anchoMapa;
        this.dibujador = dibujador;
        
        this.init();
        
        this.pack();
        this.createBufferStrategy(2);
    }
    
    private void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        
        this.setLayout(new BorderLayout());
        this.initComponents();
        
        this.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt) 
            {
                windowKeyPressed(evt);
            }
        });
    }
    
    public void windowKeyPressed(java.awt.event.KeyEvent evt)
    {
        this.dibujador.keyPressed(evt);
    }
    
    private void initComponents()
    {
        int maxSize = Math.max(this.altoMapa, this.anchoMapa) * Dibujador.tileSize * 2 + Dibujador.tileSize;
        this.mapPanel = new Map(maxSize, maxSize, this);
        this.add(mapPanel, BorderLayout.CENTER);
        
        this.sidebar = new Sidebar(350, maxSize, this);
        this.add(sidebar, BorderLayout.LINE_END);
    }
    
    public JPanel getMapPanel()
    {
        return this.mapPanel;
    }
    
    public Sidebar getSideBar()
    {
        return this.sidebar;
    }
    
    public void setCommandInput(String input)
    {
        this.dibujador.setCommandInput(input);
    }
    
    public Dibujador getDibujador()
    {
        return this.dibujador;
    }
    
    public void useItemAction()
    {
        this.getDibujador().useItemAction();
        this.requestFocus();
    }
    
    public void quitGameAction()
    {
        this.getDibujador().quitGameAction();
        //this.requestFocus();
    }
    
    public void interactAction()
    {
        this.getDibujador().interactAction();
        this.requestFocus();
    }
}
