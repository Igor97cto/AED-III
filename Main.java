import Data_Structures.*;
import java.util.Scanner;

public class Main {
	static Scanner scn;
	static Crud<User> crd;

	public static void main(String[] args) throws Exception {
		scn = new Scanner(System.in);
		crd = new Crud<>("data", User.class.getConstructor(new Class[] { byte[].class }), false);

		FrontEndInterface.menuAccess();
	}
}