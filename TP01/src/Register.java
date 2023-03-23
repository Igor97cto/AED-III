package src;

import java.io.IOException;

public interface Register {

    public byte[] toByteArray() throws IOException;

    public void setId(int id) throws IOException ;

    public int getId();
}
