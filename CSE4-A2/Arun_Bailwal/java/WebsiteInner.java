import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WebsiteInner extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4;
    JTextField t1, t2;
    JRadioButton t3, t4;
    JPanel p1, p2, p3;
    JTextArea ta;
    JButton bt;

    public WebsiteInner() {
        setTitle("User  Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize components
        p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        l1 = new JLabel("Name: ");
        t1 = new JTextField(20);
        p1.add(l1);
        p1.add(t1);

        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        l2 = new JLabel("Age: ");
        t2 = new JTextField(20);
        p2.add(l2);
        p2.add(t2);

        p3 = new JPanel();
        l3 = new JLabel("Sex: ");
        t3 = new JRadioButton("Male");
        t4 = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(t3);
        bg.add(t4);
        p3.add(l3);
        p3.add(t3);
        p3.add(t4);

        bt = new JButton("Submit");
        bt.addActionListener(this);

        l4 = new JLabel("Message: ");
        ta = new JTextArea(5, 20);
        ta.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ta);

        // Add components to the frame
        add(p1);
        add(p2);
        add(p3);
        add(bt);
        add(l4);
        add(scrollPane);

        // Add mouse motion listener
        addMouseMotionListener(new pq());

        setSize(300, 400);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    class pq extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            ta.setText("Mouse Position: (" + x + ", " + y + ")");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s1 = t1.getText();
        String s2 = t2.getText();
        try {
            int age = Integer.parseInt(s2);
            if (age < 18) {
                ta.setText("Your age is low.");
            } else {
                ta.setText("Welcome " + s1 + ". Your age is " + age + ".");
            }
        } catch (NumberFormatException ex) {
            ta.setText("Please enter a valid age.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebsiteInner::new);
    }
}
