import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener,KeyListener{
    int boardheight=640;
    int boardwidth=360;

    Image backgroundImg;
    Image BirdImage;
    Image BottomPipeImage;
    Image TopPipeImage;

    //bird
    int birdx = boardwidth/8;
    int birdy = boardheight/2;
    int birdwidth=34;
    int birdheight=24;
    
    class Bird{
        int x=birdx;
        int y=birdy;
        int width=birdwidth;
        int height=birdheight;
        Image img;
    
    Bird(Image img){
            this.img=img; 
                }
    }

    //pipes
    int pipex = boardwidth;
    int pipey = 0;
    int pipewidth=64;
    int pipeheight=512;

    class Pipe{
        int x=pipex;
        int y=pipey;
        int height=pipeheight;
        int width=pipewidth;
        Image img;
        boolean passed =false;

        Pipe(Image img){
            this.img=img;
        }
    }
    
    



    //bird logic
    Bird bird;
    int velocityX= -4;
    int velocityY= 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Timer gameloop;
    Timer placepipestimer;
    double score=0;
    boolean gameover =false;


    FlappyBird(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setFocusable(true);
        addKeyListener(this);

        
        
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        BirdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        BottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        TopPipeImage =new ImageIcon(getClass().getResource("./toppipe.png")).getImage();


        bird =new Bird(BirdImage);
        pipes=new ArrayList<Pipe>();



        //game timer
        gameloop =new Timer(1000/60,this);
        gameloop.start();
        placepipestimer =new Timer(1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placepipes();
            }
        });
        placepipestimer.start();

    }
   

    public void placepipes(){
        int randomPipeY = (int) (pipey - pipeheight/4 - Math.random()*(pipeheight/2)); 
        int openingspace =boardheight/4;
        Pipe topPipe =new Pipe(TopPipeImage);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe =new Pipe(BottomPipeImage);
        bottomPipe.y=topPipe.y + pipeheight + openingspace;
        pipes.add(bottomPipe);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg, 0, 0,boardwidth,boardheight,null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height,null);

        for(int i=0;i<pipes.size();i++){
            Pipe pipe =pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y,pipe.width,pipe.height,null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameover){
            g.drawString("Game Over: "+ String.valueOf((int) score), 10,35);
        }
        else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }
       
    }

    public void move(){
        //bird
        velocityY += gravity;
        bird.y+=velocityY;
        bird.y=Math.max(bird.y,0);
        
        
        //pipes
        for(int i=0;i<pipes.size();i++){
            Pipe pipe =pipes.get(i);
            pipe.x +=velocityX;

            if(!pipe.passed && bird.x>pipe.x + pipe.width){
                pipe.passed=true;
                score+=0.5;
            }

            if(collision(bird, pipe)){
                gameover=true;

            }
            }

            if(bird.y>boardheight){
                gameover=true;

            }

            

        }
    public boolean collision(Bird a,Pipe b){
        return a.x<b.x+b.width && a.x+a.width>b.x &&
        a.y<b.y+b.height && a.y+a.height>b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
        move();
        repaint();
        if(gameover){
            placepipestimer.stop();
            gameloop.stop();
        }

    }



    @Override
    public void keyTyped(KeyEvent e) {
        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY=-9;
            if(gameover){
                bird.y=birdy;
                velocityY=0;
                pipes.clear();
                score=0;
                gameover=false;
                gameloop.start();
                placepipestimer.start();

            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    
}
