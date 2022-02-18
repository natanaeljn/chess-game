    package chess;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.board;
import boardgame.piece;
import boardgame.position;
import chess.pieces.bishop;
import chess.pieces.king;
import chess.pieces.knight;
import chess.pieces.pawn;
import chess.pieces.queen;
import chess.pieces.rook;

public class chessmatch {
	private int turn;
	private board board;
	private color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private chesspiece enPassantVulnerable;
	private chesspiece promoted;
	
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
	public chesspiece getPromoted() {
		return promoted;
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
		
		//special move promotion
		promoted= null;
		if(movedPiece instanceof pawn) {
		if((movedPiece.getColor()==color.WHITE&&target.getRow()==0)||(movedPiece.getColor()==color.BLACK&&target.getRow()==7)) {
			promoted =(chesspiece)board.piece(target);
			promoted = replacePromotedPiece("Q");
			
		}
		}
		
		
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
	public chesspiece replacePromotedPiece(String type) {
		if(promoted==null) {
			throw new IllegalStateException("there is no piece  to be promoted");
		}
		if(!type.equals("B")&&!type.equals("N")&&!type.equals("R")&&!type.equals("Q")) {
			return promoted;
		}
		position pos = promoted.getChessPosition().toPosition();
		piece p= board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		chesspiece newPiece = newPiece(type,promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		return newPiece;
		
	}
	private chesspiece newPiece(String type ,color color) {
		if(type.equals("B")) return new bishop(board,color);
		if(type.equals("N")) return new knight(board,color);
		if(type.equals("R")) return new queen(board,color);
		 return new rook(board,color);
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
				//special move enpassant
				if(p instanceof pawn) {
					if(source.getColumn()!= target.getColumn()&&capturedPiece== null) {
						position pawnPosition;
						if(p.getColor()==color.WHITE) {
							pawnPosition =new position(target.getRow()+1,target.getColumn());
						}else {
							pawnPosition =new position(target.getRow()-1,target.getColumn());
						}
						capturedPiece = board.removePiece(pawnPosition);
						capturedPieces.add(capturedPiece);
						piecesOnTheBoard.remove(capturedPiece);
					}
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
						//special move enpassant
						if(p instanceof pawn) {
							if(source.getColumn()!= target.getColumn()&&capturedPiece== enPassantVulnerable) {
								chesspiece pawn =(chesspiece)board.removePiece(target);
								
								position pawnPosition;
								if(p.getColor()==color.WHITE) {
									pawnPosition =new position(3,target.getColumn());
								}else {
									pawnPosition =new position(4,target.getColumn());
								}
								board.placePiece(pawn, pawnPosition);
								
							}
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
		 placeNewPiece('a', 1, new rook(board, color.WHITE));
	        placeNewPiece('b', 1, new knight(board, color.WHITE));
	        placeNewPiece('c', 1, new bishop(board, color.WHITE));
	        placeNewPiece('d', 1, new queen(board, color.WHITE));
	        placeNewPiece('e', 1, new king(board, color.WHITE, this));
	        placeNewPiece('f', 1, new bishop(board, color.WHITE));
	        placeNewPiece('g', 1, new knight(board, color.WHITE));
	        placeNewPiece('h', 1, new rook(board, color.WHITE));
	        placeNewPiece('a', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('b', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('c', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('d', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('e', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('f', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('g', 2, new pawn(board, color.WHITE, this));
	        placeNewPiece('h', 2, new pawn(board, color.WHITE, this));

	        placeNewPiece('a', 1, new rook(board, color.BLACK));
	        placeNewPiece('b', 1, new knight(board, color.BLACK));
	        placeNewPiece('c', 1, new bishop(board, color.BLACK));
	        placeNewPiece('d', 1, new queen(board, color.BLACK));
	        placeNewPiece('e', 1, new king(board, color.BLACK, this));
	        placeNewPiece('f', 1, new bishop(board, color.BLACK));
	        placeNewPiece('g', 1, new knight(board, color.BLACK));
	        placeNewPiece('h', 1, new rook(board, color.BLACK));
	        placeNewPiece('a', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('b', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('c', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('d', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('e', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('f', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('g', 2, new pawn(board, color.BLACK, this));
	        placeNewPiece('h', 2, new pawn(board, color.BLACK, this));
		
	}

		
		
	}
	


