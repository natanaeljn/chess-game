package chess;

import boardgame.piece;
import boardgame.position;

public abstract class chesspiece extends piece {

	public color color;
	private int moveCount;

	public chesspiece(boardgame.board board, chess.color color) {
		super(board);
		this.color = color;

	}

	public color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		moveCount++;
	}

	public void decreaseMoveCount() {
		moveCount--;
	}

	public chessposition getChessPosition() {
		return chessposition.fromPosition(position);
	}

	protected boolean isThereOpponentPiece(position position) {
		chesspiece p = (chesspiece) getBoard().piece(position);
		return p != null && p.getColor() != color;

	}

}
