import Data_Structures.Question;

public class FrontEndInterface
{

	public static boolean choiceAccess(int escolha) throws Exception
	{
		switch (escolha)
		{
		case 0:
			System.out.println("\nSistema finalizado!");
			return false;

		case 1:
			return BackEndInterface.userAccess();

		case 2:
			return BackEndInterface.userRegister();

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
		int idQuestion;
		int alterar;
		int arquivar;

		switch (escolha)
		{
		case 0:
			return false;// voltar

		case 1:
			return BackEndInterface.listQuestion();

		case 2:
			return BackEndInterface.readQuestion();

		case 3:
			// Obter a lista de IDs de perguntas na Árvore B+ usando o ID do usuário
			// lista( idUsuario );
			System.out.println("Qual o número da pergunta que você deseja alterar? ");
			alterar = Main.scn.nextInt();
			if (alterar > 0)
			{
				// read(alterar);
				// Apresentar os dados da pergunta
				System.out.print("Escreva a sua nova pergunta: ");
				pergunta = Main.scn.nextLine();
				if (pergunta != "")
				{
					System.out.print("Escreva as palavras chaves relacionadas a sua pergunta: ");
					palavrasChave = Main.scn.nextLine();
					System.out.print("Você confirma a nova redação da pergunta[S/N]: " + pergunta);
					confirmacao = Main.scn.next().charAt(0);

					if (confirmacao == 'S' || confirmacao == 's')
					{
						// delete par id palavraChaves antigas do indice invertido
						palavrasChave = palavrasChave + ';';
						palavrasChave = deAccent(palavrasChave);
						palavrasChave.toLowerCase();
						palavrasChave = palavrasChave.replaceAll(" ", ";");
						// inserir par id palavraChaves nova no indice invertido
						// update() crud palavra
						// if(update()crud palavra){ System.out.println("Atualização feita com
						// sucesso");}

					}

				}
			}
			return true;

		case 4:
			// Obter a lista de IDs de perguntas na Árvore B+ usando o ID do usuário
			// lista( idUsuario );
			System.out.println("Qual o número da pergunta que você deseja alterar? ");
			arquivar = Main.scn.nextInt();
			if (arquivar > 0)
			{
				// Questions.toString
				System.out.print("Você confirma o arquivamento dessa pergunta[S/N]: ");
				confirmacao = Main.scn.next().charAt(0);

				if (confirmacao == 'S' || confirmacao == 's')
				{
					// delete par id palavraChaves antigas do indice invertido
					// update() question.ativa = false
					// if(question.ativa == false ){ System.out.println("Remoção feita com
					// sucesso");}
				}
			}
			return true;

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