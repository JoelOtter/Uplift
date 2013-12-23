package es.bearwav.uplift;

import java.util.HashMap;

public class Stats {
	private int health;
	private int money;
	private HashMap<String, Integer> quests;
	
	public Stats(int health, int money){
		this.health = health;
		this.money = money;
		this.quests = new HashMap<String, Integer>();
		this.quests.put("MAIN", 0);
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
		return this.quests.get(name);
	}
	
	public void incQuest(String name){
		this.quests.put(name, this.quests.get(name) + 1);
	}
}
