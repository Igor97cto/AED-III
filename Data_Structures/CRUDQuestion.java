package Data_Structures;

import java.lang.reflect.Constructor;

public class CRUDQuestion extends CRUD <Question> {
    private ArvoreBMais<ParIdUsuarioPergunta> pariduidp; //par IdUsuario e IdPergunta
    private ListaInvertida lpchave; //Lista palavra chave

	public CRUDQuestion(String filename, Constructor<Question> cstr, boolean deletefolder) throws Exception{
        super(filename, cstr, deletefolder);

        pariduidp = new ArvoreBMais<>(ParIdUsuarioPergunta.class.getConstructor(), 4,
        FOLDER_NAME + "/usuario_pergunta.db");
        lpchave = new ListaInvertida(4, FOLDER_NAME + "/palavras1.db", FOLDER_NAME + "/palavras2.db");
    }

    
    @Override
    public int create(Question quest) throws Exception{
        int idp = super.create(quest);
        pariduidp.create(new ParIdUsuarioPergunta(quest.getIDUsuario(), idp)); //cria o par idUsuario e idPergunta
        String[] palavras = quest.getPC().split(";");

        for( String lpc : palavras){
            lpchave.create(lpc, idp);
        }

        return idp;
    }
    

    public Question[] list(int idu)
    {
        return pariduidp.read();
    }
}