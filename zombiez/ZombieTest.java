package zombiez;

public class ZombieTest {

	public static void main(String[] args) {
		final int STARTING_FOOD = 80, NUM_DAYS = 10;
		Apocalypse apoc = new Apocalypse(STARTING_FOOD, NUM_DAYS);
		for (int i = 0; i < 100000; i++) {
			apoc.runSimulation();
			apoc.resetSimulation();
		}

	}

}
