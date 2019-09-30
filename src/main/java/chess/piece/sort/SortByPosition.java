package chess.piece.sort;

import java.util.Comparator;

import chess.piece.Piece;

// Sorts pieces by position (top left of board to bottom right
public class SortByPosition implements Comparator<Piece> {

	@Override
	public int compare(Piece o1, Piece o2) {
		if (o1.getPosition().getY() > o2.getPosition().getY()) {
			return -1;
		} else if (o1.getPosition().getY() < o2.getPosition().getY()) {
			return 1;
		} else {
			if (o1.getPosition().getX() < o2.getPosition().getX()) {
				return -1;
			} else if (o1.getPosition().getX() > o2.getPosition().getX()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}