package ixn.snakegame.objects;

import ixn.snakegame.SnakeGame;
import ixn.snakegame.WorkWithFile;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Snake 
{
	SnakeGame main;
	
	public int direction = 0;
	public int lenght = 2;
	
	Color snakeColor = new Color(200, 150, 0);
	Color Background = new Color(5, 50, 10);
	
	public int snakeX[] = new int[main.WIDTH*main.HEIGHT];
	public int snakeY[] = new int[main.WIDTH*main.HEIGHT];
	
	public Snake(int x0, int y0, int x1, int y1)
	{
		snakeX[0] = x0;
		snakeY[0] = y0;
		snakeX[1] = x1;
		snakeY[1] = y1;
	}
	
	public void drawSnake(Graphics graph, int Scale)
	{
		//–исуем голову змеейки
		graph.setColor(snakeColor);//цвет €рко зеленый
		graph.fillRect(snakeX[0]*Scale+1, snakeY[0]*Scale+1, Scale-1, Scale-1);
		for(int d = 1; d < lenght; d++)//рисуем тело змейки
		{
			graph.setColor(snakeColor);//цвет €рко зеленый
			graph.fillRect(snakeX[d]*Scale+1, snakeY[d]*Scale+1, Scale-1, Scale-1);; 
			graph.setColor(Background);//цвет зеленый
			graph.fillRect(snakeX[d]*Scale+8, snakeY[d]*Scale+8, Scale/2, Scale/2);
		}
	}
	
	public void move()
	{
	
		for(int d = lenght; d > 0; d--)
		{
			snakeX[d] = snakeX[d - 1];
			snakeY[d] = snakeY[d - 1];
		}
		
		if(direction == 0) snakeX[0]++;
		if(direction == 1) snakeY[0]++;
		if(direction == 2) snakeX[0]--;
		if(direction == 3) snakeY[0]--;
		
		for(int d = lenght; d > 0; d--)
		{
			if((snakeX[0] == snakeX[d]) && (snakeY[0] == snakeY[d])) 
				dead();
		}
		
		if(snakeX[0] > main.WIDTH-1) snakeX[0] = 0;
		if(snakeX[0] < 0) snakeX[0] = main.WIDTH-1;
		if(snakeY[0] > main.HEIGHT-1) snakeY[0] = 0;
		if(snakeY[0] < 0) snakeY[0] = main.HEIGHT-1;
	}
	public void dead()
	{
		lenght = 2;
		main.lastResult = main.count;
		JOptionPane.showMessageDialog(null, "¬ы проиграли!");
		try {
			WorkWithFile.writeFile(main.lastResult, main.bestResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
