package digitalhuarongdao;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyPanel extends JPanel
{
	
	
	protected void paintComponent(Graphics g)
	{
		// ���ؼ��Ŀ�Ⱥ͸߶�
		int width = this.getWidth();
		int height = this.getHeight();

		// �����ʾ
		g.clearRect(0, 0, width, height);

		/////////////////////////
		
		try {
			Image image = imageFromResource("/image/F6{C5_7VJ}Y_]4]{I}WEXAV.jpg");
			
			// ռ�������ؼ�
			g.drawImage(image, 0, 0, width, height,  null);
			//͸����
			g.setColor(new Color(255,255,255,100));
			g.fillRect(0,0,width,height);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	// ����Դ����ͼƬ
	private Image imageFromResource(String imagePath) throws Exception
	{
		URL imageUrl = this.getClass().getResource(imagePath);
		BufferedImage image = ImageIO.read(imageUrl);
		return image;
	}
	
}
