package warriorsandweapons;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.StringWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Closeable;

public class DiskImpl implements DiskI{

    @Override
    public WarriorI loadWarrior(File f) throws IOException {
       WeaponI weapon = new WeaponImpl();
       WarriorI warrior = new WarriorImpl();
       Scanner s = new Scanner(f);
       if(s.hasNextLine()){
           String input = s.nextLine();
           warrior.initFromString(input);
       }
       if(s.hasNextLine()){
         String input = s.nextLine();
         weapon.initFromString(input);  
       }
       warrior.setWeapon(weapon);
       return warrior;
    }

    @Override
    public File saveWarrior(WarriorI w) throws IOException {
        File file = new File(w.getName() + ".txt");
        try(PrintWriter pw = new PrintWriter(file)){
            pw.write(w.toString() + "\n");
            pw.write(w.getWeapon().toString());
        }
        return file;
    }
}
