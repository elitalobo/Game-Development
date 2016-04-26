package Bricks;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class MainGame extends JFrame {

    public MainGame() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        setTitle("Bricks");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                MainGame game = new MainGame();
                game.setVisible(true);                
            }
        });
    }
}