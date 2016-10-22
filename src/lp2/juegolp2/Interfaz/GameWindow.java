package lp2.juegolp2.Interfaz;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author pmvb
 */
public class GameWindow extends JFrame
{
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel sidebar;
    private int tileSize = 32;
    private int altoMapa;
    private int anchoMapa;
    
    public GameWindow(int altoMapa, int anchoMapa)
    {
        this.altoMapa = altoMapa;
        this.anchoMapa = anchoMapa;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        this.mainPanel = new JPanel();
        mainPanel.setBackground(Color.black);
        mainPanel.setLayout(new BorderLayout());
        
        this.mapPanel = new JPanel();
        mapPanel.setPreferredSize(new Dimension(480, 480));
        mapPanel.setBackground(Color.yellow);
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        
        this.sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(160, 480));
        sidebar.setBackground(Color.red);
        mainPanel.add(sidebar, BorderLayout.LINE_END);
        
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }
    
    public JPanel getMapPanel()
    {
        return this.mapPanel;
    }
    
    public JPanel getSideBar()
    {
        return this.sidebar;
    }
}
