package ah.shared;


import ah.shared.enums.BankMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 */
public class BankProxy {

    private PrintWriter bankOut;
    private BufferedReader bankIn;
    private boolean waiting;
    private String input;

    public BankProxy(PrintWriter bankOut, BufferedReader bankIn) {
        this.bankOut = bankOut;
        this.bankIn = bankIn;
    }

    /**
     * For testing only
     * @param msg message to send
     */
    public void sendMsg(String msg) {
        bankOut.println(msg);
    }

    /**
     * Sends the DEPOSIT message to the bank. Waits for a response. On success, the methods receives a SUCCESS message
     * followed by a response containing the balance.
     * @return The balance in the account
     * @throws IOException Error connecting to bank
     */
    public int checkBalance() throws IOException {
        waiting = true;
        bankOut.println(BankMessages.GETBALANCE);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input); // First message sent back should always be a success or failure message
                if(input.equalsIgnoreCase(BankMessages.SUCCESS.name())) { // If success message was sent
                    input = bankIn.readLine();
                    System.out.println(input); // The following message is the balance.
                    return Integer.valueOf(input);
                }
                else if(input.equalsIgnoreCase(BankMessages.FAILURE.name())) {
                    System.out.println("Error message received. Failed to receive balance from bank");
                }
                else {
                    System.out.println("Something went wrong -- Success nor Failure message was received from bank");
                }
                //System.out.println(input);
                waiting = false;
            }
        }
        return -1; // Error
    }

    /**
     * Sends the DEPOSIT message to the bank. Waits for a response. On success, the methods receives a SUCCESS message
     * followed by a response containing the new balance. The new balance is printed to console for testing.
     * @param amount The amount to withdraw from the account
     * @return true if the deposit was a success, else false if the withdraw was a failure
     * @throws IOException Error connecting to bank
     */
    public boolean deposit(int amount) throws IOException {
        waiting = true;
        bankOut.println(BankMessages.DEPOSIT);
        bankOut.println(amount);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input); // Print the message received to console for testing
                if(input.equalsIgnoreCase(BankMessages.SUCCESS.name())) {
                    System.out.println(bankIn.readLine());
                    return true;
                }
                else {
                    System.out.println("Error processing deposit");
                }
                waiting = false;
            }
        }
        return false; // Error depositing
    }

    /**
     * Sends the WITHDRAW message to the bank. Waits for a response. On success, the method receives a SUCCESS message
     * followed by a response containing the new balance. The new balance is printed to console for testing.
     * @param amount The amount to withdraw from the account
     * @return true if the withdraw was a success, else false if the withdraw was a failure
     * @throws IOException Error connecting to bank
     */
    public boolean withdraw(int amount) throws IOException {
        waiting = true;
        bankOut.println(BankMessages.WITHDRAW);
        bankOut.println(amount);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input); // Print the message received to console for testing
                if(input.equalsIgnoreCase(BankMessages.SUCCESS.name())) {
                    System.out.println(bankIn.readLine());
                    return true;
                }
                else if(input.equalsIgnoreCase(BankMessages.FAILURE.name())) {
                    System.out.println("Error withdrawing the given amount from your bank account");
                }
                else {
                    System.out.println("Error processing withdraw: Success nor failure response was received");
                }
                waiting = false;
            }
        }
        return false; // Error depositing
    }

    /**
     * Sends the GETAUCTIONHOUSES message to the bank. Waits for a response. On success, the method receives a SUCCESS
     * message followed by a message containing the number of auction houses available. The subsequent messages are the
     * strings containing the auction house information.
     * @return A list of information pertaining to auction houses
     * @throws IOException Error connecting with bank
     */
    public ArrayList<String> getAuctionHouses() throws IOException {
        int numberOfAHs; // Second message sent back by bank is the number of AHs available
        ArrayList<String> listOfAHs = new ArrayList<>();
        waiting = true;
        bankOut.println(BankMessages.GETAUCTIONHOUSES);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input); // Print message received for testing
                if(input.equalsIgnoreCase(BankMessages.SUCCESS.name())) {
                    // The following message is the number of AHs to be coming in
                    numberOfAHs = Integer.valueOf(bankIn.readLine());
                    System.out.println(numberOfAHs); // For console testing
                    for(int i = 0; i < numberOfAHs; i++) {
                        listOfAHs.add(bankIn.readLine());
                    }
                    return listOfAHs;
                }
                else if (input.equalsIgnoreCase(BankMessages.FAILURE.name())) {
                    System.out.println("Error processing get auction houses with Bank");
                }
                else {
                    System.out.println("Error: BankProxy --> getAuctionHouses");
                }
            }
        }
        return listOfAHs; // If empty --> error
    }

    /**
     * Sends the GETBIDDINGKEY message to bank. Waits for a response. On success, the method receives a SUCCESS message
     * followed by a unique bidding key represented as a string.
     * @return a bidding key
     * @throws IOException error connecting to bank
     */
    public String getBiddingKey() throws IOException {
        String biddingKey = null;
        bankOut.println(BankMessages.GETBIDDINGKEY);
        System.out.println("Waiting for response from Bank...");
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input); // Print the message received to console for testing
                if (input.equalsIgnoreCase(BankMessages.SUCCESS.name())) {
                    input = bankIn.readLine();
                    System.out.println(input);
                    biddingKey = input;
                    return biddingKey;
                } else {
                    System.out.println("Error processing getBiddingKey");
                }
                waiting = false;
            }
        }
        return biddingKey; // If null --> error
    }


}
