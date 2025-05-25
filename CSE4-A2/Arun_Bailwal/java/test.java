

class Display {
    public synchronized  void wish(String name){
        try {
            System.out.print(Thread.currentThread().getName());
            System.out.print("Hello ");
            Thread.sleep(500);
            System.out.println(name);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        // System.out.print("Rest of");

    }
}

class Object1 extends  Thread{
    Display d;

    public Object1(Display d) {
        this.d = d;
    }
    
    public void run(){
        // System.out.println("thread 1");
        d.wish("Arun");
        
    }
}

class Object2 extends  Thread{
    Display d;

    public Object2(Display d) {
        this.d = d;
    }
    
    public void run(){
        
        d.wish("Gurmeet");
        
    }
}

class Test{
    public static void main(String args[]){
        Display d1 = new Display();
        Object1 o1 = new Object1(d1);
        Object2 o2 = new Object2(d1);
        o1.start();
        o2.start();
        try {
            o1.join();
        } catch (Exception e) {
        }
        try {
            o2.join();
        } catch (Exception e) {
        }
        System.out.println("End of program");

    }
}