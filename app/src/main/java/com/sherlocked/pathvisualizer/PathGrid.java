package com.sherlocked.pathvisualizer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PathGrid extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;
    private final int blockedCellColor;
    private final int visCellColor;

    private final int letterColor;
    private final int letterColorSolve;

    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();
    private final Paint blockedCellColorPaint = new Paint();
    private final Paint visCellColorPaint = new Paint();

    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    int n = 9;
    private int cellSize;

    private final Solver solver = new Solver();

    public PathGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.PathGrid,
                0,0);
        try{
            boardColor = a.getInteger(R.styleable.PathGrid_boardColor,0);
            cellFillColor = a.getInteger(R.styleable.PathGrid_cellFillColor,0);
            cellsHighlightColor = a.getInteger(R.styleable.PathGrid_cellsHighlightColor,0);
            blockedCellColor = a.getInteger(R.styleable.PathGrid_blockedCellColor,0);
            visCellColor = a.getInteger(R.styleable.PathGrid_visCellColor,0);

            letterColor = a.getInteger(R.styleable.PathGrid_letterColor,0);
            letterColorSolve = a.getInteger(R.styleable.PathGrid_letterColorSolve,0);

        }finally{
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width,height);
        int dimension = Math.min(this.getMeasuredWidth(),this.getMeasuredHeight());
        cellSize = dimension/n;
        setMeasuredDimension(dimension,dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setAntiAlias(true);
        cellFillColorPaint.setColor(cellFillColor);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColorPaint.setAntiAlias(true);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);

        blockedCellColorPaint.setStyle(Paint.Style.FILL);
        blockedCellColorPaint.setAntiAlias(true);
        blockedCellColorPaint.setColor(blockedCellColor);

        visCellColorPaint.setStyle(Paint.Style.FILL);
        visCellColorPaint.setAntiAlias(true);
        visCellColorPaint.setColor(visCellColor);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        colorCell(canvas,solver.getSelectedRow(),solver.getSelectedColumn(),0);
        canvas.drawRect(0,0,getWidth(),getHeight(),boardColorPaint);
        drawBoard(canvas);
        drawNumbers(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            solver.setSelectedRow((int)Math.ceil(y/cellSize));
            solver.setSelectedColumn((int)Math.ceil(x/cellSize));
            isValid = true;
        }else{
            isValid = false;
        }

        return(isValid);
    }

    private void drawNumbers(Canvas canvas){
        letterPaint.setTextSize(cellSize);
        for(int r =0 ; r<n ; r++) {
            for(int c = 0 ; c<n ; c++) {
                if(solver.getVisBoard()[r][c]>0){
                    colorCell(canvas,r+1,c+1,1);
                }
                if(solver.getBoard()[r][c]!=' ') {
                    String text = Character.toString(solver.getBoard()[r][c]);
                    float height,width;

                    letterPaint.getTextBounds(text,0,text.length(),letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text,(c*cellSize) + (cellSize-width)/2,(r*cellSize+cellSize)-(cellSize-height)/2,
                            letterPaint);
                }
            }
        }

        letterPaint.setColor(letterColorSolve);

        for(ArrayList<Object> letter : solver.getEmptyBoxIndex()){
            int r = (int)letter.get(0);
            int c = (int)letter.get(1);

            String text = Character.toString(solver.getBoard()[r][c]);
            float height,width;

            letterPaint.getTextBounds(text,0,text.length(),letterPaintBounds);
            width = letterPaint.measureText(text);
            height = letterPaintBounds.height();

            canvas.drawText(text,(c*cellSize) + (cellSize-width)/2,(r*cellSize+cellSize)-(cellSize-height)/2,
                    letterPaint);

        }
    }

    private void colorCell(Canvas canvas, int r , int c , int d) {
        if(solver.getSelectedRow()!=-1 && solver.getSelectedColumn()!=-1) {
            if(d==0){
                canvas.drawRect((c-1)*cellSize,0,c*cellSize,cellSize*n,
                        cellsHighlightColorPaint);
                canvas.drawRect(0,(r-1)*cellSize,n*cellSize,r*cellSize,
                        cellsHighlightColorPaint);
                canvas.drawRect((c-1)*cellSize,(r-1)*cellSize,c*cellSize,r*cellSize,
                        cellsHighlightColorPaint);
            }else{
                canvas.drawRect((c-1)*cellSize,(r-1)*cellSize,c*cellSize,r*cellSize,
                        visCellColorPaint);
            }
        }
        invalidate();
    }

    private void drawThickLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    private void drawThinLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(8);
        boardColorPaint.setColor(boardColor);
    }

    private void drawBoard(Canvas canvas){
        for(int c = 0 ; c<n+1 ; c++)
        {
            drawThinLine();
            canvas.drawLine(cellSize*c,0,
                    cellSize*c,getWidth(),boardColorPaint);

        }

        for(int r = 0 ; r<n+1 ; r++)
        {
            drawThinLine();
            canvas.drawLine(0,cellSize*r,
                    getWidth(),cellSize*r,boardColorPaint);
        }
    }

    public Solver getSolver(){
        return this.solver;
    }
}
