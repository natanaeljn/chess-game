package boardgame;

public class piece  extends position{
	protected position position;
	private board board;
	public piece(boardgame.board board) {
		super();
		this.board = board;
	}
	protected board getBoard() {
		return board;
	}
	public void setBoard(board board) {
		this.board = board;
	}
	

}
