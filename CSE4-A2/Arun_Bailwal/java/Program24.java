import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 class Program24 extends  JFrame implements ActionListener{
    JTextField tf1,tf2;
    public Program24(){
        FlowLayout flyt = new FlowLayout(FlowLayout.CENTER,30,10);
        setLayout(flyt);
        setBackground(Color.yellow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel str = new JLabel("Enter string: ");
        
         tf1  = new JTextField(20);
        JLabel Result = new JLabel("Result: ");
        
         tf2  = new JTextField(20);
        JButton bt = new JButton("CountVowel");
        JButton bt2 = new JButton("Reset");
        JButton bt3 = new JButton("Exit");
        add(str);
        add(tf1);
        add(tf2);
        add(Result);
        add(tf2);
        add(bt);
        add(bt2);
        add(bt3);
        bt.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
    }

    public boolean  checkVowel(char ch){
        ch = Character.toLowerCase(ch);
        return (ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u');
    }

    public void actionPerformed(ActionEvent e){
        String x = tf1.getText();
        String s2=e.getActionCommand();
        if(s2.equals("CountVowel")){
            int count =0;
            for(int i=0;i<x.length();i++){
                if(checkVowel(x.charAt(i))){
                    count++;
                }
            }
            tf2.setText(Integer.toString(count));
        }else if(s2.equals("Reset")){
             tf1.setText("");
             tf2.setText("");
        }else if(s2.equals("Exit")){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Program24 f = new Program24();
        f.setSize(400,300);
        f.setVisible(true);
    }   
}
