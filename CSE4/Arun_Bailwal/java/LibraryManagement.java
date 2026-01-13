import java.util.Scanner;

class Book{
    public String author,title;
    public int isbn;
    public Book(){};
    public Book(String author,String title,int isbn)
    {
        this.author=author;
        this.title=title;
        this.isbn=isbn;
    }
    public void displayBookInfo()
    {
        System.out.println("Title of book : "+this.title);
        System.out.println("Author of this book "+this.author);
        System.out.println("ISBN of this book "+this.isbn);
    }
}

interface LibraryOperations {
     public void addBook(Book b);
     public void listBooks();
     public boolean searchBooks(String title,String author);    
}

class Library implements LibraryOperations
{
    public String name,address;
    public Book[]arr;
    int i;
    public Library(){
        this.i = 0;
    }
    public Library(String name,String add)
    {
        super();
        this.name=name;
        this.address=add;
        arr=new Book[100];
        for(int j=0;j<100;j++){
            arr[j] = null;
        }
    }
    
    @Override
    public void addBook(Book b)
    {
        this.arr[i]=b;
        i++;
    }
    @Override
    public void listBooks()
    {
        System.out.println("ALL BOOKS AVAILABLE IN THE LIBRARY : ");
        for(int j=0;j<i;j++)
        {
            this.arr[j].displayBookInfo();
        }
    }

    @Override
    public boolean searchBooks(String title,String author)
    {
        for(int j=0;j<i;j++)
        {
            if(this.arr[j].author.equals(author) && this.arr[j].title.equals(title))
            {
                System.out.println("Bool is available in library . Details : ");
                arr[j].displayBookInfo();
                return true;
            }
        }
        return false;
    }
}

class myLibrary extends Library{

    public myLibrary(String name,String add)
    {
        super(name,add);   
    }
    
}

public class LibraryManagement {
    public static void main(String []args)
    {
        Scanner sc=new Scanner(System.in);
        int choice;
        myLibrary l = new myLibrary("My local Library","Main Street");
        System.out.println("Press 1. Add a Book 2. Search a book 3. List books 4. Exit");
        do
        {
            choice=sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    Book b =new Book();
                    System.out.println("Enter the title of the book : ");
                    b.author=sc.nextLine();
                    System.out.println("Enter the author of the book : ");
                    b.title=sc.nextLine();
                    System.out.println("Enter the ISBN of the book : ");
                    b.isbn=sc.nextInt();
                    l.addBook(b);
                    break;
                case 2: 
                    String title,author;
                    System.out.println("Enter the title of the book : ");
                    title=sc.nextLine();
                    System.out.println("Enter the author of the book : ");
                    author=sc.nextLine();
                    if(!l.searchBooks(title,author))
                    {
                        System.out.println("This book is not available in library ");
                    }
                    break;
                case 3: 
                    l.listBooks();
                    break;    

                default:
                    break;
            }

        }while(choice!=4);
    }
}