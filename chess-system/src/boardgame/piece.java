package boardgame;

public abstract class piece {
	protected position position;
	private board board;

	public piece(boardgame.board board) {

		this.board = board;
	}

	protected board getBoard() {
		return board;
	}

	public void setBoard(board board) {
		this.board = board;
	}

	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}

		}
		return false;
	}

}
