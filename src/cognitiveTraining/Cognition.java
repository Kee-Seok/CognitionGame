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
	
	MainPanel mp = new MainPanel();
	
	public static final int SCREEN_WIDTH = 648;
	public static final int SCREEN_HEIGHT = 648;
	int aboveGab = 300; //패널 윗쪽부터 버튼까지의 갭
	int betweenBtnsGab = 20; //버튼끼리의 갭
	int btnWidth = 200;
	int btnHeight = 40;
	
	class MainPanel extends JPanel{
	
		public void paint(Graphics g) {
			buffImage = createImage(this.getWidth(),this.getHeight());
			graphics = buffImage.getGraphics();
			drawScreen(graphics);
			g.drawImage(buffImage, 0, 0, this);
			calculationGameBtn.repaint();
			memoryGameBtn.repaint();
		}
		public void drawScreen(Graphics g) {
			g.drawImage(mainPage.getImage(),0, 0, this);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(args);
		new Cognition();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==calculationGameBtn) {
			System.out.println("계산능력 훈련으로");
			this.setVisible(false);
			CalculationGame cal = new CalculationGame();
			cal.setVisible(true);
		}else if(e.getSource()==memoryGameBtn) {
			System.out.println("기억력 훈련으로");
		}
	}
	

	
	public Cognition() {
		init();
		
		setLocationRelativeTo(null);
		setBtn();
	}
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setUndecorated(true);
		setTitle("인지수업");
		setResizable(false);
		mp.setLayout(null);
		add(mp);
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setVisible(true);
	}
	
	public void setBtn() {
		calculationGameBtn.addActionListener(this);
		memoryGameBtn.addActionListener(this);
		calculationGameBtn.setBounds(SCREEN_WIDTH/2-btnWidth/2,aboveGab+betweenBtnsGab*1,btnWidth,btnHeight);
		
		memoryGameBtn.setBounds(SCREEN_WIDTH/2-btnWidth/2,aboveGab+btnHeight+betweenBtnsGab*2,btnWidth,btnHeight);
		calculationGameBtn.setBorderPainted(false);
		memoryGameBtn.setBorderPainted(false);
		mp.add(calculationGameBtn);
		mp.add(memoryGameBtn);
	}
	
	

}
