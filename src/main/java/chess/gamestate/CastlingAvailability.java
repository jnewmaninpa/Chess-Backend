package chess.gamestate;

public class CastlingAvailability {

	public static class Builder {
		private boolean whiteKingside;
		private boolean whiteQueenside;
		private boolean blackKingside;
		private boolean blackQueenside;

		public Builder withWhiteKingside(Boolean whiteKingside) {
			this.whiteKingside = whiteKingside;
			return this;
		}

		public Builder withWhiteQueenside(Boolean whiteQueenside) {
			this.whiteQueenside = whiteQueenside;
			return this;
		}

		public Builder withBlackKingside(Boolean blackKingside) {
			this.blackKingside = blackKingside;
			return this;
		}

		public Builder withBlackQueenside(Boolean blackQueenside) {
			this.blackQueenside = blackQueenside;
			return this;
		}

		public CastlingAvailability build() {
			CastlingAvailability availability = new CastlingAvailability();
			availability.whiteKingside = this.whiteKingside;
			availability.whiteQueenside = this.whiteQueenside;
			availability.blackKingside = this.blackKingside;
			availability.blackQueenside = this.blackQueenside;
			return availability;
		}
	}

	private boolean whiteKingside;
	private boolean whiteQueenside;
	private boolean blackKingside;
	private boolean blackQueenside;

	private CastlingAvailability() {

	}

	public boolean getWhiteKingside() {
		return whiteKingside;
	}

	public boolean getWhiteQueenside() {
		return whiteQueenside;
	}

	public boolean getBlackKingside() {
		return blackKingside;
	}

	public boolean getBlackQueenside() {
		return blackQueenside;
	}

	public void setWhiteKingside(boolean o) {
		this.whiteKingside = o;
	}

	public void setWhiteQueenside(boolean o) {
		this.whiteQueenside = o;
	}

	public void setBlackKingside(boolean o) {
		this.blackKingside = o;
	}

	public void setBlackQueenside(boolean o) {
		this.blackQueenside = o;
	}

}
