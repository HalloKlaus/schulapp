package sebastian.design.code.schulapp;

/**
 * Created by Sebastian on 08.06.2017.
 */

public class Storage {

    private String message;
    private String time;
    private String sender;
    private long id;


    public Storage(String message, String time, String sender, long id) {
        this.message = message;
        this.time = time;
        this.sender = sender;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        String output = message + " + " + time + " + " + sender;

        return output;
    }
}

