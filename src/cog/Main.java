package cog;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 720;
	public static int screenW = Main.SCREEN_WIDTH; // 각 게임패널들 크기
	public static int screenH = Main.SCREEN_HEIGHT*4/5; //각 게임패널들 크기
	static String title = "인지프로그램";
	int screenX, screenY, screenX2, screenY2, firstX, firstY, secondX, secondY, btnIndex;
	int stringSize = 400;
	int gameBtnWidth = 440; // 각 게임버튼 사이즈
	int gameBtnHeight = 120;
	int diceBeginX = SCREEN_WIDTH*265625/1000000-stringSize/6;
	int diceBeginY = 43;
	int diceBeginX2 = SCREEN_WIDTH*664062/1000000+stringSize/6;
	int diceBeginY2 = 43;
	int x = diceBeginX;
	int y = diceBeginY;
	int x2 = diceBeginX2;
	int y2 = diceBeginY2;
	int width = 50;
	int height = 50;
	Image img = new ImageIcon("temp/dice6.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	Image img2 = new ImageIcon("temp/dice1.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	ImageIcon btnImg = new ImageIcon(
			new ImageIcon("temp/exit.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	ImageIcon btnImg2 = new ImageIcon(
			new ImageIcon("temp/exit2.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	DrawThread t = new DrawThread();
	static JScrollPane scroll;
	JButton[] btn = { new JButton("사진 맞추기"), new JButton("계산하기"), new JButton("같은 그림카드 찾기"), new JButton("주사위계산하기"),
			new JButton("초성 단어찾기"), new JButton("필사"), new JButton("치매예방수칙"), new JButton("미정"), new JButton("미정"),
			new JButton("미정"), new JButton("미정"), new JButton("미정"), new JButton("미정"), };
	JButton exitBtn = new JButton(btnImg);
	SelectGamePanel selectGamePanel = new SelectGamePanel();
	TitlePanel titlePanel = new TitlePanel();
	PictureQuiz pictureQuizPanel = new PictureQuiz();
	
	static Color titleFontColor = C.a10035[0];
	static Color selectGamePanelColor = C.a10035[1];
	static Color titlePanelColor = C.a10035[2];
	static Color btnColor = C.a10035[3];
	
	public Main() {
		init();
		add();
		goToMain();
		setVisible(true);
		pictureQuizPanel.refresh();
		this.requestFocus();
		t.start();
	}

	public void goToMain() {
		titlePanel.setVisible(true);
		pictureQuizPanel.setVisible(false);
		selectGamePanel.setVisible(true);
		scroll.setVisible(true);
	}

	public void goPictureQuiz() {// 사진 맞추기 버튼누르면 패널 전환하는 메소드
		titlePanel.setVisible(true);
		pictureQuizPanel.setVisible(true);
		selectGamePanel.setVisible(false);
		scroll.setVisible(false);
	}

	public void add() {
		exitBtn.setBounds(SCREEN_WIDTH - 100, 30, 60, 60);
		exitBtn.addActionListener(new ActionSetting());
		exitBtn.setRolloverIcon(btnImg2);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.addMouseListener(new MouseSetting());
		exitBtn.addKeyListener(new KeySetting());
		titlePanel.setLayout(null);
		titlePanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 5);
		titlePanel.add(exitBtn);

		pictureQuizPanel.setBounds(0, SCREEN_HEIGHT / 5, SCREEN_WIDTH, SCREEN_HEIGHT * 4 / 5);
		pictureQuizPanel.addKeyListener(new KeySetting());
		scroll = new JScrollPane(selectGamePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0, SCREEN_HEIGHT / 5, SCREEN_WIDTH, SCREEN_HEIGHT * 4 / 5);
		scroll.addKeyListener(new KeySetting());
		scroll.getVerticalScrollBar().setUnitIncrement(5);// 스크롤 속도
		for (int y = 0; y < btn.length / 2; y++) {
			for (int x = 0; x < 2; x++) {
				btn[btnIndex].setBounds((SCREEN_WIDTH-gameBtnWidth*2)/3+(gameBtnWidth+(SCREEN_WIDTH-gameBtnWidth*2)/3)*x, 20 + ((gameBtnHeight + 20) * y)
						, gameBtnWidth,gameBtnHeight);
				btn[btnIndex].addActionListener(new ActionSetting());
				btn[btnIndex].addMouseListener(new MouseSetting());
				btn[btnIndex].setBorder(BorderFactory.createEtchedBorder(BevelBorder.RAISED, C.orange, C.black));
				btn[btnIndex].setFont(new Font("경기천년제목 Bold", Font.BOLD, 40));
				btn[btnIndex].setForeground(C.white);
				btn[btnIndex].setBackground(btnColor);
				// btn[btnIndex].setIcon(new ImageIcon(new
				// ImageIcon("temp/btnImg.png").getImage().getScaledInstance(490, 100,
				// Image.SCALE_SMOOTH)));
				selectGamePanel.add(btn[btnIndex]);
				btnIndex++;
			}
		}
		btnIndex=0;
		selectGamePanel.setPreferredSize(new Dimension(SCREEN_WIDTH, 20 + ((gameBtnHeight + 20) * (btn.length / 2)))); // 버튼
																														// 개수에
																														// 따른
																														// 패널
																														// 크기
																														// 조정
		add(pictureQuizPanel);
		add(titlePanel);
		add(scroll);
	}

	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(null);
		addKeyListener(new KeySetting());
		addMouseListener(new MouseSetting());
		addMouseMotionListener(new MouseSetting());

	}

	class KeySetting extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_BACK_SPACE:
				goToMain();
				title = "인지프로그램";
				titlePanel.repaint();
				
				break;
			}
		}
	}

	class MouseSetting extends MouseMotionAdapter implements MouseListener {
		public void mouseDragged(MouseEvent e) {
			secondX = e.getLocationOnScreen().x;
			secondY = e.getLocationOnScreen().y;
			screenX2 = screenX + (secondX - firstX);
			screenY2 = screenY + (secondY - firstY);
			setLocation(screenX2, screenY2);
			System.out.println(screenX2 + " " + screenY2);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			firstX = e.getLocationOnScreen().x;
			firstY = e.getLocationOnScreen().y;
			screenX = e.getLocationOnScreen().x - e.getPoint().x;
			screenY = e.getLocationOnScreen().y - e.getPoint().y;
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == btn[0] || e.getSource() == btn[1] || e.getSource() == btn[2] || e.getSource() == btn[3]
					|| e.getSource() == btn[4] || e.getSource() == btn[5] || e.getSource() == btn[6]
					|| e.getSource() == btn[7] || e.getSource() == btn[8] || e.getSource() == btn[9]
					|| e.getSource() == btn[10] || e.getSource() == exitBtn) {
				setCursor(new Cursor(12));

			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(new Cursor(0));
		}
	}

	class ActionSetting implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "사진 맞추기") {
				goPictureQuiz();
				title = " 사진 맞추기";
				titlePanel.repaint();
				pictureQuizPanel.requestFocus();
			} else if (e.getSource() == exitBtn) {
				System.exit(0);
			}
		}

	}

	class TitlePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.setColor(titlePanelColor);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(titleFontColor);
			g.setFont(new Font("경기천년제목 Bold", Font.BOLD, 70));
			g.drawString(title, (SCREEN_WIDTH-stringSize)/2, 100);
			drawDice(g);
			drawDice2(g);
		}

		public void drawDice(Graphics g) {
			g.drawImage(img, x, y, this);
		}

		public void drawDice2(Graphics g) {
			g.drawImage(img2, x2, y2, this);
		}
	}

	class DrawThread extends Thread {
		int dir = 0; // 0은 x++ 1은 y++ 2은 x-- 3은 y--
		int dir2 = 0; // 0은 x++ 1은 y++ 2은 x-- 3은 y--

		public void run() {
			while (true) {
				try {
					Thread.sleep(15);
					if (dir == 0) {
						x++;
						y2++;
						dir();
						dir2();
						titlePanel.drawDice(getGraphics());
						titlePanel.drawDice2(getGraphics());
					} else if (dir == 1) {
						y++;
						x2--;
						dir();
						dir2();
						titlePanel.drawDice(getGraphics());
						titlePanel.drawDice2(getGraphics());
					} else if (dir == 2) {
						x--;
						y2--;
						dir();
						dir2();
						titlePanel.drawDice(getGraphics());
						titlePanel.drawDice2(getGraphics());
					} else if (dir == 3) {
						y--;
						x2++;
						dir();
						dir2();
						titlePanel.drawDice(getGraphics());
						titlePanel.drawDice2(getGraphics());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void dir() {
			if (x <= diceBeginX && y <= diceBeginY) { // 왼쪽 위일때
				dir = 0;
			} else if (x > diceBeginX + 20 && y <= diceBeginY) { // 오른쪽 위일때
				dir = 1;
			} else if (x > diceBeginX + 20 && y > diceBeginY + 20) { // 오른쪽 아래일때
				dir = 2;
			} else if (x < diceBeginX && y > diceBeginY + 20) { // 왼쪽 아래일때
				dir = 3;
			}
		}

		public void dir2() {
			if (x2 <= diceBeginX2 && y2 <= diceBeginY2) {// 0은 x++ 1은 y++ 2은 x-- 3은 y--
				dir2 = 3;
			} else if (x2 > diceBeginX2 + 20 && y2 <= diceBeginY2) {
				dir2 = 2;
			} else if (x2 > diceBeginX2 + 20 && y2 > diceBeginY2 + 20) {
				dir2 = 1;
			} else if (x2 < diceBeginX2 && y2 > diceBeginY2 + 20) {
				dir2 = 0;
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
