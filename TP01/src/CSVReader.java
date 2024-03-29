package src;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.ArrayList;


public class CSVReader {
    
    private final String PATH;

    private BufferedReader breader;
    private int fieldnum;
    private String [] fields;
    private String [] values;
    

    public CSVReader(String path) throws FileNotFoundException, IOException {

        this.PATH= path;
        breader= new BufferedReader (new FileReader(PATH));

        try {fields = readLine();}
        catch (BadCSVFormatException bcfe) {
            new BadCSVFormatException(new NullPointerException());
        }//CSV vazio

        fieldnum= fields.length;
    }
 
    public void close() throws IOException{

        breader.close();
    }

    public String[] readLine () throws IOException, BadCSVFormatException {

        boolean out= true;
        String buffer= breader.readLine();
        ArrayList <String> values= new ArrayList<>();
        StringBuilder stb= new StringBuilder();

        for(int i = 0; i < buffer.length(); i++)
        {
            if(buffer.charAt(i) == ',' && out) {

                values.add(stb.toString());
                stb= new StringBuilder();
            }
            else if(buffer.charAt(i) == '\"') {

                out= !out;
            }
            else
            {
                stb.append(buffer.charAt(i));
            }

        }

        values.add(stb.toString());
        this.values= values.toArray(new String[values.size()]);

        if(fieldnum != this.values.length){

            throw new BadCSVFormatException(fields, this.values);
        }

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
