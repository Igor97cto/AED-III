package src;

import java.io.File;
import java.io.FileNotFoundException;

public class App {

    public static final String USER_PATH = acquireUserPath();
    public static final String DATA_NAME = "data";
    public static final String DATA_PATH = USER_PATH + "/" + DATA_NAME;


    public static String acquireUserPath()
    {
        String path= System.getProperty("user.dir");

        if(path.contains("src"))
            path = path.substring(0,
                path.lastIndexOf('/'));

        return path;
    }

    public static void mkDirData () throws FileNotFoundException {

        File fp = new File(DATA_PATH);

        if (!(fp.exists())) {

			if (!(fp.mkdir())) {

				throw new FileNotFoundException
                    ("Exceção: Impossível criar ou localizar pasta de dados");
			}

		}
    }

    public static void delDirData () throws FileNotFoundException {

        File fp = new File(DATA_PATH);

        if (!(fp == null)) {
			File[] entries = fp.listFiles();

            for (File file : entries) {
                file.delete();
            }

			fp.delete();
		} 
    }

    public static void main (String[] args)  {
        
        Controller.loadDatabase(DATA_PATH, DATA_NAME);
        
    }
}