package Bricks;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Paddle extends Sprite implements Commons{
	private int dx;
	public Paddle() {
			ImageIcon img = new ImageIcon("data/paddle.png");
			image = img.getImage();
			
			i_width = image.getWidth(null);
			i_height = image.getHeight(null);
			resetState();
	}
	
	public void move() {
		x+= dx;
		if(x<=0){
			x=0;
		}
		
		if(x>WIDTH- i_width){
			x=WIDTH-i_width;
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT){
			dx =-1;
		}
		
		if(key== KeyEvent.VK_RIGHT){
			dx=1;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT){
			dx=0;
		}
	}
	
	private void resetState() {
		x = INIT_PADDLE_X;
		y = INIT_PADDLE_Y;
	}
}
