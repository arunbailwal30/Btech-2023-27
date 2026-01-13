import java.util.Scanner;

class Xyz extends RuntimeException{
	public Xyz(String s){
		super(s);
	}
}

class Program16{
	int id,dept_id;
	String name;
	void set(String name, int dept_id, int id){
		try{
			if(name.charAt(0 ) >= 97 && name.charAt(0) <=122) {
				Xyz e = new Xyz("invalid name\n");
				throw e;
			}
			this.name = name;
		}catch(Xyz e){
			System.out.print(e);
		}
		try{
			if(id <2001 || id > 5001){
				throw new Xyz("invalid id\n");
			}else{
				this.id = id;
			}
		}catch(Xyz e){
			System.out.print(e);
		}
		try{
			if(dept_id <1 || dept_id >5){
				throw new Xyz("invalid department id\n");
			}
			this.dept_id = dept_id;
		}catch(Xyz e){
			System.out.print(e);
		}
	}
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name: ");
		String name = sc.nextLine();
		System.out.print("Enter employee id: ");
		int id = sc.nextInt();
		System.out.print("Enter department id: ");
		int dep_id = sc.nextInt();
		Program16 ob = new Program16();
		ob.set(name, dep_id, id);
		System.out.println("End of program");
		
		
	}
}