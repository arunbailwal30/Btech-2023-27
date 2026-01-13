import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ElectricityBill extends JFrame {
    ElectricityBill() {
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
    }

    public static void main(String[] args) {
        new ElectricityBill().setVisible(true);
    }
}
