package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Hoover {

    public static void main(String[] args) {
        boolean[] dirty = new boolean[2];
        dirty[0] = true;
        dirty[1] = true;
        boolean[] q = new boolean[2];
        bfs(new State(0, dirty), q);
    }


    public static void bfs(State root, boolean[] query){
        Queue<State> queue = new LinkedList<State>();
        ArrayList<State> explored = new ArrayList<State>();
        queue.add(root);
        State state;
        while (!queue.isEmpty()){
            state = queue.poll();
            if (explored.contains(state)){
                continue;
            }
            explored.add(state);
            if (Arrays.equals(state.getDirty(), query)){
                for (State s : explored){
                    System.out.println(s);
                }
                return;
            }


            queue.add(state.move_left());
            queue.add(state.move_right());
            queue.add(state.hover());
        }
        System.out.println("Nie znaleziono!");
    }
}
class State{
    State(int pos, boolean[] dirty){
        this.pos = pos;
        this.dirty = dirty;
    }



    public int pos;
    public boolean[] dirty;
    public State left;
    public State right;
    public State vaccum;

    public State move_left() {
        if (this.pos == 0)
            return this;
        if (left == null)
            this.setLeft(new State(this.getPos() - 1, this.getDirty().clone()));
        return  left;
    }

    public State move_right() {
        if (this.pos == this.getDirty().length - 1)
            return this;
        if(right == null)
            this.setRight(new State(this.getPos() + 1, this.getDirty().clone()));
        return right;
    }

    public  State hover() {
        boolean[] dirty = this.getDirty().clone();
        int pos = this.getPos();
        if (!dirty[pos])
            return this;

        dirty[pos] = false;
        if(this.vaccum == null)
            this.setVaccum(new State(this.getPos(), dirty));
        return vaccum;
    }

    @Override
    public String toString() {
        return "State{" +
                "pos=" + pos +
                ", dirty=" + Arrays.toString(dirty) +
                '}';
    }

    public void setLeft(State left) {
        this.left = left;
    }

    public void setDirty(boolean[] dirty) {
        this.dirty = dirty;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setRight(State right) {
        this.right = right;
    }

    public void setVaccum(State vaccum) {
        this.vaccum = vaccum;
    }
    public int getPos() {
        return pos;
    }
    public boolean[] getDirty() {
        return dirty;
    }
}

