package digitalhuarongdao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
public class BeginFrame extends JFrame{
	static private BeginFrame instance = null;
	MyPanel cards =new MyPanel();
	String timestr ;
	static Vector<Records> history = new Vector();
	static Music homesound = new Music("src/music/������ - ȥ�����е�ԭҰ�������¾���.wav");
	
	//��ҳ�ؼ�
	JLabel welcome = new JLabel("Welcome to ���ֻ��ݵ�");
	JLabel timeshower = new JLabel("00:00");
	JButton beginner = new JButton ("��ʼ��Ϸ");
	JButton HistoryButton = new JButton("��ʷ��¼");
	JButton exit = new JButton("�˳�");
	JButton gamesettingButton = new JButton("����");
	JButton help = new JButton("����");
	
	//���ÿؼ�
	JButton setback = new JButton("��");
	
	//history�ؼ�
	JButton idback = new JButton("��");
	
	static int operationWayc = 1;
	
	//����ѡ��
	static int orderchoice = 0;
	JButton choiceback = new JButton("��");
	JButton order3 = new JButton("3��");
	JButton order4 = new JButton("4��");
	JButton order5 = new JButton("5��");
	JButton order6 = new JButton("6��");
	JButton order7 = new JButton("7��");
	JButton order8 = new JButton("8��");
	
	//ģʽѡ��
	JButton modelback = new JButton("��");
	JButton classic = new JButton("����ģʽ");
	JButton stepLimited = new JButton("�޲�ģʽ"); 
	
	//help
	JButton helpback = new JButton("��");
	
	private BeginFrame (String title) {
		super(title);

		ConfigTimeShower();
		homesound.start();
		
		Container contentPanel = getContentPane();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(cards,BorderLayout.CENTER);
		
		MyPanel beginPanel = new MyPanel();
		beginPanel.setLayout(null);
		beginPanel.add(welcome);
		welcome.setFont(new Font("΢���ź�",0,14));
		welcome.setForeground(Color.pink);
		beginPanel.add(beginner);//��ʼ��Ϸ
		beginPanel.add(HistoryButton);
		beginPanel.add(gamesettingButton);
		beginPanel.add(help);
		beginPanel.add(exit);
		//��ҳʱ��
		beginPanel.add(timeshower);
		timeshower.setBounds(250, 10, 50, 10);
		
		
		MyPanel HistoryPanel =new MyPanel();
		HistoryPanel.setLayout(null);
		HistoryPanel.add(idback);
		
		MyPanel gamesettingPanel = new MyPanel();
		gamesettingPanel.setLayout(null);
		gamesettingPanel.add(setback);
		
		MyPanel OrderChoicePanel = new MyPanel();
		OrderChoicePanel.setLayout(null);
		OrderChoicePanel.add(choiceback);
		OrderChoicePanel.add(order3);
		OrderChoicePanel.add(order4);
		OrderChoicePanel.add(order5);
		OrderChoicePanel.add(order6);
		OrderChoicePanel.add(order7);
		OrderChoicePanel.add(order8);
		
		MyPanel ModelPanel = new MyPanel();
		ModelPanel.setLayout(null);
		ModelPanel.add(modelback);
		ModelPanel.add(classic);
		ModelPanel.add(stepLimited);
		
		//help
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(null);
		JTextArea userhelp;
		try{ userhelp = new JTextArea(getHelp());}catch(Exception e) {e.printStackTrace();userhelp = null;}
		userhelp.setLineWrap(true);
		userhelp.setEditable(false);
		userhelp.setBackground(new Color(238,238,238));
		helpPanel.add(userhelp);
		helpPanel.add(helpback);
		userhelp.setBounds(new Rectangle(0,30,260,460));
		helpback.setBounds(new Rectangle(0,10,50,20));
		
		//��Ƭ����
		cards.setLayout(new CardLayout());
		cards.add(beginPanel,"initial");
		cards.add(HistoryPanel,"History");
		cards.add(gamesettingPanel,"Gameset");
		cards.add(OrderChoicePanel,"choice");
		cards.add(ModelPanel,"Model");
		cards.add(helpPanel,"Help");
		
		//initial����
		welcome.setBounds(new Rectangle(70, 70, 300, 20));
		welcome.setPreferredSize(new Dimension(150,60));
		beginner.setBounds(new Rectangle(100,110,100,20));
		HistoryButton.setBounds(new Rectangle(100,150,100,20));
		gamesettingButton.setBounds(new Rectangle(115,190,70,20));
		help.setBounds(new Rectangle(115, 230, 70, 20));
		exit.setBounds(new Rectangle(115, 270, 70, 20));
		
		//history����
		Vector<Records> history = new Vector();
		try {
			history = ReadHistory();
			CountHistory();
		}catch(Exception e) {
			e.printStackTrace();
		}
		JLabel la = new JLabel("��ʱ(s)"+"|"+"����"+"|"+"����"+"|"+"����");
		HistoryPanel.add(la);
		la.setBounds(new Rectangle(0,20*2,200,20));
		int index = 1;
		ReverseHistory();
		for(Iterator<Records> i = history.iterator();i.hasNext();) {
			Records records = i.next();
			JLabel l = new JLabel(records.getCost()+"|"+records.getDate()+"|"+records.getOrder()+"|"+records.getStep());
			HistoryPanel.add(l);
			l.setBounds(new Rectangle(0,20*(index+2),200,20));
			index++;
		}
		ReverseHistory();
		idback.setBounds(new Rectangle(0,10,50,20));
		
		//Gameset����
		setback.setBounds(new Rectangle(0,10,50,20));
      
		/*
        * ������ʽ
        */
		String[] operationChoice = {"���̲���1","���̲���2"};
        JComboBox operationWay = new JComboBox(operationChoice);
        operationWay.setBounds(new Rectangle(100,200,100,20));
        
        gamesettingPanel.add(operationWay);
        operationWay.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(operationWay.getSelectedItem() == "���̲���1") {operationWayc = 1;}
				else if(operationWay.getSelectedItem() == "���̲���2") {operationWayc = 2;}
			}
		});
		
		//OrderChoicePanel����
        choiceback.setBounds(new Rectangle(0,10,50,20));
		order3.setBounds(new Rectangle(120,50,60,20));
		order4.setBounds(new Rectangle(120,90,60,20));
		order5.setBounds(new Rectangle(120,130,60,20));
		order6.setBounds(new Rectangle(120,170,60,20));
		order7.setBounds(new Rectangle(120,210,60,20));
		order8.setBounds(new Rectangle(120,250,60,20));
		
		//ModelPanel����
		modelback.setBounds(new Rectangle(0,10,50,20));
		classic.setBounds(new Rectangle(90,50,100,20));
		stepLimited.setBounds(new Rectangle(90,150,100,20));
			
		//listeners for begin
		MyButtonListener mainlistener = new MyButtonListener();
		gamesettingButton.addActionListener(mainlistener);//����	
		HistoryButton.addActionListener(mainlistener);//��ʷ��¼
		beginner.addActionListener(mainlistener);//��ʼ��Ϸ
		help.addActionListener(mainlistener);//����
		exit.addActionListener(mainlistener);//�˳�
		
		MyButtonListener backlistener = new MyButtonListener();
		idback.addActionListener(backlistener);
		setback.addActionListener(backlistener);
		choiceback.addActionListener(backlistener);
		helpback.addActionListener(backlistener);
		modelback.addActionListener(backlistener);//����
		
		MyButtonListener choiceListener = new MyButtonListener();
		order3.addActionListener(choiceListener);//3��
		order4.addActionListener(choiceListener);//4��
		order5.addActionListener(choiceListener);//5��
		order6.addActionListener(choiceListener);//6��
		order7.addActionListener(choiceListener);//7��
		order8.addActionListener(choiceListener);//8��
		
		ModelListener classiclistener = new ModelListener();
		classic.addActionListener(classiclistener);
		ModelListener stepLimitedlistener = new ModelListener();
		stepLimited.addActionListener(stepLimitedlistener);
		//listeners end
	}
	
	
	static public BeginFrame getInstance() throws IOException {
		if(instance == null) {
			instance = new BeginFrame("���ֻ��ݵ�");
		}
		return instance;
	}
	
	public class MyButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout)cards.getLayout();
			if(e.getSource() == HistoryButton) {
				cardLayout.show(cards,"History");
			}
			else if(e.getSource() == gamesettingButton) {
				cardLayout.show(cards, "Gameset");
			}
			else if(e.getSource() == beginner) {
				cardLayout.show(cards, "choice");
			}
			else if(e.getSource() == idback||e.getSource() == setback
					||e.getSource() == choiceback||e.getSource() == helpback) {
				cardLayout.show(cards,"initial");
			}
			else if(e.getSource() == modelback) {
				cardLayout.show(cards, "choice");
			}
			else if(e.getSource() == help) {
				cardLayout.show(cards,"Help");
			}
			else if(e.getSource() == exit) {
				System.exit(0);
			}
			/*
			 * ������Ϸ����
			 */
			else if(e.getSource() == order3) {
				orderchoice = 3;
				cardLayout.show(cards, "Model");
			}
			else if(e.getSource() == order4) {
				orderchoice = 4;
				MainJFrame mainJFrame = new MainJFrame();
				homesound.stop();dispose();
			}
			else if (e.getSource() == order5) {
				orderchoice = 5;
				MainJFrame mainJFrame = new MainJFrame();
				homesound.stop();dispose();
			}
			else if (e.getSource() == order6) {
				orderchoice = 6;
				MainJFrame mainJFrame = new MainJFrame();
				homesound.stop();dispose();
			}
			else if (e.getSource() == order7) {
				orderchoice = 7;
				MainJFrame mainJFrame = new MainJFrame();
				homesound.stop();dispose();
			}
			else if (e.getSource() == order8) {
				orderchoice = 8;
				MainJFrame mainJFrame = new MainJFrame();
				homesound.stop();dispose();
			}
		}
	}
	public class ModelListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == classic) {
				MainJFrame mainJFrame = new MainJFrame();
				homesound.stop();dispose();
			}
			else if(e.getSource() == stepLimited) {
				LimitedStepFrame limitedStepFrame = new LimitedStepFrame();
				homesound.stop();dispose();
			}
			
		}
		
	}

	private void ConfigTimeShower() {
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new JLabelTimerTask(),new Date(),1000);
	}
	
	protected class JLabelTimerTask extends TimerTask {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		/*
		 * ��ȡʱ��
		 */
		public void run() {
			timestr = sdf.format(new Date());
			timeshower.setText(timestr);
		}
	}

	public String getHelp() throws IOException {
		
		String helptext = "";String line;
		BufferedReader bfr = new BufferedReader(new FileReader("src/text/help.txt")); 
		while((line = bfr.readLine()) != null) {
			helptext += line;
		}
		return helptext;
	}
	
	/*
	 * ���ݽ���
	 */
	public static int getOrder() {
		return orderchoice;
	}
	/*
	 * ������ʽ
	 */
	public static int getWay() {
		return operationWayc;
	}
	public static Vector ReadHistory() throws IOException{
		try {
			history = RecordsLoader.read("src/text/history.txt");
		}catch(Exception e) {
			e.printStackTrace();
		}
			return history;
	}
	public int CountHistory() {
		Iterator<Records> i = history.iterator();
		int k = 0;
		while(i.hasNext()) {
			k++;
			i.next();
		}
		while(k>20) {
			history.remove(0);
		}
		return k;
	}
	public void ReverseHistory() {
		int n = CountHistory();
		for(int k = 0;k<n/2;k++) {
			Records tmp = history.get(k);
			history.set(k,history.get(n-k-1));
			history.set(n-k-1, tmp);
		}
	}
}
