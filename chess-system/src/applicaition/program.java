package applicaition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<chesspiece> captured = new ArrayList<>();

		while (!chessmatch.getcheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessmatch, captured);
				System.out.println();
				System.out.print("source:");
				chessposition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessmatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessmatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("target");
				chessposition target = UI.readChessPosition(sc);

				chesspiece capturedPiece = chessmatch.performChessMove(source, target);
				if (capturedPiece != null) {
					captured.add(capturedPiece);

				}
				if (chessmatch.getPromoted() != null) {
					System.out.print("enter piece for promotion B/N/R/Q:");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Invalid value ,enter piece for promotion B/N/R/Q:");
						type = sc.nextLine().toUpperCase();
					}
					chessmatch.replacePromotedPiece(type);
				}

			} catch (chessexception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}
		}
		UI.clearScreen();
		UI.printMatch(chessmatch, captured);

	}

}
