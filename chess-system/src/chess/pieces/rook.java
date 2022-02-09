package chess.pieces;

import chess.chesspiece;

public class rook extends chesspiece {

	public rook(boardgame.board board, chess.color color) {
		super(board, color);
		
	}
	@Override
	public String toString() {
		return "T";
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean [getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}
	

}
