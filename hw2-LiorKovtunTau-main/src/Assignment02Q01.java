
public class Assignment02Q01 {

	public static void main(String[] args) {
		int i = 0;
		while (i < args.length){
			if ((args[i].charAt(0) % 5) == 0) {
				System.out.println(args[i].charAt(0));
				i++;
			}else{
				System.out.println();
				i++;
			}
		}
	}
}