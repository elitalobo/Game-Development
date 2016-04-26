package Bricks;
import javax.swing.ImageIcon;

public class Ball extends Sprite implements Commons{
	private int xdir;
	private int ydir;
	private int speed = 1;
	
	public Ball() {
		xdir = -1;
		ydir = -1;
		ImageIcon img = new ImageIcon("data/ball.png");
		image = img.getImage();
		i_width = image.getWidth(null);
		i_height = image.getHeight(null);
		resetState();
	}
	
	public void move() {
		x += xdir*speed;
		y += ydir*speed;
		
		if(x==0){
			setXDir(1);
		}
		
		if(x==WIDTH- i_width){
			setXDir(-1);
		}
		
		if(y<=0){
			setYDir(1);
		}
	}
	
	private void resetState() {
		x = INIT_BALL_X;
		y = INIT_BALL_Y;
	}
	
	public void setXDir(int x){
		xdir = x;
	}
	
	public void setYDir(int y){
		ydir = y;
	}
	
	public int getYDir() {
		return ydir;
	}
	
	public int getXDir() {
		return xdir;
	}
	
	public void setSpeed(int x){
		speed = x;
	}
}
