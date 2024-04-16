/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package warriorsandweapons;

/**
 *
 * @author steve
 */
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Arena {
  public final static Random RAND = new Random();

  List<WarriorI> warriors = new ArrayList<>();
  List<WeaponI> weapons = new ArrayList<>();

  public void addWarrior(WarriorI wadd) {
    int index = -1;
    for(int i=0;i<warriors.size();i++) {
      WarriorI w = warriors.get(i);
      if(w.getName().equals(wadd.getName())) {
        index = i;
        break;
      }
    }
    if(index >= 0) {
      WarriorI w = warriors.get(index);
      warriors.set(index,wadd);
    } else {
      warriors.add(wadd);
    }
  }

  public void addWeapon(WeaponI wadd) {
    int index = -1;
    for(int i=0;i<weapons.size();i++) {
      WeaponI w = weapons.get(i);
      if(w.getName().equals(wadd.getName())) {
        index = i;
        break;
      }
    }
    if(index >= 0) {
      WeaponI w = weapons.get(index);
      weapons.set(index,wadd);
    } else {
      weapons.add(wadd);
    }
  }

  public void replace(WarriorI wrep) {
    int index = -1;
    for(int i=0;i<warriors.size();i++) {
      WarriorI w = warriors.get(i);
      if(w.getName().equals(wrep.getName())) {
        index = i;
        break;
      }
    }
    if(index >= 0) {
      WarriorI w = warriors.get(index);
      warriors.set(index,wrep);
    } else {
      warriors.add(wrep);
    }
  }

  void addWarrior(String init) throws Exception {
    WarriorI w = (WarriorI)warriors.get(0).getClass().newInstance();
    w.initFromString("Warrior "+init);
    warriors.add(w);
  }

  void addWeapon(String init) throws Exception {
    WeaponI w = (WeaponI)weapons.get(0).getClass().newInstance();
    w.initFromString("Weapon "+init);
    weapons.add(w);
  }

  public WarriorI[] battle(Class war,Class wpnc) throws Exception {
    if(warriors.size() == 0) {
      warriors.add((WarriorI)war.newInstance());
      addWarrior("Marvin, health=10, max health=10");
      addWarrior("DejahThoris, health=11, max health=11");
      addWarrior("Kaldane, health=7, max health=7");
      addWarrior("Karmang, health=6, max health=6");
      addWarrior("RasThavas, health=8, max health=9");
      Collections.shuffle(warriors);
    }
    if(weapons.size() == 0) {
      weapons.add((WeaponI)wpnc.newInstance());
      addWeapon("DeathRay, damage=7");
      addWeapon("StunGun, damage=9");
      addWeapon("Sword, damage=4");
      addWeapon("Q36SpaceModulator, damage=8");
      Collections.shuffle(weapons);
    }
    System.out.println();
    System.out.println("===============================================");
    Set<String> m = new HashSet<>();
    for(WarriorI w : warriors) {
      assert !m.contains(w.getName()) : w.getName();
      m.add(w.getName());
    }
    for(WeaponI w : weapons) {
      assert !m.contains(w.getName()) : w.getName();
      m.add(w.getName());
    }
    WarriorI[] warriorArray = warriors.toArray(new WarriorI[0]);
    int r1 = RAND.nextInt(warriorArray.length);
    int r2 = RAND.nextInt(warriorArray.length-1);
    if(r2 >= r1)
      r2++;
    WarriorI w1 = warriorArray[r1];
    WarriorI w2 = warriorArray[r2];

    w1.heal();
    w2.heal();

    if(!w1.isHealthy())
      throw new Error(String.format(
        "Warrior %s is not healthy",w1.getName()));
    if(!w2.isHealthy())
      throw new Error(String.format(
        "Warrior %s is not healthy",w2.getName()));

    System.out.printf("Battle between %s and %s%n",
        w1.getName(),w2.getName());

    WeaponI[] weaponArray = weapons.toArray(new WeaponI[0]);
    r1 = RAND.nextInt(weaponArray.length);
    r2 = RAND.nextInt(weaponArray.length-1);
    if(r2 >= r1)
      r2++;
    WeaponI wn1 = weaponArray[r1];
    WeaponI wn2 = weaponArray[r2];

    w1.setWeapon(wn1);
    w2.setWeapon(wn2);
    System.out.println();
    System.out.println("  Player1: "+w1);
    System.out.println("    "+w1.getWeapon());
    System.out.println("  Player2: "+w2);
    System.out.println("    "+w2.getWeapon());
    System.out.println();

    while(true) {
      WeaponI wpn = w1.getWeapon();
      int damage = w2.takeDamage(wpn);
      if(damage <= 0  || damage > wpn.getMaxDamage())
        throw new Error(String.format(
            "Weapon %s did %d damage",wpn.getName(),damage));

      if(w2.isHealthy())
        throw new Error(String.format(
            "Warrior %s is not supposed to be healthy",w2.getName()));

      System.out.printf(
        "  %s did %d damage to %s (health is now %d)%n",
          w1.getName(),damage,w2.getName(),w2.getHealth());

      if(w2.isDefeated()) {
        System.out.printf("%s is defeated%n",w2.getName());
        break;
      }

      WarriorI w = w1;
      w1 = w2;
      w2 = w;
    }
    return new WarriorI[]{w1,w2};
  }

  public static void testWeapon(WeaponI w) {
    assert w.getName() != null : "The weapon's name is null";
    Pattern p = Pattern.compile("^Weapon (\\w+), damage=(\\d+)$");
    Matcher matcher = p.matcher(w.toString());
    assert matcher.matches() :
        "The weapon's toString() format is incorrect: "+w;
    assert matcher.group(1).equals(w.getName()) :
      "The weapon's toString() format is incorrect. The name is wrong: "+
        matcher.group(1);
    assert Integer.parseInt(matcher.group(2)) == w.getMaxDamage():
      "The weapon's toString() format is incorrect. The maxDamage is wrong: "+
        matcher.group(2);
    for(String init : new String[]{"","a b c","light saber, 4"}) {
        try {
            w.initFromString(init);
            assert false : "Pattern '"+init+"' should have thrown PatternMatchException";
        } catch(PatternMatchException pe) {
        }
    }
    int m = w.getMaxDamage();
    assert m >= 4 :
      String.format("Max damage for weapon %s is too small (%d < 4)",
        w.getName(),m);
    for(int i=0;i<100;i++) {
      int n = w.getDamage();
      assert 1 <= n && n <= m:
        String.format("Damage=%d from weapon %s",n,w.getName());
      assert w.getMaxDamage() == m:
        String.format("Max damage from %s is not consistent",w.getName());
    }
    String[] weaponNames={"Sword","Axe","Pike","Spear",
        "Dagger","Bow","CrossBow"};
    for(int i=0;i<20;i++) {
      String name = weaponNames[RAND.nextInt(weaponNames.length)];
      int dmg = RAND.nextInt(1000)+4;
      String str = "Weapon"+spaces(1,2)+name+","+spaces(0,2)+"damage="+dmg;
      System.out.println("Parsing weapon ("+str+")");
      w.initFromString(str);
      assert name.equals(w.getName()) :
        "initFromString didn't work, the name is wrong: "+
            name+" != "+w.getName();
      assert dmg == w.getMaxDamage() :
        "initFromString didn't work, the max damage is wrong: "+
            dmg+" != "+w.getMaxDamage();
    }
  }

  static String spaces(int lo,int hi) {
    return spaces(RAND.nextInt(hi-lo+1)+lo);
  }

  static String spaces(int n) {
    int r = RAND.nextInt(2)+n;
    StringBuilder sb = new StringBuilder();
    for(int i=0;i<r;i++)
      if(RAND.nextInt(2)==0)
        sb.append(' ');
      else
        sb.append('\t');
    return sb.toString();
  }

  public static void testWarrior(WarriorI w,WeaponI wpn) {
    assert w.getName() != null :
        "The warrior's name cannot be null";
    assert w.getName().length()>0 :
        "The warrior's name can't be zero in size";
    assert w.getMaxHealth() > 0 :
        "The Warrior's initial max health must be greater than zero";
    assert w.getHealth() == w.getMaxHealth() :
        "The Warrior's initial health must be equal to their max health";
    Pattern p = Pattern.compile(
        "^Warrior (\\w+), health=(\\d+), max health=(\\d+)$");
    for(String init : new String[]{"","a b c","luke skywalker, 4"}) {
        try {
            w.initFromString(init);
            assert false : "Calling initFromString() with  '"+init+"' should have thrown PatternMatchException";
        } catch(PatternMatchException pe) {
        }
    }
    String wstr = w.toString();
    System.out.println("To string on Warrior produced: "+wstr);
    Matcher matcher = p.matcher(wstr);
    assert matcher.matches() :
      "The warrior's toString() format is incorrect: ("+wstr+")";
    assert matcher.group(1).equals(w.getName()):
      "The warrior's toString() format is incorrect. The name is wrong.";
    assert Integer.parseInt(matcher.group(2)) == w.getMaxHealth() :
      "The warrior's toString() format is incorrect. The health is wrong.";
    assert Integer.parseInt(matcher.group(3)) == w.getMaxHealth() :
      "The warrior's toString() format is incorrect. The max health is wrong.";
    assert w.isHealthy():
      String.format("Warrior %s is not healthy",w.getName());
    int m = w.getMaxHealth();
    assert m > 0:
      String.format("Warrior %s has max health less than 1",w.getName());
    while(true) {
      int d = w.takeDamage(wpn);
      assert 0 < d && d <= wpn.getMaxDamage():
        String.format("Damage=%d from weapon %s",d,wpn.getName());
      m -= d;
      assert !w.isHealthy() :
        String.format("Warrior %s should not be health",w.getName());
      assert w.getHealth()==m:
        String.format("Warrior %s health is wrong: %d != %d",
            w.getName(),m,w.getHealth());
      if(m <= 0) {
        assert w.isDefeated():
          String.format("Warrior %s should be defeated",w.getName());
        break;
      } else {
        assert !w.isDefeated():
          String.format("Warrior %s should not be defeated",w.getName());
      }
    }
    String[] warriorNames={"Conan","Xena","HeMan","Sonja","Ralph","Helga"};
    for(int i=0;i<20;i++) {
      String name = warriorNames[RAND.nextInt(warriorNames.length)];
      int health = RAND.nextInt(1000)+1;
      int maxHealth = health+RAND.nextInt(1000)+1;
      String str = "Warrior"+spaces(1,2)+name+","+spaces(1,2)+
        "health="+health+","+spaces(0,2)+"max"+spaces(1,2)+"health="+maxHealth;
      System.out.println("Parsing warrior ("+str+")");
      w.initFromString(str);
      assert name.equals(w.getName()) :
        "initFromString didn't work, the name is wrong: "+
            name+" != "+w.getName();
      assert health == w.getHealth() :
        "initFromString didn't work, the health is wrong: "+
            health+" != "+w.getHealth();
      assert maxHealth == w.getMaxHealth() :
        "initFromString didn't work, the max health is wrong: "+
            maxHealth+" != "+w.getMaxHealth();
    }
  }

  public static void tournament(Arena a,Class war,Class wpn,DiskI d)
        throws Exception {
    Map<String,Integer> victories = new HashMap<>();
    for(int i=0;i<25;i++) {
      WarriorI[] players = a.battle(war,wpn);
      players[0].heal();
      players[1].heal();
      System.out.printf("Winner: %s%n",players[0]);

      File f1 = d.saveWarrior(players[0]);
      WarriorI w = d.loadWarrior(f1);
      assert players[0].getName().equals(w.getName()) :
        "load/save failed for "+players[0].getName();
      a.replace(w);

      Integer score = victories.get(w.getName());
      if(score == null)
        victories.put(w.getName(),1);
      else
        victories.put(w.getName(),score+1);

      File f2 = d.saveWarrior(players[1]);
      assert !f2.equals(f1) :
        "When saving different warriors, different file "+
        "names should be generated.";
      w = d.loadWarrior(f2);
      assert players[1].getName().equals(w.getName()) :
        "load/save failed for "+players[1].getName();
      a.replace(w);
    }
    System.out.println("===============================================");
    System.out.println("Victories by Warrior:");
    for(Map.Entry e : victories.entrySet()) {
      System.out.printf("%25s : %3d%n",e.getKey(),e.getValue());
    }
  }

  public static void main(String[] args) throws Exception {
    try {
      assert(false);
      throw new Error("You need to enable assertions");
    } catch(AssertionError ae) {
    }
    Class warriorClass = null, weaponClass = null, diskClass = null;
    for(int i=0;i<args.length;i++) {
      Class c = Class.forName(args[i]);
      if(WeaponI.class.isAssignableFrom(c))
        weaponClass = c;
      else if(WarriorI.class.isAssignableFrom(c))
        warriorClass = c;
      else if(DiskI.class.isAssignableFrom(c))
        diskClass = c;
      else
        assert false : "Not a class name: "+args[i];
    }

    assert weaponClass != null : "You need to supply a weapon class";
    WeaponI weapon = (WeaponI)weaponClass.getConstructor().newInstance();
    testWeapon(weapon);

    assert warriorClass != null : "You need to supply a warrior class";
    WarriorI warrior = (WarriorI)warriorClass.getConstructor().newInstance();
    testWarrior(warrior,weapon);

    assert diskClass != null : "You need to supply a disk class";
    DiskI disk = (DiskI)diskClass.getConstructor().newInstance();
    Arena a = new Arena();
    tournament(a,warriorClass,weaponClass,disk);
  }
}
