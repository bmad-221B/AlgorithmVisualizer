package com.sherlocked.pathvisualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private PathGrid gameBoard;
    private Solver gameBoardSolver;
//    boolean sourceChosen,destChosen;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.PathGrid);
        gameBoardSolver = gameBoard.getSolver();
//        sourceChosen = destChosen = false;
        context = getApplicationContext();
    }

    public void BTNSourcePress(View view) {
        gameBoardSolver.setNumberPos('S');
        gameBoard.invalidate();
    }

    public void BTNDestinationPress(View view) {
        gameBoardSolver.setNumberPos('D');
        gameBoard.invalidate();
    }

    public void BTNBlockPress(View view) {
        gameBoardSolver.setNumberPos('X');
        gameBoard.invalidate();
    }

    public void BTNOnePress(View view) {
        gameBoardSolver.setNumberPos('1');
        gameBoard.invalidate();
    }

    public void BTNTwoPress(View view) {
        gameBoardSolver.setNumberPos('2');
        gameBoard.invalidate();
    }

    public void BTNThreePress(View view) {
        gameBoardSolver.setNumberPos('3');
        gameBoard.invalidate();
    }

    public void BTNFourPress(View view) {
        gameBoardSolver.setNumberPos('4');
        gameBoard.invalidate();
    }

    public void BTNFivePress(View view) {
        gameBoardSolver.setNumberPos('5');
        gameBoard.invalidate();
    }

    public void BTNSixPress(View view) {
        gameBoardSolver.setNumberPos('6');
        gameBoard.invalidate();
    }

    public void BTNSevenPress(View view) {
        gameBoardSolver.setNumberPos('7');
        gameBoard.invalidate();
    }

    public void BTNEightPress(View view) {
        gameBoardSolver.setNumberPos('8');
        gameBoard.invalidate();
    }

    public void BTNNinePress(View view) {
        gameBoardSolver.setNumberPos('9');
        gameBoard.invalidate();
    }
}