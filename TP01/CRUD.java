import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class CRUD <T extends Register> {
	
	private final String FILE_NAME;     //const
	private final String FILE_FORMAT;   //const
	private final String FOLDER_NAME;   //const
	private final String USER_DIR;      //const
	private final boolean DELETE_DB;    //Para testes

	private Constructor<T> cstr;
	private File fp;
	private RandomAccessFile raf;


	public CRUD (String filename, Constructor<T> cstr) throws Exception {
		
		this.FILE_FORMAT= ".db";
		this.FOLDER_NAME= "Data";
		this.FILE_NAME= filename + FILE_FORMAT;
		this.USER_DIR= System.getProperty("user.dir");
		this.DELETE_DB= true;   		//Para testes

		fp= new File(USER_DIR + "/" + FOLDER_NAME);

		if(DELETE_DB)   				//Para testes
		{
			delDB();
		}

		initDB();
	}


	private void initDB () throws Exception {

		if(!(fp.exists())){

			if(!(fp.mkdir())){
			
				throw new FileNotFoundException
				("Impossível criar ou localizar pasta de dados");
			}
			
		}

		raf= new RandomAccessFile
		(USER_DIR + "/" + FOLDER_NAME + "/" +this.FILE_NAME, "rw");

		if(raf.length()== 0)
		{
			raf.writeInt(0);
		}
	}


	private void delDB () throws Exception {

		if(!(fp==null))
		{
			File[]entries = fp.listFiles();

			for(int i = 0; i< entries.length; i++)
			{
				if(entries[i].getName().equals(FILE_NAME))
				entries[i].delete();
			}

			fp.delete();
		}
		else
		{
			throw new FileNotFoundException
			("Impossível deletar ou localizar pasta de dados");
		}

	}


	/** 
	 * Cria um novo registro no arquivo a partir de um objeto passado como
	 * parametro.
	 * 
	 * O metodo le o ultimo id do cabecalho e atualiza com o novo id do registro
	 * a ser criado. Em seguida, insere no final do arquivo, um lapide vazio, o
	 * tamanho do vetor de dados provido pelo objeto e o vetor de dados ao
	 * final.
	 * 
	 * @param obj e o objeto a ser transformado em registro no arquivo
	 * @throws Exception para excecao de qualquer natureza
	 */
	public void create(T obj) throws Exception{

		raf.seek(0);
		int lastid= raf.readInt();

		raf.seek(0);
		obj.setId(++lastid);
		raf.writeInt(lastid);

		raf.seek(raf.length());
		byte[] ba= obj.toByteArray();

		raf.writeByte(0);		//escreve lapide
		raf.writeInt(ba.length);	//escreve o tamanho do registro
		raf.write(ba);				//escreve o registro
	}    

}