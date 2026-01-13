import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 class Form extends  JFrame implements ActionListener{
    JTextField tf1,tf2,tf3;
    public Form(){
        FlowLayout flyt = new FlowLayout(FlowLayout.CENTER,30,10);
        flyt.setHgap(500);
        // JLabel ll = new JLabel("Form");
        flyt.setVgap(100);
        setLayout(flyt);
        setBackground(Color.yellow);


        JLabel name = new JLabel("First Name: ");
        
         tf1  = new JTextField(20);
        JLabel sec = new JLabel("Second Name: ");
        
         tf2  = new JTextField(20);
        JLabel Result = new JLabel("Result: ");
        
         tf3  = new JTextField(20);
        JButton bt = new JButton("Sum");
        JButton bt2 = new JButton("Subtract");
        // add(ll);

        // JScrollBar sb3 = new JScrollBar(JScrollBar.HORIZONTAL,50,15);
        // String data[][] = {
        //     {
        //         "101","Amit","67000"
        //     },{
        //         "102","Jai","7800000"
        //     }
        // };
        // String column[] = {"roll","name","salary"};
        // JTable t = new JTable(data,column);
        // JScrollPane sp = new JScrollPane(t);
        // add(sp);
        add(name);
        add(tf1);
        add(sec);
        add(tf2);
        add(Result);
        add(tf3);
        add(bt);
        add(bt2);
        bt.addActionListener(this);
        bt2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        int x;
        //object s2 = e.getSource();
        //if(s2==b1)
        String s2=e.getActionCommand();
        if(s2.equals("Sum")){
             x= Integer.parseInt(tf1.getText())+Integer.parseInt(tf2.getText());
        }else{
             x= Integer.parseInt(tf1.getText())-Integer.parseInt(tf2.getText());
        }
        String s = Integer.toString(x);
        tf3.setText(s);
    }

    public static void main(String[] args) {
        Form f = new Form();
        f.setSize(1500,800);
        f.setVisible(true);
    }   

    // private void setLayout(FlowLayout flowLayout) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }
}
