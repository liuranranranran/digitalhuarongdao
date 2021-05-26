package digitalhuarongdao;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.SimpleFormatter;

import javax.print.PrintService;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

import digitalhuarongdao.BeginFrame.JLabelTimerTask;

public class MainJFrame extends JFrame implements KeyListener, ActionListener,GameFrame{

	Music gamesound = new Music("src/music/Hea2t - ���ŷ����Ů.wav");
	Vector<Records> history = BeginFrame.history;
	
	//����
    int order = BeginFrame.getOrder();
    
    //ʱ��
    JLabel timecostLabel = new JLabel("00:00");
    Timer tmr = new Timer();
    boolean stop = false;
    String timecoststr;
    
    //������ʽ
    int operationWaychoice = BeginFrame.getWay();
	
	//��Ա��������������ʼ��һ����ά����������ͼƬ
    int data[][]=new int[order][order];

    // ��¼�հ׿������λ��
    int x0=0;
    int y0=0;
    
    //������ʽ��¼
    int operationWayc = 1;

    //��¼����
    int count=0;
    
    
    //�ȶ�ʤ��ʹ�õ�����
    
    int[][] win = new int[order][order];
    
    //���ݳ�ʼ��������
    int countforwin = 1;
    int countforrandom = 0;
    
    //JMenuItem
    JMenuItem restart,back;
    
    /*
     * ��ʼ��
     */
    public MainJFrame(){
        //��ʼ������
        this.initFJrame();

        //��ʱ
        ConfigTimeCostShower();
        
        //����
        gamesound.start();
        
        //��ʼ������
        this.InitDate();
        

        //���ƽ���
        this.paintView();

        //��ʼ���˵�
        this.InitMenu();

        //���ô��ڿɼ�
        super.setVisible(true);
        
    }


	/**
     *�˷������ڳ�ʼ������
     */
    public void initFJrame(){
        //���ô�������
        super.setTitle("���ֻ��ݵ�   "+order+"��");

        //���ô��ڴ�С
        super.setSize(514,595);

        //���ô��ھ���
        super.setLocationRelativeTo(null);

        //���ô��ڹر�ģʽ
        super.setDefaultCloseOperation(2);

        //������С
        super.setResizable(false);
        
        //���̼���
        this.addKeyListener(this);

        //ȡ��Ĭ�ϲ���
        super.setLayout(null);
    }

    /**
     * ��ʼ���˵�
     */
    public void InitMenu(){
        //����JMenuBar
        JMenuBar jMenuBar=new JMenuBar();

        //����JMenu(����)
        JMenu jMenu=new JMenu("����");

        //���¿�ʼ����
        restart=new JMenuItem("���¿�ʼ");
        restart.addActionListener(this);
        back = new JMenuItem("��");
        back.addActionListener(this);

        //������¿�ʼ,���ص�jMenu��
        jMenu.add(restart);
        jMenu.add(back);

        //��ӹ��ܵ�jMenuBar��
        jMenuBar.add(jMenu);

        //���ò˵�
        super.setJMenuBar(jMenuBar);
    }

    /*
     * ��ʼ������
     */
    public void InitDate(){
    	
       
    	
    	
        int nums[]=new int[order*order];
        int inversenumber = 0;
        for(int i = 0;i<order*order;i++) {
        		nums[i] = countforrandom;
        		countforrandom++;
        }
        
        for(int i = 0;i<order;i++) {
        	for (int j = 0;j<order;j++) {
        		win[i][j] = countforwin;
        		countforwin++;
        	}
        }
        win[order-1][order-1]=0;
        countforwin = 1;
        countforrandom = 0;
        
        //��������������
        do {
        	inversenumber = 0;
        	Random r=new Random();
        	for (int i = 0; i < nums.length; i++) {
        		int index=r.nextInt(nums.length);
        		int temp=nums[i];
        		nums[i]=nums[index];
        		nums[index]=temp;
        	}
        	
        	//���ո�������½�
        	for(int i = 0;i < nums.length-1;i++) {
        		int temp = 0;
        		if(nums[i] == 0) {
        			temp = nums[i];
        			nums[i] = nums[nums.length-1];
        			nums[nums.length-1] = 0;
        		}
        	}
        	//�������������ж��Ƿ��н�
        	for(int i = 0;i < nums.length-2;i++) {
        		for(int j = i+1;j < nums.length-1;j++) {
        			if(nums[i] > nums[j]) {
        				inversenumber++;
        			}
        		}
        	}
        }while(inversenumber%2 == 1);
        System.out.println(inversenumber);
        for (int i = 0; i < nums.length; i++) {
            //�����ҵ�����ŵ�date������
            data[i/order][i%order]=nums[i];
            if(nums[i]==0)
            {
                //���ڼ�¼�հ׷��������
                x0=i/order;
                y0=i%order;
            }
        }
    }

    
    /**
     * ������Ϸ����
     */
    public void paintView(){
        //�Ƴ����������е���������
        this.getContentPane().removeAll();
        
        //��ʾ��ʱ
    	timecostLabel.setBounds(new Rectangle(100,0,100,20));
    	this.add(timecostLabel);
        this.getContentPane().add(timecostLabel);
        
        //���ò�����ʾ
        JLabel jL_num=new JLabel("����"+count);
        jL_num.setBounds(0,0,100,20);
        this.getContentPane().add(jL_num);

        //����ʤ��ͼƬ
        if(this.victory()){
        	URL winurl = getClass().getResource("/image/��Ϸʤ��.png");
            JLabel win=new JLabel(new ImageIcon(winurl));
            win.setBounds(124,230,266,200);
            this.getContentPane().add(win);
            stop = true;
            WriteHistory("src/text/history.txt",history);
        }
        int lengthofside = 0;
        switch (order){
        case 3:
        	lengthofside = 135;
        	break;
        case 4:
        	lengthofside = 100;
        	break;
        case 5:
        	lengthofside = 80;
        	break;
        case 6:
        	lengthofside = 67;
        	break;
        case 7:
        	lengthofside = 57;
        	break;
        case 8:
        	lengthofside = 50;
        	break;
        }
        
        //���÷���
        for (int i = 0; i < order; i++)
        {
            for (int j = 0; j < order; j++)
            {
            	
            	URL url = this.getClass().getResource("/image/"+data[i][j]+".png");
            	ImageIcon chesspieces = new ImageIcon(url);
            	Image img = chesspieces.getImage();
        		img = img.getScaledInstance(lengthofside, lengthofside, Image.SCALE_AREA_AVERAGING);
        		chesspieces.setImage(img);
                JLabel jLabel=new JLabel(chesspieces);
                jLabel.setBounds(50+lengthofside*j,90+lengthofside*i,lengthofside,lengthofside);
                super.getContentPane().add(jLabel);
            }
        }

        //���ñ�������
        URL background_url = getClass().getResource("/image/3T@]$_3XX5~~FA]L6A98F2X.png");
        JLabel background=new JLabel(new ImageIcon(background_url));
        background.setBounds(0,0,514,595);
        super.getContentPane().add(background);

        //ˢ�½���
        this.getContentPane().repaint();
        
    }
    

    private void ConfigTimeCostShower() {
		tmr.scheduleAtFixedRate(new JLabelTimerTask(),0,1000);
	}
	
	protected class JLabelTimerTask extends TimerTask {
		
		 SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		 Date StartTime = new Date();
		/*
		 * ��ȡʱ��
		 */
		public void run() {
			while(!stop){
				timecoststr = sdf.format(new Date().getTime()-StartTime.getTime());
				timecostLabel.setText(timecoststr);
	        }
			
		}
	}
    /**
     * �жϵ�ǰ��������, ��ʤ�����������Ƿ���ͬ
     */
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ��ʾ���������ƶ�
     * */
    public void moveToLeft(){
        data[x0][y0]=data[x0][y0+1];
        data[x0][y0+1]=0;
        y0++;
    } 
    public void moveToLeft2(){
        data[x0][y0]=data[x0][y0-1];
        data[x0][y0-1]=0;
        y0--;
    }

    /**
     * ��ʾ���������ƶ�
     * */
    public void moveUp(){
        data[x0][y0]=data[x0+1][y0];
        data[x0+1][y0]=0;
        x0++;
    }
    public void moveUp2(){
        data[x0][y0]=data[x0-1][y0];
        data[x0-1][y0]=0;
        x0--;
    }

    /**
     * ��ʾ���������ƶ�
     * */
    public void moveToRight(){
        data[x0][y0]=data[x0][y0-1];
        data[x0][y0-1]=0;
        y0--;
    }
    public void moveToRight2(){
        data[x0][y0]=data[x0][y0+1];
        data[x0][y0+1]=0;
        y0++;
    }

    /**
     * ��ʾ���������ƶ�
     * */
    public void moveDown(){
        data[x0][y0]=data[x0-1][y0];
        data[x0-1][y0]=0;
        x0--;
    }
    public void moveDown2(){
        data[x0][y0]=data[x0+1][y0];
        data[x0+1][y0]=0;
        x0++;
    }
     
    //���̰��£�������������(KeyListener) 
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (operationWaychoice == 1) {
        	if (keyCode == 37) {
        		if (y0 == (order - 1)) {
        			return;
        		} else {
        			// ���ƶ�
        			moveToLeft();
        			count++;
        		}
        	} else if (keyCode == 38) {
        		if (x0 == (order - 1)) {
        			return;
        		} else {
        			// ���ƶ�
        			moveUp();
        			count++;
        		}
        	} else if (keyCode == 39) {
        		if (y0 == 0) {
        			return;
        		} else {
        			// ���ƶ�
        			moveToRight();
        			count++;
        		}
        		
        	} else if (keyCode == 40) {
        		if (x0 == 0) {
        			return;
        		} else {
        			// ���ƶ�
        			this.moveDown();
        			count++;
        		}
        	}
        }
        else if(operationWaychoice == 2) {
        	if (keyCode == 37) {
        		if (y0 == 0) {
        			return;
        		} else {
        			// ���ƶ�
        			moveToLeft2();
        			count++;
        		}
        	} else if (keyCode == 38) {
        		if (x0 == 0) {
        			return;
        		} else {
        			// ���ƶ�
        			moveUp2();
        			count++;
        		}
        	} else if (keyCode == 39) {
        		if (y0 == (order-1)) {
        			return;
        		} else {
        			// ���ƶ�
        			moveToRight2();
        			count++;
        		}
        		
        	} else if (keyCode == 40) {
        		if (x0 == (order-1)) {
        			return;
        		} else {
        			// ���ƶ�
        			this.moveDown2();
        			count++;
        		}
        	}
        }
        if (keyCode == 32) {
        	//����ʹ�ã�����space
        	data = new int[order][order];
        	for(int i = 0;i<order;i++) {
        		for (int j = 0;j<order;j++) {
        			data[i][j] = countforwin;
        			countforwin++;
        		}
        	}
        	data[order-1][order-1] = 0;
        	countforwin = 1;
        }

        // ���ƴ���
        paintView();
    }

    // ���¿�ʼ(ActionListener)
   	public void actionPerformed(ActionEvent e) {
   		if(e.getSource()==restart) {
   			//��ʼ������
   			count=0;
   			
   			//��ʼ������
   			InitDate();
   			//���ƴ���
   			paintView();
        }
   		else if(e.getSource()==back) {
   			//�ر���Ϸ
   			gamesound.stop();
   			dispose();
   			try {
				BeginFrame.ReadHistory();
				gametest.creatBeginner();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
   		}
    }
    
    public void keyTyped(KeyEvent e){}

    public void keyReleased(KeyEvent e) {}

	@Override
	public boolean stepLeft() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void WriteHistory(String FileName,Vector<Records> vector) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String gameDate = sdfDate.format(new Date());
		Records records  = new Records(timecoststr,gameDate,String.valueOf(order),String.valueOf(count));
		vector.addElement(records);
		try {
			RecordsLoader.write(FileName, vector);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

                

