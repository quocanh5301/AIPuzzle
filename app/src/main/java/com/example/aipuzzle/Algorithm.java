package com.example.aipuzzle;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TimingLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {
    State startState, goalState;
    List<State> checkedState;
    int move;
    long endTime;

    public Algorithm(State startState, State goalState) {
        this.startState = startState;
        this.goalState = goalState;
        checkedState = new ArrayList<>();
    }

    public long getEndTime() {
        return endTime;
    }

    private boolean checkDuplicatedState (State a, State b){
        boolean same = true;
        for (int i = 0; i < b.getCurrState().size(); i++){
            if (a.getCurrState().get(i) != b.getCurrState().get(i)){
                same = false;
            }
        }
        return same;
    }

    private boolean checkCheckedState (List<State> list, State check){
        boolean contain = false;
        for (State state : list){
            if (checkDuplicatedState(state, check)){
                contain = true;
            }
        }
        return contain;
    }

    private void sortStateListBaseOnHeuris2(List<State> states){
        for (int i = 0; i < states.size() - 1; i++){
            for (int j = i + 1; j < states.size(); j++){
                if (states.get(i).heurisValue(goalState) > states.get(j).heurisValue(goalState)){
                    State temp = states.get(i);
                    states.set(i, states.get(j));
                    states.set(j, temp);
                }
            }
        }
    }

    private void sortStateListBaseOnHeuris1(List<State> states){
        for (int i = 0; i < states.size() - 1; i++){
            for (int j = i + 1; j < states.size(); j++){
                if (states.get(i).heurisValuePos(goalState) > states.get(j).heurisValuePos(goalState)){
                    State temp = states.get(i);
                    states.set(i, states.get(j));
                    states.set(j, temp);
                }
            }
        }
    }

    private void sortStateListBaseOnHeuris3(List<State> states){
        for (int i = 0; i < states.size() - 1; i++){
            for (int j = i + 1; j < states.size(); j++){
                if (states.get(i).heurisValue(goalState) > states.get(j).heurisValue(goalState)){
                    State temp = states.get(i);
                    states.set(i, states.get(j));
                    states.set(j, temp);
                }
                if (states.get(i).heurisValue(goalState) == states.get(j).heurisValue(goalState)){
                    if (states.get(i).heurisValuePos(goalState) > states.get(j).heurisValuePos(goalState)){
                        State temp = states.get(i);
                        states.set(i, states.get(j));
                        states.set(j, temp);
                    }
                }
            }
        }
    }

    private void sortStateListBaseOnHeuris4(List<State> states){
        for (int i = 0; i < states.size() - 1; i++){
            for (int j = i + 1; j < states.size(); j++){
                if (states.get(i).heurisValueAndCost(goalState) > states.get(j).heurisValueAndCost(goalState)){
                    State temp = states.get(i);
                    states.set(i, states.get(j));
                    states.set(j, temp);
                }
                if (states.get(i).heurisValueAndCost(goalState) == states.get(j).heurisValueAndCost(goalState)){
                    if (states.get(i).heurisValuePos(goalState) > states.get(j).heurisValuePos(goalState)){
                        State temp = states.get(i);
                        states.set(i, states.get(j));
                        states.set(j, temp);
                    }
                }
            }
        }
    }

    public List<State> track (State state){
        Log.i("track: ", "DONE");
        List<State> road = new ArrayList<>();
        State curr = state;
        road.add(curr);
        while (curr.getParentNode() != null){
            curr = curr.getParentNode();
            road.add(curr);
        }
        Collections.reverse(road);
        return road;
    }

    public int getMove() {
        return move;
    }

    public List<State> solve1(){
        move = 0;
        List<State> result = new ArrayList<>();
        List<State> queuedState = new ArrayList<>();
        queuedState.add(startState);
        while (queuedState.size() > 0){
            State dequeueState = queuedState.get(0);
            queuedState.remove(0);
            checkedState.add(dequeueState);
            move++;
            List<State> childState = dequeueState.createChildStateFromOneState();
            for (State state : childState){
                if (state.heurisValue(goalState) == 0){
                    result = track(state);
                    endTime = SystemClock.elapsedRealtime();
                    return result;
                }
                if (!checkCheckedState(checkedState, state)){
                    queuedState.add(state);
                    sortStateListBaseOnHeuris1(queuedState);
                }
            }
        }
        return result;
    }

    public List<State> solve2(){
        move = 0;
        List<State> result = new ArrayList<>();
        List<State> queuedState = new ArrayList<>();
        queuedState.add(startState);
        while (queuedState.size() > 0){
            State dequeueState = queuedState.get(0);
            queuedState.remove(0);
            checkedState.add(dequeueState);
            move++;
            List<State> childState = dequeueState.createChildStateFromOneState();
            for (State state : childState){
                if (state.heurisValue(goalState) == 0){
                    result = track(state);
                    endTime = SystemClock.elapsedRealtime();
                    return result;
                }
                if (!checkCheckedState(checkedState, state)){
                    queuedState.add(state);
                    sortStateListBaseOnHeuris2(queuedState);
                }
            }
        }
        return result;
    }

    public List<State> solve3(){
        move = 0;
        List<State> result = new ArrayList<>();
        List<State> queuedState = new ArrayList<>();
        queuedState.add(startState);
        while (queuedState.size() > 0){
            State dequeueState = queuedState.get(0);
            queuedState.remove(0);
            checkedState.add(dequeueState);
            move++;
            List<State> childState = dequeueState.createChildStateFromOneState();
            for (State state : childState){
                if (state.heurisValue(goalState) == 0){
                    result = track(state);
                    endTime = SystemClock.elapsedRealtime();
                    return result;
                }
                if (!checkCheckedState(checkedState, state)){
                    queuedState.add(state);
                    sortStateListBaseOnHeuris3(queuedState);
                }
            }
        }
        return result;
    }

    public List<State> solve4(){
        move = 0;
        List<State> result = new ArrayList<>();
        List<State> queuedState = new ArrayList<>();
        queuedState.add(startState);
        while (queuedState.size() > 0){
            State dequeueState = queuedState.get(0);
            queuedState.remove(0);
            checkedState.add(dequeueState);
            move++;
            List<State> childState = dequeueState.createChildStateFromOneState();
            for (State state : childState){
                if (state.heurisValue(goalState) == 0){
                    result = track(state);
                    endTime = SystemClock.elapsedRealtime();
                    return result;
                }
                if (!checkCheckedState(checkedState, state)){
                    queuedState.add(state);
                    sortStateListBaseOnHeuris4(queuedState);
                }
            }
        }
        return result;
    }

}
