import java.util.*;
class PC{
    LinkedList<Integer> list = new LinkedList<>();
    public int capacity=2;
    public void produce() throws Exception{
        int value = 0;
        while (true) { 
            synchronized (this) {
                while (list.size()==capacity) {
                    wait();
            }
            System.out.println("Producer is producing "+(++value));
            list.add(value);
            notifyAll();
            Thread.sleep(500);
        }}
    }
    public void consume() throws Exception{
        int value = 0;
        while (true) { 
            synchronized (this) {
                while (list.size()==0) {
                    wait();
            }
            System.out.println("Consuming item: "+list.remove());
            Thread.sleep(500);
            notifyAll();
        }}
    }

}
class A extends Thread{
    PC p;
    public A(PC p){
        this.p = p;
    }
    public void run(){
        try{
            p.produce();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
class B extends Thread{
    PC p;
    public B(PC p){
        this.p = p;
    }
    public void run(){
        try{
            p.consume();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

class Program20{    
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter capacity of list: ");
        int cap = sc.nextInt();
        
        PC p = new PC();
        p.capacity = cap;
        A t1 = new A(p);
        B t2 = new B(p);
        t1.start();
        t2.start();

    }
}