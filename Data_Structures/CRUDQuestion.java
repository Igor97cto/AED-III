package Data_Structures;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class CRUDQuestion extends CRUD <Question> {

    private final static String FILE_NAME= "question_data";
    private final static String HASH_NAME= "question_hash";
    private ArvoreBMais <ParIdUsuarioPergunta> ixidpergunta; //par IdUsuario e IdPergunta
    private ListaInvertida lpchave; //Lista palavra chave

	public CRUDQuestion(Constructor<Question> cstr, boolean deletefolder) throws Exception{

        super(FILE_NAME, HASH_NAME, cstr, deletefolder);

        ixidpergunta = new ArvoreBMais<>(ParIdUsuarioPergunta.class.getConstructor(), 4,
        FOLDER_NAME + "/usuario_pergunta.db");
        lpchave = new ListaInvertida(4, FOLDER_NAME + "/palavras1.db", FOLDER_NAME + "/palavras2.db");
    }

    
    @Override
    public int create(Question quest) throws Exception{
        int idp = super.create(quest);
        ixidpergunta.create(new ParIdUsuarioPergunta(quest.getIDUsuario(), idp)); //cria o par idUsuario e idPergunta
        String[] palavras = quest.getPC().split(";");

        for( String lpc : palavras){
            lpchave.create(lpc, idp);
        }

        return idp;
    }
    

    public Question[] list(int idu) throws Exception
    {
        byte[] ba;
        ByteArrayInputStream bais;
		DataInputStream dis;

        ArrayList<ParIdUsuarioPergunta> arr =
            ixidpergunta.read(new ParIdUsuarioPergunta(idu, -1));

        Question[] qst= new Question[arr.size()];
        
        for(int i= 0; i< qst.length; i++)
        {
            ba= arr.get(i).toByteArray();
            bais= new ByteArrayInputStream(ba);
            dis= new DataInputStream(bais);
            dis.skipBytes(4);

            qst[i]= read(dis.readInt());//registro
        }

        return qst;
    }


/*     @Override
    public boolean delete(int id) throws Exception{
        Question quest = super.read(id);

        if(quest != null) && (super.delete(id))){
            String[] palavras = quest.getPC().split(";");

            for( String lpc : palavras){
                lpchave.create(lpc, idp);
                return true;
            }
        }
        return false;
    } */
}