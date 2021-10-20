import Data_Structures.*;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class BackEndInterface
{
	static User usr;
	static Question qst;

  	public static boolean userRegister() throws Exception
	{
		String name;
		String email;
		String password;
		boolean status = true;
		char conf;
		User usr;

		System.out.println("Inserir novo email: ");
		Main.scn.nextLine();
		email = Main.scn.nextLine();
		usr = Main.crdusr.readbyEmail(email);

		// Verificar se email vazio ou muito pequeno
		if (email.length() < 6)
		{
			System.out.println("\nERRO: Email invalido");
		} else if (usr != null)
		{
			System.out.println("\nERRO: Usuario ja cadastrado");
		} else
		{ // senao, cadastrar usuário
			System.out.println("\nInserir o nome do usuário: ");
			name = Main.scn.nextLine();

			System.out.println("\nInserir nova senha: ");
			password = Main.scn.nextLine();

			// Confirmar o cadastro
			System.out.print(
					"\nVocê confirma esses dados? [S-N]\n" + name + "\n" + email + "\n" + password + "\n>Opção: ");
			conf = Main.scn.next().charAt(0);

			// Se confirmar
			if (conf == 'S' || conf == 's')
			{
				Main.crdusr.create(new User(name, email, password));
				System.out.println("Usuário criado com sucesso");
				status = true;
			}
		}

		return status;
	}

  	public static boolean userAccess() throws Exception
	{
		boolean status = false;
		int escolha = -1;
		String email;
		String password;
		User usr = null;

		System.out.println("Insira o email: ");
		Main.scn.nextLine();
		email = Main.scn.nextLine();

		usr = Main.crdusr.readbyEmail(email);

		if (usr != null)
		{
			// verificar se a senha está correta
			System.out.println("Insira a senha: ");
			password = Main.scn.nextLine();

			if (usr.getPassword() != password.hashCode())
			{
				System.out.println("ERRO: Senha incorreta");
				status = true;
			} else
			{
				FrontEndInterface.menuMain();
			}
		} else
		{
			System.out.println("ERRO: Não existe um usuário cadastrado com esse email");
			status = true;
		}
		// retornar pro 1) cadastro
		return status;
	}


	public static String deAccent(String str)
	{
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}


	public static boolean readQuestion() throws Exception
	{
		String pergunta;
		String palavrasChave;
		char confirmacao;

		System.out.print("Escreva a sua pergunta: ");
		pergunta = Main.scn.nextLine();
		
		if(pergunta != "")
		{
			System.out.print("Escreva as palavras chaves relacionadas a sua pergunta: ");
			palavrasChave = Main.scn.nextLine();
			System.out.print("Você confirma a inserção da pergunta[S/N]: " + pergunta);
			confirmacao = Main.scn.next().charAt(0);

			if (confirmacao == 'S' || confirmacao == 's')
			{
				palavrasChave = palavrasChave + ';';
				palavrasChave = deAccent(palavrasChave);
				palavrasChave.toLowerCase();
				palavrasChave = palavrasChave.replaceAll(" ", ";");
				Main.crdqst.create(new Question(usr.getId(), pergunta, palavrasChave));
			}
		}

		return true;
	}

	public static boolean listQuestion() throws Exception
	{
		Question [] qsta;

		System.out.println("Minhas Perguntas");
		qsta= Main.crdqst.list(usr.getId());

		for (Question q : qsta) {
			System.out.println(q);
		}
		
		System.out.println("Pressione qualquer tecla para continuar...");
		Main.scn.next();
		return true;
	}
}