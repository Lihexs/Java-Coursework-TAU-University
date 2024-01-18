package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper  extends Fighter{
	private static final int COLONIALVIPER_ANNUAL_COST = 4000;
	private static final int CREW_CARE_ANNUAL_COST = 500;
	private static final int ENGINE_ANNUAL_COST = 500;
	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear,maximalSpeed,crewMembers,weapons);
	annualMaintenanceCost = COLONIALVIPER_ANNUAL_COST + annualWeaponsCost(weapons) + crewMembers.size() * CREW_CARE_ANNUAL_COST
			+ (int)(maximalSpeed * ENGINE_ANNUAL_COST);
	}
}
