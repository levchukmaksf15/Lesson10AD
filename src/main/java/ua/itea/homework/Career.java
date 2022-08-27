package ua.itea.homework;

public class Career {

	private static final int AMOUNT_OF_SAND = 100;

	private int amountOfSandAtDepartPoint = AMOUNT_OF_SAND;
	private int amountOfSandAtArrivePoint = 0;
	private boolean isDepartPointEmpty = false;

	public boolean isDepartPointEmpty() {
		return isDepartPointEmpty;
	}

	public int getAmountOfSandAtDepartPoint() {
		return amountOfSandAtDepartPoint;
	}

	public int getAmountOfSandAtArrivePoint() {
		return amountOfSandAtArrivePoint;
	}

	public int takeSomeSand(int someSand) {
		if (isDepartPointEmpty) {
			return 0;
		}
		if (amountOfSandAtDepartPoint <= someSand) {
			int returnSand = amountOfSandAtDepartPoint;
			amountOfSandAtDepartPoint = 0;
			isDepartPointEmpty = true;
			return returnSand;

		}
		amountOfSandAtDepartPoint -= someSand;
		return someSand;
	}

	public void putSomeSand(int someSand) {
		if (someSand > 0) {
			amountOfSandAtArrivePoint += someSand;
		}
	}
}
