import java.io.*;
import java.util.Scanner;

/**
 *  URL
 *  This class is for use all the classes
 *  <h2>Library  simulation  class</h2>
 *  @author Sara Tajernia
 *  @version 1.00
 *  @since 1398-4-21
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String str = new String();
        for (String s: args){
            str += s +" ";
        }
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();

//        System.out.println("CODE: " +str);

//        Url url1 = new Url();
        try{
            Url url = new Url();
//            System.out.println("hiii dar");
            url.Request(str);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
