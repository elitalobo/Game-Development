package Bricks;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Board extends JPanel implements Commons{
	private Timer timer;
	private String message = "Game Over";
	private static boolean status;
	private int level=0;
	private Ball ball;
	private Paddle paddle;
	private Brick bricks[];
	private boolean ingame = true;
	
	public Board() {
		initBoard();
	}
	
	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		restartBoard();
	}
	
	private void restartBoard(){
		bricks = new Brick[NO_OF_BRICKS];
		setDoubleBuffered(true);
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(),DELAY,PERIOD);
	}
	@Override 
	public void addNotify() {
		super.addNotify();
		gameInit();
	}
	
	private void gameInit() {
		level += 1;
		ball = new Ball();
		ball.setSpeed(level);
		paddle = new Paddle();
		int k =0;
		for(int i=0;i<5;i++) {
			for(int j=0;j<6;j++) {
				bricks[k++]= new Brick(j*40+30,i*10+50);
			
			}
		}
	}
	
	 @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        Graphics2D g2d = (Graphics2D) g;

	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);

	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
	                RenderingHints.VALUE_RENDER_QUALITY);

	        if (ingame && !status) {
	            
	            drawObjects(g2d);
	        } else if(ingame && status && level!=2){
	        	levelCleared(g2d);
	        	status = false;
	        	message = "Game Over :(";
	        	restartBoard();
	        	gameInit();
	        }
	        else {
	            gameFinished(g2d);
	        }

	        Toolkit.getDefaultToolkit().sync();
	    }
	 
	 private void drawObjects(Graphics2D g2d) {
	        
	        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
	                ball.getWidth(), ball.getHeight(), this);
	        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
	                paddle.getWidth(), paddle.getHeight(), this);

	        for (int i = 0; i < NO_OF_BRICKS; i++) {
	            if (!bricks[i].isDestroyed()) {
	                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
	                        bricks[i].getY(), bricks[i].getWidth(),
	                        bricks[i].getHeight(), this);
	            }
	        }
	    }
	 
	 private void gameFinished(Graphics2D g2d) {

	        Font font = new Font("Verdana", Font.BOLD, 18);
	        FontMetrics metr = this.getFontMetrics(font);

	        g2d.setColor(Color.BLACK);
	        g2d.setFont(font);
	        g2d.drawString(message,
	                (Commons.WIDTH - metr.stringWidth(message)) / 2,
	                Commons.WIDTH / 2);
	    }
	 
	 public void levelCleared(Graphics2D g2d){
		 Font font = new Font("Verdana", Font.BOLD, 18);
	        FontMetrics metr = this.getFontMetrics(font);

	        g2d.setColor(Color.BLACK);
	        g2d.setFont(font);
	        g2d.drawString("Level cleared!",
	                (Commons.WIDTH - metr.stringWidth(message)) / 2,
	                Commons.WIDTH / 2);
	 }
	 
	 private class TAdapter extends KeyAdapter {
		 
		 @Override 
		 public void keyReleased(KeyEvent e){
			 paddle.keyReleased(e);
			 
		 }
		 
		 @Override
		 public void keyPressed(KeyEvent e){
			 paddle.keyPressed(e);
		 }
	 }
	 
	 private class ScheduleTask extends TimerTask {

	        @Override
	        public void run() {

	            ball.move();
	            paddle.move();
	            checkCollision();
	            repaint();
	        }
	 }
	 
	 private void stopGame() {

	        ingame = false;
	        timer.cancel();
	        if(level==1 && status){
	        	ingame = true;
	        }
	    }
	 
	 private void checkCollision() {
		 if(ball.getRect().getMaxY() > Commons.BOTTOM_EDGE || ball.getRect().getMaxY() > paddle.getY()+ paddle.getHeight())
			 stopGame();
		 for(int i=0,j=0;i<NO_OF_BRICKS;i++){
			 if(bricks[i].isDestroyed()){
				 j++;
			 }
			 if(j==NO_OF_BRICKS){
				 message= "You Won! :D";
				 status = true;
				 stopGame();
			 }
		 }
		 
		 if(ball.getRect().intersects(paddle.getRect())) {
			 int paddlePos = (int) paddle.getRect().getMinX();
			 int ballPos = (int) ball.getRect().getMinX();
			 int first = paddlePos + 8;
			 int second = paddlePos + 16;
			 int third = paddlePos + 24;
			 int fourth = paddlePos + 32;
			 
			 if(ballPos < first){
				 ball.setXDir(-1);
				 ball.setYDir(-1);
			 }
			 
			 if (ballPos >= first && ballPos < second) {
	                ball.setXDir(-1);
	                ball.setYDir(-1 * ball.getYDir());
	            }

	            if (ballPos >= second && ballPos < third) {
	                ball.setXDir(0);
	                ball.setYDir(-1);
	            }

	            if (ballPos >= third && ballPos < fourth) {
	                ball.setXDir(1);
	                ball.setYDir(-1 * ball.getYDir());
	            }

	            if (ballPos > fourth) {
	                ball.setXDir(1);
	                ball.setYDir(-1);
	            }
	        }
			 
		 for (int i = 0; i < NO_OF_BRICKS; i++) {
	            
	            if ((ball.getRect()).intersects(bricks[i].getRect())) {

	                int ballLeft = (int) ball.getRect().getMinX();
	                int ballHeight = (int) ball.getRect().getHeight();
	                int ballWidth = (int) ball.getRect().getWidth();
	                int ballTop = (int) ball.getRect().getMinY();

	                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
	                Point pointLeft = new Point(ballLeft - 1, ballTop);
	                Point pointTop = new Point(ballLeft, ballTop - 1);
	                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

	                if (!bricks[i].isDestroyed()) {
	                    if (bricks[i].getRect().contains(pointRight)) {
	                        ball.setXDir(-1);
	                    } else if (bricks[i].getRect().contains(pointLeft)) {
	                        ball.setXDir(1);
	                    }

	                    if (bricks[i].getRect().contains(pointTop)) {
	                        ball.setYDir(1);
	                    } else if (bricks[i].getRect().contains(pointBottom)) {
	                        ball.setYDir(-1);
	                    }

	                    bricks[i].setDestroyed(true);
	                }
	            }
	        }
	    }

}
