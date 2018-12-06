package ah.shared;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AuctionHouseProxy {
    //think I need to do some error checking here, like
    //make sure messages are being passed correctly...
    //to do that gotta make sure server is correct
    private PrintWriter ahOut;
    private BufferedReader ahIn;

    public AuctionHouseProxy(PrintWriter ahOut, BufferedReader ahIn) {
        this.ahOut = ahOut;
        this.ahIn = ahIn;
    }

    // need to get inventory list
    //maybe server number, if bank uses it

    public void sendMsg(String msg) {
        ahOut.println(msg);
    }
}
