package com.company;

import java.util.*;

public class Puzzle{
    public static void main(String[] args) {
        PuzzleSolver  solver = new PuzzleSolver(new int[][]{{0,1,3},{4,2,5},{7,8,6}}, new int[][]{{1,2,3}, {4,5,6}, {7,8,0}});
        Deque<Node> path = solver.aStarSearch();
        System.out.println(path);
    }
}

class PuzzleSolver{
    public PuzzleSolver(int[][] start, int[][] goal){
       this.start = start;
       this.goal = goal;
    }
    private int[][] goal;
    private int[][] start;
    static int[][] moves = {{0,-1}, {1,0}, {0,1}, {-1,0}};
    public Deque<Node> aStarSearch(){
        int[] position;
        Node node = new Node(start);
        List<Node> openList= new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        openList.add(node);
        node.setgScore(0);
        if (Arrays.deepEquals(node.state, goal)) {
            return new LinkedList<>();
        }
        while (!openList.isEmpty()){
            node = min(openList);
            openList.remove(node);
            closedList.add(node);
            position = Node.cordinates(node.state, 0);
            if (Arrays.deepEquals(node.state, goal)) {
                return reconstuctedPath(node);
            }
            for( int[]mv : moves){
                int[]m = new int[]{mv[0] + position[0], mv[1] + position[1]};
                if (m[0] < 0 || m[0] > 2 || m[1] < 0 || m[1] > 2)
                    continue;

                Node new_node = move(node, m, position);
                if (closedList.contains(new_node)){
                    continue;
                }
                openList.add(new_node);
            }

        }


        return null;
    }
    private Deque<Node>reconstuctedPath(Node node){
        Deque<Node> path = new LinkedList<>();
        while (node != null){
            path.addFirst(node);
            node = node.parent;
        }
        return path;
    }
    private Node move(Node node, int[] from, int[] to){
        int[][] newState =  Arrays.stream(node.state).map(int[]::clone).toArray(int[][]::new);

        newState[to[0]][to[1]] = node.state[from[0]][from[1]];
        newState[from[0]][from[1]] = 0;

        return new Node(newState, node);
    }
    private Node min(List<Node> open){
        Node minimalNode = null;
        int min = Integer.MAX_VALUE;
        int score;
        for (Node n : open){
            score = n.gScore + n.manhattanHeuristic(goal);
            if (score < min) {
                min = score;
                minimalNode = n;
            }

        }
        return minimalNode;
    }
}
class Node {
    protected Node parent;
    protected int[][] state;
    protected int gScore;
    public Node(int[][] state){
        this.state =state;
        this.parent = null;
        this.gScore = 0;
    }
    public Node(int[][] state, Node parent){
        this.state = state;
        this.parent = parent;
        this.gScore = parent.gScore + 1;
    }
    public int manhattanHeuristic(int[][] goal){
        int sum = 0;
        for(int i = 0; i < 9; i++){
            int[] cordState = Node.cordinates(this.state, i);
            int[] cordGoal = Node.cordinates(goal, i);
            sum += Math.abs(cordState[0] - cordGoal[0])  + Math.abs(cordState[1] - cordGoal[1]);
        }
        return sum;
    }
    public static int[] cordinates(int[][] state, int value){
        for(int i = 0; i<3; i++)
            for(int j = 0; j<3; j++)
                if (state[i][j] == value)
                    return new int[]{i,j};
        return new int[]{-1, -1};
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    @Override
    public String toString() {
        String arr = "";
        for(int[] s : state){
            arr += Arrays.toString(s);
        }
        return "Node{" +
                ", state={" + arr +
                '}';
    }
    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if(!(o instanceof Node)){
            return false;
        }
        Node n = (Node) o;
        return Arrays.deepEquals(n.state, state);
    }

}
