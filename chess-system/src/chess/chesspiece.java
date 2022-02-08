package chess;

import boardgame.piece;

public class chesspiece  extends piece{
	
	public color color ;
	
	public chesspiece(boardgame.board board, chess.color color) {
		super(board);
		this.color = color;
		
	}
	public color getColor() {
		return color;
	}
	
	

}
