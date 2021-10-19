package Data_Structures;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class User implements Register
{
	int id;
	String name;
	String email;
	int password;
	
	
	public User()
	{
		this.id = -1;
		this.name = null;
		this.email = null;
		this.password = -1;
	}
	
	
	public User(String name, String email, String password)
	{
		this.id = -1;
		this.name = name;
		this.email = email;
		this.password = password.hashCode();
	}


	public User(byte[] adata) throws Exception
	{
		ByteArrayInputStream bais= new ByteArrayInputStream(adata);
		DataInputStream dis= new DataInputStream(bais);

		id= dis.readInt();
		name= dis.readUTF();
		email= dis.readUTF();
		password= dis.readInt();
	}


  	public void fromByteArray(byte[] adata) throws Exception
	{
		ByteArrayInputStream bais= new ByteArrayInputStream(adata);
    	DataInputStream dis= new DataInputStream(bais);
	
    	id= dis.readInt();
    	name= dis.readUTF();
    	email= dis.readUTF();
    	password= dis.readInt();
  	}	


	public byte[] toByteArray() throws Exception
  	{
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		DataOutputStream dos= new DataOutputStream(baos);

		dos.writeInt(id);
		dos.writeUTF(name);
		dos.writeUTF(email);
		dos.writeInt(password);

		return baos.toByteArray();
  	}


	public int getId()
	{
		return id;
	}


	public String getName()
	{
		return name;
	}


	public String getEmail()
	{
		return email;
	}


	public int getPassword()
	{
		return password;
	} 


	public void setId(int id)
	{
		this.id = id;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public void setEmail(String email)
	{
		this.email = email;
	}


	public void setPassword(String password)
	{
		this.password = password.hashCode();
	}


  @Override
  public String toString()
  {
    return "Id: " + getId() + "\n"
          + "Name: " + getName() + "\n"
          + "Email: " + getEmail() + "\n";
  }
  
}