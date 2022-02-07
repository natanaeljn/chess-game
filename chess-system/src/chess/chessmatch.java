package chess;

import boardgame.board;

public class chessmatch {
	
	private board board;
	
	public  chessmatch() {
		board = new board(8,8);
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
	

}
