package Examples;

public class BadHash {
    int n=0;
    @Override
    public int hashCode() {
        return n++;
    }
}
