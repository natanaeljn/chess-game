package boardgame;

public class piece  {
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
	

}
