package src;

public class BadCSVFormatException extends Exception {
    
    String[] fields;
    String[] values;

    public BadCSVFormatException (String[] fields, String[] values) {
        super();
        this.fields= fields;
        this.values= values;
    }

    public BadCSVFormatException (Throwable cause)
    {
        super(cause);
    }

    public String toString () {

        int fsz= fields.length;
        int vsz= values.length;
        String msg = "Os valores obtidos não condizem com a quantidade de"
            + "campos, pois a quantidade de campos: " + fsz;

        if(fsz > vsz) {

            msg+= ", é maior que a quantidade de valores: ";
        }
        else
        {
            msg+= ", é menor que a quantidade de valores: ";
        }

        msg+= vsz + ", obtidos durante a leitura";

        return msg;
    }
}
