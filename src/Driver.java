import java.util.Scanner;

/**
 * Created by Sophia on 11/15/2016.
 */
public class Driver {
    public static void main(String[] args) {
        int size;
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("What's the size of your maze? ");
            size = scan.nextInt();
            MazeGenerator maze = new MazeGenerator(size);
            maze.makeMaze();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        scan.close();




    }
}
