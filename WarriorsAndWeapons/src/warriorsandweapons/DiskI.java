/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package warriorsandweapons;

/**
 *
 * @author steve
 */
import java.io.File;
import java.io.IOException;

public interface DiskI {

  /**
   * Open a file and read in the data needed to
   * instantiate a warrior, namely the warrior's
   * name and max health and the warrior's weapon.
   * Use the initFromString() methods on the
   * WeaponI class and the WarriorI
   * class to parse the data.
   */
  public WarriorI loadWarrior(File f) throws IOException;

  /**
   * Create a file (based on the warriors name), and write the warrior's name and
   * max health and weapon to that file using the toString() method of the warrior
   * and the toString() method of the warrior's weapon. Also write
   * his weapon to the file using getWeapon(), and the Weapon's toString() method.
   * To write a file, * you need to instantiate a PrintWriter (from
   * package java.io).
   */
  public File saveWarrior(WarriorI w) throws IOException;
}
