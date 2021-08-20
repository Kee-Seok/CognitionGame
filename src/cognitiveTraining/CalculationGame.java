package cognitiveTraining;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings({ "serial" })
public class CalculationGame extends JFrame implements ActionListener, MouseListener{

	int a, b, c, d;
	
	
	Image buffImage;
	Graphics graphics;
	
	Image calculationPanel = new ImageIcon("./temp/calculationPanel.png").getImage();
	Icon firstBtnImage = new ImageIcon("./temp/firstBtn.png");
	Icon firstBtnImage2 = new ImageIcon("./temp/firstBtn2.png");
	Icon firstBtnImage3 = new ImageIcon("./temp/firstBtn3.png");
	Icon secondBtnImage = new ImageIcon("./temp/secondBtn.png");
	Icon secondBtnImage2 = new ImageIcon("./temp/secondBtn2.png");
	Icon secondBtnImage3 = new ImageIcon("./temp/secondBtn3.png");
	Icon thirdBtnImage = new ImageIcon("./temp/thirdBtn.png");
	Icon thirdBtnImage2 = new ImageIcon("./temp/thirdBtn2.png");
	Icon thirdBtnImage3 = new ImageIcon("./temp/thirdBtn3.png");
	Icon fourthBtnImage = new ImageIcon("./temp/fourthBtn.png");
	Icon fourthBtnImage2 = new ImageIcon("./temp/fourthBtn2.png");
	Icon fourthBtnImage3 = new ImageIcon("./temp/fourthBtn3.png");
	Icon backToMainBtnImage = new ImageIcon("./temp/backToMainBtn.png");
	Icon backToMainBtnImage2 = new ImageIcon("./temp/backToMainBtn2.png");
	Icon exitBtnImage = new ImageIcon("./temp/exitBtn.png");
	Icon exitBtnImage2 = new ImageIcon("./temp/exitBtn2.png");
	
	JButton firstBtn = new JButton(firstBtnImage);
	JButton secondBtn = new JButton(secondBtnImage);
	JButton thirdBtn = new JButton(thirdBtnImage);
	JButton fourthBtn = new JButton(fourthBtnImage);
	
	JButton startBtn = new JButton("시작");
	JButton backToMainBtn = new JButton(backToMainBtnImage);
	JButton exitBtn = new JButton(exitBtnImage);
	
	JButton[] btnsArray = {firstBtn,secondBtn,thirdBtn,fourthBtn,backToMainBtn};
	ArrayList<JButton> btns = new ArrayList<JButton>();
	
	JLabel question = new JLabel("-",SwingConstants.CENTER);
	
	Cognition cognition = new Cognition();
	
	class Cal {
		
		public int plus(int a, int b) {
			
			return c;
		}
		
		public int minus(int a, int b) {
			
			return c;
		}
		
		public int times(int a, int b) {
			
			return c;
		}
		
		public int divide(int a, int b) {
			
			return c;
		}
	}
	
	@SuppressWarnings("static-access")
	public void paint(Graphics g) {
		buffImage = createImage(cognition.SCREEN_WIDTH,cognition.SCREEN_HEIGHT);
		graphics = buffImage.getGraphics();
		drawScreen(graphics);
		g.drawImage(buffImage, 0, 0, null);
		for(int i = 0; i < btns.size(); i++) {
			btns.get(i).repaint();
		}
		backToMainBtn.repaint();
	}
	
	public void drawScreen(Graphics g) {
		g.drawImage(calculationPanel, 0,0,null);
	}
	
	public CalculationGame() {
		setBtn();
		init();
	}
	
	@SuppressWarnings("static-access")
	public void init() {
		setSize(cognition.SCREEN_WIDTH,cognition.SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("계산능력 훈련");
		setUndecorated(true);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setBtn() {
		firstBtn.addMouseListener(this); secondBtn.addMouseListener(this); thirdBtn.addMouseListener(this); fourthBtn.addMouseListener(this); backToMainBtn.addMouseListener(this); exitBtn.addMouseListener(this);
		firstBtn.setBounds(10,350,300,70); firstBtn.setBorderPainted(false); firstBtn.setContentAreaFilled(false); firstBtn.setFocusPainted(false); firstBtn.addActionListener(this);
		secondBtn.setBounds(330,350,300,70); secondBtn.setBorderPainted(false); secondBtn.setContentAreaFilled(false); secondBtn.setFocusPainted(false); secondBtn.addActionListener(this);
		thirdBtn.setBounds(10,440,300,70); thirdBtn.setBorderPainted(false); thirdBtn.setContentAreaFilled(false); thirdBtn.setFocusPainted(false); thirdBtn.addActionListener(this);
		fourthBtn.setBounds(330,440,300,70); fourthBtn.setBorderPainted(false); fourthBtn.setContentAreaFilled(false); fourthBtn.setFocusPainted(false); fourthBtn.addActionListener(this);
		backToMainBtn.setBounds(559,540,71,71); backToMainBtn.setBorderPainted(false); backToMainBtn.setContentAreaFilled(false); backToMainBtn.setFocusPainted(false); backToMainBtn.addActionListener(this);
		exitBtn.setBounds(600,5,50,50); exitBtn.setFocusPainted(false); exitBtn.setContentAreaFilled(false); exitBtn.setBorderPainted(false); exitBtn.addActionListener(this);
		
		for(int i = 0; i < btnsArray.length; i++) {
			btns.add(btnsArray[i]);
		}
		add(firstBtn);
		add(secondBtn);
		add(thirdBtn);
		add(fourthBtn);
		add(backToMainBtn);
		add(exitBtn);
		
	}
	
	public void actionPerformed(ActionEvent e) { //버튼들의 기능
		if(e.getSource() == firstBtn) {
			int c = 1000000*1000000/1000000;
			System.out.println(c);
			
		}else if(e.getSource() == secondBtn) {
			int c = 1000000/1000000*1000000;
			System.out.println(c);
		}else if(e.getSource() == thirdBtn) {
			int i = '2'-'0';
			System.out.println(i);
		}else if(e.getSource() == fourthBtn) {
			System.out.println(4);
		}else if(e.getSource() == backToMainBtn) {
			this.setVisible(false);
			cognition.setVisible(true);
		}else if(e.getSource()==exitBtn) {
			System.exit(0);
		}
	}
	
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) { //버튼에 마우스 커서 들어갈때 버튼 모양 변화
		if(e.getSource()==firstBtn) {
			firstBtn.setIcon(firstBtnImage2);
		}else if(e.getSource()==secondBtn) {
			secondBtn.setIcon(secondBtnImage2);
		}else if(e.getSource()==thirdBtn) {
			thirdBtn.setIcon(thirdBtnImage2);
		}else if(e.getSource()==fourthBtn) {
			fourthBtn.setIcon(fourthBtnImage2);
		}else if(e.getSource()==backToMainBtn) {
			backToMainBtn.setIcon(backToMainBtnImage2);
		}else if(e.getSource()==exitBtn) {
			exitBtn.setIcon(exitBtnImage2);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) { //버튼에서 커서 나올때 버튼 모양 변화
		if(e.getSource()==firstBtn) {
			firstBtn.setIcon(firstBtnImage);
		}else if(e.getSource()==secondBtn) {
			secondBtn.setIcon(secondBtnImage);
		}else if(e.getSource()==thirdBtn) {
			thirdBtn.setIcon(thirdBtnImage);
		}else if(e.getSource()==fourthBtn) {
			fourthBtn.setIcon(fourthBtnImage);
		}else if(e.getSource()==backToMainBtn) {
			backToMainBtn.setIcon(backToMainBtnImage);
		}else if(e.getSource()==exitBtn) {
			exitBtn.setIcon(exitBtnImage);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==firstBtn) {
			firstBtn.setIcon(firstBtnImage3);
		}else if(e.getSource()==secondBtn) {
			secondBtn.setIcon(secondBtnImage3);
		}else if(e.getSource()==thirdBtn) {
			thirdBtn.setIcon(thirdBtnImage3);
		}else if(e.getSource()==fourthBtn) {
			fourthBtn.setIcon(fourthBtnImage3);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==firstBtn) {
			firstBtn.setIcon(firstBtnImage);
		}else if(e.getSource()==secondBtn) {
			secondBtn.setIcon(secondBtnImage);
		}else if(e.getSource()==thirdBtn) {
			thirdBtn.setIcon(thirdBtnImage);
		}else if(e.getSource()==fourthBtn) {
			fourthBtn.setIcon(fourthBtnImage);
		}
	}
}
