package warriorsandweapons;

import java.util.Scanner;
import java.util.regex.MatchResult;


public class WarriorImpl implements WarriorI {

    String name = "null";
    int maxHp = 1000; 
    int hp = 1000;
    WeaponI weapon;

    public WarriorImpl(){
        name = "dave";
        maxHp = 1000;
        hp = 1000;
    }

    @Override
    public WeaponI getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(WeaponI w) {
        this.weapon = w;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isDefeated() {
        return (hp < 0);
    }

    @Override
    public boolean isHealthy() {
        return (hp == maxHp);
    }

    @Override
    public int takeDamage(WeaponI w) {
        int tempDmg = w.getDamage();
        hp = hp - tempDmg;
        return tempDmg;
    }

    @Override
    public int getMaxHealth() {
        return maxHp;
    }

    @Override
    public int getHealth() {
        return hp;
    }

    @Override
    public String toString() {
        return "Warrior " + name + ", health=" + hp + ", max health=" + maxHp;
    }

    @Override
    public void initFromString(String input) {

        Scanner s = new Scanner(input);
        String patternString = "Warrior\\s*(\\w+),\\s*health=(\\d+),\\s*max\\s*health=(\\d+)\\s*";
        try{
            s.findInLine(patternString);
            MatchResult m = s.match();
            name = m.group(1);
            hp = Integer.parseInt(m.group(2));
            maxHp = Integer.parseInt(m.group(3));

        } catch (Exception e){
            throw new PatternMatchException();
        }
    }

    @Override
    public void heal() {
        hp = maxHp;
    }
}