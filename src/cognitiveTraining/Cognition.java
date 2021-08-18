package cognitiveTraining;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Cognition extends JFrame implements ActionListener{
	
	ImageIcon mainPage = new ImageIcon("./temp/mainPage.png");
	Icon caculationIcon = new ImageIcon("./temp/calculationTraining.png");
	Icon memoryIcon = new ImageIcon("./temp/memoryTraining.png");
	JButton calculationGameBtn = new JButton(caculationIcon);
	JButton memoryGameBtn = new JButton(memoryIcon);
	
	Image buffImage;
	Graphics graphics;
	
	public static final int SCREEN_WIDTH = 648;
	public static final int SCREEN_HEIGHT = 648;
	int aboveGab = 300; //패널 윗쪽부터 버튼까지의 갭
	int betweenBtnsGab = 20; //버튼끼리의 갭
	int btnWidth = 200;
	int btnHeight = 40;
	
	JPanel btnPanel = new JPanel() {
		public void paint(Graphics g) {
			g.clearRect(0, 0, btnPanel.getWidth(), btnPanel.getHeight());
		}
	};
	
	public static void main(String[] args) {
		System.out.println(args);
		new Cognition();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==calculationGameBtn) {
			System.out.println("계산능력 훈련으로");
			this.setVisible(false);
			CaculationGame cal = new CaculationGame();
			cal.setVisible(true);
		}else if(e.getSource()==memoryGameBtn) {
			System.out.println("기억력 훈련으로");
		}
	}
	

	
	public Cognition() {
		init();
		
		setLocationRelativeTo(null);
		setBtn();
		setVisible(true);
	}
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setUndecorated(true);
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setTitle("인지수업");
		setResizable(false);
		btnPanel.setLayout(null);
		add(btnPanel);
	}
	
	public void setBtn() {
		calculationGameBtn.addActionListener(this);
		memoryGameBtn.addActionListener(this);
		calculationGameBtn.setBounds(SCREEN_WIDTH/2-btnWidth/2,aboveGab+betweenBtnsGab*1,btnWidth,btnHeight);
		
		memoryGameBtn.setBounds(SCREEN_WIDTH/2-btnWidth/2,aboveGab+btnHeight+betweenBtnsGab*2,btnWidth,btnHeight);
		calculationGameBtn.setBorderPainted(false);
		memoryGameBtn.setBorderPainted(false);
		btnPanel.add(calculationGameBtn);
		btnPanel.add(memoryGameBtn);
	}
	
	
	public void paint(Graphics g) { //메인페이지의 백그라운드 이미지
		buffImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		graphics = buffImage.getGraphics();
		drawScreen(g);
	}
	public void drawScreen(Graphics g) {
		graphics.drawImage(mainPage.getImage(),0,0,this);
		g.drawImage(buffImage, 0, 0,  this);
	}

}
