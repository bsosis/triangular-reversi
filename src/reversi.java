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

    
    private List<List<Integer>> board;

    private Board() {
        int width = 8;
//      fill out first 4 rows
        for (int row = 0; row < 4; row++){
            board.add(new ArrayList<>());
            for (int col = 0; col < width;col++){
                board.get(row).add(0);
            }
            width += 2;
        }
//        Fill out bottom 4 rows
        width = 14;
        for (int row = 0; row < 4; row++){
            board.add(new ArrayList<>());
            for (int col = 0; col < width;col++){
                board.get(row).add(0);
            }
            width -= 2;
        }
        board.get(3).get(6) = 2;

    }

    private Board(Board other){
        this.board  = other.board;
    }

    public Board playMove(int row, int col, Player player){
        Board updatedBoard = new Board();
        if (!moveIsLegal(row,col,player)){
            return null;
        }
        return true;
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