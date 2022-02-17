    package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.board;
import boardgame.piece;
import boardgame.position;
import chess.pieces.king;
import chess.pieces.pawn;

public class chessmatch {
	private int turn;
	private board board;
	private color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private chesspiece enPassantVulnerable;
	
	private List<piece>piecesOnTheBoard = new ArrayList<>();
	private List<piece>capturedPieces = new ArrayList<>();
	
	
	public  chessmatch() {
		board = new board (8,8);
		turn = 1 ;  
		currentPlayer = color.WHITE;
		check = false;
		initialSetup();
	}
	public int getTurn() {
		return turn;
	}
	public color getCurrentPlayer() {
		return currentPlayer;
				
	}
	public  boolean getCheck () {
		return check;
	}
	public boolean getcheckMate() {
		return checkMate;
	}
	public chesspiece getenPassantVulnerable() {
		return enPassantVulnerable;
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
		
		if(testCheck(currentPlayer)) {
			undoMove(source,target,capturedPiece);
			throw new chessexception("you cant put yourself in check");
		}
		
		chesspiece movedPiece = (chesspiece)board.piece(target);
		
		
		
		
		check = (testCheck(opponent(currentPlayer)))? true : false ;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			
		
		nextTurn();
		
	
		}
		if(movedPiece instanceof pawn && (target.getRow()==source.getRow()-2||target.getRow()==source.getRow()+2)) {;
		   enPassantVulnerable =movedPiece;
	    }
	    else {
		enPassantVulnerable = null;
	
	    }
	
		
		
		return (chesspiece)capturedPiece;
	}
	private piece makeMove(position source , position target) {
		chesspiece p = (chesspiece)board.removePiece(source);
		p.increaseMoveCount();
		piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if(capturedPiece!= null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		//special move castling kingside
		if(p instanceof king &&target.getColumn()==source.getColumn()+2) {
			position sourceT = new position (source.getRow(),source.getColumn()+3);
			position targetT=new position (source.getRow(),source.getColumn()+1);
			chesspiece rook =(chesspiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
			
		}
		//special move castling queenside rook
				if(p instanceof king &&target.getColumn()==source.getColumn()-2) {
					position sourceT = new position (source.getRow(),source.getColumn()-4);
					position targetT=new position (source.getRow(),source.getColumn()-1);
					chesspiece rook =(chesspiece)board.removePiece(sourceT);
					board.placePiece(rook, targetT);
					rook.increaseMoveCount();
					
				}
		
		return capturedPiece;
	}
	
	private void undoMove(position source, position target , piece capturedPiece) {
		chesspiece p = (chesspiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		capturedPieces.remove(capturedPiece);
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
			
		}
		//special move castling kingside
				if(p instanceof king &&target.getColumn()==source.getColumn()+2) {
					position sourceT = new position (source.getRow(),source.getColumn()+3);
					position targetT=new position (source.getRow(),source.getColumn()+1);
					chesspiece rook =(chesspiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
					
				}
				//special move castling queenside rook
						if(p instanceof king &&target.getColumn()==source.getColumn()-2) {
							position sourceT = new position (source.getRow(),source.getColumn()-4);
							position targetT=new position (source.getRow(),source.getColumn()-1);
							chesspiece rook =(chesspiece)board.removePiece(targetT);
							board.placePiece(rook, sourceT);
							rook.decreaseMoveCount();
							
						}
		
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
	private color opponent(color color) {
		return(color==color.WHITE)? color.BLACK:color.WHITE;
	}
	private chesspiece king(color color) {
		List<piece> list =piecesOnTheBoard.stream().filter(x ->((chesspiece)x).getColor()==color).collect(Collectors.toList());
		for(piece p:list) {
			if(p instanceof king) {
				return (chesspiece)p;
				
			}
		}
		throw new IllegalStateException("there is no "+color+" king on the board" );
	}
	
	private boolean testCheck(color color) {
		position kingPosition = king(color).getChessPosition().toPosition();
		List<piece> opponentPieces =piecesOnTheBoard.stream().filter(x ->((chesspiece)x).getColor()==opponent(color)).collect(Collectors.toList());
		for(piece p :opponentPieces) {
			boolean [][]mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
			
		}
		return false;
		
		
		
		
		
	}
	private boolean testCheckMate(color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<piece> list =piecesOnTheBoard.stream().filter(x ->((chesspiece)x).getColor()==color).collect(Collectors.toList());
		for(piece p:list) {
			boolean[][] mat = p.possibleMoves();
			for(int i =0 ; i<board.getRows() ; i++) {
				for(int j = 0 ; j<board.getColumns(); j++) {
				    if(mat[i][j]) {
					position source = ((chesspiece)p).getChessPosition().toPosition();
					position target  = new position (i , j);
					piece capturedPiece = makeMove(source,target);
					boolean testCheck = testCheck(color);
					undoMove(source, target , capturedPiece);
					if(!testCheck) {
						return false;
					}
					
				}
				
				
				
				
			}
			
			
		}
		}
		return true;
		
		
		
		
	}
	
	private void placeNewPiece(char column , int row , chesspiece piece) {
		board.placePiece(piece, new chessposition(column , row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	
	
	
	
	

	private void initialSetup() {
		
	}

		
		
	}
	


