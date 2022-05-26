import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Pong {
    public static void main(String[] args){
        JFrame frame=new JFrame("Pong");
        frame.setSize(1000,800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PongPanel panel=new PongPanel();
        frame.add(panel);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setAlwaysOnTop(false);
    }
    public static class PongPanel extends JPanel implements ActionListener,KeyListener{
        boolean wPressed=false;
        boolean start=true;
        boolean sPressed=false;
        int LeftPaddleY;
        int RightPaddleY;
        int BallX;
        int BallY;
        int leftScore=0;
        int rightScore=0;
        boolean downPressed=false;
        boolean upPressed=false;
        int BallXSpeed=1;
        int BallYSpeed=7;
        Timer timer;
        int LeftPaddleX;
        int LeftPaddleW;
        int LeftPaddleH;
        int RightPaddleX;
        int RightPaddleW;
        int RightPaddleH;
        int BallW;
        int BallH;
        public PongPanel(){
            setFocusable(true);
            addKeyListener(this);
            timer=new Timer(1000/60,this);
            timer.start();
            setBackground(new Color(0, 0, 0));
            LeftPaddleY=getHeight()/2;
            RightPaddleY=getHeight()/2;
        }
        @Override
        public void keyTyped(KeyEvent e){

        }
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode()==KeyEvent.VK_W){
                wPressed=true;
            }
            if(e.getKeyCode()==KeyEvent.VK_S){
                sPressed=true;
            }
            if(e.getKeyCode()==KeyEvent.VK_UP){
                upPressed=true;
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                downPressed=true;
            }
        }
        public void keyReleased(KeyEvent e){
            if(e.getKeyCode()==KeyEvent.VK_W){
                wPressed=false;
            }
            if(e.getKeyCode()==KeyEvent.VK_S){
                sPressed=false;
            }
            if(e.getKeyCode()==KeyEvent.VK_UP){
                upPressed=false;
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                downPressed=false;
            }
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if(start){
                LeftPaddleY=getHeight()/2-50;
                RightPaddleY=getHeight()/2-50;
                BallX=getWidth()/2-5;
                BallY=getHeight()/2-5;
                LeftPaddleX=getWidth()/10;
                LeftPaddleW=10;
                LeftPaddleH=100;
                RightPaddleX=getWidth()/10*9;
                RightPaddleW=10;
                RightPaddleH=100;
                BallW=10;
                BallH=10;
                BallYSpeed=(int)(Math.random()*6);
                BallXSpeed=5;
                if(BallYSpeed==0){
                    BallYSpeed=1;
                }
                start=false;
            }
            g.setColor(new Color(224, 13, 13));
            g.fillRect(LeftPaddleX,LeftPaddleY,LeftPaddleW,LeftPaddleH);
            g.fillRect(BallX,BallY,BallW,BallH);
            g.fillRect(RightPaddleX,RightPaddleY,RightPaddleW,RightPaddleH);
            g.setFont(new Font("Times New Roman",Font.BOLD,30));
            g.drawString("Left Score: "+leftScore,0,getHeight()/10);
            g.drawString("Right Score: "+rightScore,getWidth()/5*4,getHeight()/10);
        }
        public void actionPerformed(ActionEvent e){
            if(BallY<=0||BallY+10>=getHeight()){
                BallYSpeed=-BallYSpeed;
            }
            if(BallX<=0||BallX+10>getWidth()){
                if(BallX<=0){
                    rightScore++;
                }
                else{
                    leftScore++;
                }
                start=true;
                BallXSpeed=-BallXSpeed;
                BallYSpeed=-BallYSpeed;
            }
            if(e.getSource()==timer) {
                if (wPressed&&!(LeftPaddleY<0)) {
                    LeftPaddleY -= 10;
                }
                if (sPressed&&LeftPaddleY+LeftPaddleH<getHeight()) {
                    LeftPaddleY += 10;
                }
                if (upPressed&&RightPaddleY>0) {
                    RightPaddleY -= 10;
                }
                if (downPressed&&RightPaddleY+RightPaddleH<getHeight()) {
                    RightPaddleY += 10;
                }
                if(intersects(LeftPaddleX,LeftPaddleY,LeftPaddleW,LeftPaddleH)){
                    BallXSpeed=-BallXSpeed;
                    BallXSpeed+=BallXSpeed<0?-1:1;
                    BallYSpeed+=BallYSpeed<0?-1:1;
                    if(BallX<LeftPaddleX+LeftPaddleW){
                        BallX=LeftPaddleX+LeftPaddleW;
                    }
                }
                if(intersects(RightPaddleX,RightPaddleY,RightPaddleW,RightPaddleH)){
                    BallXSpeed=-BallXSpeed;
                    BallXSpeed+=BallXSpeed<0?-1:1;
                    BallYSpeed+=BallYSpeed<0?-1:1;
                    if(BallX+BallW>RightPaddleX){
                        BallX=RightPaddleX-BallW;
                    }
                }
                BallX += BallXSpeed;
                BallY += BallYSpeed;
            }
            repaint();
        }
        public boolean intersects(int pX,int pY,int pW,int pH){
            int bx1=this.BallX;
            int by1=this.BallY;
            int bx2=bx1+BallW;
            int by2=BallH+by1;
            int px2=pX+pW;
            int py2=pY+pH;
            return bx2>pX&&bx1<px2&&by2>pY&&by1<py2;
        }
    }
}
