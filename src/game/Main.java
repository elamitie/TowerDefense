package game;
import javax.swing.JFrame;

public class Main 
{
	public static void main(String[] args)
    {
		 JFrame window = new JFrame();
		 
		 window.setTitle("Tower Defense");
	     
		 window.setSize(965, 540);
	     // Puts frame to center of the screen.
		 window.setLocationRelativeTo(null);
	     // So that frame cannot be resized by the user.
		 window.setResizable(false);
	        
		 window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	     // Creates the instance of Game that extends the Canvas which is a JPanel and puts it on the frame.
	     //ContentPane is a top level container
		 window.setContentPane(Game.instance());
	        
		 window.setVisible(true);
    }
}
