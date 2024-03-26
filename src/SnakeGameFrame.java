import javax.swing.*;

public class SnakeGameFrame extends JFrame {
    SnakeGameFrame(){
    SnakeGamePanel panel = new SnakeGamePanel();
    this.add(panel);
    this.setTitle("Snake Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    }
}
