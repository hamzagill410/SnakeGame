import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JLabel;

public class SnakeGamePanel extends JPanel implements ActionListener {
static final int GAME_WIDTH = 500;
static final int GAME_HEIGHT = 650;

static final int FOOD_SIZE = 20;

static final int SNAKE_FOOD = (GAME_WIDTH*GAME_HEIGHT)/FOOD_SIZE; //how many objects can fit in the game


static final int GAME_DELAY = 75;

final int x[] = new  int[FOOD_SIZE];//Hold coordinate of the head of snake
final int y[] = new int[FOOD_SIZE];//Hold coordinate of the body part of snake

int snakeBody = 5;
int foodEat;
int foodX;
int foodY;

char snakeDirection = 'D';
boolean running = false;
Timer gameTimer;
Random random;
    SnakeGamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter() );

        initGame();

    }
    public void initGame(){
        newEatFood();
        running = true;
        gameTimer = new Timer(GAME_DELAY,this);
        gameTimer.start();

    }
    public void paintComponent(Graphics x){
        super.paintComponent(x);
        drawComponent(x);
      }

    public void drawComponent(Graphics g) {
        if (running) {//If game is running
            g.setColor(Color.black);
            g.fillRect(foodX, foodY, FOOD_SIZE, FOOD_SIZE);
            //iterates through  the body parts of a snake to create
            for (int i = 0; i < snakeBody; i++) {
                if (i == 0) {//indicates snake starts at its head
                    g.setColor(Color.RED);
                    g.fillOval(x[i], y[i], FOOD_SIZE, FOOD_SIZE);
                } else {
                    g.setColor(Color.BLUE);
                    g.fillOval(x[i], y[i], FOOD_SIZE, FOOD_SIZE);
                }
                g.setFont(new Font("Action Man",Font.ITALIC,40));
                g.setColor(Color.black);
                FontMetrics fMetrics = getFontMetrics(g.getFont());
                g.drawString("Your Score:" + foodEat,(GAME_WIDTH - fMetrics.stringWidth("Your Score:" + foodEat))/2,g.getFont().getSize());
            }
        }else {
            snakeGameOver(g);
        }
    }


    public void checkCrash(){//checks instances of snake crashing with its body
        for(int i = snakeBody;i>0;i--){
           if((x[0] == x[i]) && (y[0] == y[i])){
               running = false;//end game

           }
        }
        //Checks instances of snake crashing with horizontal and vertical border walls
        switch (snakeDirection){
            case 'R':
                if(x[0] > GAME_WIDTH){
                    running = false;
                }
            case 'L':
                if(x[0]< 0){
                    running = false;
                }
            case 'U':
                if(y[0] < 0){
                    running = false;
                }
            case 'D':
                if(y[0] > GAME_HEIGHT){
                    running = false;
                }
                break;
        }
        if(!running){
            gameTimer.stop();
        }

    }

    public void newEatFood(){//generate coordinate for food when game begins
        foodX = random.nextInt(GAME_WIDTH/FOOD_SIZE)*FOOD_SIZE; //vertical food
        foodY = random.nextInt(GAME_HEIGHT/FOOD_SIZE)*FOOD_SIZE;//horizontal food
    }
    public void checkFood(){
        if((x[0] == foodX) && (y[0] == foodY)){
            snakeBody = snakeBody + 1;
            foodEat = foodEat + 1;
            newEatFood();//calls method to create new food to eat
        }

    }

    public void crawl(){//iterate through the body parts of snake
        for(int i= snakeBody;i > 0 ;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(snakeDirection){//checking snake direction
            case 'D':
                y[0] = y[0] + FOOD_SIZE;
                break;
            case 'U':
                y[0] = y[0] - FOOD_SIZE;
                break;
            case 'L':
                x[0] = x[0] - FOOD_SIZE;
                break;
            case 'R':
                x[0] = x[0] + FOOD_SIZE;


        }

    }
    public void snakeGameOver(Graphics x){
    //generates Text of Game over
        x.setFont(new Font("Action Man",Font.ITALIC,55));
        x.setColor(Color.BLACK);
        FontMetrics fMetrics = getFontMetrics(x.getFont());
        x.drawString("Game Over!",(GAME_WIDTH - fMetrics.stringWidth("Game Over !"))/2,GAME_HEIGHT/2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            crawl();//calls for snake to move
            checkFood();//if run into a food
            checkCrash();//checks for collisions in the border

        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
    int  keybutton = e.getKeyCode();
    if(keybutton == KeyEvent.VK_LEFT && snakeDirection !='R'){
        snakeDirection = 'L';

    } else if (keybutton == KeyEvent.VK_RIGHT && snakeDirection !='L') {
        snakeDirection = 'R';
    } else if (keybutton == KeyEvent.VK_UP && snakeDirection!= 'D') {
        snakeDirection = 'U';
    } else if (keybutton == KeyEvent.VK_DOWN && snakeDirection != 'U') {
        snakeDirection = 'D';
    }
    }
        }

        }



