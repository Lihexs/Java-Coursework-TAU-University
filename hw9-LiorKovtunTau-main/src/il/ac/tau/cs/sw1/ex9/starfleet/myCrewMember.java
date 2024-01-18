package il.ac.tau.cs.sw1.ex9.starfleet;

public abstract class myCrewMember implements CrewMember{
    private String name;
    private int age;
    private int yearsInService;

    public myCrewMember(String name , int age , int yearsInService)
    {
        this.name = name;
        this.age = age;
        this.yearsInService = yearsInService;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
    public int getYearsInService(){
        return yearsInService;
    }
}
