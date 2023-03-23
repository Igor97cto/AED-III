package src;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TimeZone;

public class AVproducts implements Register {
    
    private int id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private LocalDateTime date_added;
    private LocalDateTime release_year;
    private String rating; 
    private String duration;
    private String[] listed_in;
    private String description;


    public AVproducts(String type, String title, String director,
        String[] cast, String country, String date_added, String release_year,
            String rating, String duration, String listed_in,
                String description) {

        DateTimeFormatter dtlong= 
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);//US date format
        
        DateTimeFormatter dtyear=
            DateTimeFormatter.ofPattern("yyyy");//only year

        this.id = -1;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.date_added = LocalDateTime.parse(date_added, dtlong);
        this.release_year = LocalDateTime.parse(release_year, dtyear);
        this.rating = rating;
        this.duration = duration;
        this.listed_in = listed_in.split(", ");
        this.description = description;
    }


    public AVproducts (String[] csvdata) {

        DateTimeFormatter dtlong= 
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);

        DateTimeFormatter dtyear=
            DateTimeFormatter.ofPattern("yyyy");//only year

        id= -1;
        type = csvdata[1];
        title = csvdata[2];
        director= csvdata[3];
        cast = csvdata[4].split(", ");
        country = csvdata[5];
        date_added = LocalDateTime.parse(csvdata[6], dtlong);
        release_year = LocalDateTime.parse(csvdata[7], dtyear);;
        rating = csvdata[8];
        duration = csvdata[9];
        listed_in = csvdata[10].split(", ");
        description = csvdata[11];
    }


    public AVproducts (byte[] regdata) throws IOException {
        
        int cast_arraylen;
        int listed_in_arraylen;

        ByteArrayInputStream input= new ByteArrayInputStream(regdata);
        DataInputStream reader= new DataInputStream(input);

        id= reader.readInt();
        type = reader.readUTF();
        title = reader.readUTF();
        director= reader.readUTF();
        cast_arraylen = reader.readByte();
        cast = readUTFArray(cast_arraylen, reader);
        country = reader.readUTF();
        date_added =  LocalDateTime.ofInstant(Instant.ofEpochMilli
        (reader.readLong()), TimeZone.getTimeZone("US/Eastern").toZoneId());
        release_year = LocalDateTime.ofInstant(Instant.ofEpochMilli
        (reader.readLong()), TimeZone.getTimeZone("US/Eastern").toZoneId());
        rating = reader.readUTF();
        duration = reader.readUTF();
        listed_in_arraylen= reader.readByte();
        listed_in = readUTFArray(listed_in_arraylen, reader);
        description = reader.readUTF();
    }


    public byte[] toByteArray () throws IOException {
        
        ByteArrayOutputStream output= new ByteArrayOutputStream();
        DataOutputStream writer= new DataOutputStream(output);

        writer.writeInt(id);
        writer.writeUTF(type);
        writer.writeUTF(title);
        writer.writeUTF(director);
        writeUTFArray(cast, writer);
        writer.writeUTF(country);
        writer.writeLong(ZonedDateTime.of(date_added,
            TimeZone.getTimeZone("US/Eastern").toZoneId()).toInstant().
                toEpochMilli());
        writer.writeLong(ZonedDateTime.of(release_year,
            TimeZone.getTimeZone("US/Eastern").toZoneId()).toInstant().
                toEpochMilli());
        writer.writeUTF(rating); 
        writer.writeUTF(duration);
        writeUTFArray(listed_in, writer);
        writer.writeUTF(description);

        return output.toByteArray();
    }

    
    private String[] readUTFArray (int len, DataInputStream reader)
        throws IOException {

        String[] array= new String[len];

        for(int i = 0; i < len ; i++){

            array[i] = reader.readUTF();
        }

        return array;
    }


    private void writeUTFArray (String[] values, DataOutputStream writer)
        throws IOException {

        int len= values.length;

        if(len > 256)
        len= 256;

        writer.writeByte(len);

        for(int i= 0; i < len ; i++){

            writer.writeUTF(values[i]);
        }
    }


    public int getId() {
        return id;
    }


    public String getType() {
        return type;
    }


    public String getTitle() {
        return title;
    }


    public String getDirector() {
        return director;
    }


    public String[] getCast() {
        return cast;
    }


    public String getCountry() {
        return country;
    }


    public String getDate_added() {

        //FormatStyle.LONG recomendado

        DateTimeFormatter dtlong= 
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        
        return date_added.format(dtlong);
    }


    public String getRelease_year() {
        
        DateTimeFormatter dtyear=
            DateTimeFormatter.ofPattern("yyyy");//only year

        return release_year.format(dtyear);
    }


    public String getRating() {
        return rating;
    }


    public String getDuration() {
        return duration;
    }


    public String[] getListed_in() {
        return listed_in;
    }


    public String getDescription() {
        return description;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setDirector(String director) {
        this.director = director;
    }


    public void setCast(String[] cast) {
        this.cast = cast;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public void setDate_added(String date_added) {
        
        this.date_added = LocalDateTime.parse(date_added,
            DateTimeFormatter.ofPattern("yyyy"));
    }


    public void setDate_added(LocalDateTime date_added) {

        this.date_added = date_added;
    }


    public void setRelease_year(String release_year) {
        
        this.release_year = LocalDateTime.parse(release_year,
            DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }


    public void setRelease_year(LocalDateTime release_year) {

        this.release_year= release_year;
    }


    public void setRating(String rating) {
        this.rating = rating;
    }


    public void setDuration(String duration) {
        this.duration = duration;
    }


    public void setListed_in(String listed_in) {
        this.listed_in = listed_in.split(", ");
    }


    public void setDescription(String description) {
        this.description = description;
    }

}