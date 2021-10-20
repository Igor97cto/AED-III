import Data_Structures.*;
import java.util.Scanner;

public class Main {
	static Scanner scn;
	static CRUDUser crdusr;
    static CRUDQuestion crdqst;

	public static void main(String[] args) throws Exception {
		scn = new Scanner(System.in);
		crdusr = new CRUDUser("user_data", User.class.getConstructor(new Class[] { byte[].class }), true);
		crdqst = new CRUDQuestion("user_data", Question.class.getConstructor(new Class[] { byte[].class }), true);
		FrontEndInterface.menuAccess();
	}
}
