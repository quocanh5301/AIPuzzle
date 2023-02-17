package com.example.aipuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GridLayout puzzleLayout;
    ImageView template;
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15, imageView16, imageView17, imageView18, imageView19, imageView20, imageView21, imageView22, imageView23, imageView24, imageView25;
    State goalState, startState;
    int level, blankPos;
    List<Integer> goalList, startList;
    List<ImageView> childImageViewList;
    Spinner levelDropdown;
    List<Integer> levelList;
    ArrayAdapter<Integer> arrayAdapter;
    Button solve1, solve2, solve3, solve4, retry, nextState, previousState;
    List<State> road;
    int stateNumber = 0;
    Algorithm algorithm;
    long startTime;
    double runTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        level = getIntent().getIntExtra("level", 3);//////

        levelDropdown = findViewById(R.id.chooseLevel);
        solve1 = findViewById(R.id.solve1);
        solve2 = findViewById(R.id.solve2);
        solve3 = findViewById(R.id.solve3);
        solve4 = findViewById(R.id.solve4);
        retry = findViewById(R.id.retryButton);
        nextState = findViewById(R.id.nextState);
        previousState = findViewById(R.id.previousState);
        solve1.setOnClickListener(this);
        solve2.setOnClickListener(this);
        solve3.setOnClickListener(this);
        solve4.setOnClickListener(this);
        retry.setOnClickListener(this);
        nextState.setOnClickListener(this);
        previousState.setOnClickListener(this);


        levelList = new ArrayList<>();
        levelList.add(3);
        levelList.add(4);
        levelList.add(5);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, levelList);
        levelDropdown.setAdapter(arrayAdapter);


        childImageViewList = new ArrayList<>();
        goalList = new ArrayList<>();
        createGoalList(level);
        goalState = new State(goalList, (level * level) - 1, level);
        startList = new ArrayList<>();
        createStartListAndBlankPos(level);
        startState = new State(startList, blankPos, level, 0);

        createListOfViewBaseOnLevel(level);
        setImageTagBaseOnList(startList);
        setImageSrcBaseOnTagAndLevel(level);
        for (int i = 0; i < puzzleLayout.getChildCount(); i++){
            puzzleLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    private void openBottomSheetDialogFragment(double runTime, int move) {
        BottomSheet bottomSheetFragment = BottomSheet.newInstance(runTime, move);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void solveUp(){
        solve1.setVisibility(View.VISIBLE);
        solve2.setVisibility(View.VISIBLE);
        solve3.setVisibility(View.VISIBLE);
        levelDropdown.setVisibility(View.INVISIBLE);
        retry.setVisibility(View.INVISIBLE);
        nextState.setVisibility(View.INVISIBLE);
        previousState.setVisibility(View.INVISIBLE);
    }

    private void solveDown(){
        solve1.setVisibility(View.INVISIBLE);
        solve2.setVisibility(View.INVISIBLE);
        solve3.setVisibility(View.INVISIBLE);
        levelDropdown.setVisibility(View.VISIBLE);
        retry.setVisibility(View.VISIBLE);
        nextState.setVisibility(View.VISIBLE);
        previousState.setVisibility(View.VISIBLE);
    }

    private void setImageSrcBaseOnTagAndLevel(int level) {
        switch (level){
            case 3:
                for (int i = 0; i < 9; i++){
                    switch ((Integer.parseInt(String.valueOf(puzzleLayout.getChildAt(i).getTag())))){
                        case 1:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.one3x3);
                            break;
                        case 2:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.two3x3);
                            break;
                        case 3:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.three3x3);
                            break;
                        case 4:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.four3x3);
                            break;
                        case 5:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.five3x3);
                            break;
                        case 6:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.six3x3);
                            break;
                        case 7:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.seven3x3);
                            break;
                        case 8:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.eight3x3);
                            break;
                        case 9:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.blank);
                            break;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < 16; i++){
                    switch ((Integer.parseInt(String.valueOf(puzzleLayout.getChildAt(i).getTag())))){
                        case 1:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.one4x4);
                            break;
                        case 2:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.two4x4);
                            break;
                        case 3:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.three4x4);
                            break;
                        case 4:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.four4x4);
                            break;
                        case 5:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.five4x4);
                            break;
                        case 6:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.six4x4);
                            break;
                        case 7:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.seven4x4);
                            break;
                        case 8:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.eight4x4);
                            break;
                        case 9:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.nine4x4);
                            break;
                        case 10:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.ten4x4);
                            break;
                        case 11:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.eleven4x4);
                            break;
                        case 12:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twelve4x4);
                            break;
                        case 13:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.thirteen4x4);
                            break;
                        case 14:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.fourteen4x4);
                            break;
                        case 15:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.fifteen4x4);
                            break;
                        case 16:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.blank);
                            break;
                    }
                }
                break;
            case 5:
                for (int i = 0; i < 25; i++){
                    switch ((Integer.parseInt(String.valueOf(puzzleLayout.getChildAt(i).getTag())))){
                        case 1:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.one5x5);
                            break;
                        case 2:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.two5x5);
                            break;
                        case 3:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.three5x5);
                            break;
                        case 4:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.four5x5);
                            break;
                        case 5:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.five5x5);
                            break;
                        case 6:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.six5x5);
                            break;
                        case 7:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.seven5x5);
                            break;
                        case 8:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.eight5x5);
                            break;
                        case 9:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.nine5x5);
                            break;
                        case 10:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.ten5x5);
                            break;
                        case 11:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.eleven5x5);
                            break;
                        case 12:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twelve5x5);
                            break;
                        case 13:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.thirteen5x5);
                            break;
                        case 14:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.fourteen5x5);
                            break;
                        case 15:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.fifteen5x5);
                            break;
                        case 16:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.sixteen5x5);
                            break;
                        case 17:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.seventeen5x5);
                            break;
                        case 18:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.eighteen5x5);
                            break;
                        case 19:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.nineteen5x5);
                            break;
                        case 20:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twenty5x5);
                            break;
                        case 21:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twentyone5x5);
                            break;
                        case 22:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twentytwo5x5);
                            break;
                        case 23:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twentythree5x5);
                            break;
                        case 24:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.twentufour5x5);
                            break;
                        case 25:
                            puzzleLayout.getChildAt(i).setBackgroundResource(R.drawable.blank);
                            break;
                    }
                }
                break;
        }
        puzzleLayout.refreshDrawableState();
    }

    private void setImageTagBaseOnList(List<Integer> list){
        for (int i = 0; i < list.size(); i++){
            puzzleLayout.getChildAt(i).setTag(list.get(i));
        }
    }

    private void setBlankPosAtHighestValue(List<Integer> startList, int level){
        int max = level * level;
        for (int i = 0; i < startList.size(); i++){
            if (startList.get(i) == max){
                blankPos = i;
            }
        }
    }

    private void createStartListAndBlankPos(int level) {
        switch (level){
            case 3:
                startList.add(1);
                startList.add(2);
                startList.add(9);
                startList.add(3);
                startList.add(4);
                startList.add(6);
                startList.add(7);
                startList.add(5);
                startList.add(8);
//                Collections.shuffle(startList);
                setBlankPosAtHighestValue(startList, level);
                break;
            case 4:
                startList.add(5);
                startList.add(1);
                startList.add(2);
                startList.add(3);
                startList.add(6);
                startList.add(7);
                startList.add(11);
                startList.add(4);
                startList.add(13);
                startList.add(9);
                startList.add(15);
                startList.add(8);
                startList.add(16);
                startList.add(10);
                startList.add(14);
                startList.add(12);
//                startList.add(5);
//                startList.add(2);
//                startList.add(16);
//                startList.add(3);
//                startList.add(6);
//                startList.add(1);
//                startList.add(7);
//                startList.add(11);
//                startList.add(13);
//                startList.add(15);
//                startList.add(14);
//                startList.add(4);
//                startList.add(10);
//                startList.add(9);
//                startList.add(12);
//                startList.add(8);
//                Collections.shuffle(startList);
                setBlankPosAtHighestValue(startList, level);
                break;
            case 5:
                startList.add(6);
                startList.add(1);
                startList.add(2);
                startList.add(9);
                startList.add(4);
                startList.add(7);
                startList.add(12);
                startList.add(3);
                startList.add(8);
                startList.add(5);
                startList.add(16);
                startList.add(11);
                startList.add(14);
                startList.add(15);
                startList.add(10);
                startList.add(17);
                startList.add(22);
                startList.add(13);
                startList.add(24);
                startList.add(19);
                startList.add(25);
                startList.add(21);
                startList.add(18);
                startList.add(23);
                startList.add(20);
//                Collections.shuffle(startList);
                setBlankPosAtHighestValue(startList, level);
                break;
        }
    }

    public void createGoalList(int level){
        switch (level){
            case 3:
                goalList.add(1);
                goalList.add(2);
                goalList.add(3);
                goalList.add(4);
                goalList.add(5);
                goalList.add(6);
                goalList.add(7);
                goalList.add(8);
                goalList.add(9);
                break;
            case 4:
                goalList.add(1);
                goalList.add(2);
                goalList.add(3);
                goalList.add(4);
                goalList.add(5);
                goalList.add(6);
                goalList.add(7);
                goalList.add(8);
                goalList.add(9);
                goalList.add(10);
                goalList.add(11);
                goalList.add(12);
                goalList.add(13);
                goalList.add(14);
                goalList.add(15);
                goalList.add(16);
                break;
            case 5:
                goalList.add(1);
                goalList.add(2);
                goalList.add(3);
                goalList.add(4);
                goalList.add(5);
                goalList.add(6);
                goalList.add(7);
                goalList.add(8);
                goalList.add(9);
                goalList.add(10);
                goalList.add(11);
                goalList.add(12);
                goalList.add(13);
                goalList.add(14);
                goalList.add(15);
                goalList.add(16);
                goalList.add(17);
                goalList.add(18);
                goalList.add(19);
                goalList.add(20);
                goalList.add(21);
                goalList.add(22);
                goalList.add(23);
                goalList.add(24);
                goalList.add(25);
                break;
        }
    }

    public void createListOfViewBaseOnLevel(int level){
        switch (level){
            case 3:
                template = findViewById(R.id.puzzleTemplate3x3);
                puzzleLayout = findViewById(R.id.puzzle3x3);
                imageView1 = findViewById(R.id.imageView1);
                imageView2 = findViewById(R.id.imageView2);
                imageView3 = findViewById(R.id.imageView3);
                imageView4 = findViewById(R.id.imageView4);
                imageView5 = findViewById(R.id.imageView5);
                imageView6 = findViewById(R.id.imageView6);
                imageView7 = findViewById(R.id.imageView7);
                imageView8 = findViewById(R.id.imageView8);
                imageView9 = findViewById(R.id.imageView9);
                childImageViewList.add(imageView1);
                childImageViewList.add(imageView2);
                childImageViewList.add(imageView3);
                childImageViewList.add(imageView4);
                childImageViewList.add(imageView5);
                childImageViewList.add(imageView6);
                childImageViewList.add(imageView7);
                childImageViewList.add(imageView8);
                childImageViewList.add(imageView9);
                template.setVisibility(View.VISIBLE);
                puzzleLayout.setVisibility(View.VISIBLE);
                break;
            case 4:
                template = findViewById(R.id.puzzleTemplate4x4);
                puzzleLayout = findViewById(R.id.puzzle4x4);
                imageView1 = findViewById(R.id.imageView4x41);
                imageView2 = findViewById(R.id.imageView4x42);
                imageView3 = findViewById(R.id.imageView4x43);
                imageView4 = findViewById(R.id.imageView4x44);
                imageView5 = findViewById(R.id.imageView4x45);
                imageView6 = findViewById(R.id.imageView4x46);
                imageView7 = findViewById(R.id.imageView4x47);
                imageView8 = findViewById(R.id.imageView4x48);
                imageView9 = findViewById(R.id.imageView4x49);
                imageView10 = findViewById(R.id.imageView4x410);
                imageView11 = findViewById(R.id.imageView4x411);
                imageView12 = findViewById(R.id.imageView4x412);
                imageView13 = findViewById(R.id.imageView4x413);
                imageView14 = findViewById(R.id.imageView4x414);
                imageView15 = findViewById(R.id.imageView4x415);
                imageView16 = findViewById(R.id.imageView4x416);
                childImageViewList.add(imageView1);
                childImageViewList.add(imageView2);
                childImageViewList.add(imageView3);
                childImageViewList.add(imageView4);
                childImageViewList.add(imageView5);
                childImageViewList.add(imageView6);
                childImageViewList.add(imageView7);
                childImageViewList.add(imageView8);
                childImageViewList.add(imageView9);
                childImageViewList.add(imageView10);
                childImageViewList.add(imageView11);
                childImageViewList.add(imageView12);
                childImageViewList.add(imageView13);
                childImageViewList.add(imageView14);
                childImageViewList.add(imageView15);
                childImageViewList.add(imageView16);
                template.setVisibility(View.VISIBLE);
                puzzleLayout.setVisibility(View.VISIBLE);
                break;
            case 5:
                template = findViewById(R.id.puzzleTemplate5x5);
                puzzleLayout = findViewById(R.id.puzzle5x5);
                imageView1 = findViewById(R.id.imageView5x51);
                imageView2 = findViewById(R.id.imageView5x52);
                imageView3 = findViewById(R.id.imageView5x53);
                imageView4 = findViewById(R.id.imageView5x54);
                imageView5 = findViewById(R.id.imageView5x55);
                imageView6 = findViewById(R.id.imageView5x56);
                imageView7 = findViewById(R.id.imageView5x57);
                imageView8 = findViewById(R.id.imageView5x58);
                imageView9 = findViewById(R.id.imageView5x59);
                imageView10 = findViewById(R.id.imageView5x510);
                imageView11 = findViewById(R.id.imageView5x511);
                imageView12 = findViewById(R.id.imageView5x512);
                imageView13 = findViewById(R.id.imageView5x513);
                imageView14 = findViewById(R.id.imageView5x514);
                imageView15 = findViewById(R.id.imageView5x515);
                imageView16 = findViewById(R.id.imageView5x516);
                imageView17 = findViewById(R.id.imageView5x517);
                imageView18 = findViewById(R.id.imageView5x518);
                imageView19 = findViewById(R.id.imageView5x519);
                imageView20 = findViewById(R.id.imageView5x520);
                imageView21 = findViewById(R.id.imageView5x521);
                imageView22 = findViewById(R.id.imageView5x522);
                imageView23 = findViewById(R.id.imageView5x523);
                imageView24 = findViewById(R.id.imageView5x524);
                imageView25 = findViewById(R.id.imageView5x525);
                childImageViewList.add(imageView1);
                childImageViewList.add(imageView2);
                childImageViewList.add(imageView3);
                childImageViewList.add(imageView4);
                childImageViewList.add(imageView5);
                childImageViewList.add(imageView6);
                childImageViewList.add(imageView7);
                childImageViewList.add(imageView8);
                childImageViewList.add(imageView9);
                childImageViewList.add(imageView10);
                childImageViewList.add(imageView11);
                childImageViewList.add(imageView12);
                childImageViewList.add(imageView13);
                childImageViewList.add(imageView14);
                childImageViewList.add(imageView15);
                childImageViewList.add(imageView16);
                childImageViewList.add(imageView17);
                childImageViewList.add(imageView18);
                childImageViewList.add(imageView19);
                childImageViewList.add(imageView20);
                childImageViewList.add(imageView21);
                childImageViewList.add(imageView22);
                childImageViewList.add(imageView23);
                childImageViewList.add(imageView24);
                childImageViewList.add(imageView25);
                template.setVisibility(View.VISIBLE);
                puzzleLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.solve1:
                startTime = SystemClock.elapsedRealtime();
                algorithm = new Algorithm(startState, goalState);
                road = algorithm.solve1();
                runTime = ((algorithm.getEndTime() - startTime) / 1000.0);
                openBottomSheetDialogFragment(runTime, algorithm.getMove());
                solveDown();
                break;
            case R.id.solve2:
                startTime = SystemClock.elapsedRealtime();
                algorithm = new Algorithm(startState, goalState);
                road = algorithm.solve2();
                runTime = ((algorithm.getEndTime() - startTime) / 1000.0);
                openBottomSheetDialogFragment(runTime, algorithm.getMove());
                solveDown();
                break;
            case R.id.solve3:
                startTime = SystemClock.elapsedRealtime();
                algorithm = new Algorithm(startState, goalState);
                road = algorithm.solve3();
                runTime = ((algorithm.getEndTime() - startTime) / 1000.0);
                openBottomSheetDialogFragment(runTime, algorithm.getMove());
                solveDown();
                break;
            case R.id.solve4:
                startTime = SystemClock.elapsedRealtime();
                algorithm = new Algorithm(startState, goalState);
                road = algorithm.solve4();
                runTime = ((algorithm.getEndTime() - startTime) / 1000.0);
                openBottomSheetDialogFragment(runTime, algorithm.getMove());
                solveDown();
                break;
            case R.id.retryButton:
                level = (int) levelDropdown.getSelectedItem();
                puzzleLayout.setVisibility(View.INVISIBLE);
                template.setVisibility(View.INVISIBLE);
                stateNumber = 0;
                childImageViewList = new ArrayList<>();
                goalList = new ArrayList<>();
                createGoalList(level);
                goalState = new State(goalList, (level * level) - 1, level);
                startList = new ArrayList<>();
                createStartListAndBlankPos(level);
                startState = new State(startList, blankPos, level, 0);

                createListOfViewBaseOnLevel(level);
                setImageTagBaseOnList(startList);
                setImageSrcBaseOnTagAndLevel(level);
                for (int i = 0; i < puzzleLayout.getChildCount(); i++){
                    puzzleLayout.getChildAt(i).setOnClickListener(this);
                }
                solveUp();
                break;
            case R.id.nextState:
                if (stateNumber < road.size() - 1){
                    setImageTagBaseOnList(road.get(stateNumber + 1).getCurrState());
                    setImageSrcBaseOnTagAndLevel(level);
                    stateNumber++;
                } else {
                    Toast.makeText(MainActivity.this, "end", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.previousState:
                if (stateNumber > 0){
                    setImageTagBaseOnList(road.get(stateNumber - 1).getCurrState());
                    setImageSrcBaseOnTagAndLevel(level);
                    stateNumber--;
                } else {
                    Toast.makeText(MainActivity.this, "end", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                for (int i = 0; i < childImageViewList.size(); i++){
                    if (childImageViewList.get(i).getTag() == v.getTag()){
                        if (i == blankPos - 1 || i == blankPos + 1 || i == blankPos + level || i == blankPos - level){
                            int temptag = (int) childImageViewList.get(blankPos).getTag();
                            childImageViewList.get(blankPos).setTag(childImageViewList.get(i).getTag());
                            childImageViewList.get(i).setTag(temptag);
                            int temp = startList.get(i);
                            startList.set(i, startList.get(blankPos));
                            startList.set(blankPos, temp);
                            blankPos = i;
                            setImageSrcBaseOnTagAndLevel(level);
                            startState = new State(startList, blankPos, level, 0);
                            if (startState.heurisValuePos(goalState) == 0){
                                Toast.makeText(MainActivity.this, "YOU WIN!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                break;
        }
    }
}