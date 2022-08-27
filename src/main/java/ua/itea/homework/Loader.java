package ua.itea.homework;

public class Loader implements Runnable {

	private static final int MAX_WEIGHT_FOR_ONE_LOAD = 2;

	private Cart cart;
	private Career career;

	public Loader(Cart cart, Career career) {
		this.cart = cart;
		this.career = career;

		new Thread(this).start();
	}

	@Override
	public void run() {
		while (!career.isDepartPointEmpty()) {
			cart.loading(MAX_WEIGHT_FOR_ONE_LOAD);
		}
	}
}
