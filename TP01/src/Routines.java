package src;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Routines {
    

    public static void loadDatabase (String input, String db) 
    throws NullPointerException,FileNotFoundException, IOException,
        NoSuchMethodException, SecurityException {
        
        String [] buffer;
        AVproducts avp;
        CSVreader csvr = new CSVreader(input);
        CRUD <AVproducts> crud= new CRUD <> (db, AVproducts.class.
            getConstructor(new Class[]{byte[].class}));

        do
        {
            buffer = csvr.readLine();
            if(buffer != null)
            {
                avp = new AVproducts(buffer);
                crud.create(avp);
            }
        }while(buffer != null);
    }

    
}
