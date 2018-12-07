package ah.shared;

import ah.shared.enums.AuctionHouseMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

        ArrayList<String> listOfAuctions = new ArrayList<>();
        waiting = true;
        ahOut.println(AuctionHouseMessages.GETAUCTIONS);
        System.out.println("Waiting for response from Auction House...");
        while(waiting) {
            if((input = ahIn.readLine()) != null) {
                System.out.println(input); // Print message received for testing
                if(input.equalsIgnoreCase(AuctionHouseMessages.SUCCESS.name())) {

                    // If a success then the list of auctions follows the success message in one line
                    // Split the line at new line characters and add to ArrayList
                    Scanner sc = new Scanner(ahIn.readLine());
                    while (sc.hasNext()){
                        listOfAuctions.add(sc.nextLine());
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

    public boolean makeBid(String item, int money) throws IOException{
        waiting = true;
        ahOut.println(AuctionHouseMessages.BID);
        ahOut.println(item);
        ahOut.println(money);
        while(waiting){

            if((input = ahIn.readLine()) != null){
                System.out.println(input); // Print the message received to console for testing
                if(input.equalsIgnoreCase(AuctionHouseMessages.SUCCESS.name())) {
                    System.out.println(ahIn.readLine());
                    waiting = false;
                    return true;
                }
            } else {
                System.out.println("Error processing bid");
            }
            waiting = false;
        }
        return false; // Error depositing

    }

}
