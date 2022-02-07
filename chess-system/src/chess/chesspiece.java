package chess;

import boardgame.piece;

public class chesspiece  extends piece{
	
	public color color ;
	private int  moveCount;
	public chesspiece(boardgame.board board, chess.color color, int moveCount) {
		super(board);
		this.color = color;
		this.moveCount = moveCount;
	}
	public color getColor() {
		return color;
	}
	
	

}
