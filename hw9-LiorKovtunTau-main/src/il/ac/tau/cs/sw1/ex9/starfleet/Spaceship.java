package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.*;

public interface Spaceship {
    public String getName();
    public int getCommissionYear();
    public float getMaximalSpeed();
    public int getFirePower();
    public Set<? extends CrewMember> getCrewMembers();
    public int getAnnualMaintenanceCost();
}
