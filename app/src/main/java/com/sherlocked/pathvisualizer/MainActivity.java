package com.sherlocked.pathvisualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PathGrid gameBoard;
    private Solver gameBoardSolver;
    int sourceChosen,destChosen;
    static Context context;
    static int algo;
//    boolean goSolve;
    private Button solveBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.PathGrid);
        gameBoardSolver = gameBoard.getSolver();
        sourceChosen = destChosen = algo = 0;
//        goSolve = false;
        context = getApplicationContext();
        solveBTN = findViewById(R.id.solveButton);
    }

    public void BTNSourcePress(View view) {
        sourceChosen++;
        gameBoardSolver.setNumberPos('S');
        gameBoard.invalidate();
    }

    public void BTNDestinationPress(View view) {
        destChosen++;
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

    private void showOptionsDialog(){
        String[] algorithms = {"DFS","BFS","Dijkstra","BellmanFord"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Algorithm:");
        builder.setSingleChoiceItems(algorithms, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                algo = i;
            }
        });
        builder.setPositiveButton("Solve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //goSolve = true;
                Toast.makeText(getApplicationContext(),"Applying " + algorithms[algo] + "...",Toast.LENGTH_SHORT).show();
                solveBTN.setText(getString(R.string.clear));
                gameBoardSolver.getEmptyBoxIndexes();
                SolveBoardThread solveBoardThread = new SolveBoardThread();
                new Thread(solveBoardThread).start();
                gameBoard.invalidate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //goSolve = false;
            }
        });
        builder.show();
    }

    public void solve(View view){
        if(solveBTN.getText().toString().equals(getString(R.string.solve))){
            if(sourceChosen%2==0) {
                Toast.makeText(getApplicationContext(),"Source Not Chosen",Toast.LENGTH_SHORT).show();
                return;
            }else if(destChosen%2==0){
                Toast.makeText(getApplicationContext(),"Destination Not Chosen",Toast.LENGTH_SHORT).show();
                return;
            }
            showOptionsDialog();
//            Toast.makeText(getApplicationContext(),"RSSB",Toast.LENGTH_SHORT).show();

        }else{
            solveBTN.setText(getString(R.string.solve));
            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
    }
    class SolveBoardThread implements Runnable {
        @Override
        public void run(){
            gameBoardSolver.solve(gameBoard);
        }
    }

}