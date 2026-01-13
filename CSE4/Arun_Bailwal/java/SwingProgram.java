// java awt // cons - gui not same in different computers and os

//java swing // Platform independent


//JFC java foundatino classes
import java.awt.*;
import javax.swing.*;

public class SwingProgram extends Canvas{
    public void paint(Graphics g){
        setBackground(Color.yellow);
        // setForeground(Color.green);
        // g.drawString("hello java",150,200);
        // g.drawLine(50, 80,200 ,300);
        // g.drawOval(150, 200, 200, 100);
        // g.fillOval(150, 200, 200, 100);
        // g.fillArc(50,160,300,100,0,90);
        // g.fillRect(10, 20, 100, 200);
        // int[] x = {20,35,50,65,80,95};
        // int[] y = {60,105,105,110,95,95};
        // g.drawPolygon(x,y,6);
        // g.fillPolygon(x,y,6);
        for(int i=0;i<200;i++){
            // resetDrawing();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
            }
        g.drawOval(150+i,150,200,200);
        setForeground(Color.red);
        g.fillOval(200+i,190,30,30);
        g.fillOval(270+i,190,30,30);
        g.drawArc(200+i, 240, 100, 70, 225, 90);
        g.drawArc(200+i, 290, 100, 70, 45, 90);
        g.setColor(getBackground());
        }

        
    }

    public void resetDrawing() {
        revalidate();
        repaint();
    }
    public static void main(String args[]){
        SwingProgram b1 = new SwingProgram();
        JFrame f = new JFrame();
        f.add(b1);
        f.setSize(700,400);
        f.setVisible(true);
    }
}

