package cog;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FindSamePicture extends JPanel {

	int level = 1; // 단계별로 카드 개수 조절
	int screenW = Main.screenW;
	int screenH = Main.screenH; // 현재 패널 크기
	int cardW = 170;
	int cardH = 220;// 각 카드 크기
	Point[][] cardP = new Point[5][2]; // 카드의 좌표를 담을 인스턴스 변수들.
	JLabel[][] label = new JLabel[5][2];// JLabel 세팅
	int gabX; // 카드간의 좌우 거리
	int gabY = (screenH - (cardH * 2)) / 3; // 카드간의 위아래 거리
	int reversedCard; // 뒤집힌 카드 개수
	ImageIcon currImg; // 방금 뒤집힌 카드를 집어넣을 변수.
	int currX, currY, backIndex; // 방금 누른 카드의 좌표를 저장하는 변수.
	File dir = new File("cardImg");
	ImageIcon[] cardBackImg = new ImageIcon[] {new ImageIcon(new ImageIcon("temp/001.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/002.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/003.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/004.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/005.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/006.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/007.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/008.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/009.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/010.jpg").getImage().getScaledInstance(cardW,cardH,Image.SCALE_SMOOTH))}; //카드 뒷면 이미지
	ImageIcon[][] cardFrontImg = new ImageIcon[5][2];
	ArrayList<Image> imgArr = new ArrayList<>();
	ArrayList<Image> frontImgs = new ArrayList<>();

	public FindSamePicture() {
		getImages();
		this.setLayout(null);
		startLevel(3); // 매개변수는 레벨임.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenW, screenH);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE :
					System.exit(0);
					break;
				}
				
			}
		});
		frame.add(this);
		frame.setVisible(true);
	}

	class MouseSetting implements MouseListener {
		public void mousePressed(MouseEvent e) {
			check(e, level);
		}

		public void mouseEntered(MouseEvent e) {
			if (e.getComponent().getClass().equals(JLabel.class)) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		public void mouseExited(MouseEvent e) {
			if (e.getComponent().getClass().equals(JLabel.class)) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}
	}

	public void check(MouseEvent e, int level) {
		if (e.getSource().equals(label[0][0])) {
			reversedCard++;
			label[0][0].setIcon(cardFrontImg[0][0]);
			if (isReverseTwice(level)) {
				sleep(1000); //1000ms 쉰다.
				if (checkIsSame(cardFrontImg[0][0], currImg)) {
					return;// 두개 이미지가 같은지 다른지 체크, 같으면 그냥 냅둔다.
				} else { //두개 이미지가 다르면 뒤집는다.
					System.out.println("다르다!");
					label[0][0].setIcon(cardBackImg[0]);
					label[currX][currY].setIcon(cardBackImg[backIndex]);
					reversedCard-=2;
					return;
				}
			}else {
			currImg = cardFrontImg[0][0];
			currImg = cardFrontImg[0][0];
			currX = 0;
			currY = 0; // 방금 누른 카드의 좌표를 기억하도록 해서 다음번에 JLabel 이미지 처리할때 써먹을꺼임.
			backIndex = 0;
			}
		} else if (e.getComponent().equals(label[1][0])) {
			reversedCard++;
			label[1][0].setIcon(cardFrontImg[1][0]);
			System.out.println("눌렸다");
			if (isReverseTwice(level)) {
				sleep(1000); //1000ms 쉰다.
				if (checkIsSame(cardFrontImg[1][0], currImg)) {
					return;// 두개 이미지가 같은지 다른지 체크, 같으면 그냥 냅둔다.
				} else { //두개 이미지가 다르면 뒤집는다.
					System.out.println("다르다!");
					label[1][0].setIcon(cardBackImg[1]);
					label[currX][currY].setIcon(cardBackImg[backIndex]);
					reversedCard-=2;
					return;
				}
			}else {
			currImg = cardFrontImg[1][0];
			currX = 1;
			currY = 0;
			backIndex = 1;
			}
		} else if (e.getSource().equals(label[2][0])) {
			reversedCard++;
			label[2][0].setIcon(cardFrontImg[2][0]);
			System.out.println("눌렸다");
			if (isReverseTwice(level)) {
				sleep(1000); //1000ms 쉰다.
				if (checkIsSame(cardFrontImg[2][0], currImg)) {
					return;// 두개 이미지가 같은지 다른지 체크, 같으면 그냥 냅둔다.
				} else { //두개 이미지가 다르면 뒤집는다.
					System.out.println("다르다!");
					label[2][0].setIcon(cardBackImg[2]);
					label[currX][currY].setIcon(cardBackImg[backIndex]);
					reversedCard-=2;
					return;
				}
			}else {
			currImg = cardFrontImg[2][0];
			currX = 2;
			currY = 0;
			backIndex = 2;
			}
		} else if (e.getSource().equals(label[3][0])) {
			reversedCard++;
			label[3][0].setIcon(cardFrontImg[3][0]);
			currX = 3;
			currY = 0;
			backIndex = 3;
		} else if (e.getSource().equals(label[4][0])) {
			reversedCard++;
			label[4][0].setIcon(cardFrontImg[4][0]);
			currX = 4;
			currY = 0;
			backIndex = 4;
		} else if (e.getSource().equals(label[0][1])) {
			reversedCard++;
			label[0][1].setIcon(cardFrontImg[0][1]);
			currX = 0;
			currY = 1;
			backIndex = 5;
		} else if (e.getSource().equals(label[1][1])) {
			reversedCard++;
			label[1][1].setIcon(cardFrontImg[1][1]);
			currX = 1;
			currY = 1;
			backIndex = 6;
		} else if (e.getSource().equals(label[2][1])) {
			reversedCard++;
			label[2][1].setIcon(cardFrontImg[2][1]);
			currX = 2;
			currY = 1;
			backIndex = 7;
		} else if (e.getSource().equals(label[3][1])) {
			reversedCard++;
			label[3][1].setIcon(cardFrontImg[3][1]);
			currX = 3;
			currY = 1;
			backIndex = 8;
		} else if (e.getSource().equals(label[4][1])) {
			reversedCard++;
			label[4][1].setIcon(cardFrontImg[4][1]);
			currX = 4;
			currY = 1;
			backIndex = 9;
		}
		System.out.println("뒤집힌 카드 개수 : "+reversedCard);
		System.out.println("currX : "+currX);
		System.out.println("currY : "+currY);
	}

	public void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isReverseTwice(int level) { // 카드 두개가 뒤집혔는지 확인.
		if (level == 1) {
			if (reversedCard == 2 || reversedCard == 4 || reversedCard == 6) {
				return true;
			}
		} else if (level == 2) {
			if (reversedCard == 2 || reversedCard == 4 || reversedCard == 6 || reversedCard == 8) {
				return true;
			}
		} else if (level == 3) {
			if (reversedCard == 2 || reversedCard == 4 || reversedCard == 6 || reversedCard == 8
					|| reversedCard == 10) {
				return true;
			}
		}
		return false;
	}

	public boolean checkIsSame(ImageIcon img1, ImageIcon img2) { // 두개 이미지가 같은지 여부 체크
		if (img1.equals(img2)) {
			return true;
		}
		return false;
	}

	public void setCardImages(int level) { // 현재 레벨에 따른 보여줄 카드의 개수를 지정한다. 레벨 1이면 3개, 레벨 2면 4개, 레벨 3이면 5개의 그림으로 같은그림을
											// 찾는다.
		frontImgs.removeAll(frontImgs);
		getImages();
		for (int i = 0; i < getCardNumbs(level); i++) {
			for (int j = 0; j < 2; j++) {
				frontImgs.add(imgArr.get(i));
			}
		}
		Collections.shuffle(frontImgs);
	}

	public void startLevel(int level) { // 레벨에 따라서 좌표를 알아서 구하게끔 한 후, 카드 뒷면을 패널 위에 띄워주고 대기한다.
		gabX = (screenW - (cardW * (getCardNumbs(level)))) / (getCardNumbs(level) + 1); // gab은 오브젝트 사이사이를 나타내기때문에 오브젝트가
																						// 3개면 gab은 그보다 1개 더 많은 4개의
																						// 공간이다.
		int index = 0;
		setCardImages(level);
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < getCardNumbs(level); x++) {
				cardP[x][y] = new Point(gabX + (cardW + gabX) * x, gabY + (gabY + cardH) * y);
				label[x][y] = new JLabel(cardBackImg[index]);
				label[x][y].setBounds(cardP[x][y].x, cardP[x][y].y, cardW, cardH);
				label[x][y].addMouseListener(new MouseSetting());
				cardFrontImg[x][y] = new ImageIcon(new ImageIcon(frontImgs.get(index)).getImage().getScaledInstance(cardW, cardH,
						Image.SCALE_SMOOTH));
				add(label[x][y]);
				index++;
			}
		}
	}

	public boolean checkCorrect(Image img, Image img2) { // 뒤집은 두 이미지가 같은지 체크하는 메소드.
		if (img.equals(img2))
			return true;
		return false;
	}

	public void getImages() { // 폴더에 이미지파일이 있는지 체크 후 있으면 배열에 담는다.
		imgArr.removeAll(imgArr);// 한번 배열을 비워준 후 다시 담아준다. (배열 인덱스 관리를 수월하게 하기 위해서.)
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("jpg") || name.endsWith("png") || name.endsWith("jpeg"); // 파일 확장자명이 이미지파일이면 true
																								// 반환 후 그 파일만 files 배열에
																								// 담는다.
			}
		});
		if (files.length == 0) { // 폴더에 파일이 없으면 아무것도 하지 않는다.
			System.out.println("폴더에 이미지 파일이 없습니다.");
		} else {
			for (int i = 0; i < files.length; i++) {
				imgArr.add(new ImageIcon(files[i].getPath()).getImage().getScaledInstance(cardW, cardH,
						Image.SCALE_SMOOTH));
			}
			System.out.println("폴더에 이미지 파일이 " + files.length + "개 있습니다.");
		}
		Collections.shuffle(imgArr); // 이미지들을 섞어준다.
	}

	public int getCardNumbs(int level) { // 카드 개수 가져오기. cardImg폴더에 적어도 이미지 5개 이상은 있어야 한다.
		if (level == 2) {
			return 4;
		} else if (level == 3) {
			return 5;
		}
		return 3;
	}

	public static void main(String[] args) {
		new FindSamePicture();
	}
}
