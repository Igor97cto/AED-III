public interface Register {

    public byte[] toByteArray() throws Exception;

    public void fromByteArray(byte[] adata) throws Exception;

    public void setId(int id)throws Exception;

    public int getId();
}
