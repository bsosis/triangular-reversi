import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.io.*;

public class triangularreversi {
    public static void main(String[] args) {
        triangularreversi r = new triangularreversi();
        r.go();
    }

    public void go() throws IllegalArgumentException{
        Board board = new Board();

        ArrayList<BoardMovePair> possibleMoves = board.getPossibleMoves(Player.ONE);
        if (possibleMoves.size() == 0){
            // This should not happen
            throw new IllegalArgumentException("No available moves");
        }
        else{
            int maxScore = Integer.MIN_VALUE;
            int bestRow = 0;
            int bestCol = 0;
            for (BoardMovePair nextBoardMovePair : possibleMoves){
                int score = alphaBeta(nextBoardMovePair.board, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.TWO);
                if (score >= maxScore){
                    maxScore = score;
                    bestRow = nextBoardMovePair.row;
                    bestCol = nextBoardMovePair.col;
                }
            }

            // Output row,col pair, adjusting for 1-indexing and board shape
            int row = bestRow+1;
            int col = bestCol+1 - (10-row);
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
                    v = Math.max(v, alphaBeta(nextBoardMovePair.board, depth-1, alpha, beta, Player.TWO));
                    if (v >= beta){
                        return v;
                    }
                    else{
                        alpha = Math.max(alpha, v);
                    }
                }
                return v;
            }
            else{
                int v = Integer.MAX_VALUE;
                for (BoardMovePair nextBoardMovePair : possibleMoves){
                    v = Math.min(v, alphaBeta(nextBoardMovePair.board, depth-1, alpha, beta, Player.ONE));
                    if (v <= alpha){
                        return v;
                    }
                    else{
                        beta = Math.min(beta, v);
                    }
                }
                return v;
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


    public enum Direction {UP,DOWN,RIGHT,LEFT,UPLEFT,UPRIGHT,DOWNLEFT,DOWNRIGHT}

    private int[][] board;

    public Board() throws IllegalArgumentException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try{
            board = new int[10][20];
            int lowerBound = 9;
            int upperBound = 10;
            for(int row = 0; row < 10; row++){
                String[] line = br.readLine().split(" ");
                for (int col = 0; col < 20; col++){
                    if (col >= lowerBound && col <= upperBound){
                        board[row][col] = Integer.parseInt(line[col - lowerBound]);
                    } else {
                        board[row][col] = -1;
                    }
                }
                lowerBound--;
                upperBound++;
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
            throw new IllegalArgumentException("Invalid input");
        }
        catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
            // If input is the wrong size
            throw new IllegalArgumentException("Invalid input");
        }
        // System.out.print(this);
        // System.out.println("======================");
    }

    private Board(Board other){
        board = new int[10][20];

        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 20; col++){
                board[row][col] = other.board[row][col];
            }
        }
    }

    private int getSpace(int row, int col){
        if (row < 0 || row > 9 || col < 0 || col > 19){
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
        ArrayList<Direction> directions = moveIsLegal(row,col,player);
        if (directions == null){
            return null;
        }
        else{
            Board newBoard = new Board(this);
            newBoard.board[row][col] = currPlayer(player);

            for(Direction dir : directions){
                int offset = 1;
                switch (dir){
                    case UP:
                        // Keep going while not empty or out of bounce
                        while (row - offset >= 0 && newBoard.getSpace(row-offset, col) == otherPlayer(player)){
                            newBoard.board[row-offset][col] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case DOWN:
                        // Keep going while not empty or out of bounce
                        while (row + offset >= 0 && newBoard.getSpace(row+offset, col) == otherPlayer(player)){
                            newBoard.board[row+offset][col] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case LEFT:
                        // Keep going while not empty or out of bounce
                        while (col - offset >= 0 && newBoard.getSpace(row, col-offset) == otherPlayer(player)){
                            newBoard.board[row][col-offset] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case RIGHT:
                        // Keep going while not empty or out of bounce
                        while (col + offset >= 0 && newBoard.getSpace(row, col+offset) == otherPlayer(player)){
                            newBoard.board[row][col+offset] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case UPLEFT:
                        // Keep going while not empty or out of bounce
                        while (row - offset >= 0 && col - offset >= 0 && newBoard.getSpace(row-offset, col-offset) == otherPlayer(player)){
                            newBoard.board[row-offset][col-offset] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case UPRIGHT:
                        // Keep going while not empty or out of bounce
                        while (row - offset >= 0 && col + offset >= 0 && newBoard.getSpace(row-offset, col+offset) == otherPlayer(player)){
                            newBoard.board[row-offset][col+offset] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case DOWNLEFT:
                        // Keep going while not empty or out of bounce
                        while (row + offset >= 0 && col - offset >= 0 && newBoard.getSpace(row+offset, col-offset) == otherPlayer(player)){
                            newBoard.board[row+offset][col-offset] = currPlayer(player);
                            offset++;
                        }
                        break;
                    case DOWNRIGHT:
                        // Keep going while not empty or out of bounce
                        while (row + offset >= 0 && col + offset >= 0 && newBoard.getSpace(row+offset, col+offset) == otherPlayer(player)){
                            newBoard.board[row+offset][col+offset] = currPlayer(player);
                            offset++;
                        }
                        break;
                }
            }
            return newBoard;
        }
    }

    public ArrayList<Direction> moveIsLegal(int row, int col, Player player) {
        if (getSpace(row,col) != 0){
            return null;
        }
        ArrayList<Direction> directions = new ArrayList<Direction>();

        directions.addAll(checkHorizontal(row,col,player));
        
        directions.addAll(checkVertical(row,col,player));

        directions.addAll(checkDiagonal(row,col,player));

        if (directions.size() == 0){
            return null;
        }
        else{
            return directions;
        }
    }

    private ArrayList<Direction> checkDiagonal(int row, int col, Player player) {
        ArrayList<Direction> directions = new ArrayList<Direction>();

        int offset = 1;
//      Check left up
        if (row-offset >= 0 && col-offset >= 0 && getSpace(row-offset,col-offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row - ++offset >= 0 && col - offset >= 0 && getSpace(row-offset, col-offset) > 0){
                if (getSpace(row-offset,col-offset) == currPlayer(player)){
                    directions.add(Direction.UPLEFT);
                    break;
                }
            }
        }
        offset = 1;
//      Check down right
        if (row+offset <= 9 && col+offset <= 19 && getSpace(row+offset,col+offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row + ++offset <= 9 && col + offset <= 19 && getSpace(row+offset, col+offset) > 0){
                if (getSpace(row+offset,col+offset) == currPlayer(player)){
                    directions.add(Direction.DOWNRIGHT);
                    break;
                }
            }
        }
        offset = 1;
//      Check up right
        if (row-offset >= 0 && col+offset <= 19 && getSpace(row-offset,col+offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row - ++offset >= 0 && col + offset <= 19 && getSpace(row-offset, col+offset) > 0){
                if (getSpace(row-offset,col+offset) == currPlayer(player)){
                    directions.add(Direction.UPRIGHT);
                    break;
                }
            }
        }
        offset = 1;
        if (row+offset <= 9 && col-offset >= 0 && getSpace(row+offset,col-offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row + ++offset <= 9 && col - offset >= 0 && getSpace(row+offset, col-offset) > 0){
                if (getSpace(row+offset,col-offset) == currPlayer(player)){
                    directions.add(Direction.DOWNLEFT);
                    break;
                }
            }
        }
        return directions;
    }

    private ArrayList<Direction> checkVertical(int row, int col, Player player) {
        ArrayList<Direction> directions = new ArrayList<Direction>();

        int offset = 1;
//      Check up
        if (row-offset >= 0 && getSpace(row-offset,col) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (row - ++offset >= 0 && getSpace(row-offset, col) > 0){
                if (getSpace(row-offset,col) == currPlayer(player)){
                    directions.add(Direction.UP);
                    break;
                }
            }
        }
        offset = 1;
//      Check down
        if (row+offset <= 9 && getSpace(row+offset,col) == otherPlayer(player)){
//            Keep going while not empty or out of bounce
            while (row + ++offset <= 9 && getSpace(row+offset,col) > 0){
                if (getSpace(row+offset,col) == currPlayer(player)){
                    directions.add(Direction.DOWN);
                    break;
                }
            }
        }
        return directions;
    }

    private ArrayList<Direction> checkHorizontal(int row, int col, Player player) {
        ArrayList<Direction> directions = new ArrayList<Direction>();

        int offset = 1;
//      Check left
        if (col-offset >= 0 && getSpace(row,col-offset) == otherPlayer(player)){
            // Keep going while not empty or out of bounce
            while (col - ++offset >= 0 && getSpace(row, col-offset) > 0){
                if (getSpace(row,col - offset) == currPlayer(player)){
                    directions.add(Direction.LEFT);
                    break;
                }
            }
        }
        offset = 1;
//      Check right
        if (col+offset <= 19 && getSpace(row,col+offset) == otherPlayer(player)){
//            Keep going while not empty or out of bounce
            while (col + ++offset <= 19 && getSpace(row,col+ offset) > 0){
                if (getSpace(row,col + offset) == currPlayer(player)){
                    directions.add(Direction.RIGHT);
                    break;
                }
            }
        }
        return directions;
    }

    public ArrayList<BoardMovePair> getPossibleMoves(Player player){
        ArrayList<BoardMovePair> nextMoves = new ArrayList<BoardMovePair>();

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                Board newBoard = playMove(i, j, player);
                if (newBoard != null){
                    nextMoves.add(new BoardMovePair(newBoard, i, j));
                    // System.out.print(newBoard);
                    // System.out.println(nextMoves.get(nextMoves.size()-1).row + ", " + nextMoves.get(nextMoves.size()-1).col + "\n");

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

    public String toString(){
        StringBuffer sb = new StringBuffer();
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 20; col++){
                if(board[row][col] == -1){
                    sb.append("  ");
                }
                else{
                    sb.append(board[row][col] + " ");    
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}