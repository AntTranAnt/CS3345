//Anthony Tran
//axt220037
//CS 3445.001
//Class that uses disjoint sets to generate mazes in terminal.

import java.util.Random;

public class Maze {
    //Cell has {up, right, down, left} walls, 1 denotes a destroyable wall, 0 denotes no walls, -1 denotes border wall.
    private class Cell {
        private int[] walls = new int[4];

        //default constructor makes empty cells
        Cell() {
            walls[UP] = 0;
            walls[RIGHT] = 0;
            walls[DOWN] = 0;
            walls[LEFT] = 0;
        }
        //method to set cell to default layout
        private void setCell(int up, int right, int down, int left) {
            walls[UP] = up;
            walls[RIGHT] = right;
            walls[DOWN] = down;
            walls[LEFT] = left;
        }

        //function to return a random wall number to destroy.
        private int getRandomWall() {
            MyArrayList<Integer> destroyableWalls = new MyArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (walls[i] == WALL) {
                    destroyableWalls.add(i);
                }
            }
            //check if 0 walls, return -1 if empty
            if (destroyableWalls.isEmpty()) {
                return -1;
            }
            Random random = new Random();
            int randomIndex = random.nextInt(destroyableWalls.size());
            return destroyableWalls.get(randomIndex);
        }
    }
    //global variables
    private static int UP = 0;
    private static int RIGHT = 1;
    private static int DOWN = 2;
    private static int LEFT = 3;

    private static int NOWALL = 0;
    private static int WALL = 1;
    private static int BORDER = -1;

    //class variables
    private int rows;
    private int cols;
    private int cells;
    DisjSets maze;
    Cell mazeRep[]; //array to represent cell walls

    //creates a default maze of rows x columns of all cells filled in
    Maze (int rows, int columns) {
        this.rows = rows;
        this.cols = columns;
        this.cells = this.rows * this.cols;
        maze = new DisjSets(cells);
        mazeRep = new Cell[cells];
        //fill up with empty cells
        for (int i = 0; i < cells; i++) {
            mazeRep[i] = new Cell();
        }
        fillCells();
        generateMaze();
    }

    public void testCell() {
        for (int i = 0; i < cells; i++) {
            //System.out.println(mazeRep[i].walls[UP] + " " + mazeRep[i].walls[RIGHT] + " " + mazeRep[i].walls[DOWN] + " " + mazeRep[i].walls[LEFT]);
            System.out.println(mazeRep[i].walls[UP]);
        }
    }

    //method to generate a full maze of cells
    //cols is number of cells in first row
    private void fillCells() {
        int numCell = 0;
        while (numCell < cells) {
            if (numCell == 0) {
                //first cell
                mazeRep[numCell].setCell(NOWALL, WALL, WALL, NOWALL);
            } else if (numCell == cols - 1) {
                //top right cell
                mazeRep[numCell].setCell(BORDER, BORDER, WALL, WALL);
            } else if (numCell == cells - 1) {
                //bot right cell
                mazeRep[numCell].setCell(WALL, NOWALL, NOWALL, WALL);
            } else if (numCell == cells - cols) {
                //bot left cell
                mazeRep[numCell].setCell(WALL, WALL, BORDER, BORDER);
            } else if (numCell < cols) {
                //topmost cell
                mazeRep[numCell].setCell(BORDER, WALL, WALL, WALL);
            } else if ((numCell + 1) % cols== 0) {
                //rightmost cell in row
                mazeRep[numCell].setCell(WALL, BORDER, WALL, WALL);
            } else if (numCell % (cols) == 0) {
                //leftmost cell in row
                mazeRep[numCell].setCell(WALL, WALL, WALL, BORDER);
            } else if (numCell > cells - cols) {
                //botmost row
                mazeRep[numCell].setCell(WALL, WALL, BORDER, WALL);
            } else {
                //middle cells
                mazeRep[numCell].setCell(WALL, WALL, WALL, WALL);
            }
            numCell++;
        }
    }

    //prints out maze layout in terminal
    //each cell segment represented by:
        //# # # full wall #   # no wall 
        //#               
        //#               #
    public void printMaze() {
        int i = 0;
        while (i < rows) { 
            int rowNum = 0;
            int index = (i*cols) + rowNum;
            //each row consists of 3 lines
            while (rowNum < cols) { 
                index = (i*cols) + rowNum;
                //first line
                if (mazeRep[index].walls[UP] == NOWALL) {
                    System.out.print("*     ");
                } else {
                    System.out.print("* * * ");
                }
                rowNum++;
            }
            System.out.println("*");
            rowNum = 0;
            while (rowNum < cols) { 
                index = (i*cols) + rowNum;
                //second line
                if (mazeRep[index].walls[LEFT] == NOWALL) {
                    System.out.print("      ");
                } else {
                    System.out.print("*     ");
                }
                rowNum++;
            }
            System.out.println("*");
            rowNum = 0;
            while (rowNum < cols) { 
                index = (i*cols) + rowNum;
                //third line
                if (mazeRep[index].walls[LEFT] == NOWALL) {
                    System.out.print("      ");
                } else {
                    System.out.print("*     ");
                }
                rowNum++;
            }
            System.out.println("*");
            i++;
        }
        //print bot border
        i = 0;
        while (i < cols - 1) {
            System.out.print("* * * ");
            i++;
        }
        System.out.println("*     *");
    }
    
    //knocks down walls to create a maze where every cell is connected
    private void generateMaze() {
        int wallsDestroyed = 0;
        Random random = new Random();

        while (wallsDestroyed < cells - 1) { //while every cell isnt connected
            int randomCell = random.nextInt(cells);
            
            //select random cell
            Cell tempCell = mazeRep[randomCell];
            //check if cell has destroyable walls
            int randomWall = tempCell.getRandomWall();

            //if has destroyable walls
            if (randomWall == UP) {
                int oppCellIndex = randomCell - cols;
                Cell oppCell = mazeRep[oppCellIndex];
                //check if cells in same set
                if (!sameSet(randomCell, oppCellIndex)) {
                    maze.union(maze.find(randomCell), maze.find(oppCellIndex));
                    tempCell.walls[UP] = 0;
                    oppCell.walls[DOWN] = 0;
                    wallsDestroyed++;
                }
            } else if (randomWall == RIGHT) {
                int oppCellIndex = randomCell + 1;
                Cell oppCell = mazeRep[oppCellIndex];
                //check if cells in same set
                if (!sameSet(randomCell, oppCellIndex)) {
                    maze.union(maze.find(randomCell), maze.find(oppCellIndex));
                    tempCell.walls[RIGHT] = 0;
                    oppCell.walls[LEFT] = 0;
                    wallsDestroyed++;
                }

            } else if (randomWall == DOWN) {
                int oppCellIndex = randomCell + cols;
                Cell oppCell = mazeRep[oppCellIndex];
                //check if cells in same set
                if (!sameSet(randomCell, oppCellIndex)) {
                    maze.union(maze.find(randomCell), maze.find(oppCellIndex));
                    tempCell.walls[DOWN] = 0;
                    oppCell.walls[UP] = 0;
                    wallsDestroyed++;
                }

            } else if (randomWall == LEFT) {
                int oppCellIndex = randomCell - 1;
                Cell oppCell = mazeRep[oppCellIndex];
                //check if cells in same set
                if (!sameSet(randomCell, oppCellIndex)) {
                    maze.union(maze.find(randomCell), maze.find(oppCellIndex));
                    tempCell.walls[LEFT] = 0;
                    oppCell.walls[RIGHT] = 0;
                    wallsDestroyed++;
                }
            }
        }
    }

    //check if two cells are in same set
    private boolean sameSet(int x, int y) {
        return maze.find(x) == maze.find(y);
    }
}
