package Data_Structures;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;

public class Question implements Register
{
	int idPergunta;
  int idUsuario;
  long criacao;
  short nota;
  String pergunta;
  String palavrasChave;
  boolean ativa;
  Date date;
  
	
	
	public Question(int idUsuario)
	{
		this.idPergunta = -1;
    this.idUsuario = idUsuario;
    this.criacao = -1;
    this.nota = 0;
		this.pergunta = null;
		this.palavrasChave = null;
		this.ativa = false;
	}
	
	
	public Question(int idUsuario, String pergunta, String palavrasChave)
	{
		this.idPergunta = -1;
    this.idUsuario = idUsuario;
    this.criacao = date.getTime();
    this.nota = 0;
		this.pergunta = pergunta;
		this.palavrasChave = palavrasChave;
		this.ativa = true;
	}


	public Question(byte[] adata) throws Exception
	{
		ByteArrayInputStream bais= new ByteArrayInputStream(adata);
		DataInputStream dis= new DataInputStream(bais);

		idPergunta = dis.readInt();
    idUsuario = dis.readInt();
    criacao = dis.readLong();
    nota = dis.readShort();
    pergunta = dis.readUTF();
    palavrasChave = dis.readUTF();
  }


  public void fromByteArray(byte[] adata) throws Exception
	{
		ByteArrayInputStream bais= new ByteArrayInputStream(adata);
		DataInputStream dis= new DataInputStream(bais);

		idPergunta = dis.readInt();
    idUsuario = dis.readInt();
    criacao = dis.readLong();
    nota = dis.readShort();
    pergunta = dis.readUTF();
    palavrasChave = dis.readUTF();
  }	


	public byte[] toByteArray() throws Exception
  {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		DataOutputStream dos= new DataOutputStream(baos);

		dos.writeInt(idPergunta);
		dos.writeInt(idUsuario);
    dos.writeLong(criacao);
    dos.writeShort(nota);
    dos.writeUTF(pergunta);
		dos.writeUTF(palavrasChave);

		return baos.toByteArray();
  }


	public int getId()
	{
		return idPergunta;
	}
  
  public long getCriacao()
	{
		return criacao;
	}

  public short getNota()
	{
		return nota;
	}

	public String getPergunta()
	{
		return pergunta;
	}


	public String getPC()
	{
		return palavrasChave;
	}

	public void setId(int idPergunta)
	{
		this.idPergunta = idPergunta;
	}


	public void setCriacao(long criacao)
	{
		this.criacao = criacao;
	}


	public void setNota(short nota)
	{
		this.nota = nota;
	}


	public void setPergunta(String pergunta)
	{
		this.pergunta = pergunta;
	}

  public void setPC(String palavrasChave)
	{
		this.palavrasChave = palavrasChave;
	}


  @Override
  public String toString()
  {
    return  "IdPergunta: " + getId() + "\n"
          + "Data: " + getCriacao() + "\n"
          + "Nota: " + getNota() + "\n"
          + "Pergunta: " + getPergunta() + "\n"
          + "Palavras-chave: " + getPC() + "\n";
  }
  
}