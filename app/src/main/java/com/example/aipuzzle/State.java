package com.example.aipuzzle;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class State {
    List<Integer> currState;
    int blankPos;
    int level;
    State parentNode;
    int deep;

    public State(List<Integer> currState, int blankPos, int level) {
        this.currState = currState;
        this.blankPos = blankPos;
        this.level = level;
    }

    public State(List<Integer> currState, int blankPos, int level, int deep) {
        this.currState = currState;
        this.blankPos = blankPos;
        this.level = level;
        this.deep = deep;
    }

    public State(List<Integer> currState, int blankPos, int level, State parentNode, int deep) {
        this.currState = currState;
        this.blankPos = blankPos;
        this.level = level;
        this.parentNode = parentNode;
        this.deep = deep;
    }

    public int getDeep() {
        return deep;
    }

    public List<Integer> getCurrState() {
        return currState;
    }

    public int getBlankPos() {
        return blankPos;
    }

    public int getLevel() {
        return level;
    }

    public State getParentNode() {
        return parentNode;
    }

    public List<State> createChildStateFromOneState(){
        List<State> stateList = new ArrayList<>();
        if (blankPos - level >= 0){
            List<Integer> copy = new ArrayList<>();
            copy.addAll(getCurrState());
            int temp = copy.get(blankPos);
            copy.set(blankPos, copy.get(blankPos - level));
            copy.set(blankPos - level, temp);
            stateList.add(new State(copy, (blankPos - level), level, this, deep +1));
        }
        //down
        if (blankPos + level < this.getCurrState().size()){
            List<Integer> copy = new ArrayList<>();
            copy.addAll(getCurrState());
            int temp = copy.get(blankPos);
            copy.set(blankPos, copy.get(blankPos + level));
            copy.set(blankPos + level, temp);
            stateList.add(new State(copy, (blankPos + level), level, this, deep +1));
        }
        //left
        if (blankPos % level > 0){
            List<Integer> copy = new ArrayList<>();
            copy.addAll(getCurrState());
            int temp = copy.get(blankPos);
            copy.set(blankPos, copy.get(blankPos - 1));
            copy.set(blankPos - 1, temp);
            stateList.add(new State(copy, (blankPos - 1), level, this, deep +1));
        }
        //right
        if (blankPos % level < level-1){
            List<Integer> copy = new ArrayList<>();
            copy.addAll(getCurrState());
            int temp = copy.get(blankPos);
            copy.set(blankPos, copy.get(blankPos + 1));
            copy.set(blankPos + 1, temp);
            stateList.add(new State(copy, (blankPos + 1), level, this, deep +1));
        }
        return stateList;
    }

    //neu heuris == 0 => thang

    public int heurisValuePos(State goalState){
        int heuris = 0;
        for (int i = 0; i < goalState.getCurrState().size(); i++){
            if (currState.get(i) != goalState.getCurrState().get(i)){
                heuris++;
            }
        }
        return heuris;
    }

    public int heurisValue(State goalState){
        int distance = 0;
        int b = 0;
        for (int i = 0; i < (level * level); i++){
            int value = currState.get(i);
            int a = 0;
            for (int j = 0; j < (level * level); j++){
                if (value == goalState.getCurrState().get(j)){
                    a = j;
                    break;
                }
            }
            if (value != 0){
                distance += (Math.abs((i % level) - (a % level)) + Math.abs((i / level) - (a / level)));
            }
            if((i % level != level-1) && (currState.get(i) == i+2) && (currState.get(i+1) == i+1)){
                b += 2;
            }
            if((i < (level * level) - level) && (currState.get(i) == i+1+level) && (currState.get(i+level) == i+1)){
                b += 2;
            }
        }
        return distance + b;
    }


    public int heurisValueAndCost(State goalState){
        int distance = 0;
        int b = 0;
        for (int i = 0; i < (level * level); i++){
            int value = currState.get(i);
            int a = 0;
            for (int j = 0; j < (level * level); j++){
                if (value == goalState.getCurrState().get(j)){
                    a = j;
                    break;
                }
            }
            if (value != 0){
                distance += (Math.abs((i % level) - (a % level)) + Math.abs((i / level) - (a / level)));
            }
            if((i % level != level-1) && (currState.get(i) == i+2) && (currState.get(i+1) == i+1)){
                b += 2;
            }
            if((i < (level * level) - level) && (currState.get(i) == i+1+level) && (currState.get(i+level) == i+1)){
                b += 2;
            }
        }
        return distance + b + deep;
    }
}
