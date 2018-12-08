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
                    // System.out.println(input); // The following message is the balance.
                    waiting = false;
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
                    waiting = false;
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
                    waiting = false;
                    return true;
                }
                else if(input.equalsIgnoreCase(BankMessages.FAILURE.name())) {
                    // System.out.println("Error withdrawing the given amount from your bank account");
                    System.out.println(bankIn.readLine()); // Print error given by bank
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
                    // System.out.println(numberOfAHs); // For console testing
                    for(int i = 0; i < numberOfAHs; i++) {
                        listOfAHs.add(bankIn.readLine());
                    }
                    waiting = false;
                    return listOfAHs;
                }
                else if (input.equalsIgnoreCase(BankMessages.FAILURE.name())) {
                    System.out.println(bankIn.readLine()); // Print error message sent from bank
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
        waiting = true;
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

    /**
     * Used by Agents to transfer funds from their account to an auction house account
     * @param biddingKey The agent's personal bidding key (Given to by the bank)
     * @param auctionHouseKey The auction house's key (Given to it by the bank, given to agent in getAuctionHouses
     * @return true if the transfer was successful. False if there was a failure
     */
    public boolean transferFunds(String biddingKey, String auctionHouseKey, int amount) throws IOException {
        bankOut.println(BankMessages.TRANSFERFUNDS);
        bankOut.println(biddingKey); // First send the agent's key
        bankOut.println(auctionHouseKey); // Then send the auction house's key
        bankOut.println(amount);
        return handleBankResponse();
    }

    /**
     * Used by auction houses to block funds in an agent's account. Sends 4 messages to Bank and expects a SUCCESS or
     * FAILURE response. If a FAILURE response is sent, an error message will follow. Prints to console
     * @param auctionHouseKey The auction house's personal key
     * @param agentBiddingKey The agent's bidding key
     * @return True if successful, false if not
     */
    public boolean blockFunds(String auctionHouseKey, String agentBiddingKey, int amount) throws IOException {
        bankOut.println(BankMessages.BLOCKFUNDS);
        bankOut.println(auctionHouseKey);
        bankOut.println(agentBiddingKey);
        bankOut.println(amount);
        return handleBankResponse();
    }

    /**
     * Used by auction houses to unblock funds in an agent's account. Sends 4 messages to Bank and expects a SUCCESS or
     * FAILURE response. If a FAILURE response is sent, an error message will follow. Prints to console
     * @param auctionHouseKey The auction house's personal key
     * @param agentBiddingKey The agent's bidding key
     * @return True if successful, false if not
     */
    public boolean unblockFunds(String auctionHouseKey, String agentBiddingKey, int amount) throws IOException {
        bankOut.println(BankMessages.UNBLOCKFUNDS);
        bankOut.println(auctionHouseKey);
        bankOut.println(agentBiddingKey);
        bankOut.println(amount);
        return handleBankResponse();
    }

    /**
     * Used by the AH to get its unique key for transferring funds and blocking/unblocking funds
     * @return The AH's unique key
     */
    public String getAHKey() throws IOException {
        String ahKey = null;
        bankOut.println(BankMessages.GETAHKEY);
        System.out.println("Waiting for response from Bank...");
        waiting = true;
        while (waiting) {
            if ((input = bankIn.readLine()) != null) {
                System.out.println(input); // Print the message received to console for testing
                if (input.equalsIgnoreCase(BankMessages.SUCCESS.name())) {
                    input = bankIn.readLine();
                    System.out.println(input);
                    ahKey = input;
                    return ahKey;
                } else {
                    System.out.println("Error processing getAHKey");
                }
                waiting = false;
            }
        }
        return ahKey;
    }

    /**
     * Helper function for BankProxy. If the bank only responds a simple success or failure/error message, this
     * method can be used to handle it.
     * @return True if the request was successful, false if there was an error.
     */
    private boolean handleBankResponse() throws IOException {
        System.out.println("Waiting for response from Bank...");
        waiting = true;
        while(waiting) {
            if((input = bankIn.readLine()) != null) {
                System.out.println(input); // Print to console for testing
                if(input.equalsIgnoreCase(BankMessages.SUCCESS.name())) {
                    waiting = false;
                    return true;
                }
                else if(input.equalsIgnoreCase(BankMessages.FAILURE.name())) {
                    //System.out.println("Error unblocking funds");
                    //System.out.println("Insufficient funds in bank account");
                    System.out.println(bankIn.readLine()); // Print error message sent by bank
                }
                else {
                    System.out.println("Error processing transfer request");
                }
            }
            waiting = false;
        }
        return false;
    }
}
