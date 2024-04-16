public class Monster {
    private int hitpoints=10, damage;
    public String name = "Orc";
    public Monster() { hitpoints = 15; damage = 0; name = "Orc"; }
    public Monster(String s,int hp) { int hitpoints = hp; name = s; }
    public int getHealth() { return hitpoints - damage; }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monster(");
        System.out.print(name+">");
        sb.append(")");
        return sb.toString(); }
    public void doDamage(int n) { damage += n; }
    public static void main(String[] args) {
        Monster m = new Monster("Kobald", 20);
        m.doDamage(3);
        String ms = m.toString();
        System.out.println(ms+" "+m.getHealth()); } }