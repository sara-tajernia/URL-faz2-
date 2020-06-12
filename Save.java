import java.io.*;
import java.util.Date;

/**
 * we use this class to save respond or request
 */

public class Save {

    /**
     * We use this method to save a request as an Object
     * @param request that we want to save
     */
    public void SaveRequest(Request request) {
        Date date = new Date();
        String address = "request_" +date.toString() +".txt";
        File save = new File(address);

        if (save.exists())
            save.delete();

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save));
            out.writeObject(request);
            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * we use this method to save a respond as a string
     * @param respond string that we want to save
     * @param address name of the file
     */

    public void SaveRespond(String respond, String address) {
        File save = new File(address);

        if (save.exists())
            save.delete();

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(save));
            out.writeObject(respond);
            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
