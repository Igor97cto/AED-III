import Data_Structures.*;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Controller
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
		usr = null;

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
				View.menuMain();
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


	public static boolean createQuestion() throws Exception
	{
		String pergunta;
		String palavrasChave;
		char confirmacao;

		Main.scn.nextLine();
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
				palavrasChave = palavrasChave.toLowerCase();
				palavrasChave = palavrasChave.replaceAll(",", "");
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
		try
        {
            System.in.read();
        }  
        catch(Exception e)
        {
			return false;
		}

		return true;
	}


	public static boolean updateQuestion() throws Exception
	{
		/* System.out.println("Qual o número da pergunta que você deseja alterar? ");
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
		} */

		return true;
	}


	public static boolean fileQuestion()
	{
		/* System.out.println("Qual o número da pergunta que você deseja alterar? ");
		arquivar = Main.scn.nextInt();
		if (arquivar > 0)
		{
			// Questions.toString
			System.out.print("Você confirma o arquivamento dessa pergunta[S/N]: ");
			confirmacao = Main.scn.next().charAt(0);

			if (confirmacao == 'S' || confirmacao == 's')
			{
				// delete par id palavraChaves antigas do indice invertido
				// update() Exclusão de usuários
No nosso projeto, não estamos prevendo a exclusão de usuários. Caso você decida implementar essa possibilidade, a ser executada exclusivamente pelo administrador do sistema, deverá excluir também todas as perguntas, respostas, comentários e votos vinculados a esse usuário. Para isso, poderá usar a árvore B+ do relacionamento de cada entidade para obter os IDs das entidades vinculadas e excluirquestion.ativa = false
				// if(question.ativa == false ){ System.out.println("Remoção feita com
				// sucesso");}
			}
		} */

		return true;
	}
}