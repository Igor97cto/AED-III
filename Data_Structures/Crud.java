package Data_Structures;

import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * A instancia desta classe permite a criacao e a manipulacao de um arquivo de
 * registros indexados derivados de objetos de tipos genericos compativeis. O
 * {@code Crud}, trabalha com a troca de informacao binaria, que requisita ou
 * passa aos objetos, vetores de bytes como recuperacao ou passagem de
 * parametros. E valido salientar que para uma classe generica possuir
 * compatibilidade com esta, a {@code Crud}, e necessario que a classe generica
 * implemente a interface {@code Register}.
 */

public class Crud <T extends Register>
{
	
	private final String FILE_NAME;
	private final String FILE_FORMAT;
	private final String FOLDER_NAME;
	private final String USER_DIR;

	private Constructor<T> cstr;
	private File flp;
	private HashExtensivel<ParIDEndereco> ixi;  //index IDEndereco
  	private HashExtensivel<ParEmailID> ixe;     //index EmailId
	private RandomAccessFile raf;
	

	/**
	 * Cria um novo manipulador de registros genericos compativeis, o arquivo
	 * onde sera armazenado os registros, e os arquivos da tabela hash.
	 * 
	 * O arquivo possui o formato pre-determinado pelas constantes, e se
	 * encontra dentro de uma pasta com o nome tambem pre-determinado.
	 * 
	 * O construtor inicializa as variaveis constantes que pre-determinam uma
	 * estrutura padrao de arquitetura e informacoes do arquivo, em seguida
	 * recebe o construtor da classe generica para que seus objetos possam ser
	 * manipulados.
	 * 
	 * Tambem verifica se o caminho de criacao da pasta com os arquivo des dados
	 * existe. Caso nao exista a pasta e o arquivo sao criados.
	 * 
	 * @param filename nome do arquivo a escolha
	 * @param cstr construtor do objeto da classe generica compativel
	 * @throws Exception para excecao de qualquer natureza
	 */

	public Crud(String filename, Constructor<T> cstr, boolean deletefolder)throws Exception
	{
		this.cstr= cstr;
		this.FILE_FORMAT= ".bin";
		this.FOLDER_NAME= "Data";
		this.FILE_NAME= filename + FILE_FORMAT;
		this.USER_DIR= System.getProperty("user.dir");
		
		//Checa se a pasta existe
		flp= new File(USER_DIR + "/" + FOLDER_NAME);
		
		
		//Se nao existe, cria
		if(!(flp.exists()))
		{
			flp.mkdir();
		}
		//Se existir, deleta a instancia antiga e cria uma nova
		else if(deletefolder)
		{
			deleteDataFolder();
			flp.mkdir();
		}
		
    //Cria arquivo de registro
		raf= new RandomAccessFile(USER_DIR + "/" + FOLDER_NAME
			+ "/" +this.FILE_NAME, "rw");

    //Cria indice de ID e endereco
		ixi= new HashExtensivel<>(ParIDEndereco.class.getConstructor(), 4,
			USER_DIR + "/" + FOLDER_NAME + "/" + ".hash_d_ixi.db",
				USER_DIR + "/" + FOLDER_NAME + "/" + ".hash_c_ixi.db");

    //Cria indice de Email e ID
    ixe= new HashExtensivel<>(ParEmailID.class.getConstructor(), 4,
      USER_DIR + "/" + FOLDER_NAME + "/" + ".hash_d_ixe.db",
				USER_DIR + "/" + FOLDER_NAME + "/" + ".hash_c_ixe.db");

		if(raf.length()== 0)
		{
			raf.writeInt(0);
		}
	}


	/** 
	 * Cria um novo registro no arquivo a partir de um objeto passado como
	 * parametro.
	 * 
	 * O metodo le o ultimo id do cabecalho e atualiza com o novo id do registro
	 * a ser criado. Em seguida, insere no final do arquivo, um lapide vazio, o
	 * tamanho do vetor de dados provido pelo objeto, o vetor de dados, e passa
	 * para os arquivos de dados da hash o endereço do registro inserido.
	 * 
	 * @param obj e o objeto a ser transformado em registro no arquivo
	 * @throws Exception para excecao de qualquer natureza
	 */

	public void create(T obj) throws Exception
	{
		int id;
		String email;

		raf.seek(0);

		int lastid= raf.readInt();

		raf.seek(0);
		obj.setId(++lastid);
		raf.writeInt(lastid);
		raf.seek(raf.length());
		byte[] ba= obj.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DataInputStream dis = new DataInputStream(bais);

		id= dis.readInt();
		dis.skipBytes(dis.readShort());
		email= dis.readUTF();

		//indece id e endereco
		ixi.create(new ParIDEndereco(lastid, raf.getFilePointer()));

		//indece email e id
		ixe.create(new ParEmailID(email, id));

		raf.writeByte(0); //gravestone
		raf.writeInt(ba.length); //size of the register
		raf.write(ba);//register
	}


	/** 
	 * Fecha o arquivo manipulado pelo {@code Crud}
	 * 
	 * @throws Exception para excecao de qualquer natureza
	 */

	public void close()throws Exception
	{
		raf.close();
	}


	/** 
	 * Deleta o registro no arquivo, marcando o lapide do registro que possui
	 * o id de valor {@code id} passado como parametro.
	 * 
	 * O metodo lê posicao na tabela hash onde se encontra o registro de valor
	 * {@code id}. Caso o valor do ponteiro retornado pela tabela seja nulo, o
	 * metodo retorna {@value null}. Se o ponteiro retornado pela tabela hash
	 * nao for nulo, o metodo marca a lapide com o valor {@value 1} e em seguida
	 * retorna um objeto correspondente ao registro deletado.
	 * 
	 * @param id valor do registro a ser procurado
	 * @return T objeto correspondente ao registro deletado
	 * @throws Exception para excecao de qualquer natureza
	 */

	public T delete(int id)throws Exception
	{
		T obj= null;
		long hp;
		byte[] ba= ixi.read(id).toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DataInputStream dis = new DataInputStream(bais);

		dis.skipBytes(4);
		
		hp= dis.readLong();

		if(hp > 0)
		{
			obj= getObjByPointer(hp);
			raf.seek(hp);
			raf.writeByte(1);
		}

		return obj;
	}


	/**
	 * Deleta a pasta de dados com todos os arquivos de registro.
	 * 
	 * O metodo verifica se o arquivo que o Crud esta manipulando, esta aberto
	 * ainda. Caso esteja aberto, fecha o arquivo. Em seguida, cria um vetor
	 * {@code Entries} contendo uma lista de todos os arquivos contidos no
	 * caminho da pasta de dados. Ao final, o metodo percorre a lista,
	 * deletando um por um, todos os arquivos de dados, e por ultimo, deleta a
	 * pasta de dados.
	 * 
	 * @throws Exception para excecao de qualquer natureza
	 * @return {@value true} se foi possivel deletar
	 */

	public boolean deleteDataFolder()throws Exception
	{
		if(raf!= null)
		{
			raf.close();
		}
		else if(flp== null)
		{
			return false;
		}
	
		String[]entries = flp.list();

		for(int i = 0; i< entries.length; i++)
		{
			File currentFile = new File(flp.getPath(), entries[i]);
			currentFile.delete();
		}

		flp.delete();

		return true;
	}


	/** 
	 * Le um registro na posicao de valor {@code hp} passado por parametro.
	 * 
	 * O metodo verifica se o valor passado por parametro nao e nulo, caso seja,
	 * retorna um objeto de valor {@value null}. Se nao for nulo, o cabecote de
	 * de leitura salta para o valor contido em {@code hp}. Em seguida, e feita
	 * a leitura do registro naquela regiao apontada por {@code hp}. Ao final,
	 * os dados recuperados pela leitura sao inseridos em um objeto, e este
	 * objeto e retornado pelo metodo.
	 * 
	 * @param hp aponta para onde o cabecote de leitura deve deslocar
	 * @return T objeto resultante da leitura 
	 * @throws Exception para excecao de qualquer natureza
	 */

	public T getObjByPointer(long hp) throws Exception
	{
		boolean grave= false;
		byte[] ba= null;
		T obj= null;
		int size= 0;

		if(hp > 0)
		{
			raf.seek(hp);

			grave= (raf.readByte() & 1)== 0;
			size= raf.readInt();
			ba= new byte[size];
			raf.read(ba);

			if(grave)
			obj= cstr.newInstance(ba);
		}

		return obj;
	}


	private long getAddrsById(int id) throws Exception
	{
		byte[] ba;
		ByteArrayInputStream bais;
		DataInputStream dis;

		if(ixi.read(id) != null)
		{
			ba = ixi.read(id).toByteArray();
			bais = new ByteArrayInputStream(ba);
			dis = new DataInputStream(bais);

			dis.skipBytes(4);
			
			return dis.readLong();
		}
			
		return -1;
	} 


	private int getIdByEmail(String email) throws Exception
	{
		byte[] ba;
		ByteArrayInputStream bais;
		DataInputStream dis;

		if(ixe.read(email.hashCode()) != null)
		{
			ba = ixe.read(email.hashCode()).toByteArray();
			bais = new ByteArrayInputStream(ba);
			dis = new DataInputStream(bais);

			dis.skipBytes(dis.readUnsignedShort());
			return dis.readInt();
		}

		return -1;
	}


	/** 
	 * Lê um registro de id igual ao valor do parametro {@code id} na tabela
	 * hash e em seguida retorna um objeto criado a partir da leitura deste
	 * registro.
	 * 
	 * O metodo obtem a posicao do cabecote onde esta o registro na tabela hash,
	 * em seguida este valor e passado ao metodo {@code getObjPointer()} que le
	 * o registro e cria um objeto correspondente. Por fim, o objeto e
	 * retornado.
	 * 
	 * @param id valor do registro a ser procurado
	 * @return T objeto correspondente ao registro deletado
	 * @throws Exception para excecao de qualquer natureza
	 */

	public T readbyId(int id) throws Exception
	{
		return getObjByPointer(getAddrsById(id));
	}

   /** 
	* Lê um {@code String email} igual ao valor do parametro {@code email}
    * na tabela hash de EmailId, recupera o id, em seguida busca o endereco
    * do objeto na hash de IdEndereco e retorna um objeto criado a partir da
    * leitura deste registro.
	* 
	* O metodo obtem a posicao do cabecote onde esta o registro na tabela hash
    * de IdEndereco em seguida este valor e passado ao metodo
    * {@code getObjPointer()} que le o registro e cria um objeto correspondente.
    * Por fim, o objeto e retornado.
	* 
	* @param id valor do registro a ser procurado
	* @return T objeto correspondente ao registro deletado
	* @throws Exception para excecao de qualquer natureza
	*/

  	public T readbyEmail(String email) throws Exception
	{
		return readbyId(getIdByEmail(email));
	}


	/** 
	 * Recupera um registro de id igual ao valor do parametro {@code id}, e
	 * atualiza os valores do registro caso o id exista.
	 *
	 * O metodo obtem a posicao do registro correspondente ao {@code obj.id},
	 * através da leitura da tabela hash. Caso o valor obtido nao seja nulo, o
	 * metodo verifica se o espaco necessario para alocar o registro atualizado
	 * e menor ou igual ao espaco do registro original.
	 * 
	 * Caso o valor seja menor ou igual ao espaco do registro original, o
	 * registro e escrito no espaco do registro original. Caso o valor seja
	 * maior que o espaco do registro original, a lapide do registro original
	 * e marcada, e o cabecote de leitura e escrita salta para o fim do arquivo
	 * e escreve o registro atualizado.
	 * 
	 * @param obj
	 * @return {@value true} se foi possivel atualizar
	 * @throws Exception
	 */

	public boolean update(T obj)throws Exception
	{
		long hp= getAddrsById(obj.getId());
		byte[] baobj= null;
		int regsize= 0;

		if(hp > 0)
		{
			raf.seek(hp + 1);
			regsize= raf.readInt();
			baobj= obj.toByteArray();

			if(baobj.length <= regsize)
			{
				raf.write(baobj);
				return true;
			}
			else
			{
				raf.seek(hp);
				raf.writeByte(1);

				raf.seek(raf.length());
				raf.writeByte(0); //gravestone
				raf.writeInt(baobj.length); //size of the register
				raf.write(baobj);//register
				return true;
			}
		}
		else
		{
			return false;
		}
	}
}