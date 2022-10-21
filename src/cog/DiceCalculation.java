package cog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class DiceCalculation extends JPanel{
	int firstNumLength, secondNumLength, firstNum, secondNum, answer, a, b, one_1, one_2, one_3, one_4, two_1, two_2, two_3;
	String a1 = a+"";
	String b1 = b+"";
	int diceWidth = 150; //주사위 크기
	int diceHeight = 150;
	int gapW = 42; //주사위간 좌우 간격
	int gapH = 25; //주사위간 위아래 간격
	int isViewAns = 0; //0 : 정답 숨긴상태 , 1 : 정답 공개 상태
	Font font = new Font("경기천년제목_Bold",Font.BOLD,100); //폰트 크기
	Image dice1 = new ImageIcon("./diceImg/dice1.png").getImage().getScaledInstance(diceWidth,diceHeight,Image.SCALE_SMOOTH);
	Image dice2 = new ImageIcon("./diceImg/dice2.png").getImage().getScaledInstance(diceWidth,diceHeight,Image.SCALE_SMOOTH);
	Image dice3 = new ImageIcon("./diceImg/dice3.png").getImage().getScaledInstance(diceWidth,diceHeight,Image.SCALE_SMOOTH);
	Image dice4 = new ImageIcon("./diceImg/dice4.png").getImage().getScaledInstance(diceWidth,diceHeight,Image.SCALE_SMOOTH);
	Image dice5 = new ImageIcon("./diceImg/dice5.png").getImage().getScaledInstance(diceWidth,diceHeight,Image.SCALE_SMOOTH);
	Image dice6 = new ImageIcon("./diceImg/dice6.png").getImage().getScaledInstance(diceWidth,diceHeight,Image.SCALE_SMOOTH);
	DecimalFormat form = new DecimalFormat("###,###,###,###");
	JLabel answerLabel = new JLabel("정답은 무엇일까요?",JLabel.RIGHT);
	String arith;
	/**
	 * ⓐ 랜덤으로 뽑힌 arith가 "+"면 a1 + b1 = answer
	 * ⓑ 랜덤으로 뽑힌 arith가 "-"면 a1 - b1 = answer
	 * ⓒ 랜덤으로 뽑힌 arith가 "*"면 a1 * b1 = answer
	 */
	Cal cal = (a1, b1) -> {
		if (arith == "+") {
			answer = a1 + b1;
			return answer;
		} else if (arith == "-") {
			answer = a1 - b1;
			return answer;
		} else {
			answer = a1 * b1;
			return answer;
		}
	};
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(C.skin);
		g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setOneTwoNum(a,b); //각 숫자들 변수에 집어넣기.

		if(a1.length()==3) {
			g.drawImage(getDiceImage(one_3),426,25,this);
			g.drawImage(getDiceImage(one_2),618,25,this);
			g.drawImage(getDiceImage(one_1),810,25,this);
		}else if(a1.length()==4) {
			g.drawImage(getDiceImage(one_4),234,25,this);
			g.drawImage(getDiceImage(one_3),426,25,this);
			g.drawImage(getDiceImage(one_2),618,25,this);
			g.drawImage(getDiceImage(one_1),810,25,this);
		}
		if(b1.length()==1) {
			g.drawImage(getDiceImage(two_1),810,200,this);
		}else if(b1.length()==2) {
			g.drawImage(getDiceImage(two_2),618,200,this);
			g.drawImage(getDiceImage(two_1),810,200,this);
		}else if(b1.length()==3) {
			g.drawImage(getDiceImage(two_3),426,200,this);
			g.drawImage(getDiceImage(two_2),618,200,this);
			g.drawImage(getDiceImage(two_1),810,200,this);
		}
		if(isViewAns == 1) {
			drawEachNumbs(g);
		}
		g.setColor(Color.black);
		g.fillRect(230, 380, Main.SCREEN_WIDTH-230, 20);
		g.setFont(new Font("경기천년제목_Bold",Font.BOLD, 200));
		g.drawString(arith, 60, 337);
	
		requestFocus();
	}
	/**
	 * 정답을 표시할 때 각 주사위 문제 위에 숫자를 출력하는 메소드
	 * @param g
	 */
	public void drawEachNumbs(Graphics g) {
			int Add = 105;
			int Add2 = 46;
			g.setColor(Color.white);
			g.setFont(new Font("경기천년제목_Bold",Font.BOLD,100));
			if(a1.length()==3) {
				g.drawString(one_3+"",426+Add2,25+Add);
				g.drawString(one_2+"",618+Add2,25+Add);
				g.drawString(one_1+"",810+Add2,25+Add);
				repaint();
			}else if(a1.length()==4) {
				g.drawString(one_4+"",234+Add2,25+Add);
				g.drawString(one_3+"",426+Add2,25+Add);
				g.drawString(one_2+"",618+Add2,25+Add);
				g.drawString(one_1+"",810+Add2,25+Add);
				repaint();
			}
			if(b1.length()==1) {
				g.drawString(two_1+"",810+Add2,200+Add);
			}else if(b1.length()==2) {
				g.drawString(two_2+"",618+Add2,200+Add);
				g.drawString(two_1+"",810+Add2,200+Add);
			}else if(b1.length()==3) {
				g.drawString(two_3+"",426+Add2,200+Add);
				g.drawString(two_2+"",618+Add2,200+Add);
				g.drawString(two_1+"",810+Add2,200+Add);
			}
		}
	
	public void setOneTwoNum(int a, int b) {
		a1 = a+"";
		b1 = b+"";
		if(a1.length()==3) {
			one_1 = Integer.parseInt(a1.substring(2));
			one_2 = Integer.parseInt(a1.substring(1,2));
			one_3 = Integer.parseInt(a1.substring(0,1));
		}else if(a1.length()==4) {
			one_1 = Integer.parseInt(a1.substring(3));
			one_2 = Integer.parseInt(a1.substring(2,3));
			one_3 = Integer.parseInt(a1.substring(1,2));
			one_4 = Integer.parseInt(a1.substring(0,1));
		}
		if(b1.length()==1) {
			two_1 = Integer.parseInt(b1.substring(0));
		}else if(b1.length()==2) {
			two_1 = Integer.parseInt(b1.substring(1));
			two_2 = Integer.parseInt(b1.substring(0,1));
		}else if(b1.length()==3) {
			two_1 = Integer.parseInt(b1.substring(2));
			two_2 = Integer.parseInt(b1.substring(1,2));
			two_3 = Integer.parseInt(b1.substring(0,1));
		}
//		g.drawString("정답은 무엇일까요?",200,400+Main.SCREEN_HEIGHT/5);
		answer = cal.cal(a,b);
	}
	
	public DiceCalculation() {
		arith = getArithmetic();
		setSize(Main.screenW,Main.SCREEN_HEIGHT);
		setLayout(null);
		a = getNumb(1);
		b = getNumb(2);
		System.out.println(a);
		System.out.println(b);
		answerLabel.setFont(font);
		answerLabel.setBounds(0,Main.SCREEN_HEIGHT/5+40,Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT*4/5);
		this.addKeyListener(new MyKeyListener());
		JFrame frame = new JFrame();
		
		this.add(answerLabel);
		this.setToolTipText("<html>"+"ⓐ 정답을 확인하려면 엔터키를 누르세요."+"<br><br>"
				+ "ⓑ 뒤(메인화면)로 돌아가려면 Backspace 키를 눌러주세요."+"<br><br>"
						+ "ⓒ 게임을 종료하려면 ESC키를 눌러주세요."
				+ "</html>");
		
		this.requestFocus();
	}

	public void refresh() {
		
	}
	
	public String getArithmetic() {
		String[] arith = { "+", "-", "x" };
		int rand = (int) (Math.random() * arith.length);
		String arithmeticOperation = arith[rand];
		return arithmeticOperation;
	}

	@FunctionalInterface
	public interface Cal {
		public int cal(int a, int b);
	}
	
	/**
	 * 실제 나온 숫자와 합이 7이 되는 주사위 단면 이미지를 리턴하는 메소드
	 * @param numb
	 * @return dice image
	 */
	public Image getDiceImage(int numb) {
		if(numb==1) {
			return dice6;
		}else if(numb==2) {
			return dice5;
		}else if(numb==3) {
			return dice4;
		}else if(numb==4) {
			return dice3;
		}else if(numb==5) {
			return dice2;
		}
		return dice1;
	}
	
	/**
	 * 매개변수 index에 1을 넣으면 첫번째 숫자, 인덱스에 2를 넣으면 두번째 숫자를 생성해서 리턴하는 메소드 1과 2 이외의 숫자를 넣으면
	 * -1을 리턴
	 * 
	 * @param index
	 */
	public int getNumb(int index) {
		firstNumLength = (int) (Math.random() * 2) + 3;
		secondNumLength = (int) (Math.random() * 3) + 1;

		int firstNum = 0;
		int secondNum = 0;

		if (index == 1) {
			for (int i = 0; i < firstNumLength; i++) {
				if (firstNumLength == 3) {
					one_1 = (int) (Math.random() * 6) + 1;
					one_2 = (int) (Math.random() * 6) + 1;
					one_3 = (int) (Math.random() * 6) + 1;
					String num = one_3 + "" + one_2 + "" + one_1;
					firstNum = Integer.parseInt(num);
				} else if (firstNumLength == 4) {
					one_1 = (int) (Math.random() * 6) + 1;
					one_2 = (int) (Math.random() * 6) + 1;
					one_3 = (int) (Math.random() * 6) + 1;
					one_4 = (int) (Math.random() * 6) + 1;
					String num = one_4 + "" + one_3 + "" + one_2 + "" + one_1;
					firstNum = Integer.parseInt(num);
				}
			}
			return firstNum;
		} else if (index == 2) {
			for (int i = 0; i < secondNumLength; i++) {
				if (secondNumLength == 1) {
					two_1 = (int) (Math.random() * 6) + 1;
					secondNum = two_1;
				} else if (secondNumLength == 2) {
					two_1 = (int) (Math.random() * 6) + 1;
					two_2 = (int) (Math.random() * 6) + 1;
					String num = two_2 + "" + two_1;
					secondNum = Integer.parseInt(num);
				} else if (secondNumLength == 3) {
					two_1 = (int) (Math.random() * 6) + 1;
					two_2 = (int) (Math.random() * 6) + 1;
					two_3 = (int) (Math.random() * 6) + 1;
					String num = two_3 + "" + two_2 + "" + two_1;
					secondNum = Integer.parseInt(num);
				}
			}
			return secondNum;
		}
		return -1;
	}
	
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER :
				if(isViewAns==0) {
					answerLabel.setText(form.format(answer)+"  ");
					isViewAns = 1;
					requestFocus();
				}else if(isViewAns==1) {
					answerLabel.setText("문제를 풀어보세요.");
					a = getNumb(1);
					b = getNumb(2);
					arith = getArithmetic();
					answer = cal.cal(a, b);
					isViewAns = 0;
					System.out.println(a +" "+ arith + " "+b+" = "+" "+form.format(answer));
					repaint();
					requestFocus();
				}
				break;
			case KeyEvent.VK_ESCAPE :
				System.exit(0);
				break;
			}
		}
	}
	
}
