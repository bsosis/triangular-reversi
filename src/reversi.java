import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.io.*;

public class reversi {
    public static void main(String[] args) {
        reversi r = new reversi();
        r.go();
    }

    public void go() throws InvalidInputException{
        Board board = new Board(false);

        ArrayList<BoardMovePair> possibleMoves = board.getPossibleMoves(Player.ONE);
        if (possibleMoves.size() == 0){
            // This should not happen
            throw new InvalidInputException("No available moves");
        else{
            int maxScore = Integer.MIN_VALUE;
            int bestRow = 0;
            int bestCol = 0;
            for (BoardMovePair nextBoardMovePair : possibleMoves){
                int score = alphaBeta(nextBoardMovePair.board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.TWO);
                if (score >= maxScore){
                    maxScore = score;
                    bestRow = nextBoardMovePair.row;
                    bestCol = nextBoardMovePair.col;
                }
            }

            // Output row,col pair, adjusting for 1-indexing and corners
            row = bestMove[0]+1;
            col = bestMove[1]+1;
            if (row <= 3){
                col -= 4 - row;
            }
            else if (row >= 6){
                col -= row - 5;
            }
            System.out.printf("%d %d\n ", row, col);
        }
    }

    public int alphaBeta(Board board, int depth, int alpha, int beta, Player player){
        ArrayList<BoardMovePair> possibleMoves = board.getPossibleMoves(player);
        if (possibleMoves.size() == 0){
            int score = board.getScore();
            if (score > 0){
                score = Integer.MAX_VALUE;
            }
            else if (score < 0){
                score = Integer.MIN_VALUE;
            }
            return score;
        }
        else if (depth == 0){
            return board.getScore();
        }
        else{
            if (player == Player.ONE){
                int v = Integer.MIN_VALUE;
                for (BoardMovePair nextBoardMovePair : possibleMoves){
                    v = Math.max(v, alpha_beta(nextBoardMovePair.board, depth-1, alpha, beta, Player.TWO));
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
                for (BoardMovePair nextBoardMovePair : possibleMoves){
                    v = Math.min(v, alpha_beta(nextBoardMovePair.board, depth-1, alpha, beta, Player.ONE));
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

class BoardMovePair {
    public Board board;
    public int row;
    public int col;

    public BoardMovePair(Board b, int r, int c){
        board = b;
        row = r;
        col = c;
    }
}

class Board {

    private int[][] board;

    public Board(boolean useDefault) throws InvalidInputException{
        if (useDefault){
            board = new int[14][8];
            int lowerBound = 3;
            int upperBound = 10;
            // fill out first 4 rows
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

            // Add starting tokens
            board[3][6] = 2;
            board[4][7] = 2;
            board[3][7] = 1;
            board[4][6] = 1;
        }
        else{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try{
                board = new int[14][8];
                int lowerBound = 3;
                int upperBound = 10;
                // fill out first 4 rows
                for (int row = 0; row < 4; row++) {
                    String[] line = br.readLine().split(" ");
                    for (int col = 0; col < 14; col++) {
                        if (col >= lowerBound && col <= upperBound){
                            board[row][col] = Integer.parseInt(line[col - lowerBound]);
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
                    String[] line = br.readLine().split(" ");
                    for (int col = 0; col < 14; col++) {
                        if (col >= lowerBound && col <= upperBound){
                            board[row][col] = Integer.parseInt(line[col - lowerBound]);
                        } else {
                            board[row][col] = -1;
                        }
                    }
                    lowerBound--;
                    upperBound--;
                }
            }
            catch (IOException ex){
                raise new InvalidInputException("Invalid input")
            }
            catch (IndexOutOfBoundsException ex){
                // If input is the wrong size
                raise new InvalidInputException("Invalid input")   
            }
            
        }
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

    public ArrayList<BoardMovePair> getPossibleMoves(Player player){
        ArrayList<BoardMovePair> nextMoves = new ArrayList<BoardMovePair>();

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                Board newBoard = playMove(i, j, player);
                if (newBoard != null){
                    nextMoves.add(new BoardMovePair(newBoard, i, j));
                }
            }
        }

        return nextMoves;
    }

    public int getScore(){
        // Return (# of tiles for player 1) - (# of tiles for player 2)
        int score = 0;
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                int currSpace = getSpace(i,j);
                if (currSpace == 1){
                    score++;
                }
                else if (currSpace == 2){
                    score--;
                }
                // Otherwise it's 0 or -1 so ignore
            }
        }
        return score;
    }

}