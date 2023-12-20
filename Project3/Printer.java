//Anthony Tran
//axt220037
//CS 3345.
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Printer {
    public static void main(String[] args) throws IOException{
        BinaryHeap<Printjob> printer = new BinaryHeap<>();
        String filename = "jobs.txt";

        Scanner input = new Scanner(new File(filename));
        processLine(input, printer);
        printQueue(printer);

    }

    //method to read .txt file and input each entry into binary heap
    public static void processLine(Scanner input, BinaryHeap<Printjob> printer) {
        //read in new line
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Scanner lineScanner = new Scanner(line);
            String name = lineScanner.next();
            int priority = lineScanner.nextInt();
            int pages = lineScanner.nextInt();
            String type = lineScanner.next();

            if (type.equals("I")) {
                Printjob newPrint = new Printjob(name, priority, pages);
                printer.insert(newPrint);
            } else {
                OutsidePrintjob newPrint = new OutsidePrintjob(name, priority, pages);
                printer.insert(newPrint);
            }
            
        }
    }

    //method to remove item with smallest priority and print item
    public static void printQueue(BinaryHeap<Printjob> printer) {
        while (!printer.isEmpty()) {
            System.out.println(printer.deleteMin());
        }
    }
}

//Maria prints first