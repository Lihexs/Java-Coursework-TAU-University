package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myBattleSpaceShip{
	protected static final int ANNUAL_FIGHTER_COST = 2500;
	private static final int ENGINE_MAINTENANCE_COST = 1000;
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name,commissionYear,maximalSpeed, crewMembers,weapons);
		this.annualMaintenanceCost = (int)((ANNUAL_FIGHTER_COST + annualWeaponsCost(weapons) +
				(int)(ENGINE_MAINTENANCE_COST * maximalSpeed)));}

}
