package fullOutput;

public class Messages {
	public static void startOfGame(int days, int minZombies, int maxZombies, int food){
		print(String.format("You have %s days to survive.\nEach night between %d "
				+ "and %d zombies will attack\nand you need to fight them off.\nYou have "
				+ "enough food for %d people to survive for %d days.\nGod's speed! Survive!", days, minZombies,maxZombies, (food/10), days));
	}
	//(STARTING_DAYS-day), totalZombiesKilled, (NUM_PEOPLE-people.getPersonCount()
	public static void summary(int numSimulations, int daysSurvived, 
			int zombiesKilled, int peopleDead, int surviveCount, 
			int allDeadCount, int starvationCount, int lowestDay){
		print("");
		print("Summary");
		divider();
		print(String.format("%d simulations have run.", numSimulations));
		print(String.format("You have survived %d days.\n"
				+ "You have killed %d zombies.\n"
				+ "%d people have died.\n"
				+ "%d simulations were won.\n"
				+ "%d simulations were lost.\n"
				+ "%d simulations lost to starvation.\n"
				+ "%d was the lowest day reached.", daysSurvived, zombiesKilled, peopleDead, surviveCount, allDeadCount, starvationCount, lowestDay));
		divider();
	}
	
	public static void dayMessage(int daysLeft){
		print(String.format("You have %s days left until salvation!", daysLeft));
	}
	
	public static void peopleCountMessage(int peopleLeft){
		print(String.format("There are %s people left.", peopleLeft));
	}
	  
	public static void outOfAmmoMessage(){
		print("You have run out of ammo!  Time to use a blunt object!");
	}
	
	public static void ammoCountMessage(int ammo){
		print(String.format("You have %d rounds of ammo.", ammo));
	}
	
	public static void allPeopleDeadMessage(){
		print("All hope is lost.  Everyone has died.");
	}
	
	public static void victoryMessage(){
		print("You have survived!");
	}
	
	public static void divider(){
		print("============================================");
	}
	
	public static void zombieCountMessage(int numZombies){
		print(String.format("Gird your loins! %d zombies are coming! ", numZombies));
	}
	
	public static void zombieApproachMessage(){
		print("A zombie approaches. Get ready!");
	}
	
	public static void gunFightMessage(){
		print("A zombie approches; you have the ammo so you use a gun!");
	}
	
	public static void hitZombieMessage(){
		print("You hit a zombie!");
	}
	
	public static void personDeathMessge(){
		print("OMG!!!! A person has died!!!!");
	}
	
	public static void zombieDeathMessage(){
		print("YES!! You have killed a zombie!");
	}
	
	private static void print(String message){
		System.out.println(message);
	}
}
