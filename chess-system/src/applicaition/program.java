package applicaition;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.chessexception;
import chess.chessmatch;
import chess.chesspiece;
import chess.chessposition;

public class program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		chessmatch chessmatch = new chessmatch();
		while(true) {
			try {
			UI.clearScreen();
			UI.printBoard(chessmatch.getPieces());
			System.out.println();
			System.out.print("source:");
			chessposition source = UI.readChessPosition(sc);
			System.out.println();
			System.out.print("target");
			chessposition target = UI.readChessPosition(sc);
			
			chesspiece capturedPiece = chessmatch.performChessMove(source, target);
			}
			catch(chessexception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		
		
	}

}
