package chess;

import boardgame.board;
import boardgame.position;
import chess.pieces.king;
import chess.pieces.rook;

public class chessmatch {
	
	private board board;
	
	public  chessmatch() {
		board = new board(8,8);
		initialSetup();
	}
	public chesspiece[][]getPieces(){
		chesspiece [][] mat = new chesspiece[board.getRows()][board.getColumns()];
		for(int i = 0 ;i <board.getRows(); i++) {
		for	(int j = 0 ;j<board.getColumns() ; j++ ){
				mat[i][j] = (chesspiece)board.piece(i, j);
			}
		}
		
		return mat;
	}
	private void initialSetup() {
		board.placePiece(new rook(board,color.WHITE), new position(2,1));
		board.placePiece(new king(board,color.BLACK), new position(0,4));
		board.placePiece(new king(board,color.WHITE), new position(7,4));
		
		
	}
	

}
