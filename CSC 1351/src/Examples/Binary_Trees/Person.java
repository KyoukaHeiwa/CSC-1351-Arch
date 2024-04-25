package Examples.Binary_Trees;

public class Person implements Comparable<Person> {
    String firstName;
    String lastName;
    int ssn;
    Person(String firstName, String lastName, int ssn){
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }
    public String toString(){
        return firstName + " " + ssn;
    }
    String name; // Add a name field
    @Override
    public int compareTo(Person p){
        int d = firstName.compareTo(p.firstName);
        if (d == 0) d = lastName.compareTo(p.lastName);
        if (d == 0) d = ssn - p.ssn;
        return d;
        //return name.compareTo(p.name); // Use the name field instead of firstName
    }
    public boolean equals(Object o){
        if (o instanceof Person){
            Person p = (Person)o;
            return name.equals(p.name) && ssn == p.ssn; // Use the name field instead of firstName
        }
        return false;
    }
    public int hashCode(){
        return name.hashCode() + ssn; // Use the name field instead of firstName
    }

}
