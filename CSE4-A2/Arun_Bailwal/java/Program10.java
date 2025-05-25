
import java.util.*;
public class Program10 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;
        System.out.print("Enter a string: ");
        str = sc.nextLine();
        str = str.trim();
        if (!str.endsWith(".") && !str.endsWith("?") && !str.endsWith("!")) {
            System.out.println("Invalid string");
        } else {
            WordExample ex = new WordExample(str);
            ex.countWords();
            ex.placeWord();
        }
        
        sc.close();
    }
}

class WordExample {
    private String strdata;
    public WordExample(String s) {
        this.strdata = s;
    }
    public boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public void countWords() {
        int count = 0;
        String str[] = strdata.split("\\s+");
        int i=0;
        while (i<str.length) {
            if (isVowel(str[i].charAt(0)) && isVowel(str[i].charAt(str[i].length() - 1))) {
                count++;
            }
            i++;
        }
        System.out.println("Number of words beginning and ending with a vowel: " + count);
    }

    public void placeWord() {
        String[] str = strdata.split("\\s+");
        int i=0;
        String vowels = "", consonants = "";
        while(i<str.length){
            String s="";
            if (isVowel(str[i].charAt(0)) && isVowel(str[i].charAt(str[i].length() - 1))) {
                if(!"".equals(vowels))
                 s = String.join(" ",vowels,str[i]);
                else    
                    s = String.join(" ",vowels, str[i]);
                 vowels = s;
            }else{
                if(!"".equals(consonants))
                 s = String.join(" ",consonants,str[i]);
                else 
                    s = String.join(" ",consonants, str[i]);
                 consonants = s;
            }
            i++;
        }
        System.out.println(vowels+" "+consonants);

    }
}