package ixn.snakegame;

import ixn.snakegame.objects.Apple;
import ixn.snakegame.objects.Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel implements Runnable
{
	
	public static final int SCALE = 32;
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	public static int Speed = 110;
	public static int controlSpeed = 5;
	public static int count = 0;
	public static int bestResult = 0;
	public static int lastResult = 0;
	
	public static boolean isPause = false;
	
	Font stringFont = new Font("Times New Roman", Font.BOLD, 15);
	
	Color Background = new Color(5, 50, 10);
	Color Line = new Color(255, 216, 0);
	Color snakeColor = new Color(200, 150, 0);
	Color stringColor = new Color(255, 255, 255);
	Color appleColor = new Color(206, 32, 41);
	
	Apple a = new Apple((int)(Math.random()*WIDTH), (int)(Math.random()*HEIGHT));
	Snake s = new Snake(10, 10, 9, 10);
	Thread t1 = new Thread(this);
		
	public SnakeGame()
	{
		t1.start();
		addKeyListener(new Keyboard());
		setFocusable(true);
	}
	
	public void paint(Graphics g)//������� ��� ��������� �����
	{
		g.setColor(Background);
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);//���
		
		g.setColor(Line);
		//������ ����� �� �(�����������)
		for(int xx = 0; xx <= WIDTH*SCALE; xx+=SCALE)
		{
			g.drawLine(xx, 0, xx, HEIGHT*SCALE);
		}
		//������ ����� �� y(�������������)
		for(int yy = 0; yy <= HEIGHT*SCALE; yy+=SCALE)
		{
			g.drawLine(0, yy, WIDTH*SCALE, yy);
		}
		
		//������ ������ �������
		g.setColor(snakeColor);//���� ���� �������
		g.fillRect(s.snakeX[0]*SCALE+1, s.snakeY[0]*SCALE+1, SCALE-1, SCALE-1);
		for(int d = 1; d < s.lenght; d++)//������ ���� ������
		{
			g.setColor(snakeColor);//���� ���� �������
			g.fillRect(s.snakeX[d]*SCALE+1, s.snakeY[d]*SCALE+1, SCALE-1, SCALE-1);; 
			g.setColor(Background);//���� �������
			g.fillRect(s.snakeX[d]*SCALE+8, s.snakeY[d]*SCALE+8, SCALE/2, SCALE/2);
		}
		
		//������ ������
		g.setFont(stringFont);
		g.setColor(stringColor);
		g.drawString("�������: " + count, 5, 15);
		g.drawString("���������� ���������: " + lastResult, 100, 15);
		g.drawString("������ ���������: " + bestResult, 310, 15);
		g.drawString("��� ����� �������: P", WIDTH*SCALE-170, HEIGHT*SCALE-10);
		
		if(isPause)
			g.drawString("�����", (WIDTH*SCALE+7)/2, (HEIGHT*SCALE+29)/2);
		
		//������ ������
		g.setColor(appleColor);
		g.fillOval(a.posX*SCALE+1, a.posY*SCALE+1, SCALE-1, SCALE-1);
	}
	
	public static void main(String[] args)
	{
		int results[] = new int[2];
		JFrame f = new JFrame("Snake");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(WIDTH*SCALE+7, HEIGHT*SCALE+29);
		f.setLocationRelativeTo(null);
		try {
			results = WorkWithFile.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lastResult = results[0];
		bestResult = results[1];
		f.add(new SnakeGame());
		f.setVisible(true);
	}
	
	private class Keyboard extends KeyAdapter//������ � ����������
	{
		public void keyPressed(KeyEvent kEvent)
		{
			int key = kEvent.getKeyCode();
			
			if((key == KeyEvent.VK_P)  && (isPause == false))isPause = true;
			else if((key == KeyEvent.VK_P)  && (isPause == true))isPause = false;
			
			if((key == KeyEvent.VK_RIGHT) && s.direction != 2) s.direction = 0;
			if((key == KeyEvent.VK_DOWN) && s.direction != 3) s.direction = 1;
			if((key == KeyEvent.VK_LEFT) && s.direction != 0) s.direction = 2;
			if((key == KeyEvent.VK_UP) && s.direction != 1) s.direction = 3;
		}
	}

	public void run() {
		while (true){
			if(isPause == false)
			{
				s.move();//�������� ������
				
				if((s.snakeX[0] == a.posX) && (s.snakeY[0] == a.posY))//������� ������� ������
				{
					a.setRandomPosition();//��������� ������� ������
					s.lenght++;
					count++;
				}
				//��� ����� ������ �� �������������� �� ������
				for(int k = 1; k < s.lenght; k++)
				{
					if((s.snakeX[k] == a.posX) && (s.snakeY[k] == a.posY))//���� ���������� ������ ��������� �� ����������� ������
					{
						a.setRandomPosition();//�� �������� �������
					}
				}
				if(bestResult < count)
					bestResult = count;
			}
			if(controlSpeed == count)
			{
				controlSpeed += 5;
				Speed -= 2;
			}
			repaint();//�����������
			try {
				Thread.sleep(Speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}
}