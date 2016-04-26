package Bricks;
import javax.swing.ImageIcon;

public class Brick extends Sprite {
	private boolean destroyed;
	
	public Brick(int x, int y){
		this.x =x;
		this.y = y;
		ImageIcon ii = new ImageIcon("data/brickie.png");
		image = ii.getImage();
		
		i_width = image.getWidth(null);
		i_height = image.getHeight(null);
		
		destroyed = false;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public void setDestroyed(boolean val){
		destroyed = val;
	}
}
