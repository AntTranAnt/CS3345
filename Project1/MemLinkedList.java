// Anthony Tran
// axt220037
// 3345-001

// MemLinkedList class uses Nodes to mimic memory management from C. Nodes hold 'A' or 'F' depending on size
// of memory, as well as how much memory is held.
// There is no sentinel nodes
public class MemLinkedList {
    private int totalMemory;
    private Node head;
    private Node tail;

    //Constructor to make double linked list with starting free memory block of 1000K
    MemLinkedList() {
        totalMemory = 1000;
        Node firstNode = new Node(totalMemory, 'F');
        head = firstNode;
        tail = firstNode;
    }

    //Node class contains flag, memory, and next/prev pointers
    private class Node {
        private char flag;
        private int memory;
        private Node next;
        private Node prev;

        //Node constructor to create new node of requested size and type.
        //if no type requested, assume allocated node
        Node(int size) {
            this(size, 'A');
        }
        Node(int size, char type) {
            this.memory = size;
            flag = type;
            this.next = null;
            this.prev = null;
        }
        //Node getter and setter methods
        private void setSize(int size) {
            this.memory = size;
        }
        private int getSize() {
            return this.memory;
        }
        private void setFlag(char newFlag) {
            this.flag = newFlag;
        }
        private char getFlag() {
            return this.flag;
        }
    }

    //request asks for a new memory size to allocate, and allocate it at the next available index
    //return 1 if successfully allocated, -1 if not
    public int request(int size) {
        Node curPointer = head;

        //if add as first node
        if (size <= curPointer.getSize() && curPointer.getFlag() == 'F') {
            addNodeStart(size);
            return 1;
        }

        //if add as middle or last node
        while (curPointer.next != null) {
            curPointer = curPointer.next;
            if (size <= curPointer.getSize() && curPointer.getFlag() == 'F') {
                addNode(size, curPointer);
                return 1;
            }
        }

        System.out.println("Not enough memory to allocated: " + size + "K");
        return -1;
    }

    //adds new node at the start of linked list
    //removes memory from previous first node
    private void addNodeStart(int size) {
        Node newNode = new Node(size);
        
        //case for when size is equal to total free memory of first node
        if (size == head.getSize()) {
            head.setFlag('A');
        }
        else if (head == tail) {
            //case for when there is no added node
            head = newNode;
            newNode.next = tail;
            tail.prev = newNode;
            tail.memory -= newNode.memory;
        } else {
            //case for if there is already added nodes
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            newNode.next.memory -= newNode.memory;
        }
    }

    //adds new node inbetween nextIndex and nextIndex.prev node
    //removes memory from index node that new node is connected to.
    private void addNode(int size, Node nextIndex) {
        //if size equals total memory of nextIndex
        if (size == nextIndex.getSize()) {
            nextIndex.setFlag('A');
        } else {
            //if we are allocating part of nextIndex's memory to newNode
            Node newNode = new Node(size);
            newNode.prev = nextIndex.prev;
            newNode.next = nextIndex;
            nextIndex.prev.next = newNode;
            nextIndex.prev = newNode;
            nextIndex.memory -= newNode.memory;
        }
    }

    //traverse from head -> tail, release requested memory if size==node size
    //return 1 if successfully released, -1 if not
    public int release(int size) {
        Node curPointer = head;
        //if first node is released
        if (curPointer.getFlag() == 'A' && curPointer.getSize() == size) {
            releaseNode(curPointer);
            return 1;
        }

        //else loop through middle nodes
        while (curPointer.next != null) {
            curPointer = curPointer.next;
            if (curPointer.getFlag() == 'A' && curPointer.getSize() == size) {
            releaseNode(curPointer);
            return 1;
            }
        }
        System.out.println("No memory to release: " + size +"K");
        return -1;
    }

    //sets flag of curpointer to free.
    //if adjacent blocks of memory are also free, merges the two blocks together
    private void releaseNode(Node curPointer) {
        curPointer.setFlag('F');

        //if releasing the tail
        if (curPointer.next == null) {
            //check if prev node is 'F'
            if (curPointer.prev.getFlag() == 'F') {
                mergeNode(curPointer.prev, curPointer);
            }
        } else if (curPointer.prev == null) {
            //if releasing head node

            //check if prev node is 'F'
            if (curPointer.next.getFlag() == 'F') {
                mergeNode(curPointer, curPointer.next);
            }
        } else if (head != tail) {
            //if releasing the middle
            if (curPointer.prev.getFlag() == 'F') {
                mergeNode(curPointer.prev, curPointer);
            }
            if (curPointer.next.getFlag() == 'F') {
                mergeNode(curPointer, curPointer.next);
            }
        }
        
    }

    //merges the two given nodes
    private void mergeNode(Node left, Node right) {
        right.memory += left.memory;
        if (left.prev == null) {
            //if left is the head node
            right.prev = null;
            head = right;
        } else {
            right.prev = left.prev;
            left.prev.next = right;
        }
    }

    //clear linked list, return to 1 free node of size totalMemory
    public void clear() {
        head = tail;
        head.next = null;
        tail.prev = null;
        tail.setSize(totalMemory);
        tail.setFlag('F');
    }

    //prints representation of the linked list
    public void print() {
        StringBuilder str = new StringBuilder("head <-> ");
        Node curPointer = head;
        str.append("[ " + curPointer.getFlag() + " | " + curPointer.getSize()+ "K" + " ] ");
        while (curPointer.next != null) {
            curPointer = curPointer.next;
            str.append("<-> [ " + curPointer.getFlag() + " | " + curPointer.getSize()+ "K" + " ] ");
        }
        str.append("<-> tail");
        System.out.println(str.toString());
    }

    //return total size 
    public int getSize() {
        return totalMemory;
    }

    //main method to test functions
    public static void main(String[] args) {
        MemLinkedList testList = new MemLinkedList();

        //Beginning linked list with 1000k memory
        System.out.println("Beginning linked list: ");
        testList.print();

        //A) Requesting memory
        System.out.println();
        System.out.println("Requesting memory of 100, 400, 200, 300, 100: ");
        testList.request(100);
        testList.print();
        testList.request(400);
        testList.print();
        testList.request(200);
        testList.print();
        testList.request(300);
        testList.print();
        testList.request(100);
        testList.print();

        //B) Releasing memory
        System.out.println();
        System.out.println("Releasing memory of 400, 100, 200: ");
        testList.release(400);
        testList.print();
        testList.release(100);
        testList.print();
        testList.release(200);
        testList.print();

        //C) Clearing memory
        System.out.println();
        System.out.println("Clearing memory: ");
        testList.clear();
        testList.print();
    }

}