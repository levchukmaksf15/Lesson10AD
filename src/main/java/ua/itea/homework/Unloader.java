package ua.itea.homework;

public class Unloader implements Runnable {

	private static final int MAX_WEIGHT_FOR_ONE_UNLOAD = 3;

	private Cart cart;

	public Unloader(Cart cart) {
		this.cart = cart;

		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			if (!cart.unloading(MAX_WEIGHT_FOR_ONE_UNLOAD)) {
				break;
			}
		}
	}
}
