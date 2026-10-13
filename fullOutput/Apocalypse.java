package fullOutput;

import java.util.Random;

public class Apocalypse {
	private final int AMMO=150, NUM_PEOPLE=25, MIN_ZOMBIES=10, MAX_ZOMBIES=50, STARTING_DAYS, STARTING_FOOD;
	private final int GUN_PROBABILITY=2, GUN_CRITICAL=2, BLUNT_PROBABILITY=3, BLUNT_CRITICAL=10;
	private final int GUN_PERSON_DEATH=8, BLUNT_PERSON_DEATH=1, FIGHT_MIN_VALUE=1;
	private final int GUN_HITS=2, BLUNT_HITS=3;
	private static int NUM_SIMULATIONS=0;
	private Person people; 
	private int food, startingFood, day, zombies, lowestDay;
	private int totalZombiesKilled=0, surviveCount, allDeadCount, starvationCount;

	public Apocalypse(int food, int days){
		people=new Person(AMMO, NUM_PEOPLE);	
		this.food=food;
		STARTING_FOOD=food;
		this.day=days;
		STARTING_DAYS=days;
		lowestDay=STARTING_DAYS+1;
		Messages.startOfGame(this.day, MIN_ZOMBIES, MAX_ZOMBIES, this.food);
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
		Messages.divider();
			Messages.dayMessage(day);
			Messages.peopleCountMessage(people.getPersonCount());
			Messages.divider();
			findFood();
			//generate zombies
			zombies=generateZombies(10,50);
			Messages.zombieCountMessage(zombies);
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
			Messages.victoryMessage();
		}
		else{
			if(food<1){
				starvationCount++;
			}
			allDeadCount++;
			Messages.allPeopleDeadMessage();
		}
		Messages.summary(NUM_SIMULATIONS, (STARTING_DAYS-day), totalZombiesKilled, (NUM_PEOPLE-people.getPersonCount()), surviveCount, allDeadCount, starvationCount, lowestDay);
	}	

	/**
	 * @return return true if all people survive a fight else false
	 * */
	public boolean fightZombies(){
		Messages.peopleCountMessage(people.getPersonCount());
		Messages.ammoCountMessage(people.getAmmo());
		while(zombies>0&&people.getPersonCount()>0){
			if(people.getAmmo()>0){				
				if(gunFight(GUN_HITS)){//if zombie kill
					zombies--;
				}
				else
					people.killPerson();
			}//gunfight
			else{
				if(bluntFight(BLUNT_HITS)){//if zombie kill
					zombies--;
				}
				else
					people.killPerson();

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
			if(randomNumberRange(FIGHT_MIN_VALUE, GUN_PROBABILITY)==1){//if hit				
				Messages.hitZombieMessage();
				if(randomNumberRange(FIGHT_MIN_VALUE, GUN_CRITICAL)==1){
					Messages.zombieDeathMessage();
					totalZombiesKilled++;
					return ZOMBIE_KILL;
				}	
				else{
					Messages.hitZombieMessage();
					zombieHits++;
				}
			}//if hit
			else if(randomNumberRange(FIGHT_MIN_VALUE, GUN_PERSON_DEATH)==1){
				Messages.personDeathMessge();
				return !ZOMBIE_KILL;
			}
		}//if there is ammo left kill zombie else move on to blunt fight
		if(people.getAmmo()>0){
			Messages.zombieDeathMessage();
			totalZombiesKilled++;
			return ZOMBIE_KILL;
			}
		else{
			Messages.outOfAmmoMessage();
			return bluntFight(BLUNT_HITS-zombieHits);
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
			if(randomNumberRange(FIGHT_MIN_VALUE, BLUNT_PROBABILITY)==1){//if hit				
				if(randomNumberRange(FIGHT_MIN_VALUE, BLUNT_CRITICAL)==1){
					Messages.zombieDeathMessage();
					totalZombiesKilled++;
					return ZOMBIE_KILL;
				}	
				else{
					Messages.hitZombieMessage();
					zombieHits++;
				}
			}//if hit
			else if(randomNumberRange(FIGHT_MIN_VALUE, BLUNT_PERSON_DEATH)==1){
				Messages.personDeathMessge();
				return !ZOMBIE_KILL;
			}
		}
		totalZombiesKilled++;
		return ZOMBIE_KILL;
	}

	private int randomNumberRange(int min, int max){
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
		people.setPersonCount(NUM_PEOPLE);
		food=STARTING_FOOD;
		people.setAmmo(AMMO);
		day=STARTING_DAYS;
	}
	
	public void checkLowestDay(int day){
		if(day<lowestDay)
			lowestDay=day;
	}
}