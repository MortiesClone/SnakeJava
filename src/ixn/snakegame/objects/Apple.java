package ixn.snakegame.objects;

import ixn.snakegame.SnakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class Apple {
	
	SnakeGame main;
	
	Color appleColor = new Color(206, 32, 41);
	
	public int posX;
	public int posY;
	
	public void drawApple(Graphics graph, int Scale)
	{
		//рисуем €блоко
		graph.setColor(appleColor);
		graph.fillOval(posX*Scale+1, posY*Scale+1, Scale-1, Scale-1);
	}
	
	public Apple(int startX, int startY)
	{
		posX = startX;
		posY = startY;
	}
	
	public void setRandomPosition()
	{
		posX = (int) (Math.random()*main.WIDTH);
		posY = (int) (Math.random()*main.HEIGHT);
	}
}
