package ua.itea.homework;

public class Transporter implements Runnable {

	private static final int TIME_TO_TRANSPORT = 5;

	private Cart cart;

	public Transporter(Cart cart) {
		this.cart = cart;

		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			if (!cart.transporting(TIME_TO_TRANSPORT)) {
				break;
			}
		}
	}
}
