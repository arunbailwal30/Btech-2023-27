// import java.lang.Thread;


class Table{
    public synchronized void ptable(int x){
        for(int i=1; i<=20;i++){

            System.out.println(x + " * "+i+ " = "+(x*i));
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        
    }
    public synchronized void psum(int x){
        int sum =0;
        for(int i=1;i<=10;i++){
            try{
            Thread.sleep(500);
            }catch(Exception e){

            }
            System.out.println(x+" + "+i+" = "+(x+i));
        }
    }
}

class A extends Thread{
    public Table t;
    A(Table t){
        this.t = t;
    }
    public void run(){
        t.ptable(5);
        t.psum(5);
    }
}

class B extends Thread{
    public Table t;
    B(Table t){
        this.t = t;
    }
    public void run(){
        t.ptable(2);
        t.psum(2);
    }
}


class Multithread{
    public static void main(String args[]){
        Table tt = new Table();
        A t1 = new A(tt);
        B t2 = new B(tt);


        // t1.setPriority(1);
        // t2.setPriority(10);
        
        t1.start();
        try{
            t1.join();
        }catch(Exception e){
    
        
        }
        t2.start();
        try {
            t2.join();
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("End of program");


    }
}