package digitalhuarongdao;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import digitalhuarongdao.MainJFrame.JLabelTimerTask;

public class LimitedStepFrame extends JFrame implements KeyListener, ActionListener,GameFrame{
	 //阶数
    int order = BeginFrame.getOrder();
    
    //时间
    JLabel timecostLabel = new JLabel("00:00");
    Timer tmr = new Timer();
    boolean stop = false;
    
    //操作方式选择
    int operationWaychoice = BeginFrame.getWay();
	
	//成员变量————初始化一个二维数组来设置图片
    int data[][]=new int[order][order];

    // 记录空白块的索引位置
    int x0=0;
    int y0=0;
    
    //操作方式记录
    int operationWayc = 1;
    
    //点
    private Point iniPt;

    //记录步数
    int count=100;

    //比对胜利使用的数组
    int[][] win = new int[order][order];
    //数据初始化计数器
    int countforwin = 1;
    int countforrandom = 0;
    //JMenuItem
    JMenuItem restart,back;
    
    public LimitedStepFrame() {
    	//初始化窗口
        this.initFJrame();
        
        //计时
        ConfigTimeCostShower();

        //初始化数据
        this.InitDate();

        //绘制界面
        this.paintView();

        //初始化菜单
        this.InitMenu();

        //设置窗口可见
        super.setVisible(true);
    }
	@Override
	public void initFJrame() {
		// TODO Auto-generated method stub
		//设置窗口名字
        super.setTitle("数字华容道   "+order+"阶   " +"Limited Step");

        //设置窗口大小
        super.setSize(514,595);

        //设置窗口居中
        super.setLocationRelativeTo(null);

        //设置窗口关闭模式
        super.setDefaultCloseOperation(2);

        //键盘监听
        this.addKeyListener(this);

        //取消默认布局
        super.setLayout(null);
	}

	@Override
	public void InitMenu() {
		// TODO Auto-generated method stub
		//创建JMenuBar
        JMenuBar jMenuBar=new JMenuBar();

        //创建JMenu(功能)
        JMenu jMenu=new JMenu("功能");

        //重新开始返回
        restart=new JMenuItem("重新开始");
        restart.addActionListener(this);
        back = new JMenuItem("←");
        back.addActionListener(this);

        //添加重新开始,返回到jMenu中
        jMenu.add(restart);
        jMenu.add(back);

        //添加功能到jMenuBar中
        jMenuBar.add(jMenu);

        //设置菜单
        super.setJMenuBar(jMenuBar);
	}

	@Override
	public void InitDate() {
		// TODO Auto-generated method stub
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
        
        System.out.println(order);
        //打乱数组中数据
        do {
        	inversenumber = 0;
        	Random r=new Random();
        	for (int i = 0; i < nums.length; i++) {
        		int index=r.nextInt(nums.length);
        		int temp=nums[i];
        		nums[i]=nums[index];
        		nums[index]=temp;
        	}
        	
        	//将空格放在右下角
        	for(int i = 0;i < nums.length-1;i++) {
        		int temp = 0;
        		if(nums[i] == 0) {
        			temp = nums[i];
        			nums[i] = nums[nums.length-1];
        			nums[nums.length-1] = 0;
        		}
        	}
        	//计算逆序数以判断是否有解
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
            //将打乱的数组放到date数组中
            data[i/order][i%order]=nums[i];
            if(nums[i]==0)
            {
                //用于记录空白方块的坐标
                x0=i/order;
                y0=i%order;
            }
        }
	}

	@Override
	public void paintView() {
		// TODO Auto-generated method stub
		//移除掉，界面中的所有内容
        this.getContentPane().removeAll();
        
        //显示耗时
    	timecostLabel.setBounds(new Rectangle(100,0,100,20));
    	this.add(timecostLabel);
        this.getContentPane().add(timecostLabel);
        
        //设置步数显示
        JLabel jL_num=new JLabel("剩余步数"+count);
        jL_num.setBounds(0,0,100,20);
        this.getContentPane().add(jL_num);

        //设置胜利图片
        if(this.victory()){
        	URL winUrl = getClass().getResource("/image/F6{C5_7VJ}Y_]4]{I}WEXAV.jpg");
            JLabel win=new JLabel(new ImageIcon(winUrl));
            win.setBounds(124,230,266,88);
            this.getContentPane().add(win);
            stop = true;
        }
        else if(!this.stepLeft()) {
        	URL stepOutUrl = getClass().getResource("/image/失败.png");
        	JLabel loss = new JLabel(new ImageIcon(stepOutUrl));
        	loss.setBounds(30,50,480,480);
        	this.getContentPane().add(loss);
        	stop = true;
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
        
        //设置方块
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

        //设置背景布局
        URL background_url = getClass().getResource("/image/3T@]$_3XX5~~FA]L6A98F2X.png");
        JLabel background=new JLabel(new ImageIcon(background_url));
        background.setBounds(26,30,430,484);
        super.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();
	}

	@Override
	public boolean victory() {
		// TODO Auto-generated method stub
		 for (int i = 0; i < data.length; i++) {
	            for (int j = 0; j < data[i].length; j++) {
	                if(data[i][j]!=win[i][j]){
	                    return false;
	                }
	            }
	        }
	     return true;
	}

	@Override
	public void moveToLeft() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0][y0+1];
        data[x0][y0+1]=0;
        y0++;
	}

	@Override
	public void moveToLeft2() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0][y0-1];
        data[x0][y0-1]=0;
        y0--;
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0+1][y0];
        data[x0+1][y0]=0;
        x0++;
	}

	@Override
	public void moveUp2() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0-1][y0];
        data[x0-1][y0]=0;
        x0--;
	}

	@Override
	public void moveToRight() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0][y0-1];
        data[x0][y0-1]=0;
        y0--;
	}

	@Override
	public void moveToRight2() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0][y0+1];
        data[x0][y0+1]=0;
        y0++;
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0-1][y0];
        data[x0-1][y0]=0;
        x0--;
	}

	@Override
	public void moveDown2() {
		// TODO Auto-generated method stub
		data[x0][y0]=data[x0+1][y0];
        data[x0+1][y0]=0;
        x0++;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==restart) {
   			//初始化步数
   			count=100;
   			
   			//初始化数据
   			InitDate();
   			//绘制窗体
   			paintView();
        }
   		else if(e.getSource()==back) {
   			//关闭游戏
   			dispose();
   		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
        if (operationWaychoice == 1) {
        	if (keyCode == 37) {
        		if (y0 == (order - 1)) {
        			return;
        		} else {
        			// 左移动
        			moveToLeft();
        			count--;
        		}
        	} else if (keyCode == 38) {
        		if (x0 == (order - 1)) {
        			return;
        		} else {
        			// 上移动
        			moveUp();
        			count--;
        		}
        	} else if (keyCode == 39) {
        		if (y0 == 0) {
        			return;
        		} else {
        			// 右移动
        			moveToRight();
        			count--;
        		}
        		
        	} else if (keyCode == 40) {
        		if (x0 == 0) {
        			return;
        		} else {
        			// 下移动
        			this.moveDown();
        			count--;
        		}
        	}
        }
        else if(operationWaychoice == 2) {
        	if (keyCode == 37) {
        		if (y0 == 0) {
        			return;
        		} else {
        			// 左移动
        			moveToLeft2();
        			count--;
        		}
        	} else if (keyCode == 38) {
        		if (x0 == 0) {
        			return;
        		} else {
        			// 上移动
        			moveUp2();
        			count--;
        		}
        	} else if (keyCode == 39) {
        		if (y0 == (order-1)) {
        			return;
        		} else {
        			// 右移动
        			moveToRight2();
        			count--;
        		}
        		
        	} else if (keyCode == 40) {
        		if (x0 == (order-1)) {
        			return;
        		} else {
        			// 下移动
        			this.moveDown2();
        			count--;
        		}
        	}
        }
        if (keyCode == 32) {
        	//测试使用，按键space
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

        // 绘制窗体
        paintView();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean stepLeft() {
		// TODO Auto-generated method stub
		if (count <= 0) {
			return false;
		}
		return true;
	}
	private void ConfigTimeCostShower() {
		tmr.scheduleAtFixedRate(new JLabelTimerTask(),0,1000);
	}
	
	protected class JLabelTimerTask extends TimerTask {
		
		 SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		 Date StartTime = new Date();
		 String timecoststr;
		/*
		 * 获取时间
		 */
		public void run() {
			while(!stop){
				timecoststr = sdf.format(new Date().getTime()-StartTime.getTime());
				timecostLabel.setText(timecoststr);
	        }
		}
	}
	
	@Override
	public void WriteHistory(String FileName, Vector<Records> vector) {
		// TODO Auto-generated method stub
	}
}
