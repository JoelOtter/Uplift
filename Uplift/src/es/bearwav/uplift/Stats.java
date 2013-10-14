package es.bearwav.uplift;

public class Stats {
	private int health;
	private int money;
	private int mainquest;
	
	public Stats(int health, int money, int mainquest){
		this.health = health;
		this.money = money;
		this.mainquest = mainquest;
	}
	
	public int incHealth(int inc){
		health += inc;
		if (health > 100){
			health = 100;
		}
		return health;
	}
	
	public int decHealth(int dec){
		health -= dec;
		return health;
	}
	
	public int setHealth(int set){
		health = set;
		return health;
	}
	
	public int incMoney(int inc){
		money += inc;
		return money;
	}
	
	public int decMoney(int dec){
		money -= dec;
		return money;
	}
	
	public int setMoney(int set){
		money = set;
		return money;
	}
	
	public int getMoney() { return money; }
	public int getHealth() { return health; }
	
	public int getQuest(String name) {
		if (name == "MAIN") return mainquest;
		else return 0;
	}
	
	public void incQuest(String name){
		if (name == "MAIN") mainquest++;
	}
}
