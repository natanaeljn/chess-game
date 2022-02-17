package chess.pieces;

import boardgame.position;
import chess.chessmatch;
import chess.chesspiece;

public class king extends chesspiece {

	private chessmatch chessmatch;

	public king(boardgame.board board, chess.color color, chessmatch chessmatch) {
		super(board, color);
		this.chessmatch = chessmatch;

	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(position position) {
		chesspiece p = (chesspiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRookCastling(position position) {
		chesspiece p = (chesspiece) getBoard().piece(position);
		return p != null && p instanceof rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];

		position p = new position(0, 0);
		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// #specialmove castling
		if (getMoveCount() == 0 && !chessmatch.getCheck()) {
			// #specialmove castling kingside rook
			position posT1 = new position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posT1)) {
				position p1 = new position(position.getRow(), position.getColumn() + 1);
				position p2 = new position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// #specialmove castling queenside rook
			position posT2 = new position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				position p1 = new position(position.getRow(), position.getColumn() - 1);
				position p2 = new position(position.getRow(), position.getColumn() - 2);
				position p3 = new position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}

		return mat;
	}

}
