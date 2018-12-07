package ah.shared;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AuctionHouseProxy {

    private PrintWriter ahOut;
    private BufferedReader ahIn;

    public AuctionHouseProxy(PrintWriter ahOut, BufferedReader ahIn) {
        this.ahOut = ahOut;
        this.ahIn = ahIn;
    }

    public void sendMsg(String msg) {
        ahOut.println(msg);
    }


}
