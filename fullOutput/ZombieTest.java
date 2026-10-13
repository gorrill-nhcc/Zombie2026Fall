package fullOutput;

public class ZombieTest {

	public static void main(String[] args) {
		final int FOOD = 80, DAYZ = 10;
		Apocalypse apoc = new Apocalypse(FOOD, DAYZ);
		apoc.runSimulation();
	}
}
