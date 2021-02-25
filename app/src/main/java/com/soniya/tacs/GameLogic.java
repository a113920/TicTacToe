package com.soniya.tacs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;

    private String [] playerNames = {"Player 1", "Player 2"};

    //1st element >>row, 2nd element >>column, 3rd element >>lineType
    private int[] winType = {-1, -1, -1};

    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;

    private int player = 1;
    GameLogic(){
        gameBoard = new int[3][3];
        for(int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int col){
        if(gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;

            if (player == 1){
                playerTurn.setText((playerNames[1] + "'s Turn"));
            }
            else {
                playerTurn.setText(playerNames[0] + "'s Turn");
            }

            return true;
        }
        else {
            return false;
        }
    }

    public boolean winnerCheck(){
        boolean isWinner = false;
        //Horizontal check (winType==1)
        for(int r=0; r<3; r++){
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2]
               && gameBoard[r][0] != 0){
                    winType = new int[] {r, 0, 1};
                    isWinner = true;
            }
        }

        //Vertical Check (winType==2)
        for(int c=0; c<3; c++){
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c]
                    && gameBoard[0][c] != 0){
                winType = new int[] {0, c, 2};
                isWinner = true;
            }
        }

        //Negative dialog check (winType==3)
            if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2]
                    && gameBoard[0][0] != 0){
                winType = new int[] {0, 2, 3};
                isWinner = true;
        }

        //Positive dialog check (winType==4)
            if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2]
                    && gameBoard[2][0] != 0){
                winType = new int[] {2, 2, 4};
                isWinner = true;
            }

            int boarFilled = 0;

            for (int r=0; r<3; r++){
                for(int c=0; c<3; c++){
                    if(gameBoard[r][c] != 0){
                        boarFilled += 1;
                    }
                }
            }

         if(isWinner) {
             playAgainBtn.setVisibility(View.VISIBLE);
             homeBtn.setVisibility(View.VISIBLE);
             playerTurn.setText((playerNames[player-1]+" Won!!!"));
             return true;
         }
         else if(boarFilled == 9){
             playAgainBtn.setVisibility(View.VISIBLE);
             homeBtn.setVisibility(View.VISIBLE);
             playerTurn.setText((playerNames[player-1]+" Tie Game!!!"));
             return true;
         }
         else {
             return false;
         }
    }

    public void resetGame(){
        for(int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }

        player = 1;
        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        playerTurn.setText((playerNames[0] + "'s turn"));
    }

    public void setPlayAgainBtn(Button playAgain){
        this.playAgainBtn= playAgain;
    }

    public void setHomeBtn(Button home){
        this.homeBtn= home;
    }

    public void setPlayerTurn(TextView playerTurn){
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames){
        this.playerNames = playerNames;
    }

    public int[][] getGameBoard(){
        return gameBoard;
    }
    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int[] getWinType() {
        return winType;
    }
}
