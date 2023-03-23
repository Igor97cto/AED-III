package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CRUD <T extends Register> {
	
	private final String FILE_NAME;     //const
	private final String FILE_FORMAT;   //const
	private final String FOLDER_NAME;   //const
	private final String USER_DIR;      //const
	private final boolean DELETE_DB;    //Para testes

	private Constructor<T> cstr;
	private File fp;
	private RandomAccessFile raf;


	public CRUD (String filename, Constructor<T> cstr) throws IOException {
		
		this.FILE_FORMAT= ".db";
		this.FOLDER_NAME= "Data";
		this.FILE_NAME= filename + FILE_FORMAT;
		this.USER_DIR= System.getProperty("user.dir");
		this.DELETE_DB= true;   		//Para testes
		this.cstr= cstr;

		fp= new File(USER_DIR + "/" + FOLDER_NAME);

		if(DELETE_DB)
		delDB();						//Para testes		

		initDB();
	}


	private void delDB () throws FileNotFoundException {

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
			("Exceção: Impossível deletar ou localizar pasta de dados");
		}

	}


	private void initDB () throws IOException {

		if(!(fp.exists())){

			if(!(fp.mkdir())){
			
				throw new FileNotFoundException
				("Exceção: Impossível criar ou localizar pasta de dados");
			}
			
		}

		raf= new RandomAccessFile
		(USER_DIR + "/" + FOLDER_NAME + "/" +this.FILE_NAME, "rw");

		if(raf.length()== 0)
		{
			raf.writeInt(0);			//Escreve o id inicial
		}
	}


	//Metodos auxiliares


	public long getRegPosition(int id) throws IOException {
		boolean grave= false;
		byte[] buffer= null;
		int regsize= 0;
		long headersize= 4;
		long hp= headersize;
		T obj= null;

		raf.seek(headersize);

		do
		{
			try
			{
				hp= raf.getFilePointer();
				grave= (raf.readByte() & 1)== 0;
				regsize= raf.readInt();
				buffer= new byte[regsize];
				raf.read(buffer);

				if(grave)
				obj= cstr.newInstance(buffer);
			}
			catch(Exception e)
			{
				return -1;
			}
		}while(obj== null || obj.getId() != id);

		return hp;
	}


	public T getObjByPointer(long hp) throws IOException,
		InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		byte[] buffer= null;
		int regsize= 0;					
		T obj= null;
		
		if(hp > 0)
		{
			raf.seek(++hp);
			regsize= raf.readInt();
			buffer= new byte[regsize];
			raf.read(buffer);
			obj= cstr.newInstance(buffer);
		}

		return obj;
	}


	//Métodos do CRUD

	public void create(T obj) throws IOException {

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


	public T read(int id) throws IOException, InstantiationException,
		IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		return getObjByPointer(getRegPosition(id));
	}


	public boolean update(T obj) throws IOException {

		long hp= getRegPosition(obj.getId());
		byte[] buffer= null;
		int regsize= 0;

		if(hp > 0)
		{
			raf.seek(hp + 1);
			regsize= raf.readInt();
			buffer= obj.toByteArray();

			if(buffer.length <= regsize)
			{
				raf.seek(hp);
				raf.writeInt(buffer.length);
				raf.write(buffer);
				return true;
			}
			else
			{
				raf.seek(hp - 1);
				raf.writeByte(1);

				raf.seek(raf.length());
				raf.writeByte(0); //gravestone
				raf.writeInt(buffer.length); //size of the register
				raf.write(buffer);//register
				return true;
			}
		}

		return false;
	}


	public T delete(int id) throws IOException, InstantiationException,
		IllegalAccessException, IllegalArgumentException,
			InvocationTargetException  {

		long hp= getRegPosition(id);
		T obj= null;

		if(hp > 0)
		{
			obj= getObjByPointer(hp);
			raf.seek(hp);
			raf.writeByte(1);
		}

		return obj;
	}
}