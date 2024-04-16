/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package warriorsandweapons;

/**
 *
 * @author steve
 */
/**
 * A weapon has the following properties:
 * name
 * max damage
 */
public interface WeaponI { 

  /**
   * Return a random amount of
   * damage. The value of the
   * damage should be at least
   * one, and no more than
   * getMaxDamage(). Use a static field of
   * type java.util.Random
   * to obtain the number.
   */
  public int getDamage();

  /**
   * The most damage that the
   * weapon can do. This value
   * should be stored in the
   * class as a field.
   */
  public int getMaxDamage();

  /**
   * The format of the toString() method should look like this:
   *
   * "Weapon {name}, damage={number}
   *
   * Note that the curly brackets are not to be taken literally. A
   * valid weapon might be:
   * Weapon sword, damage=10
   */
  public String toString();

  /**
   * This method should take a string in the same format
   * as the toString() method above, e.g.
   *
   * "Weapon {name}, damage={number}
   *
   * and parses the name and damage numbers using the
   * findInLine() method on Scanner, * and sets the
   * values.
   * Note that spaces may vary in length. The
   * following is valid.
   * Weapon Axe,   damage=5
   *
   * If the string doesn't mattch the above pattern,
   * you should throw a PatternMatchException.
   *
   * <em>Note</em>: it's probably a good idea to add \\s*
   * to the end of your pattern in order to deal with line
   * ending problems.
   */
  public void initFromString(String input);

  /**
   * The name of the weapon,
   * e.g. sword, dagger, etc.
   */
  public String getName();
}
