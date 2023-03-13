import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.ArrayList;


public class CSVreader {
    
    private final String FPATH;

    private BufferedReader breader;
    private int fieldnum;
    private String [] fields;
    private String [] values;
    

    public CSVreader(String path) throws NullPointerException,
        FileNotFoundException, IOException{

        this.FPATH= path;
        breader= new BufferedReader (new FileReader(FPATH));
        fields = readLine();
        fieldnum= fields.length;
    }
 
    public void close() throws IOException{

        breader.close();
    }


    public String[] readLine () throws IOException {

        boolean lock= true;
        String buffer= breader.readLine();
        ArrayList <String> values= new ArrayList<>();
        StringBuilder stb= new StringBuilder();

        for(int i = 0; i < buffer.length(); i++)
        {
            if(buffer.charAt(i) == ',' && lock) {

                values.add(stb.toString());
                stb= new StringBuilder();
            }
            else if(buffer.charAt(i) == '\"') {

                lock= !lock;
            }
            else
            {
                stb.append(buffer.charAt(i));
            }

        }

        values.add(stb.toString());
        this.values= values.toArray(new String[values.size()]);

        return this.values;
    }

    private String getString (String [] array){

        StringBuilder sb= new StringBuilder();

        for(String aux: array)
        {
            sb.append(aux);
            sb.append(", ");
        }

        if(sb.length() >= 2)
        sb.replace(sb.length()-2, sb.length(), "");

        return sb.toString();
        
    }

    public String[] getFields () {

        return fields;
    }

    public String getFieldNames () {

        return getString(fields);
    }

    public int getFieldNum () {

        return fieldnum;
    }

    public String[] getValues () {

        return values;
    }

    public String getValuesString () {

        return getString(values);
    }

    public String toString (){

        return  getFieldNames() + '\n' + getValuesString();
    }
}
