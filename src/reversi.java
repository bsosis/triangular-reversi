import java.util.ArrayList;
import java.util.List;

public class reversi {

}

class Board {

    public enum Player {ONE,TWO}
    private int[][] board;

    public Board() {
        board = new int[8][14];
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
        board[3][7] = 2;
        board[4][6] = 1;

    }

    private Board(Board other){
        this.board  = other.board;
    }

    private int getSpace(int row, int col){
        if (row < 0 || row > 7 || col < 0 || col > 13){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
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

    public boolean moveIsLegal(int row, int col, Player player) {
        if (getSpace(row,col) != 0){
            return false;
        }
        return checkHorizontal(row,col,player) || checkVertical(row,col,player) || checkDiagonal(row, col,player);
    }

    private boolean checkDiagonal(int row, int col, Player player) {
        int offset = 1;
//      Check left up
        if (row-offset >= 0 && col-offset >= 0 && getSpace(row-offset,col-offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row - ++offset >= 0 && col - offset >= 0 && getSpace(row-offset, col-offset) > 0){
                if (getSpace(row-offset,col-offset) == currPlayer(player)){
                    return true;
                }
            }
        }
        offset = 1;
//      Check down right
        if (row+offset <= 7 && col+offset <= 13 && getSpace(row+offset,col+offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row + ++offset <= 7 && col + offset <= 13 && getSpace(row+offset, col+offset) > 0){
                if (getSpace(row+offset,col+offset) == currPlayer(player)){
                    return true;
                }
            }
        }
        offset = 1;
//      Check up right
        if (row-offset >= 0 && col+offset <= 13 && getSpace(row-offset,col+offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row - ++offset >= 0 && col + offset <= 13 && getSpace(row-offset, col+offset) > 0){
                if (getSpace(row-offset,col+offset) == currPlayer(player)){
                    return true;
                }
            }
        }
        offset = 1;
        if (row+offset <= 7 && col-offset >= 0 && getSpace(row+offset,col-offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row + ++offset <= 7 && col - offset >= 0 && getSpace(row+offset, col-offset) > 0){
                if (getSpace(row+offset,col-offset) == currPlayer(player)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(int row, int col, Player player) {
        int offset = 1;
//      Check left
        if (row-offset >= 0 && getSpace(row-offset,col) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row - ++offset >= 0 && getSpace(row-offset, col) > 0){
                if (getSpace(row-offset,col) == currPlayer(player)){
                    return true;
                }
            }
        }
        offset = 1;
//      Check right
        if (row+offset <= 7 && getSpace(row+offset,col) == otherPlayer(player)){
//            Keep going while not empty or out of bounce
            while (row + ++offset <= 7 && getSpace(row+offset,col) > 0){
                if (getSpace(row+offset,col) == currPlayer(player)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(int row, int col, Player player) {
        int offset = 1;
//      Check left
        if (col-offset >= 0 && getSpace(row,col-offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (col - ++offset >= 0 && getSpace(row, col-offset) > 0){
                if (getSpace(row,col - offset) == currPlayer(player)){
                    return true;
                }
            }
        }
        offset = 1;
//      Check right
        if (col+offset <= 13 && getSpace(row,col+offset) == otherPlayer(player)){
//            Keep going while not empty or out of bounce
            while (col + ++offset <= 13 && getSpace(row,col+ offset) > 0){
                if (getSpace(row,col + offset) == currPlayer(player)){
                    return true;
                }
            }
        }
        return false;
    }

}