package ua.itea.homework;

public class App {
	public static void main(String[] args) {
		Career career = new Career();
		Cart cart = new Cart(career);

		new Loader(cart, career);
		new Transporter(cart);
		new Unloader(cart);

	}
}
