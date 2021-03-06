package sebastian.design.code.schulapp;

/**
 * Created by Sebastian on 08.06.2017.
 */

public class Storage {

    private String message;
    private String time;
    private String sender;


    public Storage(String message, String time, String sender) {
        this.message = message;
        this.time = time;
        this.sender = sender;
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


    @Override
    public String toString() {
        String output = message + " von " + sender;

        return output;
    }
}

