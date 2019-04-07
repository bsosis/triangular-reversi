import java.util.ArrayList;
import java.util.List;

public class reversi {

}

class Board {

    private enum Player {ONE,TWO}
    private int[][] board;

    private Board() {
        board = new int[14][8];
        int lowerBound = 3;
        int upperBound = 10;
//      fill out first 4 rows
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 14; col++) {
                if (col >= lowerBound && col <= upperBound){
                    board[row][col] = 0;
                } else {
                    board[row][col] = -1;
                }
            }
            lowerBound++;
            upperBound++;
        }
        lowerBound = 0;
        upperBound = 13;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 14; col++) {
                if (col >= lowerBound && col <= upperBound){
                    board[row][col] = 0;
                } else {
                    board[row][col] = -1;
                }
            }
            lowerBound--;
            upperBound--;
        }

//        Add starting tokens
        board[3][6] = 2;
        board[4][7] = 2;
        board[3][7] = 1;
        board[4][6] = 1;

    }

    private Board(Board other){
        this.board  = other.board;
    }

    private int getSpace(int row, int col){
        return board[row][col];
    }

    private int otherPlayer(Player player){
        return player == Player.ONE? 2: 1;
    }

    private int currPlayer(Player player){
        return player == Player.ONE? 1: 2;
    }


    public Board playMove(int row, int col, Player player){
        Board updatedBoard = new Board();
        if (!moveIsLegal(row,col,player)){
            return null;
        }
        return new Board();
    }

    private boolean moveIsLegal(int row, int col, Player player) {
        if (getSpace(row,col) != 0){
            return false;
        }
        return checkHorizontal(row,col,player) || checkVertical(row,col,player) || checkDiagonal(row, col,player);
    }

    private boolean checkHorizontal(int row, int col, Player player) {

        int playerNum = (player == Player.ONE) ? 1:2;
        if (board[row-1][col] != playerNum){

        } else if (getSpace())
    }

}