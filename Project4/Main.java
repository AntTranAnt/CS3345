//Anthony Tran
//axt220037
//CS 3445.001
//Program to ask user for row and column input, then generate maze through maze.java object

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int dimension[] = getDimension(input);
        input.close();

        Maze maze = new Maze(dimension[0], dimension[1]);
        maze.printMaze();
    }

    //ask user for dimension size
    //assumes user enters dimensions > 0
    public static int [] getDimension(Scanner input) {
        int result[] = new int[2];
        System.out.print("Enter Row Size: ");
        result[0] =  input.nextInt(); //row
        System.out.print("Enter Column Size: ");
        result[1] = input.nextInt(); //column
        return result;
    }
}
