package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.*;

public abstract class myBattleSpaceShip extends mySpaceship{
    List<Weapon> weapons;
    public myBattleSpaceShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers,List<Weapon> weapons) {
        super(name, commissionYear, maximalSpeed,crewMembers);
        this.weapons = weapons;

    }

    @Override
    public int getFirePower() {
       int firepower = BASIC_FIREPOWER;
        for (Weapon w : weapons) {
            firepower += w.getFirePower();
        }
        return firepower;
    }

    public List<Weapon> getWeapon(){
        return weapons;
    }

    public static int annualWeaponsCost(List<Weapon> weapons){
       int count = 0;
       if(weapons == null)
           return 0;
        for(Weapon weapon : weapons)
        {
            count += weapon.getAnnualMaintenanceCost();
        }
        return count;
    }

    public String toString(){
    return super.toString() + "\n\tWeaponArray=" + this.weapons.toString();
    }
}
