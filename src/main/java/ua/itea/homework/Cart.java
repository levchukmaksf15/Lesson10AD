package ua.itea.homework;

import java.util.concurrent.TimeUnit;

public class Cart {

	private static final int MAX_WEIGHT = 6;
	
	private static final int CART_IS_LOADERS = 0;
	private static final int CART_IS_TRANSPORTERS = 1;
	private static final int CART_IS_UNLOADERS = 2;

	private boolean isCartEmpty = true;
	private boolean isCartFull = false;
	private int weightOnTheCartNow = 0;
	private int whosIsTheCart = CART_IS_LOADERS;

	private Career career;

	public Cart(Career career) {
		this.career = career;
	}

	private boolean loadCart(int someLoad) {
		if (!isCartFull) {
			if (weightOnTheCartNow + someLoad >= MAX_WEIGHT) {
				int posibleLoad = MAX_WEIGHT - weightOnTheCartNow;
				weightOnTheCartNow = MAX_WEIGHT;

				if (career.takeSomeSand(posibleLoad) == 0) {
					return false;
				}
				System.out.printf("Load %d kg of sand on the cart\n", posibleLoad);
				System.out.println("The cart is full.");
				isCartFull = true;
			} else {
				weightOnTheCartNow += someLoad;
				career.takeSomeSand(someLoad);
				System.out.printf("Load %d kg of sand on the cart\n", someLoad);
			}
		}

		isCartEmpty = false;
		return true;
	}

	private void unloadCart(int someLoad) {
		if (!isCartEmpty) {
			if (weightOnTheCartNow - someLoad <= 0) {
				int posibleLoad = weightOnTheCartNow;
				weightOnTheCartNow = 0;
				career.putSomeSand(posibleLoad);
				System.out.printf("Unload %d kg from the cart.\n", posibleLoad);
				isCartEmpty = true;
				System.out.println("The cart is empty.");
			} else {
				weightOnTheCartNow -= someLoad;
				career.putSomeSand(someLoad);
				System.out.printf("Unload %d kg from the cart.\n", someLoad);
			}
		}

		isCartFull = false;
	}

	public synchronized boolean loading(int weightForOneLoad) {
		if (whosIsTheCart == CART_IS_LOADERS) {
			System.out.println("\nLoading...");
			while (!isCartFull()) {
				if (!loadCart(weightForOneLoad) || career.isDepartPointEmpty()) {
					System.out.printf("%d kg of sand are at the depart point\n", career.getAmountOfSandAtDepartPoint());
					whosIsTheCart = CART_IS_TRANSPORTERS;
					notifyAll();
					return false;
				}

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("%d kg of sand are at the depart point\n", career.getAmountOfSandAtDepartPoint());
			whosIsTheCart = CART_IS_TRANSPORTERS;
			notifyAll();
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public synchronized boolean transporting(int timeToTransporting) {
		if (whosIsTheCart == CART_IS_TRANSPORTERS) {
			System.out.println("\nTransporting...");
			try {
				TimeUnit.SECONDS.sleep(timeToTransporting);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isCartEmpty()) {
				whosIsTheCart = CART_IS_LOADERS;
			} else {
				whosIsTheCart = CART_IS_UNLOADERS;
			}

			notifyAll();
			if (career.isDepartPointEmpty()) {
				return false;
			}
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	public synchronized boolean unloading(int weightForOneLoad) {
		if (whosIsTheCart == CART_IS_UNLOADERS) {

			System.out.println("\nUnloading...");
			while (!isCartEmpty()) {
				unloadCart(weightForOneLoad);

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.printf("%d kg of sand are at the arrive point.\n", career.getAmountOfSandAtArrivePoint());
			whosIsTheCart = CART_IS_TRANSPORTERS;
			notifyAll();
			if (career.isDepartPointEmpty()) {
				return false;
			}
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	public boolean isCartEmpty() {
		return isCartEmpty;
	}

	public boolean isCartFull() {
		return isCartFull;
	}
}
