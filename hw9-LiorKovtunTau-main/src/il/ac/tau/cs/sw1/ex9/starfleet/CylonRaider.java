package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends Fighter{
	private static final int CYLONRAIDER_ANNUAL_COST = 3500;
	private static final int CREW_ANNUAL_COST = 500;
	private static final int ENGINE_ANNUAL_COST = 1200;
	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed , crewMembers, weapons);
		annualMaintenanceCost = CYLONRAIDER_ANNUAL_COST + annualWeaponsCost(weapons) + crewMembers.size() * CREW_ANNUAL_COST +
				(int)(maximalSpeed * ENGINE_ANNUAL_COST);
	}

}
