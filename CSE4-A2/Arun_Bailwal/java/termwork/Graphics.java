import java.awt.*;
import javax.swing.*;
class Graphics extends JFrame{
    public Graphics(){
        JLabel ll = new JLabel("Jame");
        setLayout(new FlowLayout());
        JTextField tf1  = new JTextField(20);
        JTextArea ta = new JTextArea(10,20);
        JScrollPane jp1 = new JScrollPane(ta);
        String s[] = {"India", "USA", "UK"};
        JComboBox cb = new JComboBox(s);
        JButton bt = new JButton("Submit");
        JCheckBox cb1 = new JCheckBox();
        JCheckBox cb2 = new JCheckBox("CSE");
        JCheckBox cb3 = new JCheckBox("ECE",true);
        JRadioButton r1= new JRadioButton();
        JRadioButton r2= new JRadioButton("CSE");
        JRadioButton r3= new JRadioButton("ECE", true);
        ButtonGroup bg  = new ButtonGroup();
        DefaultListModel<String> dl = new DefaultListModel<>();
        dl.addElement("Item1");
        dl.addElement("Item2");
        dl.addElement("Item3");
        JList<String> list = new JList<>(dl);
        add(list);
        bg.add(r1);
        bg.add(r2);
        bg.add(r3);
        add(r1);
        add(r2);
        add(r3);
        add(cb1);
        add(cb2);
        add(cb3);
        add(bt);
        add(cb);
        add(jp1);
        add(tf1);
        add(ll);

    }
    public static void main(String[] args) {
        Graphics f = new Graphics();
        f.setSize(300,400);
        f.setVisible(true);
    }
}