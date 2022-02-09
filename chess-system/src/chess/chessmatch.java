package chess;

import boardgame.board;
import boardgame.piece;
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
	public chesspiece performChessMove(chessposition  sourceposition , chessposition targetposition) {
		position source = sourceposition.toPosition();
		position target = targetposition.toPosition();
		validateSourcePosition(source);
		piece capturedPiece = makeMove(source, target);
		return(chesspiece)capturedPiece;
	}
	private piece makeMove(position source , position target) {
		piece p = board.removePiece(source);
		piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	
	
	
	
	
	
	
	private void validateSourcePosition(position position) {
		if(!board.thereIsAPiece(position)) {
			throw new chessexception("there is no piece on source position");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new chessexception("dont exist any possible moviments");
		}
		
	}
	
	
	
	
	private void placeNewPiece(char column , int row , chesspiece piece) {
		board.placePiece(piece, new chessposition(column , row).toPosition());
	}
	
	
	
	
	
	

	private void initialSetup() {
		
	}

		
		
	}
	


