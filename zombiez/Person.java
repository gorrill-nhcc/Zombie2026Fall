package zombiez;

public class Person {
	private int ammo, personCount;
	
	public Person(int ammo, int personCount){
		this.ammo=ammo;
		this.personCount=personCount;
	}

	public void killPerson(){
		personCount--;
		setAmmo(150);
	}
	
	public void useAmmo(){
		ammo--;
	}
	
	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}

	@Override
	public String toString() {
		return "Person [ammo=" + ammo + ", personCount=" + personCount + "]";
	}
}
