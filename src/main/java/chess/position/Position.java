package chess.position;

public class Position implements Comparable<Position> {

	public static void main(String... args) {
		Position square1 = new Position(0, 4);
		Position square2 = new Position('a', 5);
		Position square3 = new Position(32);

		System.out.println("TESTING Square.java\n");

		System.out.println("All of these should be a5");
		System.out.println("square1: x: " + square1.getX() + " y: " + square1.getY() + " file: " + square1.getFile()
				+ " rank: " + square1.getRank() + " number: " + square1.getNumber() + " toString: " + square1);
		System.out.println("square2: x: " + square2.getX() + " y: " + square2.getY() + " file: " + square2.getFile()
				+ " rank: " + square2.getRank() + " number: " + square2.getNumber() + " toString: " + square2);
		System.out.println("square3: x: " + square3.getX() + " y: " + square3.getY() + " file: " + square3.getFile()
				+ " rank: " + square3.getRank() + " number: " + square3.getNumber() + " toString: " + square3);

	}

	// (0,0) corresponds to a1, (1,0) corresponds to b1

	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(char file, int rank) {
		this.x = file - 'a';
		this.y = rank - 1;
	}

	public Position(int number) {
		this.x = number % 8;
		this.y = number / 8;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getFile() {
		return (char) (x + 'a');
	}

	public int getRank() {
		return y + 1;
	}

	public int getNumber() {
		return y * 8 + x;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		if (x != other.x) {
			return false;
		}
		return y == other.y;
	}

	@Override
	public String toString() {
		return "" + this.getFile() + this.getRank();
	}

	@Override
	public int compareTo(Position o) {

		if (this.getX() < o.getX()) {
			return -1;
		} else if (this.getX() > o.getX()) {
			return 1;
		} else {
			if (this.getY() < o.getY()) {
				return -1;
			} else if (this.getY() > o.getY()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
