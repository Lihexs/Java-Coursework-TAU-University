package enumRiddles;

enum Day {
	   MONDAY,
	   TUESDAY,
	   WEDNESDAY,
	   THURSDAY,
	   FRIDAY,
	   SATURDAY,
	   SUNDAY;
	 
	   public Day next(){
		  switch (this){
			  case MONDAY:
				  return TUESDAY;
			  case TUESDAY:
				  return WEDNESDAY;
			  case WEDNESDAY:
				  return THURSDAY;
			  case THURSDAY:
				  return FRIDAY;
			  case FRIDAY:
				  return SATURDAY;
			  case SATURDAY:
				  return SUNDAY;
			  case SUNDAY:
				  return MONDAY;
		  }
		  return null;
	   }
	 
	   int getDayNumber() {
		   switch (this) {
			   case MONDAY:
				   return 1;
			   case TUESDAY:
				   return 2;
			   case WEDNESDAY:
				   return 3;
			   case THURSDAY:
				   return 4;
			   case FRIDAY:
				   return 5;
			   case SATURDAY:
				   return 6;
			   case SUNDAY:
				   return 7;
		   }
		   return 0;
	   }
	}
	   
	public class DayTest {
	   public static void main(String[] args) {
	      for (Day day : Day.values()) {
	         System.out.printf("%s (%d), next is %s\n", day, day.getDayNumber(), day.next());
	      }
	   }
	}
