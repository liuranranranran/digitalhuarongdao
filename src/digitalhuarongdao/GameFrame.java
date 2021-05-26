package digitalhuarongdao;

import java.awt.event.KeyEvent;
import java.util.Vector;

public interface GameFrame {
	
	void initFJrame();
	void InitMenu();
	void InitDate();
	void paintView();
	void keyPressed(KeyEvent e);
	boolean victory();
	boolean stepLeft();
	void moveToLeft();
	void moveToLeft2();
	void moveUp();
	void moveUp2();
	void moveToRight();
	void moveToRight2();
	void moveDown();
	void moveDown2();
	void WriteHistory(String FileName, Vector<Records> vector);
}
