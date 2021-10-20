package Data_Structures;

import java.lang.reflect.Constructor;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class CRUDUser extends CRUD <User>
{
  	private HashExtensivel<ParEmailID> ixemailid;//index EmailId


    public CRUDUser(String filename, Constructor<User> cstr, boolean deletefolder) throws Exception
    {
        super(filename, cstr, deletefolder);

        //Cria indice hash de Email e ID
        ixemailid= new HashExtensivel<>(ParEmailID.class.getConstructor(), 4,
        USER_DIR + "/" + FOLDER_NAME + "/" + ".hash_d_ixemailid.db",
                USER_DIR + "/" + FOLDER_NAME + "/" + ".hash_c_ixemailid.db");
    }


    //O tipo User e o tipo T funcionam como assinaturas identicas do metodo?
    @Override
    public int create(User usr) throws Exception
    {
        int id= super.create(usr);

		//cria indece email e id
		ixemailid.create(new ParEmailID(usr.getEmail(), id));
        
        return id;
    }


    private int getIdByEmail(String email) throws Exception
	{
		byte[] ba;
		ByteArrayInputStream bais;
		DataInputStream dis;

		if(ixemailid.read(email.hashCode()) != null)
		{
			ba = ixemailid.read(email.hashCode()).toByteArray();
			bais = new ByteArrayInputStream(ba);
			dis = new DataInputStream(bais);

			dis.skipBytes(dis.readUnsignedShort());
			return dis.readInt();
		}

		return -1;
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

  	public User readbyEmail(String email) throws Exception
    {
        return read(getIdByEmail(email));
    }
}
