package chess.pieces;

import chess.chesspiece;

public class king  extends chesspiece{

	
	public king(boardgame.board board, chess.color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean [getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}
	

}
