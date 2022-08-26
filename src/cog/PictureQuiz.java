package cog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PictureQuiz extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String QUESTION = "위의 그림은 무엇일까요?";
	JButton refreshBtn = new JButton(new ImageIcon(new ImageIcon("temp/refresh.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
	static File dir = new File("quizImg");
	static File[] files = dir.listFiles();
	Image[] img = new Image[files.length];
	Image drawImg; 
	String[] imgName = new String[files.length];
	String question = QUESTION;

	int gameIndex = 0; // 0:사진 + 질문, 1:사진 + 정답, 2:다음사진 + 질문
	int questionIndex = 0; //문제수에 관한 인덱스
	int totalIndex = 0; //엔터키 누를때마다 증가하는 전체 인덱스
	PicPanel pic = new PicPanel(); //사진 출력하는 패널
	AnsPanel ans = new AnsPanel(); //정답 출력하는 패널
	public PictureQuiz() {
		setLayout(null);
		getImages();
		System.out.println(img[0]);
		System.out.println(imgName[0]);
		System.out.println(files.length);
		pic.setBounds(Main.screenW/10,Main.screenH/20,Main.screenW*8/10,Main.screenH*14/20);
		ans.setBounds(Main.screenW/10,Main.screenH*16/20,Main.screenW*8/10,Main.screenH*3/20);
		refreshBtn.setBounds(Main.screenW*73/80,Main.screenH/20,60,60);
		refreshBtn.setContentAreaFilled(false);
		refreshBtn.setBorderPainted(false);
		refreshBtn.addActionListener(new ActionSettings());
		refreshBtn.setRolloverIcon(new ImageIcon(new ImageIcon("temp/refresh2.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		init();
		add(refreshBtn);
		add(pic);
		add(ans);
	}
	public void init() {
		questionIndex = 0;
		drawImg = img[questionIndex];
		question = QUESTION;
		gameIndex++;
		this.addKeyListener(new KeySettings());
	}
	public void proceedGame() {
		if(gameIndex == 1) {
			question = imgName[questionIndex];
			gameIndex++;
			ans.repaint();
		}else if(gameIndex == 2) {
			questionIndex++;
			drawImg = img[questionIndex];
			question = QUESTION;
			ans.repaint();
			pic.repaint();
			gameIndex--;
		}
	}
	
	public void getImages() {
		if(files.length > 0) {
		for(int i = 0; i < files.length; i++) {
			img[i] = new ImageIcon(files[i].getPath()).getImage().getScaledInstance(Main.screenW*8/10, Main.screenH*14/20, Image.SCALE_SMOOTH);
			imgName[i] = files[i].getPath().substring(files[i].getPath().lastIndexOf("\\")+1,files[i].getPath().lastIndexOf("."));
		}
		}
		drawImg = img[0];
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(C.green);
		g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.scroll.getHeight());
	}
	
	class PicPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.setColor(C.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(drawImg, 0, 0, this);
		}
	}
	
	class AnsPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(C.white);
			g.setFont(new Font("경기천년제목 Bold",Font.BOLD, 50));
			g.drawString(question, 155, getHeight()*6/9);
		}
	}
	
	class KeySettings extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER :
				if(totalIndex<files.length*2-1) {
				proceedGame();
				totalIndex++;
				}else if(totalIndex>=files.length*2-1){
					System.out.println("폴더에 파일이 없습니다.");
					break;
				}
			}
		}
	}
	public void refresh() {
		files = dir.listFiles();
		img = new Image[files.length];
		imgName = new String[files.length];
		System.out.println("refresh");
		System.out.println(files.length);
		getImages();
		questionIndex = 0;
		gameIndex = 0;
		totalIndex = 0;
		drawImg = img[questionIndex];
		question = QUESTION;
		proceedGame();
		pic.repaint();
		ans.repaint();
		gameIndex++;
		requestFocus();
	}
	
	class ActionSettings implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==refreshBtn) {
				refresh();
			}
		}
	}
	
	
}
