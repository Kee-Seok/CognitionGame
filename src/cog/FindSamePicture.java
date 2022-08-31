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

@SuppressWarnings({ "serial", "unused" })
public class FindSamePicture extends JPanel {

	int level = 1; // 단계별로 카드 개수 조절
	int screenW = Main.screenW;
	int screenH = Main.screenH; // 현재 패널 크기
	int cardW = 170;
	int cardH = 220;// 각 카드 크기
	int sleepTime = 500; // 카드를 뒤집을때 대기할 시간.
	int firstSleep = 1000; // 처음에 카드를 보여줄 시간
	Point[][] cardP = new Point[5][2]; // 카드의 좌표를 담을 인스턴스 변수들.
	JLabel[][] label = new JLabel[5][2];// JLabel 세팅
	int gabX; // 카드간의 좌우 거리
	int gabY = (screenH - (cardH * 2)) / 3; // 카드간의 위아래 거리
	int reversedCard = -1; // 뒤집힌 카드 개수
	MyImage currImg; // 방금 뒤집힌 카드를 집어넣을 변수.
	int currX, currY, backIndex, rightX, rightY; // 방금 누른 카드의 좌표를 저장하는 변수.
	int click; // 클릭한 횟수 1로 제한.
	File dir = new File("cardImg");
	ImageIcon[][] cardBackImg = new ImageIcon[5][2];
	ImageIcon[] cardBackImg2 = new ImageIcon[] {
			new ImageIcon(new ImageIcon("temp/001.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/002.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/003.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/004.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/005.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/006.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/007.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/008.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(new ImageIcon("temp/009.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)),
			new ImageIcon(
					new ImageIcon("temp/010.jpg").getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)) }; // 카드
																														// 뒷면
																														// 이미지
	MyImage[][] cardFrontImg = new MyImage[5][2];
	ArrayList<MyImage> imgArr = new ArrayList<>();
	ArrayList<MyImage> frontImgs = new ArrayList<>();
	boolean enterEnable = true;
	public FindSamePicture() {
		this.setBackground(C.a614124[0]);
		this.setLayout(null);
		startLevel(level);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER&&enterEnable) {
					for (int y = 0; y < 2; y++) {
						for (int x = 0; x < getCardNumbs(level); x++) {
							label[x][y].setIcon(cardBackImg[x][y]);
							label[x][y].setName("활성");
						}
					}
					reversedCard = 0;
					enterEnable = false;
				}
			}
		});
	}

	public void cardCheck(int x, int y) {
		new Thread() {
			public void run() {
				try {
					label[x][y].setIcon(cardFrontImg[x][y]);
					label[x][y].setName("비활성");
					Thread.sleep(sleepTime);
					System.out.println("눌렸다");
					label[x][y].setName("활성");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		if (isReverseTwice(level)) {
			if (checkIsSame(cardFrontImg[x][y], currImg)) {
				System.out.println("두개의 이미지가 같다!");
				label[x][y].setName("비활성");
				label[currX][currY].setName("비활성"); // 일치하면 다시 클릭이 안되게끔 막는다.
				return;// 두개 이미지가 같은지 다른지 체크, 같으면 그냥 냅둔다.
			} else { // 두개 이미지가 다르면 뒤집는다.

				System.out.println(cardFrontImg[x][y].getDescription());
				System.out.println(currImg.getDescription());
				System.out.println("다르다!");

				new Thread() {
					public void run() {
						try {
							Thread.sleep(sleepTime);
							label[x][y].setIcon(cardBackImg[x][y]);
							label[currX][currY].setIcon(cardBackImg[currX][currY]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
				reversedCard -= 2;
				return;
			}
		} else {
			currImg = cardFrontImg[x][y];
			currX = x;
			currY = y;
			if (x == 0 && y == 0)
				backIndex = 0;
			else if (x == 1 && y == 0)
				backIndex = 1;
			else if (x == 2 && y == 0)
				backIndex = 2;
			else if (x == 3 && y == 0)
				backIndex = 3;
			else if (x == 4 && y == 0)
				backIndex = 4;
			else if (x == 0 && y == 0)
				backIndex = 5;
			else if (x == 0 && y == 1)
				backIndex = 6;
			else if (x == 0 && y == 2)
				backIndex = 7;
			else if (x == 0 && y == 3)
				backIndex = 8;
			else if (x == 0 && y == 4)
				backIndex = 9;
		}
	}

	public void refresh() {
		refresh(level); // 매개변수는 레벨임.
	}
	
	public void paintFirstScreen() {
		if(reversedCard == -1) {
			System.out.println("paintFirstScreen 실행중");
			new Thread() {
				public void run() {
					try {
						refresh(level);
						for (int y = 0; y < 2; y++) {
							for (int x = 0; x < getCardNumbs(level); x++) {
								label[x][y].setIcon(cardFrontImg[x][y]);
								label[x][y].setName("비활성");
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			};
		}
	}
	class MouseSetting implements MouseListener {
		public void mousePressed(MouseEvent e) {
			if (reversedCard == -1) {
				new Thread() {
					public void run() {
						try {
							refresh(level);
							for (int y = 0; y < 2; y++) {
								for (int x = 0; x < getCardNumbs(level); x++) {
									label[x][y].setIcon(cardFrontImg[x][y]);
								}
							}
							Thread.sleep(firstSleep);
							for (int y = 0; y < 2; y++) {
								for (int x = 0; x < getCardNumbs(level); x++) {
									label[x][y].setIcon(cardBackImg[x][y]);
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				};
			} else if (reversedCard == 9 && level == 3) {
				new Thread() {
					public void run() {
						try {
							checkIndex(e); //인덱스 9에서 내가 눌렀던 라벨을 직접 추출해서 그려준 후 뒤집는거임.
							label[rightX][rightY].setIcon(cardFrontImg[rightX][rightY]);
							Thread.sleep(1000);
							refresh(level);
							for (int y = 0; y < 2; y++) {
								for (int x = 0; x < getCardNumbs(level); x++) {
									label[x][y].setIcon(cardFrontImg[x][y]);
									label[x][y].setName("비활성");
								}
							}
							reversedCard = -1;
							paintFirstScreen();
							enterEnable = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}.start();
			}else if (reversedCard == 7 && level == 2) {
				new Thread() {
					public void run() {
						try {
							checkIndex(e); //인덱스 9에서 내가 눌렀던 라벨을 직접 추출해서 그려준 후 뒤집는거임.
							label[rightX][rightY].setIcon(cardFrontImg[rightX][rightY]);
							Thread.sleep(1000);
							removeLabels(level);
							level++;
							startLevel(level);
							for (int y = 0; y < 2; y++) {
								for (int x = 0; x < getCardNumbs(level); x++) {
									label[x][y].setIcon(cardFrontImg[x][y]);
									label[x][y].setName("비활성");
								}
							}
							reversedCard = -1;
							paintFirstScreen();
							labelVisible(level);
							enterEnable = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}.start();
			}else if (reversedCard == 5 && level == 1) {
				new Thread() {
					public void run() {
						try {
							checkIndex(e); //인덱스 9에서 내가 눌렀던 라벨을 직접 추출해서 그려준 후 뒤집는거임.
							label[rightX][rightY].setIcon(cardFrontImg[rightX][rightY]);
							Thread.sleep(1000);
							removeLabels(level);
							level++;
							startLevel(level);
							System.out.println("됐나?");
							for (int y = 0; y < 2; y++) {
								for (int x = 0; x < getCardNumbs(level); x++) {
									label[x][y].setIcon(cardFrontImg[x][y]);
									label[x][y].setName("비활성");
								}
							}
							System.out.println("됐따.");
							reversedCard = -1;
							paintFirstScreen();
							labelVisible(level);
							enterEnable = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}.start();
			}else {
				for (int y = 0; y < 2; y++) {
					for (int x = 0; x < getCardNumbs(level); x++) {
				label[x][y].setName("활성");
					}
				}
				check(e, level);
			}

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
	public void removeLabels(int level) {
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < getCardNumbs(level); x++) {
				label[x][y].setVisible(false);;
				remove(label[x][y]);
			}
		}
	}
	public void labelVisible(int level) {
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < getCardNumbs(level); x++) {
				add(label[x][y]);
				label[x][y].setVisible(true);
			}
		}
	}
	
	public void checkIndex(MouseEvent e) {
		if(e.getSource().equals(label[0][0])) {
			rightX = 0;
			rightY = 0;
		}else if(e.getSource().equals(label[1][0])) {
			rightX = 1;
			rightY = 0;
		}else if(e.getSource().equals(label[2][0])) {
			rightX = 2;
			rightY = 0;
		}else if(e.getSource().equals(label[3][0])) {
			rightX = 3;
			rightY = 0;
		}else if(e.getSource().equals(label[4][0])) {
			rightX = 4;
			rightY = 0;
		}else if(e.getSource().equals(label[0][1])) {
			rightX = 0;
			rightY = 1;
		}else if(e.getSource().equals(label[1][1])) {
			rightX = 1;
			rightY = 1;
		}else if(e.getSource().equals(label[2][1])) {
			rightX = 2;
			rightY = 1;
		}else if(e.getSource().equals(label[3][1])) {
			rightX = 3;
			rightY = 1;
		}else if(e.getSource().equals(label[4][1])) {
			rightX = 4;
			rightY = 1;
		}
		
	}
	public void check(MouseEvent e, int level) {
		if (e.getSource().equals(label[0][0])&&label[0][0].getName()=="활성") {
			reversedCard++;
			cardCheck(0,0);
		} else if (e.getSource().equals(label[1][0])&&label[1][0].getName()=="활성") {
			reversedCard++;
			cardCheck(1,0);
		} else if (e.getSource().equals(label[2][0])&&label[2][0].getName()=="활성") {
			reversedCard++;
			cardCheck(2,0);
		} else if (e.getSource().equals(label[3][0])&&label[3][0].getName()=="활성") {
			reversedCard++;
			cardCheck(3,0);
		} else if (e.getSource().equals(label[4][0])&&label[4][0].getName()=="활성") {
			reversedCard++;
			cardCheck(4,0);
		} else if (e.getSource().equals(label[0][1])&&label[0][1].getName()=="활성") {
			reversedCard++;
			cardCheck(0,1);
		} else if (e.getSource().equals(label[1][1])&&label[1][1].getName()=="활성") {
			reversedCard++;
			cardCheck(1,1);
		} else if (e.getSource().equals(label[2][1])&&label[2][1].getName()=="활성") {
			reversedCard++;
			cardCheck(2,1);
		} else if (e.getSource().equals(label[3][1])&&label[3][1].getName()=="활성") {
			reversedCard++;
			cardCheck(3,1);
		} else if (e.getSource().equals(label[4][1])&&label[4][1].getName()=="활성") {
			reversedCard++;
			cardCheck(4,1);
		}
		System.out.println("뒤집힌 카드 개수 : " + reversedCard);
		System.out.println("currX : " + currX);
		System.out.println("currY : " + currY);
	}

	public void checkIsClicked(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) {
			label[x1][y1].setName("비활성");
		} else if (x1 != x2 || y1 != y2) {
			label[x1][y1].setName("활성");
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

	public boolean checkIsSame(MyImage img1, MyImage img2) { // 두개 이미지가 같은지 여부 체크
		if (img1.getName().equals(img2.getName())) {
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
		for (int i = 0; i < frontImgs.size(); i++) {
			System.out.println(frontImgs.get(i).getName());
		}
	}

	public void startLevel(int level) { // 레벨에 따라서 좌표를 알아서 구하게끔 한 후, 카드 뒷면을 패널 위에 띄워주고 대기한다.
		gabX = (screenW - (cardW * (getCardNumbs(level)))) / (getCardNumbs(level) + 1); // gab은 오브젝트 사이사이를 나타내기때문에 오브젝트가
																						// 3개면 gab은 그보다 1개 더 많은 4개의
																						// 공간이다.
		int index = 0;
		setCardImages(level);
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < getCardNumbs(level); x++) {
				label[x][y] = new JLabel();
			}
		}

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < getCardNumbs(level); x++) {
				cardP[x][y] = new Point(gabX + (cardW + gabX) * x, gabY + (gabY + cardH) * y);
				cardBackImg[x][y] = cardBackImg2[index];
				label[x][y].setBounds(cardP[x][y].x, cardP[x][y].y, cardW, cardH);
				label[x][y].addMouseListener(new MouseSetting());
				label[x][y].setName("활성");
				cardFrontImg[x][y] = new MyImage(new ImageIcon(
						frontImgs.get(index).getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)));
				if (frontImgs.get(index).getName().equals("1번")) {
					cardFrontImg[x][y].setName("1번");
				} else if (frontImgs.get(index).getName().equals("2번")) {
					cardFrontImg[x][y].setName("2번");
				} else if (frontImgs.get(index).getName().equals("3번")) {
					cardFrontImg[x][y].setName("3번");
				} else if (frontImgs.get(index).getName().equals("4번")) {
					cardFrontImg[x][y].setName("4번");
				} else if (frontImgs.get(index).getName().equals("5번")) {
					cardFrontImg[x][y].setName("5번");
				}
				add(label[x][y]);
				label[x][y].setIcon(cardFrontImg[x][y]);
				index++;
			}
		}
	}

	public void refresh(int level) { // 다시 리프레쉬할때
		int index = 0;
		setCardImages(level);
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < getCardNumbs(level); x++) {
				cardBackImg[x][y] = cardBackImg2[index];
				label[x][y].setIcon(cardBackImg[x][y]);
				label[x][y].setName("비활성");
				cardFrontImg[x][y] = new MyImage(new ImageIcon(
						frontImgs.get(index).getImage().getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)));
				if (frontImgs.get(index).getName().equals("1번")) {
					cardFrontImg[x][y].setName("1번");
				} else if (frontImgs.get(index).getName().equals("2번")) {
					cardFrontImg[x][y].setName("2번");
				} else if (frontImgs.get(index).getName().equals("3번")) {
					cardFrontImg[x][y].setName("3번");
				} else if (frontImgs.get(index).getName().equals("4번")) {
					cardFrontImg[x][y].setName("4번");
				} else if (frontImgs.get(index).getName().equals("5번")) {
					cardFrontImg[x][y].setName("5번");
				}
				index++;
			}
		}
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
				MyImage img = new MyImage(new ImageIcon(new ImageIcon(files[i].getPath()).getImage()
						.getScaledInstance(cardW, cardH, Image.SCALE_SMOOTH)));
				imgArr.add(img);
			}
			Collections.shuffle(imgArr);
			for (int i = 0; i < getCardNumbs(level); i++) {
				imgArr.get(i).setName((i + 1) + "번");
			}
			System.out.println("폴더에 이미지 파일이 " + files.length + "개 있습니다.");
		}
	}

	public int getCardNumbs(int level) { // 카드 개수 가져오기. cardImg폴더에 적어도 이미지 5개 이상은 있어야 한다.
		if (level == 2) {
			return 4;
		} else if (level == 3) {
			return 5;
		}
		return 3;
	}

}
