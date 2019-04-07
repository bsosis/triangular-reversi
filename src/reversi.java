import java.util.ArrayList;
import java.util.List;

public class reversi {

}

class Board {

    private enum Player {ONE,TWO}
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

}