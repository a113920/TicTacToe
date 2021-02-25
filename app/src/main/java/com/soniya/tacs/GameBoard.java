package com.soniya.tacs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLinerColor;

    private boolean winningLine = false;

    private final GameLogic game;

    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure( width, height);

        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimensions/3;

        setMeasuredDimension(dimensions , dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       paint.setStyle(Paint.Style.STROKE);
       paint.setAntiAlias(true);

        drawGameBoards(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLinerColor);
            drawerWinningLine(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action==MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

           if(!winningLine){
               //wining position
               if(game.winnerCheck()){
                   winningLine = true;
                   invalidate();
               }

               //updating player turn
               if(game.updateGameBoard(row , col)){
                   invalidate();

                   if(game.getPlayer() % 2 == 0){
                       game.setPlayer(game.getPlayer() - 1);
                   }
                   else {
                       game.setPlayer(game.getPlayer() + 1);
                   }
               }

           }
            invalidate();

            return true;
        }
        return false;
    }

    //design of tic tac toe game

    private void drawGameBoards(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(10);
        for(int i=1;i<3;i++){
            canvas.drawLine(cellSize*i, 0 , cellSize*i, canvas.getWidth() , paint);
        }
        for(int j=1;j<3;j++){
            canvas.drawLine(0 , cellSize*j , canvas.getWidth(), cellSize*j , paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for(int r=0; r<3; r++){
            for (int c=0; c<3; c++){
              if(game.getGameBoard()[r][c]!=0){
                        if(game.getGameBoard()[r][c]==1){
                            drawX(canvas, r, c);
                  }
                        else {
                            drawO(canvas, r, c);
                        }
              }
            }
        }
    }

    private void drawX(Canvas canvas, int row , int column) {
        paint.setColor(XColor);

        canvas.drawLine((float) ((column+1)*cellSize-cellSize*0.2),
                        (float) (row*cellSize+cellSize*0.2),
                        (float) (column*cellSize+cellSize*0.2),
                        (float) ((row+1)*cellSize-cellSize*0.2),
                        paint);

        canvas.drawLine((float) (column*cellSize+cellSize*0.2),
                        (float) (row*cellSize+cellSize*0.2),
                        (float) ((column+1)*cellSize-cellSize*0.2),
                        (float) ((row+1)*cellSize-cellSize*0.2),
                        paint);
    }

    @SuppressLint("NewApi")
    private void drawO(Canvas canvas, int row , int column) {
        paint.setColor(OColor);
            canvas.drawOval((float)(column * cellSize + cellSize * 0.2),
                    (float)(row * cellSize + cellSize * 0.2),
                    (float)((column * cellSize + cellSize) - cellSize * 0.2),
                    (float)((row * cellSize + cellSize)
                    - cellSize * 0.2),paint);
    }

    public void drawerHorizontalLine(Canvas canvas, int row, int column){
        canvas.drawLine(column, row * cellSize + (float) cellSize / 2,
                        cellSize * 3, row * (float) cellSize + cellSize / 2, paint);
    }

    public void drawerVerticalLine(Canvas canvas, int row, int column){
        canvas.drawLine(column * cellSize + (float) cellSize / 2, row ,
                column * (float) cellSize + cellSize / 2, cellSize * 3, paint);
    }

    public void drawerDialogLinePos(Canvas canvas){
        canvas.drawLine(0 , cellSize * 3,cellSize * 3, 0 , paint);
    }

    public void drawerDialogLineNeg(Canvas canvas){
        canvas.drawLine(0 , 0 ,cellSize * 3, cellSize * 3 , paint);
    }

    public void drawerWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                drawerHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawerVerticalLine(canvas, row, col);
                break;
            case 3:
                drawerDialogLineNeg(canvas);
                break;
            case 4:
                drawerDialogLinePos(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names){
            game.setPlayAgainBtn(playAgain);
            game.setHomeBtn(home);
            game.setPlayerTurn(playerDisplay);
            game.setPlayerNames(names);
    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }
    public GameBoard(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );

        game = new GameLogic();

        TypedArray list = context.getTheme().
                obtainStyledAttributes(attrs, R.styleable.GameBoard, 0, 0);

        try {
            boardColor = list.getInteger(R.styleable.GameBoard_boardColor, 0);
            XColor = list.getInteger(R.styleable.GameBoard_XColor, 0);
            OColor = list.getInteger(R.styleable.GameBoard_OColor, 0);
            winningLinerColor = list.getInteger(R.styleable.GameBoard_winningLinerColor, 0);
        }
        finally {
            list.recycle();
        }
    }
}
