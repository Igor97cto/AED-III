import Data_Structures.Question;

public class View
{

	public static boolean choiceAccess(int escolha) throws Exception
	{
		switch (escolha)
		{
		case 0:
			System.out.println("\nSistema finalizado!");
			return false;

		case 1:
			return Controller.userAccess();

		case 2:
			return Controller.userRegister();

		default:
			System.out.println("Opção invalida!");
			return true;
		}
	}

	public static boolean choiceMain(int escolha) throws Exception
	{

		switch (escolha)
		{
		case 0:
			return false;// voltar

		case 1:
			menuArea();
			return true;

		case 2:
			return true;// implementar

		default:
			System.out.println("Opção invalida!");
			return true;
		}
	}

	public static boolean choiceArea(int escolha) throws Exception
	{

		switch (escolha)
		{
		case 0:
			return false;// voltar

		case 1:
			menuMyQuestions();
			return true;

		case 2:
			return true;// implementar

		case 3:
			return true;// implementar

		case 4:
			return true;// implementar

		default:
			System.out.println("Opção invalida!");
			return true;
		}
	}


	public static boolean choiceMyQuestions(int escolha) throws Exception
	{
		switch (escolha)
		{
		case 0:
			return false;// voltar

		case 1:
			return Controller.listQuestion();

		case 2:
			return Controller.createQuestion();

		case 3:
			return Controller.updateQuestion();

		case 4:
			return Controller.fileQuestion();

		default:
			System.out.println("Opção invalida!");
			return true;
		}
	}

	public static void menuArea() throws Exception
	{
		int escolha;

		do
		{
			System.out.print(showMenu(2));
			escolha = Main.scn.nextInt();
		} while (choiceArea(escolha));
	}

	public static void menuMain() throws Exception
	{
		int escolha;

		do
		{
			System.out.print(showMenu(1));

			// Ler entrada do usuario
			escolha = Main.scn.nextInt();
		} while (choiceMain(escolha));
	}


	public static void menuMyQuestions() throws Exception
	{
		int escolha;

		do
		{
			System.out.print(showMenu(3));
			escolha = Main.scn.nextInt();
		} while (choiceMyQuestions(escolha));
	}


	public static void menuAccess() throws Exception
	{
		int escolha;

		do
		{
			System.out.print(showMenu(0));

			// Ler entrada do usuario
			escolha = Main.scn.nextInt();
		} while (choiceAccess(escolha));
	}


	public static String showMenu(int escolha) throws Exception
	{

		String acesso = "\nPERGUNTAS 1.0" + "\n===========" + "\nACESSO\n" + "\n1) Acessar o sistema"
				+ "\n2) Novo Usuário (Primeiro Acesso)\n" + "\n0) Sair\n" + "\nOpção: ";

		String principal = "\nPERGUNTAS 1.0" + "\n===========" + "\nINÍCIO\n" + "\n1) Minha área"
				+ "\n2) Buscar perguntas\n" + "\n0) Sair\n" + "\nOpção: ";

		String area = "\nPERGUNTAS 1.0" + "\n===========" + "\nINÍCIO > MINHA ÁREA\n" + "\n1) Minhas perguntas"
				+ "\n2) Minhas respostas" + "\n3) Meus votos em perguntas" + "\n4) Meus votos em respostas\n"
				+ "\n0) Retornar ao menu anterior\n" + "\nOpção: ";

		String perguntas = "\nPERGUNTAS 1.0" + "\n===========" + "\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS\n"
				+ "\n1) Listar" + "\n2) Incluir" + "\n3) Alterar" + "\n4) Arquivar\n"
				+ "\n0) Retornar ao menu anterior\n" + "\nOpção: ";

		switch (escolha)
		{
		case 0:
			return acesso;

		case 1:
			return principal;

		case 2:
			return area;

		case 3:
			return perguntas;

		default:
			return null;
		}
	}
}