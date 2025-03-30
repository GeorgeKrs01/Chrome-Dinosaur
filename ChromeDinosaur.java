import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ChromeDinosaur extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 750;
    int boardHeight = 250;

    //images
    Image dinosaurImg;
    Image dinosaurDeadImg;
    Image dinosaurJumpImg;
    Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;



    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //dinosaur
    int dinosaurWidth = 88;
    int dinosaurHeight = 94;
    int dinosaurX = 50;
    int dinosaurY = boardHeight - dinosaurHeight;

    Block dinosaur;


    // cactus
    int cactus1Width = 34;
    int cactus2Width = 69;
    int cactus3Width = 102;

    int cactusHeight = 70;
    int cactusX = 700;
    int cactusY = boardHeight - cactusHeight;
    ArrayList<Block> cactusArray;


    //physics
    int velocityX = -12;
    int velocityY = 0;  //dino jump speed
    int gravity = 1;

    boolean gameOver = false;

    Timer gameLoop;
    Timer placeCactusTimer;

    public ChromeDinosaur() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        dinosaurImg = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
        dinosaurDeadImg = new ImageIcon(getClass().getResource("./img/dino-dead.png")).getImage();
        dinosaurJumpImg = new ImageIcon(getClass().getResource("./img/dino-jump.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./img/cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./img/cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./img/cactus3.png")).getImage();

        //dinosaur
        dinosaur = new Block(dinosaurX, dinosaurY, dinosaurWidth, dinosaurHeight, dinosaurImg);

        //cactus
        cactusArray = new ArrayList<Block>();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        //cactus timer
        placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeCactus();
            }
        });
        placeCactusTimer.start();
    }

    void placeCactus() {

        if (gameOver){
            return;
        }

        double placeCactusChance = Math.random(); // 0 - 0.999
        if (placeCactusChance > .90) { //10% cactus3
            Block cactus = new Block(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
            cactusArray.add(cactus);
        }
        else if (placeCactusChance > .70) { // 20% cactus2
            Block cactus = new Block(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
            cactusArray.add(cactus);
        }
        else if (placeCactusChance > .50) { // 20% cactus1
            Block cactus = new Block(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
            cactusArray.add(cactus);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //dinosaur
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);

        //cactus
        for (int i = 0; i < cactusArray.size(); i++){
            Block cactus = cactusArray.get(i);
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }
    }

    public void move() {
        //dinosaur
        velocityY += gravity;
        dinosaur.y += velocityY;

        if (dinosaur.y > dinosaurY) { //gound collision
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }

        //cactus
        for (int i = 0; i < cactusArray.size(); i++){
            Block cactus = cactusArray.get(i);
            cactus.x += velocityX;

            if (collision(dinosaur, cactus)){
                gameOver = true;
                dinosaur.img = dinosaurDeadImg;
            }
        }
    }

    public boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&               //it just works
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height >  b.y;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placeCactusTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//            System.out.println("Space");
            if (dinosaur.y == dinosaurY) {
                velocityY = -17;
                dinosaur.img = dinosaurJumpImg;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
