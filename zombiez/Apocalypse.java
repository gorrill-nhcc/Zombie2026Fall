package zombiez;

import java.util.Random;

public class Apocalypse {
	protected static int NUM_SIMULATIONS=0;
	private final int STARTING_FOOD, STARTING_DAYS;
	private Person people; 
	private int food, startingFood, day, zombies, lowestDay;
	private int totalZombiesKilled=0, totalPeopleKilled=0,surviveCount, allDeadCount, starvationCount;

	public Apocalypse(int food, int days){
		people=new Person(Constants.AMMO, Constants.NUM_PEOPLE);	
		this.food=food;
		STARTING_FOOD=food;
		this.day=days;
		STARTING_DAYS=days;
		System.out.println(STARTING_DAYS);
		lowestDay=STARTING_DAYS+1;
		Messages.startOfGame(this.day, Constants.MIN_ZOMBIES, Constants.MAX_ZOMBIES, this.food);
	}
	/*
	 * Every day generate zombies, fight until zombies are dead, eat, 
	 * decrement day, check end conditions
	 * */

	public void findFood(){
		if((double)food/STARTING_FOOD*100<33){
			if(randomNumberRange(1, 5)==3){
				food+=randomNumberRange(15, 30);
			}
		}
	}
	
	public void runSimulation(){
		NUM_SIMULATIONS++;
		while(day>0&&people.getPersonCount()>0&&food>0){			
			findFood();
			//generate zombies
			zombies=generateZombies(Constants.MIN_ZOMBIES,Constants.MAX_ZOMBIES);
			//fight until zombies are gone				
			if(fightZombies()){	
			//eatfood			
			eatFood(people.getPersonCount());
			//decrement day
			day--;
			checkLowestDay(day);
			}			
		}//day loop
		if(people.getPersonCount()>0&&food>0){
			surviveCount++;
		}
		else{
			if(food<1){
				totalPeopleKilled+=people.getPersonCount();
				starvationCount++;
			}
			allDeadCount++;
		}
		Messages.summary(NUM_SIMULATIONS, (STARTING_DAYS-day), totalZombiesKilled, totalPeopleKilled, surviveCount, allDeadCount, starvationCount, lowestDay);
	}	

	/**
	 * @return return true if all people survive a fight else false
	 * */
	public boolean fightZombies(){
		while(zombies>0&&people.getPersonCount()>0){
			if(people.getAmmo()>0){				
				if(gunFight(Constants.GUN_HITS)){//if zombie kill
					zombies--;
				}
				else{
					people.killPerson();
					totalPeopleKilled++;
				}
			}//gunfight
			else{
				if(bluntFight(Constants.BLUNT_HITS)){//if zombie kill
					zombies--;
				}
				else{
					people.killPerson();
					totalPeopleKilled++;
				}

			}//bluntfight
		}
		return people.getPersonCount()>0;
	}

	/**
	 * @Param int hits is the number of hits that a zombie can take before death
	 * 
	 * */
	private boolean gunFight(int hits){
		int zombieHits=0;
		final int MAX_ZOMBIE_HITS=hits;		
		boolean ZOMBIE_KILL=true;
		while(zombieHits<MAX_ZOMBIE_HITS&&people.getAmmo()>1){			
			people.useAmmo();
			if(randomNumberRange(Constants.FIGHT_MIN_VALUE, Constants.GUN_PROBABILITY)==1){//if hit				
				if(randomNumberRange(Constants.FIGHT_MIN_VALUE, Constants.GUN_CRITICAL)==1){
					totalZombiesKilled++;
					return ZOMBIE_KILL;
				}	
				else{
					zombieHits++;
				}
			}//if hit
			else if(randomNumberRange(Constants.FIGHT_MIN_VALUE, Constants.GUN_PERSON_DEATH)==1){
				return !ZOMBIE_KILL;
			}
		}//if there is ammo left kill zombie else move on to blunt fight
		if(people.getAmmo()>0){
			totalZombiesKilled++;
			return ZOMBIE_KILL;
			}
		else{
			return bluntFight(Constants.BLUNT_HITS-zombieHits);
			}
		
	}

	/**
	 * @Param int hits is the number of hits that a zombie can take before death
	 * 
	 * */
	private boolean bluntFight(int hits){		
		int zombieHits=0;
		final int MAX_ZOMBIE_HITS=hits;
		boolean ZOMBIE_KILL=true;
		
		while(zombieHits<MAX_ZOMBIE_HITS){
			if(randomNumberRange(Constants.FIGHT_MIN_VALUE, Constants.BLUNT_PROBABILITY)==1){//if hit				
				if(randomNumberRange(Constants.FIGHT_MIN_VALUE, Constants.BLUNT_CRITICAL)==1){
					totalZombiesKilled++;
					return ZOMBIE_KILL;
				}	
				else{
					zombieHits++;
				}
			}//if hit
			else if(randomNumberRange(Constants.FIGHT_MIN_VALUE, Constants.BLUNT_PERSON_DEATH)==1){
				return !ZOMBIE_KILL;
			}
		}
		totalZombiesKilled++;
		return ZOMBIE_KILL;
	}

	private static int randomNumberRange(int min, int max){
		Random rand=new Random();
		return (rand.nextInt((max - min) + 1) + min);
	}

	public int generateZombies(int min, int max){
		return randomNumberRange(min, max);		
	}

	public void eatFood(int numEaters){
		food-=numEaters;
	}
	
	public void resetSimulation(){
		people.setPersonCount(Constants.NUM_PEOPLE);
		food=STARTING_FOOD;
		people.setAmmo(Constants.AMMO);
		day=STARTING_DAYS;
	}
	
	public void checkLowestDay(int day){
		if(day<lowestDay)
			lowestDay=day;
	}
}
