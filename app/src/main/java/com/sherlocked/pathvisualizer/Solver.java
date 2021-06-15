package com.sherlocked.pathvisualizer;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Solver {

    char[][] board;
    int[] src;
    int[] dest;
    int[][] vis,dp;
    int[] dx,dy;
    int n = 9;
    ArrayList<ArrayList<Object>>emptyBoxIndex;
    int selected_row;
    int selected_column;
    boolean sourceChosen,destChosen;

    Solver(){
        selected_row = -1;
        selected_column = -1;

        board = new char[n][n];
        src = new int[2];
        dest = new int[2];

        vis = new int[n][n];
        dp = new int[n][n];

        dx = new int[4];
        dy = new int[4];

        for(int r = 0 ; r<n ; r++) {
            for(int c = 0 ; c<n ; c++) {
                board[r][c] = ' ';
                vis[r][c] = 0;
                dp[r][c] = 1000_000_000;
            }
        }

        dx[0] = dy[2] = -1;
        dx[1] = dy[3] = 1;
        dx[2] = dx[3] = dy[0] = dy[1] = 0;

        sourceChosen = destChosen = false;
        emptyBoxIndex = new ArrayList<>();
    }

    public void getEmptyBoxIndexes(){
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

    public int dfs(int i , int j , PathGrid dsp) {
        if(i==dest[0] && j==dest[1]) {
            return(1);
        }
        vis[i][j] = 1;
        dsp.invalidate();//display refreshes
        int ans = 0;
        for(int p = 0 ; p<4 ; p++){
            int x = i + dx[p];
            int y = j + dy[p];
            if(x<0 || y<0 || x>=n || y>=n || vis[x][y]==1 || this.board[x][y]=='X') {
                continue;
            }
            ans += dfs(x,y,dsp);
            if(ans==1) {
                return(1);
            }
        }
        vis[i][j] = 0;
        dsp.invalidate();//display refreshes
        return(0);
    }

    public Pair<Integer,Integer> goAdd(int x , int y) {
        Integer i = x;
        Integer j = y;
        Pair<Integer,Integer>p = new Pair<Integer,Integer>(i,j);
        return(p);
    }

    public Pair<Integer,Pair<Integer,Integer> > goAdd(int d , int x , int y) {
        Integer i = x;
        Integer j = y;
        Integer c = d;
        Pair<Integer,Integer>p = new Pair<Integer,Integer>(i,j);
        Pair<Integer,Pair<Integer,Integer> >q = new Pair<Integer,Pair<Integer,Integer> >(d,p);
        return(q);
    }

    public int bfs(int i , int j , PathGrid dsp) {
        Queue<Pair<Integer,Integer> > q = new LinkedList<>();
        q.add(goAdd(i,j));
        vis[i][j] = 1;
        dsp.invalidate();//display refreshes
        while(q.size()>0) {
            Pair<Integer,Integer>p = q.remove();
            i = p.first;
            j = p.second;
            int flag = 0;
            for(int p1 = 0 ; p1<4 ; p1++) {
                int x = i + dx[p1];
                int y = j + dy[p1];
                if(x<0 || y<0 || x>=n || y>=n || vis[x][y]==1 || this.board[x][y]=='X') {
                    continue;
                }
                vis[x][y] = 1;
                q.add(goAdd(x,y));
                dsp.invalidate();//display refreshes
                if(x==dest[0] && y==dest[1]) {
                    flag++;
                    break;
                }
            }
            if(flag==1)
            {
                break;
            }
        }
        return(vis[dest[0]][dest[1]]);
    }

    public int dijkstra(int i , int j , PathGrid dsp){
        vis[i][j] = 1;
        dp[i][j] = 0;
        dsp.invalidate();//display refreshes
        while(true) {
            int ans = 1_000_000_000;
            int r,c;
            r = c = -1;
            for(int x = 0 ; x<n ; x++) {
                for(int y = 0 ; y<n ; y++) {
                    if(vis[x][y]==1 && dp[x][y]<ans) {
                        ans = dp[x][y];
                        r = x;
                        c = y;
                    }
                }
            }
            if((r==-1 && c==-1) || (r==dest[0] && c==dest[1])) {
                break;
            }
            vis[r][c] = 2;
            dsp.invalidate();//display refreshes
            if(this.board[r][c]>='0' && this.board[r][c]<='9'){
                ans+=(this.board[r][c]-'0');
            }
            for(int p1 = 0 ; p1<4 ; p1++) {
                int x = r + dx[p1];
                int y = c + dy[p1];
                if(x<0 || y<0 || x>=n || y>=n || this.board[x][y]=='X' || dp[x][y]<=ans) {
                    continue;
                }
                vis[x][y] = 1;
                dp[x][y] = ans;
            }

        }
        return(vis[dest[0]][dest[1]]);
    }

    public void solve(PathGrid display){
        for(int i = 0 ; i<n ;  i++) {
            for(int j = 0 ; j<n ; j++){
                vis[i][j] = 0;
                dp[i][j] = 1_000_000_000;
            }
        }
        int flag = 0;
        if(MainActivity.algo==0) {
            //DFS
            flag = dfs(src[0],src[1],display);
        }else if(MainActivity.algo==1){
            //BFS
            flag = bfs(src[0],src[1],display);
        }else if(MainActivity.algo==2){
            //Dijkstra
            flag = dijkstra(src[0],src[1],display);
        }else{
            //BellmanFord
            flag = dijkstra(src[0],src[1],display);
        }

        Log.d("RSSB",String.valueOf(flag));
    }

    public void resetBoard(){
        for(int r = 0 ; r<n ; r++) {
            for(int c = 0 ; c<n ; c++) {
                board[r][c] = ' ';
                vis[r][c] = 0;
                dp[r][c] = 1000_000_000;
            }
        }
        sourceChosen = destChosen = false;
        this.emptyBoxIndex = new ArrayList<>();
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
                    src[0] = selected_row-1;
                    src[1] = selected_column-1;
                    sourceChosen = true;
                }else if(num=='D') {
                    dest[0] = selected_row-1;
                    dest[1] = selected_column-1;
                    destChosen = true;
                }
            }
        }
    }

    public char[][] getBoard(){
        return this.board;
    }

    public int[][] getVisBoard(){
        return this.vis;
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
