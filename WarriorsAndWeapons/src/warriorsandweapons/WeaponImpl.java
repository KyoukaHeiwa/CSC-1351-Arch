package warriorsandweapons;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class WeaponImpl implements WeaponI {

    String name = "null";
    int maxDmg = 4;
    static Random r = new Random();
    Scanner s;

    @Override
    public int getDamage() {
        return r.nextInt(getMaxDamage()) + 1;
    }

    @Override
    public int getMaxDamage() {
        return maxDmg;
    }

    @Override
    public String toString() {
        // \w\s\w,\s\w=\d

        return "Weapon " + name + ", damage=" + maxDmg;
    }

    @Override
    public void initFromString(String input) {
        s = new Scanner(input);
        String patternString = "Weapon\\s*(\\w+),\\s*damage=(\\d+)\\s*";
        Pattern pattern = Pattern.compile(patternString);
        if (s.findInLine(pattern) != null) {
            MatchResult result = s.match();

            name = result.group(1);
            maxDmg = Integer.parseInt(result.group(2));
        } else {
            s.close();
            throw new PatternMatchException();
        }

    }

    @Override
    public String getName() {
        return name;
    }

}