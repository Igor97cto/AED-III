import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws NullPointerException,
        FileNotFoundException, IOException {
       
        //System.out.println(System.getProperty("user.dir"));
        String path = "/home/igor/Área de Trabalho/AED III/TP01/Data/netflix.csv";
        CSVreader csvr= new CSVreader(path);
       
        System.out.println(csvr);

        for(int i= 0; i < 10 ; i++) {
            csvr.readLine();
            System.out.println(csvr);
        }
    }
}