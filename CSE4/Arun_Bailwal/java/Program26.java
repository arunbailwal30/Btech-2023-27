import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
 class Program26 extends  JFrame implements ActionListener{
    JTextField tf1,tf2,tf3,tf4;
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;


    public Program26(){
        FlowLayout flyt = new FlowLayout(FlowLayout.CENTER,80,10);
        setLayout(flyt);
        setBackground(Color.yellow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel str = new JLabel("Name: ");         
        JLabel cod = new JLabel("CODE: ");
        JLabel desig = new JLabel("DESIGNATION: ");
        JLabel sal =  new JLabel("SALARY: ");
        tf1  = new JTextField(25);
        tf2  = new JTextField(25);
        tf3  = new JTextField(20);
        tf4  = new JTextField(20);
        JButton bt = new JButton("Save");
        JButton bt2 = new JButton("Reset");
        JButton bt3 = new JButton("Exit");
        add(str);
        add(tf1);
        add(cod);
        add(tf2);
        add(desig);
        add(tf3);
        add(sal);
        add(tf4);
        add(bt);
        add(bt2);
        add(bt3);
        try {
            con = DriverManager.getConnection("jbdc:mysql://loacalhost:3306/test","root","");
            System.out.println("Connection created");
        } catch (Exception e) {
            System.err.println(e);
        }

        bt.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
    }

    public boolean  checkVowel(char ch){
        ch = Character.toLowerCase(ch);
        return (ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u');
    }

    public void actionPerformed(ActionEvent e){
        st = con.prepareStatement("insert into employee(name,code,designation)");
        String name = tf1.getText();
        String code = tf2.getText();
        String sesignation = tf3.getText();
        String salary  = tf4.getText();
        
    }

    public static void main(String[] args) {
        Program26 f = new Program26();
        f.setSize(500,400);
        f.setVisible(true);

    }   
}
