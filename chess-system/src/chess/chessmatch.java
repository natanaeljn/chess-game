package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.board;
import boardgame.piece;
import boardgame.position;

public class chessmatch {
	private int turn;
	private board board;
	private color currentPlayer;
	
	private List<piece>piecesOnTheBoard = new ArrayList<>();
	private List<piece>capturedPieces = new ArrayList<>();
	
	
	public  chessmatch() {
		board = new board (8,8);
		turn = 1 ; 
		currentPlayer = color.WHITE;
		
		initialSetup();
	}
	public int getTurn() {
		return turn;
	}
	public color getCurrentPlayer() {
		return currentPlayer;
				
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
	public boolean [][]possibleMoves(chessposition sourceposition){
		position position =sourceposition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
		
		
	}
	public chesspiece performChessMove(chessposition  sourceposition , chessposition targetposition) {
		position source = sourceposition.toPosition();
		position target = targetposition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source,target);
		piece capturedPiece = makeMove(source, target);
		nextTurn();
		return(chesspiece)capturedPiece;
	}
	private piece makeMove(position source , position target) {
		piece p = board.removePiece(source);
		piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if(capturedPiece!= null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	
	
	
	
	
	
	
	private void validateSourcePosition(position position) {
		if(!board.thereIsAPiece(position)) {
			throw new chessexception("there is no piece on source position");
		}
		if(currentPlayer!=((chesspiece)board.piece(position)).getColor()) {
			throw new chessexception("the chosen piece is not yours");
		}
		
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new chessexception("dont exist any possible moviments");
		}
		
	}
	private void validateTargetPosition(position source,position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new chessexception("the chosen piece cant move to target position");
			
		}
		
		
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer= (currentPlayer  == color.WHITE)?color.BLACK:color.WHITE;
		
	}
	
	
	private void placeNewPiece(char column , int row , chesspiece piece) {
		board.placePiece(piece, new chessposition(column , row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	
	
	
	
	

	private void initialSetup() {
		
	}

		
		
	}
	


