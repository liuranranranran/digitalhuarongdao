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
		// 本控件的宽度和高度
		int width = this.getWidth();
		int height = this.getHeight();

		// 清除显示
		g.clearRect(0, 0, width, height);

		/////////////////////////
		
		try {
			Image image = imageFromResource("/image/F6{C5_7VJ}Y_]4]{I}WEXAV.jpg");
			
			// 占满整个控件
			g.drawImage(image, 0, 0, width, height,  null);
			//透明度
			g.setColor(new Color(255,255,255,100));
			g.fillRect(0,0,width,height);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	// 从资源加载图片
	private Image imageFromResource(String imagePath) throws Exception
	{
		URL imageUrl = this.getClass().getResource(imagePath);
		BufferedImage image = ImageIO.read(imageUrl);
		return image;
	}
	
}
