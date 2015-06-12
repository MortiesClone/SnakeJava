package ixn.snakegame;

import ixn.snakegame.objects.Apple;
import ixn.snakegame.objects.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.Timer;

public class SnakeGame extends JPanel implements Runnable//Jpanel ��� ���� � ������ � ���, ActionListener ��� ������ � �����������
{
	
	public static final int SCALE = 32;
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	public static final int SPEED = 5;
	
	public static int count = 0;
	public static int bestResult = 0;
	public static int lastResult = 0;
	
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
		g.setColor(color (5, 50, 10));//������ ���� � ������� RGB(�������)
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);//���
		g.setColor(color(255, 216, 0));//����� ���� �����(������)
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
		g.setColor(color(200, 150, 0));//���� ���� �������
		g.fillRect(s.snakeX[0]*SCALE+1, s.snakeY[0]*SCALE+1, SCALE-1, SCALE-1);
		for(int d = 1; d < s.lenght; d++)//������ ���� ������
		{
			g.setColor(color(200, 150, 0));//���� ���� �������
			g.fillRect(s.snakeX[d]*SCALE+1, s.snakeY[d]*SCALE+1, SCALE-1, SCALE-1);; 
			g.setColor(color(5, 50, 10));//���� �������
			g.fillRect(s.snakeX[d]*SCALE+8, s.snakeY[d]*SCALE+8, SCALE/2, SCALE/2);
		}
		//������ ������
		g.setColor(color(200, 30, 100));//���� �����
		g.drawString("�������: " + count, 5, 15);//������ ������
		g.drawString("���������� ���������: " + lastResult, 80, 15);
		g.drawString("������ ���������: " + bestResult, 250, 15);
		g.setColor(color(255, 255, 255));//���� �����
		g.fillOval(a.posX*SCALE+1, a.posY*SCALE+1, SCALE-1, SCALE-1);
		g.setColor(color(55, 0, 0));//���� �������
		g.fillOval(a.posX*SCALE+8, a.posY*SCALE+8, SCALE/2, SCALE/2);
	}
	
	public Color color(int red, int green, int blue)
	{
		return new Color(red, green, blue);
	}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame("Snake");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(WIDTH*SCALE+7, HEIGHT*SCALE+29);
		f.setLocationRelativeTo(null);
		f.add(new SnakeGame());
		f.setVisible(true);
	}
	
	private class Keyboard extends KeyAdapter//������ � ����������
	{
		public void keyPressed(KeyEvent kEvent)
		{
			int key = kEvent.getKeyCode();
			
			if((key == KeyEvent.VK_RIGHT) && s.direction != 2) s.direction = 0;
			if((key == KeyEvent.VK_DOWN) && s.direction != 3) s.direction = 1;
			if((key == KeyEvent.VK_LEFT) && s.direction != 0) s.direction = 2;
			if((key == KeyEvent.VK_UP) && s.direction != 1) s.direction = 3;
		}
	}

	public void run() {
		while (true){
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
		repaint();//�����������
		try {
			t1.sleep(80);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		}
	}
}