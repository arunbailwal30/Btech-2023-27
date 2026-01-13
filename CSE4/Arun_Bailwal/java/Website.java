import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.util.concurrent.Flow;

class Website extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4;
    JTextField t1, t2;
    JRadioButton t3, t4;
    JPanel p1, p2, p3;
    JTextArea ta;
    JButton bt;

    public Website() {
        p1 = new JPanel();
        p1.setLayout(null);
        l1 = new JLabel("Name: ");
        p1.add(l1);
        t1 = new JTextField(50);
        p1.add(t1);
        l1.setBounds(300, 100, 30, 30);

        p2 = new JPanel();
        l2 = new JLabel("Age: ");
        p2.add(l2);
        t2 = new JTextField(50);
        p2.add(t2);
        l3 = new JLabel("Sex");
        p3 = new JPanel();
        p3.add(l3);
        t3 = new JRadioButton("Male");
        t4 = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(t3);
        bg.add(t4);
        p3.add(t3);
        p3.add(t4);

        add(p1);
        add(p2);
        add(p3);
        bt = new JButton("Submit");
        add(bt);
        l4 = new JLabel("message");
        add(l4);
        ta = new JTextArea(30, 50);
        add(ta);
        p1.setLayout(new FlowLayout());
        setLayout(new FlowLayout());
        bt.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s1 = t1.getText();
        String s2 = t2.getText();
        int age = Integer.parseInt(s2);
        if (age < 18) {
            ta.setText("Your age is low");
        } else {
            ta.setText("Welcome " + s1 + "Your age is " + Integer.parseInt(s2));
        }

    }

    public static void main(String args[]) {
        Website wb = new Website();
        wb.setSize(300, 500);
        wb.setVisible(true);
    }
}