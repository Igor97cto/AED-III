package src;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    
    public static void loadDatabase (String input, String db) 
    throws NullPointerException,FileNotFoundException, IOException,
        NoSuchMethodException, SecurityException, BadCSVFormatException {
        
        String [] buffer;
        AVproducts avp;
        CSVReader csvr = new CSVReader(input);
        CRUD <AVproducts> crud = 
            new CRUD <> (db, AVproducts.class.getConstructor(
                new Class[] {byte[].class}));

        buffer = csvr.readLine();
            
        while(buffer != null) {

            avp = new AVproducts(buffer);
            crud.create(avp);
            buffer = csvr.readLine();
        }

        csvr.close();
        crud.close();
    }

    
}
