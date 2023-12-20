//6a
public static void displayRecursion(int n) {
        //Base case
        if (n == 0) {
            System.out.print(n + " ");
            return;
        }
        //prints left side
        System.out.print(n + " ");
        //calls recursion for next layer
        displayRecursion(n - 1);
        //prints right side
        System.out.print(n + " ");
    }

//6a
public static int oddNum(int[] arr, int pos) {
    //Base case
    if (pos >= arr.length || pos < 0) {
        return 0;
    }
    //isOdd adds 1 to total if arr[pos] is odd
    int isOdd = arr[pos] % 2;
    return isOdd + oddNum(arr, pos + 1);
}

//7
//create new pair object with <String> type parameters
    Pair<String> newPair = new Pair<>();
    //set methods
    newPair.setX("X Value");
    newPair.setY("Y Value");
    //get methods
    String xValue = newPair.getX();
    String yValiue = newPair.getY();
    //print
    System.out.println(xValue);
    System.out.println(yValue);