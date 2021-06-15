package com.sherlocked.pathvisualizer;

import android.widget.Toast;

import java.util.ArrayList;

public class Solver {

    char[][] board;
    int n = 9;
    ArrayList<ArrayList<Object>>emptyBoxIndex;
    int selected_row;
    int selected_column;
    boolean sourceChosen,destChosen;

    Solver(){
        selected_row = -1;
        selected_column = -1;

        board = new char[n][n];

        for(int r = 0 ; r<n ; r++) {
            for(int c = 0 ; c<n ; c++) {
                board[r][c] = ' ';
            }
        }
        sourceChosen = destChosen = false;
        emptyBoxIndex = new ArrayList<>();
    }

    private void getEmptyBoxIndexs(){
        for(int r = 0 ; r<n ; r++) {
            for(int c = 0 ; c<n ; c++) {
                if(this.board[r][c]==' ') {
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size() - 1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size() - 1).add(c);
                }
            }
        }
    }

    public void setNumberPos(char num){
        if(this.selected_row!=-1 && this.selected_column!=-1) {
            if(this.board[selected_row-1][selected_column-1]==num) {
                this.board[selected_row-1][selected_column-1] = ' ';
                if(num=='S') {
                    sourceChosen = false;
                }else if(num=='D') {
                    destChosen = false;
                }
            }
            else if((sourceChosen && num=='S') || (destChosen && num=='D')) {
                Toast.makeText(MainActivity.context, num + " has already been chosen.", Toast.LENGTH_SHORT).show();
            }
            else{
                this.board[selected_row-1][selected_column-1] = num;
                if(num=='S') {
                    sourceChosen = true;
                }else if(num=='D') {
                    destChosen = true;
                }
            }
        }
    }

    public char[][] getBoard(){
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex(){
        return this.emptyBoxIndex;
    }

    public int getSelectedRow(){
        return selected_row;
    }

    public int getSelectedColumn(){
        return selected_column;
    }

    public void setSelectedRow(int r){
        selected_row = r;
    }
    public void setSelectedColumn(int c){
        selected_column = c;
    }
}
