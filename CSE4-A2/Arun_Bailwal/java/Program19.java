
class CSThread extends Thread{
    int x;
    public CSThread(int x) {
        this.x = x;
    }
    
    public void run(){
        while(true){
            try{
                Thread.sleep(500);
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println("this is thread Cs i --- "+(x++));
        }
    }
    
}

class ITThread extends Thread{
    int y;
    public ITThread(int i) {
        this.y = i;
    }

    public void run(){

        while(true){
            try{
                Thread.sleep(500);
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println("this is thread IT i --- "+(y++));
        }
    }

}


class Program19{
    public static void main(String args[]){
        
        CSThread p1 = new CSThread(0);
        ITThread p2  = new ITThread(0);
        p1.setPriority(1);
        p1.start();
        p2.start();


    }
}