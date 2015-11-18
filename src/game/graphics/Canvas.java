package game.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public abstract class Canvas extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Canvas()
    {
        // We use double buffer to draw on the screen.
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);
    }
    
    
    // This method is overridden in game.java and is used for drawing to the screen.
    public abstract void draw(Graphics2D g2d);
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;        
        super.paintComponent(g2d);        
        draw(g2d);
    }
}
