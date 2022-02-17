package chess.pieces;

import boardgame.position;
import chess.chessmatch;
import chess.chesspiece;

public class pawn extends chesspiece {

	private chessmatch chessmatch;

	public pawn(boardgame.board board, chess.color color, chessmatch chessmatch) {
		super(board, color);
		this.chessmatch = chessmatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];

		position p = new position(0, 0);
		if (getColor() == color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 2, position.getColumn());
			position p2 = new position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// enpassant
			if (position.getRow() == 3) {
				position left = new position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessmatch.getenPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;

				}
				position right = new position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessmatch.getenPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;

				}

			}

		} else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 2, position.getColumn());
			position p2 = new position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// enpassant black
			if (position.getRow() == 4) {
				position left = new position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessmatch.getenPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;

				}
				position right = new position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessmatch.getenPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;

				}

			}

		}

		return mat;
	}

}
