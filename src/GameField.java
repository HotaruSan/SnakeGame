import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by HotaruSan on 03.02.2018.
 * Main game window
 * In this class we did logic of the game
 */
public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    //pixel size of our apple and snake body
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX, appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    //size of snake
    private int dots;
    private Timer timer;
    //move direction
    private boolean left = false, right = true;
    private boolean up = false, down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        //to get focuse on GameField window
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i <= dots; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        //initialize timer every 250 milliseconds
        timer = new Timer(250, this);
        timer.start();
        createApple();

    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        //load image icon 16x16 png and initialize apple with snake body
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();

    }

    //that draw our GameField and apple with snake
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for(int i = 0; i < dots; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        } else {
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str, 125, SIZE/2);
        }
    }


    public void move(){
        for(int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left) x[0] -= DOT_SIZE;
        if(right) x[0] += DOT_SIZE;
        if(up) y[0] -= DOT_SIZE;
        if(down) y[0] += DOT_SIZE;
    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            dots++;
            createApple();
        }
    }

    public void checkCollision(){
        //If we bit our tail
        for (int i = dots; i > 0 ; i--) {
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }
        //If we stand out of the field
        if(x[0] > SIZE) inGame = false;
        if(x[0] < 0) inGame = false;
        if(y[0] > SIZE) inGame = false;
        if(y[0] < 0) inGame = false;
    }

    //main action broker
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollision();
            move();

        }
        repaint();
    }

    //read keypress and change move direction
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
