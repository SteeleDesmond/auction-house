package ah.shared;

import ah.auction.enums.AuctionHouseMessages;
import ah.shared.enums.BankMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AuctionHouseProxy {

    private PrintWriter ahOut;
    private BufferedReader ahIn;
    private boolean waiting;
    private String input;

    public AuctionHouseProxy(PrintWriter ahOut, BufferedReader ahIn) {
        this.ahOut = ahOut;
        this.ahIn = ahIn;
    }

    public void sendMsg(String msg) {
        ahOut.println(msg);
    }

    /**
     *
     * @return a list of all the items and their info
     * @throws IOException
     */
    public ArrayList<String> getAuctions() throws IOException {
        ArrayList<String> auctionList = new ArrayList<>();


        waiting = true;
        ahOut.println(AuctionHouseMessages.GETAUCTIONS);
        System.out.println("Waiting for response from Auction house...");
        while(waiting) {
            if((input = ahIn.readLine()) != null) {
                System.out.println(input); // First message sent back should always be a success or failure message
                if(input.equalsIgnoreCase(AuctionHouseMessages.SUCCESS.name())) { // If success message was sent

                    auctionList.add(ahIn.readLine());

                    waiting = false;
                    return auctionList;

                }
                else if(input.equalsIgnoreCase(AuctionHouseMessages.FAILURE.name())) {
                    System.out.println("Error message received. Failed to receive balance from bank");
                }
                else {
                    System.out.println("Something went wrong -- Success nor Failure message was received from bank");
                }
                //System.out.println(input);
                waiting = false;
            }
        }
        return null; // Error

    }
}
