package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myBattleSpaceShip{
	private int numberOfTechnicians;
	private static final int ANNUAL_BOMBER_COST = 5000;
	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		this.numberOfTechnicians = numberOfTechnicians;
		if(numberOfTechnicians == 0)
			this.annualMaintenanceCost = ANNUAL_BOMBER_COST + annualWeaponsCost(weapons);
		else
			this.annualMaintenanceCost = ANNUAL_BOMBER_COST + (int)((1 - (double)numberOfTechnicians / 10) * (annualWeaponsCost(weapons)));
	}
	public int getNumberOfTechnicians(){
		return numberOfTechnicians;
	}
	public String toString(){
		return super.toString() + "\n\tNumberOfTechnicians=" + getNumberOfTechnicians();
	}


}
