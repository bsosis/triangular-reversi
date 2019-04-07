import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class reversi {
    public int alpha_beta(Board board, int depth, int alpha, int beta, Player player){
        ArrayList<Board> possibleMoves = board.getPossibleMoves(player);
        if (possibleMoves.size() == 0){
            int score = board.getScore(player);
            if (score > 0){
                score = Integer.MAX_VALUE;
            }
            else if (score < 0){
                score = Integer.MIN_VALUE;
            }
            return score;
        }
        else if (depth == 0){
            return board.getScore(player);
        }
        else{
            if (player == Player.ONE){
                int v = Integer.MIN_VALUE;
                for (Board nextBoard : possibleMoves){
                    v = Math.max(v, alpha_beta(nextBoard, depth-1, alpha, beta, Player.TWO));
                    if (v >= beta){
                        return v;
                    }
                    else{
                        alpha = Math.max(alpha, v);
                    }
                }
            }
            else{
                int v = Integer.MAX_VALUE;
                for (Board nextBoard : possibleMoves){
                    v = Math.min(v, alpha_beta(nextBoard, depth-1, alpha, beta, Player.ONE));
                    if (v <= alpha){
                        return v;
                    }
                    else{
                        beta = Math.min(beta, v);
                    }
                }
            }
        }
    }
}

enum Player {ONE,TWO}

class Board {

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
        boolean valid = false;
        if (getSpace(row-1,col) == otherPlayer(player)){

        } else if (getSpace())
    }

    public ArrayList<Board> getPossibleMoves(Player player){
        ArrayList<Board> nextMoves = new ArrayList<Board>();

        for(int i=0; i<board.size(); i++){
            for(int j=0; j<board[i].size(); j++){
                Board newBoard = playMove(i, j, player);
                if (newBoard != null){
                    nextMoves.add(newBoard);
                }
            }
        }

        return nextMoves;
    }

    public int getScore(Player player){
        // Return (# of tiles for player) - (# of tiles for opponent)
        // TODO
        return 0;
    }

}