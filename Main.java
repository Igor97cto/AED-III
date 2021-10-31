import Data_Structures.*;
import java.util.Scanner;

public class Main {
	static Scanner scn;
	static CRUDUser crdusr;
    static CRUDQuestion crdqst;

	public static void main(String[] args) throws Exception {
		scn = new Scanner(System.in);
		crdusr = new CRUDUser(User.class.getConstructor(new Class[] { byte[].class }), false);
		crdqst = new CRUDQuestion(Question.class.getConstructor(new Class[] { byte[].class }), false);
		View.menuAccess();
		
	}
}
