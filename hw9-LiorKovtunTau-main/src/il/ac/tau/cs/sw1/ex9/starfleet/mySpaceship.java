package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.*;

public abstract class mySpaceship implements Spaceship{

    private String name;
    private int commissionYear;
    private float maximalSpeed;
    protected int annualMaintenanceCost;
    private Set<? extends CrewMember> crewMembers;
    protected static final int BASIC_FIREPOWER = 10;
    public mySpaceship(String name , int commissionYear, float maximalSpeed ,Set<? extends CrewMember> crewMembers){
        this.name = name;
        this.commissionYear = commissionYear;
        this.maximalSpeed = maximalSpeed;
        this.annualMaintenanceCost = 0;
        this.crewMembers = crewMembers;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCommissionYear() {
        return commissionYear;
    }

    @Override
    public float getMaximalSpeed() {
        return maximalSpeed;
    }

    @Override
    public int getFirePower() {
        return BASIC_FIREPOWER;
    }

    @Override
    public int getAnnualMaintenanceCost() {
        return annualMaintenanceCost;
    }

    public Set<? extends CrewMember> getCrewMembers(){
        return crewMembers;
    }

    public String toString(){
        return this.getClass().getSimpleName() + "\n\tName=" + getName() +"\n\tCommissionYear=" + getCommissionYear() +
                "\n\tMaximalSpeed=" + getMaximalSpeed() + "\n\tFirePower=" + getFirePower()
                + "\n\tCrewMembers=" + getCrewMembers().size() + "\n\tAnnualMaintenanceCost=" + getAnnualMaintenanceCost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        mySpaceship that = (mySpaceship) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
