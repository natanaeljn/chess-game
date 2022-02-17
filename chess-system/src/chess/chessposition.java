package chess;

import boardgame.position;

public class chessposition {

	private char column;
	private int row;

	public chessposition(char column, int row) {
		if (column < 'a' || column > 'h' || row > 8) {
			throw new chessexception("error instanting chessposition . Valid values are from a1 to h8 ");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	protected position toPosition() {
		return new position(8 - row, column - 'a');
	}

	protected static chessposition fromPosition(position position) {
		return new chessposition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
}
