package chess.game.move;

import chess.gameState.GameState;
import chess.piece.Bishop;
import chess.piece.Knight;
import chess.piece.None;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

public class PawnUpgradeMove extends Move {

	protected final PieceType upgrade;

	public PawnUpgradeMove(Piece piece, Position position, PieceType upgrade) {
		super(piece, position);
		this.upgrade = upgrade;
	}

	public PieceType getUpgrade() {
		return upgrade;
	}

	@Override
	public void move(GameState gameState) {
		super.move(gameState);

		if ((piece.getColor() == PieceColor.WHITE && this.position.getY() == 7)
				|| (piece.getColor() == PieceColor.BLACK && this.position.getY() == 0)) {
//				System.out.println("Upgrading a pawn");
			switch (upgrade) {
			case BISHOP:
				gameState.getPieceList()
						.add(new Bishop(this.getPosition().getFile(), this.getPosition().getRank(), piece.getColor()));
				break;
			case KNIGHT:
				gameState.getPieceList()
						.add(new Knight(this.getPosition().getFile(), this.getPosition().getRank(), piece.getColor()));
				break;
			case QUEEN:
				gameState.getPieceList()
						.add(new Queen(this.getPosition().getFile(), this.getPosition().getRank(), piece.getColor()));
				break;
			case ROOK:
				gameState.getPieceList()
						.add(new Rook(this.getPosition().getFile(), this.getPosition().getRank(), piece.getColor()));
				break;
			default:
				gameState.getPieceList().add(new None(this.getPosition().getFile(), this.getPosition().getRank()));
			}

			gameState.getPieceList().remove(piece);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((upgrade == null) ? 0 : upgrade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PawnUpgradeMove other = (PawnUpgradeMove) obj;
		return upgrade == other.upgrade;
	}

}
