package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		List<Spaceship> list = new ArrayList<>(fleet);
		List<String> nameList = new ArrayList<>();
		Comparator<Spaceship> comparator = new Comparator<Spaceship>() {
			@Override
			public int compare(Spaceship o1, Spaceship o2) {
				int compareFirePower = Integer.compare(o2.getFirePower(),o1.getFirePower());
				if(compareFirePower != 0)
					return compareFirePower;
				int compareCommissionYear = Integer.compare(o2.getCommissionYear(),o1.getCommissionYear());
				if(compareCommissionYear != 0 )
					return compareCommissionYear;
				return o1.getName().compareTo(o2.getName());
			}
		};
		list.sort(comparator);
		for(Spaceship spaceship : list)
			nameList.add(spaceship.toString());
		return nameList;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String,Integer> map = new HashMap<>();
		for(Spaceship spaceship : fleet){
			String spaceShipName = spaceship.getClass().getSimpleName();
			if(!map.containsKey(spaceShipName))
				map.put(spaceShipName,1);
			else
				map.put(spaceShipName,map.get(spaceShipName) + 1);
		}
		return map;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int count = 0;
		for(Spaceship spaceship : fleet)
		{
			count += spaceship.getAnnualMaintenanceCost();
		}
		return count;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> set = new HashSet<>();
		for(Spaceship spaceship : fleet){
			if(spaceship instanceof myBattleSpaceShip) {
				List<Weapon> list = ((myBattleSpaceShip) spaceship).getWeapon();
				for(Weapon weapon : list){
					set.add(weapon.getName());
				}
			}
		}
		return set;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int count = 0;
		for(Spaceship spaceship : fleet)
			count += spaceship.getCrewMembers().size();
		return count;
	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		int ageSum = 0;
		int officersCount = 0;
		for(Spaceship spaceship : fleet){
			Set<? extends CrewMember> crew = spaceship.getCrewMembers();
			for(CrewMember cr : crew){
				if(cr instanceof Officer) {
					ageSum += cr.getAge();
					officersCount++;
				}
			}
		}
		return ((float)ageSum)/officersCount;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer, Spaceship> map = new HashMap<>();
		for(Spaceship spaceship : fleet){
			Set<? extends CrewMember> crew = spaceship.getCrewMembers();
			Officer highestOfficer = null;
			for(CrewMember cr : crew){
				if(cr instanceof Officer) {
					if(highestOfficer != null ) {
						if(((Officer) cr).getRank().compareTo(highestOfficer.getRank()) > 0)
							highestOfficer = (Officer)cr;
					}else{
						highestOfficer = (Officer)cr;
					}
				}
			}
			if(highestOfficer != null)
				map.put(highestOfficer,spaceship);
		}
		return map;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank,Integer> map = new HashMap<>();
		for(Spaceship spaceship : fleet){
			Set<? extends  CrewMember> crew = spaceship.getCrewMembers();
			for(CrewMember cr : crew){
				OfficerRank rank;
				if(cr instanceof Officer) {
					rank = ((Officer) cr).getRank();
					if (map.containsKey(rank))
						map.put(rank, map.get(rank) + 1);
					else
						map.put(rank, 1);
				}
			}
		}
		Comparator<Map.Entry<OfficerRank, Integer>> comparator = new Comparator<Map.Entry<OfficerRank, Integer>>() {
			@Override
			public int compare(Map.Entry<OfficerRank, Integer> o1, Map.Entry<OfficerRank, Integer> o2) {
				int rankCount = Integer.compare(o1.getValue(),o2.getValue());
				if(rankCount != 0)
					return rankCount;
				return o1.getKey().compareTo(o2.getKey());
			}
		};
		SortedSet<Map.Entry<OfficerRank,Integer>> sortedMap = new TreeSet<>(comparator);
		sortedMap.addAll(map.entrySet());
		return new ArrayList<>(sortedMap);
	}

}
