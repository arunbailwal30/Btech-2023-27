import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ScrollProgram extends JFrame implements AdjustmentListener {

    JScrollBar s1, s2, s3;
    JTextArea ta;

    public ScrollProgram() {
        setTitle("User  Information");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        JLabel l1 = new JLabel("Red");
        JLabel l2 = new JLabel("Green");
        JLabel l3 = new JLabel("Blue");
        ta = new JTextArea(30, 40);
        s1 = new JScrollBar(JScrollBar.HORIZONTAL, 10, 20, 0, 255);
        s2 = new JScrollBar(JScrollBar.HORIZONTAL, 10, 20, 0, 255);
        s3 = new JScrollBar(JScrollBar.HORIZONTAL, 10, 20, 0, 255);
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        // p1.setLayout(null);
        p1.add(l1);
        p1.add(s1);
        p2.add(l2);
        p2.add(s2);
        p3.add(l3);
        p3.add(s3);
        add(p1);
        add(p2);
        add(p3);
        add(ta);
        s1.addAdjustmentListener(this);
        s2.addAdjustmentListener(this);
        s3.addAdjustmentListener(this);

    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        int r = s1.getValue();
        int g = s2.getValue();
        int b = s3.getValue();
        Color c = new Color(r, g, b);
        ta.setBackground(c);
    }

    public static void main(String[] args) {
        new ScrollProgram().setVisible(true);

    }
}
