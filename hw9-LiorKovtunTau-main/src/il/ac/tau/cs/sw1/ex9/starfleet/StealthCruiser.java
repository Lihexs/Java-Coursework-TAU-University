package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;

public class StealthCruiser extends Fighter{
	private static int cloakDevicesInTheFleet = 0;
	private static final int ANNUAL_CLOAK_DEVICE_COST = 50;
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		cloakDevicesInTheFleet++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		super(name,commissionYear, maximalSpeed, crewMembers, Arrays.asList(new Weapon("Laser Cannons",10,100)));
		cloakDevicesInTheFleet++;
	}

	public int getAnnualMaintenanceCost(){
		return annualMaintenanceCost + cloakDevicesInTheFleet * ANNUAL_CLOAK_DEVICE_COST;
	}

}
