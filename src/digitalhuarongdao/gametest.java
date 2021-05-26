package digitalhuarongdao;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class gametest {
	public static void main(String[] args) {
       
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
			try {
				creatBeginner();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		});
	}
        public static void creatBeginner() throws IOException{
    		BeginFrame frame = BeginFrame.getInstance();
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setResizable(false);
    		frame.setSize(300, 500);
    		frame.setVisible(true);
    		try {
    			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
    		}
    		catch(Exception e) {
    		     e.printStackTrace();
    		}
    		SwingUtilities.updateComponentTreeUI(frame);
	}
}
