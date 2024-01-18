package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip extends mySpaceship{
	private int cargoCapacity;
	private int passengerCapacity;
	private final int ANNUAL_TRANSPORTSHIP_COST = 3000;
	private final int ANNUAL_CARGOCAP_FOR_TON = 5;
	private final int ANNUAL_PASSENGER_CAP = 3;
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
	super(name, commissionYear,maximalSpeed, crewMembers);
	this.annualMaintenanceCost = ANNUAL_TRANSPORTSHIP_COST + ( ANNUAL_CARGOCAP_FOR_TON * cargoCapacity) + (ANNUAL_PASSENGER_CAP * passengerCapacity);
	this.cargoCapacity = cargoCapacity;
	this.passengerCapacity = passengerCapacity;}

	public int getCargoCapacity(){
		return cargoCapacity;
	}
	public int getPassengerCapacity(){
		return passengerCapacity;
	}

	public String toString(){
		return super.toString() + "\n\tCargoCapacity=" + getCargoCapacity() + "\n\tPassengerCapacity=" + getPassengerCapacity();
	}

}
