package sebastian.design.code.schulapp;

/**
 * Created by Sebastian on 09.06.2017.
 */

public class NewsMessage {

    public String newsMsg, newsSender, newsTimestamp;

    public NewsMessage(String newsMsg, String newsSender, String newsTimestamp){
        this.newsMsg = newsMsg;
        this.newsSender = newsSender;
        this.newsTimestamp = newsTimestamp;
    }
}
