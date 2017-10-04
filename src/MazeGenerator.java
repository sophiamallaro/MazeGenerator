import java.util.*;

import static javax.swing.text.StyleConstants.Size;

/**
 * Created by Sophia on 11/15/2016.
 */
public class MazeGenerator {
    public Cell[][] graphCells;
    public ArrayList<TreeNode> cellSet;
    public ArrayList<Edge> edges;
    public int size;
    public int numCells;
    public Random rand;

    MazeGenerator(int size) {
        rand = new Random();
        this.size = size;
        this.numCells = size*size;
        graphCells = new Cell[size][size];
        //Make full grid
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
               graphCells[i][j] = new Cell(false, false);
            }
        }

        cellSet = new ArrayList<TreeNode>();
        //Make set of cells

        for(int i=0; i<numCells; i++) { //Add all cells to the set
            TreeNode toAdd= new TreeNode(i,null);
            cellSet.add(toAdd);
        }

        edges = new ArrayList<Edge>();
        //Make set of Edges

        for(int i=0; i<numCells; i++) { //Add right edges
            if(i % size != (size-1)) {
                Edge edge = new Edge(i, i+1);
                edges.add(edge);
            }
        }
        for(int i=0; i<numCells; i++) { //Add right edges
            if(i < numCells - size) {
                Edge edge = new Edge(i, i+size);
                edges.add(edge);
            }
        }

    }

    public void unionCells(TreeNode one, TreeNode two) {
        int index1=0;
        int index2=0;
        for(int i=0; i<cellSet.size(); i++) {
            if(cellSet.get(i). data == one.data) {
                index1 = i;
            }
            else if(cellSet.get(i). data == two.data) {
                index2 = i;
            }
        }
        if(one.height > two.height) {
            one.addChild(two);
            cellSet.remove(index2);
        }
        else {
            two.addChild(one);
            cellSet.remove(index1);
        }
    }

    public void unionCellsByIndex(int index1, int index2) {
        TreeNode one = cellSet.get(index1);
        TreeNode two = cellSet.get(index2);
        if(one.height > two.height) {
            one.addChild(two);
            cellSet.remove(index2);
        }
        else {
            two.addChild(one);
            cellSet.remove(index1);
        }
    }

    public TreeNode find(int toFind) {
        for(TreeNode tree : cellSet) {
            if(tree.data == toFind) {
                return tree;
            }
            else if(tree.isParentof(toFind)) {
                return tree;
            }
        }
        return null;
    }

    public void getModifiedEdgeSet() {
        //ArrayList<Edge> modified = new ArrayList<Edge>();
        int next;
        int x;
        int y;
        while(cellSet.size() > 1) {
            next = rand.nextInt(edges.size()-1);
            Edge randomEdge = edges.get(next);
            x = randomEdge.sideOne;
            y = randomEdge.sideTwo;
            TreeNode u = find(x);
            TreeNode v = find(y);
            if(u!=null && v != null) {
                if(u.data != v.data) {
                    unionCells(u,v);
                    edges.remove(randomEdge);
                }
            }
        }
    }

    public void makeMaze() {
        getModifiedEdgeSet();
       for(Edge edge : edges) {
            if(edge.sideOne == edge.sideTwo -1) {
                graphCells[edge.sideOne/size][edge.sideOne % size].right = true;
            }
            if(edge.sideOne == edge.sideTwo - size) {
                graphCells[edge.sideOne/size][edge.sideOne % size].below = true;
            }
        }

       //Add Boundry Conditions
        for(int i=0; i<size; i++) {
            graphCells[i][size-1].right=true;
            graphCells[size-1][i].below=true;

        }

        graphCells[size-1][size-1].right = false; //add finish


        for(int i=0; i<size; i++) { //Build top of maze
            System.out.printf(" _");
        }
        System.out.printf("\n");

        for(int i=0; i<size; i++) {
            if(i == 0) {
                System.out.printf(" ");
            }
            else {
                System.out.printf("|");
            }
            for(int j=0; j<size; j++) {
                if(graphCells[i][j].below == true) {
                    System.out.printf("_");
                }
                else {
                    System.out.printf(" ");
                }
                if(graphCells[i][j].right == true) {
                    System.out.printf("|");
                }
                else{
                    System.out.printf(" ");
                }
            }
            System.out.printf("\n");
        }

    }

    public void printSet() { //For Debugging purposes
       TreeNode tree = find(1);
        System.out.println(tree.data);
    }


    public static class Edge {
        public int sideOne;
        public int sideTwo;
        Edge(int sideOne, int sideTwo) {
            this.sideOne = sideOne;
            this.sideTwo = sideTwo;
        }
    }

    public static class Cell {
        public boolean right;
        public boolean below;

        Cell(boolean right, boolean below) {
            this.right = right;
            this.below = below;
        }
    }


}
