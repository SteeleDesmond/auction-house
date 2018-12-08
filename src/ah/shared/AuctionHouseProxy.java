package ah.shared;

import ah.shared.enums.AuctionHouseMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * AuctionHouseProxy functions as a stand in for the Auction House
 * and is where the input and output of the socket work takes place
 */
public class AuctionHouseProxy {
    //think I need to do some error checking here, like
    //make sure messages are being passed correctly...
    //to do that gotta make sure server is correct
    private PrintWriter ahOut;
    private BufferedReader ahIn;
    private boolean waiting;
    private String input;

    /**
     * constructor for the Auction house proxy
     * @param ahOut the output stream
     * @param ahIn the input stream
     */
    public AuctionHouseProxy(PrintWriter ahOut, BufferedReader ahIn) {
        this.ahOut = ahOut;
        this.ahIn = ahIn;
    }

    /**
     * sends less specific messages to the Auction house
     * @param msg message you want to send
     */
    public void sendMsg(String msg) {
        ahOut.println(msg);
    }

    /**
     * Gets the list of Auctions from the actual Auction house
     * @return a list of all the items and their info
     * @throws IOException
     */
    public ArrayList<String> getAuctions() throws IOException {

        ArrayList<String> listOfAuctions = new ArrayList<>();
        waiting = true;
        ahOut.println(AuctionHouseMessages.GETAUCTIONS);
        System.out.println("Waiting for response from Auction House...");
        int numberOfAuctions;
        while(waiting) {
            if((input = ahIn.readLine()) != null) {
                System.out.println(input); // Print message received for testing
                if(input.equalsIgnoreCase(AuctionHouseMessages.SUCCESS.name())) {
                    // The following message is the number of AHs to be coming in
                    numberOfAuctions = Integer.valueOf(ahIn.readLine());
                    // System.out.println(numberOfAHs); // For console testing
                    for(int i = 0; i < numberOfAuctions; i++) {
                        listOfAuctions.add(ahIn.readLine());
                    }
                    waiting = false;
                    return listOfAuctions;
                }
                else if (input.equalsIgnoreCase(AuctionHouseMessages.FAILURE.name())) {
                    System.out.println(ahIn.readLine()); // Print error message sent from bank
                    // System.out.println("There are currently no auctions registered with the AH.");
                }
                else {
                    System.out.println("Error: AHProxy --> getAuctions");
                }
                waiting = false;
            }
        }
        return listOfAuctions; // If empty --> error
    }

    /**
     * Bid is meant to send bids over the socket work
     * @param itemName name of item being bid on
     * @param amount amount the person wishes to bid
     * @return a boolean
     * @throws IOException if it occurs
     */
    public boolean bid(String itemName, int amount) throws IOException {
        ahOut.println(AuctionHouseMessages.BID);
        ahOut.println(itemName);
        ahOut.println(amount);
        return handleResponse();
    }

    /**
     * Used after connecting with an Auction House to send its bidding key. Initialized in Bidder's constructor.
     * @param biddingKey The bidding key of the user
     */
    public void sendBiddingKey(String biddingKey) {
        ahOut.println(biddingKey);
    }

    /**
     * Helper function for AuctionHouseProxy. If the AH only responds a simple success or failure/error message, this
     * method can be used to handle it.
     * @return True if the request was successful, false if there was an error.
     */
    private boolean handleResponse() throws IOException {
        System.out.println("Waiting for response from Auction House...");
        waiting = true;
        while(waiting) {
            if((input = ahIn.readLine()) != null) {
                System.out.println(input); // Print to console for testing
                if(input.equalsIgnoreCase(AuctionHouseMessages.SUCCESS.name())) {
                    waiting = false;
                    return true;
                }
                else if(input.equalsIgnoreCase(AuctionHouseMessages.FAILURE.name())) {
                    System.out.println(ahIn.readLine()); // Print error message sent by auction house
                }
                else {
                    System.out.println("Error processing the bid");
                }
            }
            waiting = false;
        }
        return false;
    }
}
