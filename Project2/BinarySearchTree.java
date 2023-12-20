// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * 
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * 
     * @param x the item to remove.
     */
    public void remove(AnyType x) {
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     * 
     * @return smallest item or null if empty.
     */
    public AnyType findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return findMin(root).element;
    }

    /**
     * Find the largest item in the tree.
     * 
     * @return the largest item of null if empty.
     */
    public AnyType findMax() {
        if (isEmpty())
            throw new UnderflowException();
        return findMax(root).element;
    }

    /**
     * Find an item in the tree.
     * 
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * 
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    // a. recursively counts the number of nodes in the tree
    public int nodeCount() {
        // base case for empty tree
        if (root == null) {
            return 0;
        }

        return nodeCount(root);
    }

    // Internal method to count number of nodes in tree
    private int nodeCount(BinaryNode<AnyType> root) {
        if (root == null) {
            return 0;
        }

        return nodeCount(root.right) + nodeCount(root.left) + 1;
    }

    //b. returns true if tree is full
    public boolean isFull() {
        return isFull(root);
    }

    //internal method to recursively check if each node has 0 or 2 children.
    private boolean isFull(BinaryNode<AnyType> root) {
        if (root == null) {
            return true;
        }
        //if node only has 1 child return false
        if ((root.left == null && root.right != null) || (root.right == null && root.left != null)) {
            return false;
        }
        return isFull(root.left) && isFull(root.right);
    }

    // c. returns true if structure of tree matches structure of passed tree
    public boolean compareStructure(BinarySearchTree<AnyType> newTree) {
        return compareStructure(newTree.root, this.root);
    }

    //internal method to check if the structure of 2 trees are similar
    private boolean compareStructure(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 != null && node2 != null) {
            return compareStructure(node1.left,node2.left) && compareStructure(node1.right , node2.right);
        }
        //return false if both nodes arent null or occupied
        return false;
    }

    // d. returns true if tree equals passed tree
    public boolean equals(BinarySearchTree<AnyType> newTree) {
        return equals(newTree.root, this.root);
    }

    //internal method to check if both trees equal
    private boolean equals(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if ((node1 != null && node2 != null) && (node1.element == node2.element)) {
            return compareStructure(node1.left,node2.left) && compareStructure(node1.right , node2.right);
        }
        //if both nodes arent similar, return false.
        return false;
    }

    //e. method copies tree and returns a new copy of the tree
    public BinarySearchTree<AnyType> copy() {
        if (isEmpty()) {
            throw new UnderflowException();
        }
        BinarySearchTree<AnyType> temp = new BinarySearchTree<AnyType>();
        temp.insert(this.root.element);
        copy(this.root, temp.root);
        return temp;
    }

    //internal method to recursively copy contents from node1 to node 2
    private void copy(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
        if (node1.left != null) {
            BinaryNode<AnyType> temp = new BinaryNode<>(node1.left.element);
            node2.left = temp;
            copy(node1.left, node2.left);
        }
        if (node1.right != null) {
            BinaryNode<AnyType> temp = new BinaryNode<>(node1.right.element);
            node2.right = temp;
            copy(node1.right, node2.right);
        }
    }

    // f. mirror method to return a mirrored tree of the original tree.
    public BinarySearchTree<AnyType> mirror() {
        if (isEmpty()) {
            throw new UnderflowException();
        }
        // create new bst to return
        BinarySearchTree<AnyType> temp = new BinarySearchTree<AnyType>();
        temp.insert(this.root.element);
        // pass in roots of new and original tree to get mirrored.
        mirror(temp.root, this.root);
        return temp;
    }

    // internal method to recursively mirror each node
    private void mirror(BinaryNode<AnyType> tempRoot, BinaryNode<AnyType> thisRoot) {
        // clone left side to right
        if (thisRoot.left != null) {
            BinaryNode<AnyType> tempLeft = new BinaryNode<>(thisRoot.left.element);
            tempRoot.right = tempLeft;
            mirror(tempRoot.right, thisRoot.left);
        } else {
            tempRoot.right = null;
        }
        // clone right side to left
        if (thisRoot.right != null) {
            BinaryNode<AnyType> tempLeft = new BinaryNode<>(thisRoot.right.element);
            tempRoot.left = tempLeft;
            mirror(tempRoot.left, thisRoot.right);
        } else {
            tempRoot.left = null;
        }
    }

    // g. method returns true if current tree is mirror to passed tree
    public boolean isMirror(BinarySearchTree<AnyType> newTree) {
       return isMirror(newTree.root, this.root);
    }

    //internal method to check if the children of 2 nodes are mirrors of each other.
    private boolean isMirror(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if ((node1 != null && node2 != null) && (node1.element == node2.element)) {
            return isMirror(node1.left,node2.right) && isMirror(node1.right , node2.left);
        }
        //if both nodes arent similar, return false.
        return false;
    }

    //h. method rotates given node to the right
    public void rotateRight(AnyType target) {
        if (target == root.element) { //if we are rotating root
            if (root.left != null) {
                this.root = rotateRight(root);
            }
        } else {
            BinaryNode<AnyType> parent = nodeBefore(root, target);
            BinaryNode<AnyType> newNode = null;
            if (parent.element.compareTo(target) > 0) {
                newNode = rotateRight(parent.left);
                parent.right = newNode;
            } else {
                newNode = rotateRight(parent.right);
                parent.left = newNode;
            }
        }

    }

    //helper method to rotate node to the right
    private BinaryNode<AnyType> rotateRight(BinaryNode<AnyType> cur) {
        if (root == null || root.left == null) // this means can't rotate
            return root;
        BinaryNode<AnyType> temp = cur.left;
        cur.left = temp.right;
        temp.right = cur;
        return temp;
    }

    // Internal method to find node before given node
    //assumes target node is inside tree
    private BinaryNode<AnyType> nodeBefore(BinaryNode<AnyType> root, AnyType target) {
        BinaryNode<AnyType> parent = null;
        BinaryNode<AnyType> cur = root;
        int compareResult = cur.element.compareTo(target);

        //use parent node to tail cur node as cur node traverse bst to find target
        while (cur != root && compareResult != 0) {
            parent = cur;
            compareResult = cur.element.compareTo(target);
            if (compareResult > 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        return parent;
    }

    //i. method rotates node to the left
    public void rotateLeft(AnyType target) {
        if (target == root.element) { //if we are rotating root
            if (root.right != null) {
                this.root = rotateLeft(root);
            }
        } else {
            BinaryNode<AnyType> parent = nodeBefore(root, target);
            BinaryNode<AnyType> newNode = null;
            if (parent.element.compareTo(target) > 0) {
                newNode = rotateLeft(parent.left);
                parent.right = newNode;
            } else {
                newNode = rotateLeft(parent.right);
                parent.left = newNode;
            }
        }

    }

    //internal method to rotate node to the left
    private BinaryNode<AnyType> rotateLeft(BinaryNode<AnyType> cur) {
        if (root == null || root.right == null) // this means can't rotate
            return root;
        BinaryNode<AnyType> temp = cur.right;
        cur.right = temp.left;
        temp.left = cur;
        return temp;
    }

    // j. printLevel prints each level on a new line.
    // empty nodes are represented by _
    public void printLevels() {
        if (this.root == null) {
            System.out.println("Empty Tree");
        } else {
            BinaryNode<AnyType> cur = this.root; //pointer to root node
            int levels = height(cur) + 1;
            for (int i = 0; i < levels; i++) {
                System.out.print("Level " + i + ": ");
                printLevels(root, i);
                System.out.println();
            }
        }
    }

    //internal method to print inputed level height
    private void printLevels(BinaryNode<AnyType> cur, int lv) {
        //if null break out of recursion
        if (cur == null) {
            System.out.print("_ ");
            return;
        }
        if (lv == 0) {
            System.out.print(cur.element + " ");
        } else {
            printLevels(cur.left, lv - 1);
            printLevels(cur.right, lv - 1);
        }
    }

    /**
     * Internal method to insert into a subtree.
     * 
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return new BinaryNode<>(x, null, null);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        else
            ; // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * 
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return t; // Item not found; do nothing

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * 
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.left == null)
            return t;
        return findMin(t.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * 
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * 
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else
            return true; // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * 
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * 
     * @param t the node that roots the subtree.
     */
    private int height(BinaryNode<AnyType> t) {
        if (t == null)
            return -1;
        else
            return 1 + Math.max(height(t.left), height(t.right));
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType> {
        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        AnyType element; // The data in the node
        BinaryNode<AnyType> left; // Left child
        BinaryNode<AnyType> right; // Right child
    }

    /** The tree root. */
    private BinaryNode<AnyType> root;

    // Test program
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        //insert elements into tree
        int arr[] = { 20, 30, 12, 56, 23, 19, 58, 26, 9, 10, 22};
        for (int i = 0; i < arr.length; i++) {
            t.insert(arr[i]);
        }
        System.out.println("Example Tree");
        // j. prints each level of the bst
        t.printLevels();
        System.out.println();

        // a. display nodeCount method on tree with 9 nodes
        System.out.println("Number of Nodes: " + t.nodeCount());
        System.out.println();

        //b. Check isFull()
        System.out.println("Is example tree full? " + t.isFull()); //example tree is false
        //creating a full tree
        BinarySearchTree<Integer> fullTree = new BinarySearchTree<>();
        fullTree.insert(1);
        fullTree.insert(0);
        fullTree.insert(2);
        System.out.println("Is new tree full? " + fullTree.isFull()); //new tree is true
        System.out.println();
        
        //c. compareStructure
        System.out.println("Compare structure of example and full tree: " + t.compareStructure(fullTree));
        //create a clone of full tree to test compareStructure()
        BinarySearchTree<Integer> fullTreeClone = new BinarySearchTree<>();
        fullTreeClone.insert(1);
        fullTreeClone.insert(0);
        fullTreeClone.insert(2);
        System.out.println("Compare structure of full tree and full tree clone: " + fullTree.compareStructure(fullTreeClone));
        System.out.println();

        //d. equals
        System.out.println("Is example tree and full tree equal: " + t.equals(fullTree));
        System.out.println("Is full tree and full tree clone equal: " + fullTree.equals(fullTreeClone));
        System.out.println();

        //e. copy
        //create a copy of example tree
        BinarySearchTree<Integer> exampleTreeCopy = t.copy();
        System.out.println("Copy of Example Tree: ");
        exampleTreeCopy.printLevels();
        System.out.println();

        //f. mirror
        //create a mirror of example tree
        BinarySearchTree<Integer> exampleTreeMirror = t.mirror();
        System.out.println("Mirror of Example Tree: ");
        exampleTreeMirror.printLevels();
        System.out.println();

        //g. isMirror
        System.out.println("Does exampleTreeMirror and exampleTree mirror each other: " + t.isMirror(exampleTreeMirror));
        System.out.println("Does exampleTree and fullTree mirror each other: " + t.isMirror(fullTree));
        System.out.println();

        //h. rotateRight
        t.rotateRight(20);
        System.out.println("Example Tree Rotate 20 right: ");
        t.printLevels();
        System.out.println();

        //i. rotateLeft
        t.rotateLeft(12);
        System.out.println("Example Tree Rotate 12 left:");
        t.printLevels();
        System.out.println();
    }
}
