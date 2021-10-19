import Data_Structures.*;

public class BackEndInterface
{
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
		usr = Main.crd.readbyEmail(email);

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
				Main.crd.create(new User(name, email, password));
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

		usr = Main.crd.readbyEmail(email);

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
}