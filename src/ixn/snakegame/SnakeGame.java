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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener//Jpanel дл€ окна и работы с ним, ActionListener дл€ работы с клавиатурой
{
	
	public static final int SCALE = 32;
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	public static final int SPEED = 5;//—корость обновлени€(типа fps) и скорость змейки
	
	public static int count = 0;
	
	Apple a = new Apple((int)(Math.random()*WIDTH), (int)(Math.random()*HEIGHT));//—оздаем €блоко с случайными координатами, (int)(Math.random()*HEIGHT) <- это преобразование полученного из функции помноженной на высоту окна Math.random()*HEIGHT из типа float в тип int
	Snake s = new Snake(10, 10, 9, 10);//тут создаем змейку где 10 и 10 координаты головы, а 9 и 10 координаты второго хвоста(короче второго элемента массива)
	Timer t = new Timer(1000/SPEED, this);//“аймер дл€ fps и скорости змейки
	
	public SnakeGame()
	{
		t.start();//«апускаем таймер
		addKeyListener(new Keyboard());//создаем слушател€, дл€ клавиатуры
		setFocusable(true);//тут хрен знает что происходит
	}
	
	public void paint(Graphics g)//функци€ дл€ рисовани€ всего)
	{
		g.setColor(color (5, 50, 10));//задаем цвет в формате RGB(зеленый)
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);//рисуем квадрат, который будет выступать в каччестве фона
		g.setColor(color(255, 216, 0));//задем цвет линий(желтый)
		//–исуем линии по х(вертикально)
		for(int xx = 0; xx <= WIDTH*SCALE; xx+=SCALE)
		{
			g.drawLine(xx, 0, xx, HEIGHT*SCALE);//рисует линию первое xx это координаты вверху окна, второе xx это координаты снизу окна, HEIGHT*SCALE <- размер линий снизу вверх
		}
		//–исуем линии по y(горизонтально)
		for(int yy = 0; yy <= HEIGHT*SCALE; yy+=SCALE)
		{
			g.drawLine(0, yy, WIDTH*SCALE, yy);//ну тут тоже самое только наоборот
		}
		//–исуем змейку
		for(int d = 0; d < s.lenght; d++)
		{
			g.setColor(color(200, 150, 0));//цвет €рко зеленый
			g.fillRect(s.snakeX[d]*SCALE+1, s.snakeY[d]*SCALE+1, SCALE-1, SCALE-1);//g.fillRect( координата х, координата у, ширина, высота); 
			g.setColor(color(5, 50, 10));//цвет зеленый
			g.fillRect(s.snakeX[d]*SCALE+8, s.snakeY[d]*SCALE+8, SCALE/2, SCALE/2);
		}
		//рисуем €блоко
		g.setColor(color(255, 255, 255));//цвет белый
		g.drawString("—обрано: " + count, 5, 15);
		g.fillRect(a.posX*SCALE+1, a.posY*SCALE+1, SCALE-1, SCALE-1);
		g.setColor(color(55, 0, 0));//цвет красный
		g.fillRect(a.posX*SCALE+8, a.posY*SCALE+8, SCALE/2, SCALE/2);
	}
	
	public Color color(int red, int green, int blue)
	{
		return new Color(red, green, blue);
	}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame("Snake");//создаем окно
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//стандартна€ оперца€, короче чтобы когда нажимаешь на крестик программа закрывалась
		f.setResizable(false);//неизмен€емый размер
		f.setSize(WIDTH*SCALE+7, HEIGHT*SCALE+29);//размер окна
		f.setLocationRelativeTo(null);//размещение окна по центру экрана
		f.add(new SnakeGame());//тут надеюсь пон€тно
		f.setVisible(true);//делаем окно видимым
	}

	public void actionPerformed(ActionEvent arg0)
	{
		s.move();//движение змейки
		if((s.snakeX[0] == a.posX) && (s.snakeY[0] == a.posY))//съдание змейкой €блока, если позици€ головы ровна позиции €блока
		{
			a.setRandomPosition();//изменение позиции €блока
			s.lenght++;//увеличение змейки
			count++;
		}
		//это чтобы €блоко не генерировалось на змейке
		for(int k = 1; k < s.lenght; k++)
		{
			if((s.snakeX[k] == a.posX) && (s.snakeY[k] == a.posY))//если координаты €блока наход€тс€ на координатах змейки
			{
				a.setRandomPosition();//то изменить позицию
			}
		}
		repaint();//перерисовка
	}
	
	private class Keyboard extends KeyAdapter//чтение с клавиатуры
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
}
