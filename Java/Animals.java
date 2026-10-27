public class Animal {
    // ATTRIBUTES
    private int age;
    private String name;
    private String entryDate;
    private String gender;
    // CONSTRUCTORS / GETTERS / SETTERS / AND OTHER METHODS
    public Animal(){

    }
    public Animal(String name) {
        this.name = name;
        this.age = 0;
        this.gender = "Unknown";
        this.entryDate = "Unknown";
    }
    public Animal(int age, String name, String entryDate, String gender) {
        this.age = age;
        this.name = name;
        this.entryDate = entryDate;
        this.gender = gender;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void makeNoise(){
        System.out.println("make some noise");
    }
    
}
 
