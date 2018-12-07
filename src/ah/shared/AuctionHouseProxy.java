package ah.shared;

import ah.shared.enums.AuctionHouseMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AuctionHouseProxy {
    //think I need to do some error checking here, like
    //make sure messages are being passed correctly...
    //to do that gotta make sure server is correct
    private PrintWriter ahOut;
    private BufferedReader ahIn;
    private boolean waiting;
    private String input;

    public AuctionHouseProxy(PrintWriter ahOut, BufferedReader ahIn) {
        this.ahOut = ahOut;
        this.ahIn = ahIn;
    }

    // need to get inventory list
    //maybe server number, if bank uses it

    public void sendMsg(String msg) {
        ahOut.println(msg);
    }

    /**
     *
     * @return a list of all the items and their info
     * @throws IOException
     */
    public ArrayList<String> getAuctions() throws IOException {
        int numberOfAHs; // Second message sent back by bank is the number of AHs available
        ArrayList<String> listOfAHs = new ArrayList<>();
        waiting = true;
        ahOut.println(AuctionHouseMessages.GETAUCTIONS);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = ahIn.readLine()) != null) {
                System.out.println(input); // Print message received for testing
                if(input.equalsIgnoreCase(AuctionHouseMessages.SUCCESS.name())) {
                    // The following message is the number of AHs to be coming in
                    numberOfAHs = Integer.valueOf(ahIn.readLine());
                    System.out.println(numberOfAHs); // For console testing
                    for(int i = 0; i < numberOfAHs; i++) {
                        listOfAHs.add(ahIn.readLine());
                    }
                    waiting = false;
                    return listOfAHs;
                }
                else if (input.equalsIgnoreCase(AuctionHouseMessages.FAILURE.name())) {
                    System.out.println(ahIn.readLine()); // Print error message sent from bank
                    // System.out.println("There are currently no auction houses registered with the bank.");
                }
                else {
                    System.out.println("Error: BankProxy --> getAuctionHouses");
                }
                waiting = false;
            }
        }
        return listOfAHs; // If empty --> error

    }
}
