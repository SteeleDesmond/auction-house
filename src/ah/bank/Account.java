package ah.bank;

import ah.shared.enums.BankMessages;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The Account class is used for methods that both AuctionHouse Accounts and Agent Accounts can use.
 */
public class Account implements Runnable {

    private int id;
    private String name;
    private boolean accountIsOpen;
    private int accountBalance;
    private int holdsAmount;
    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;
    private String hostName;
    private String portNumber;
    private String personalKey = new KeyGenerator().getKey();

    public Account() {

    }

    public Account(int AccountId, String name, BankService parent, PrintWriter out, BufferedReader in,
                   String hostName, String portNumber) {
        this.id = AccountId;
        this.name = name;
        this.parent = parent;
        this.out = out;
        this.in = in;
        this.hostName = hostName;
        this.portNumber = portNumber;
        accountBalance = 5000;
        holdsAmount = 0;
        accountIsOpen = true;
        System.out.println("New account created! Account ID: " + id);
    }

    @Override
    public void run() {
        //System.out.println("Entering thread loop");
        String input;
        while(accountIsOpen) {
            //System.out.println("in agent account loop");
            try {
                if((input = in.readLine()) != null) {
                    System.out.println("Request received from account " + id + ": " + input);
                    BankMessages message = BankMessages.valueOf(input);
                    switch(message) {
                        case GETBALANCE: { // Send two messages, a success followed by a balance
                            sendSuccess();
                            sendBalance();
                            break;
                        }
                        case DEPOSIT: {
                            input = in.readLine(); // Get the deposit amount
                            accountBalance += Integer.parseInt(input);
                            sendSuccess(); // Send two messages, a success followed by a new balance
                            sendBalance();
                            break;
                        }
                        case WITHDRAW: {
                            int amount = Integer.parseInt(in.readLine()); // Get the withdrawal amount
                            if(accountBalance - amount < 0) {
                                sendFailure(); // Send a failure message if the withdrawal would overdraft
                                System.out.println("Account " + id + " would overdraft! Withdrawal was denied.");
                                out.println("Account " + id + " would overdraft! Withdrawal was denied.");
                            }
                            else {
                                accountBalance -= amount;
                                sendSuccess(); // Send two messages, success followed by a new balance
                                sendBalance();
                            }
                            break;
                        }
                        case GETAUCTIONHOUSES: {
                            ArrayList<Account> aHouses = parent.getAuctionHouses();
                            if(aHouses.size() < 1) {
                                sendFailure();
                                out.println("There aren't any auction houses currently registered with the bank.");
                                break;
                            }
                            sendSuccess();
                            out.println(aHouses.size()); // First send how many auction houses there are
                            for(Account a : aHouses) {
                                out.println(a.getAccountInformation()); // Send info of each auction house
                            }
                            break;
                        }
                        case GETBIDDINGKEY: {
                            sendSuccess();
                            out.println(personalKey);
                            break;
                        }
                        case GETAHKEY: {
                            sendSuccess();
                            out.println(personalKey);
                            break;
                        }
                        case TRANSFERFUNDS: {
                            // Expects 3 following messages: the agent's bidding key, the ah key, and the amount
                            String agentKey = in.readLine();
                            String ahKey = in.readLine();
                            String amount = in.readLine();
                            if(!personalKey.equals(agentKey)) {
                                System.out.println("Error: Agent is sending a false key");
                                sendFailure();
                                out.println("Error: The Agent key sent is not registered with the bank");
                            }
                            // If the transfer is successful send a success response
                            else if (parent.transferFunds(agentKey, ahKey, Integer.valueOf(amount))) {
                                sendSuccess();
                            }
                            else {
                                sendFailure();
                                out.println("Error processing the transfer (Insufficient funds, perhaps?)");
                            }
                            break;
                        }
                        case BLOCKFUNDS: {
                            // Expects 3 following messages: the agent's bidding key, the ah key, and the amount
                            String ahKey = in.readLine();
                            String agentKey = in.readLine();
                            String amount = in.readLine();
                            if(!personalKey.equals(ahKey)) {
                                System.out.println("Error: AHouse is sending a false key");
                                sendFailure();
                                out.println("Error: The AHouse key sent is not registered with the bank");
                            }
                            // If the hold is successful send a success response
                            else if (parent.blockFunds(ahKey, agentKey, Integer.valueOf(amount))) {
                                sendSuccess();
                            }
                            else {
                                sendFailure();
                                out.println("Error processing the hold (Insufficient funds, perhaps?)");
                            }
                            break;
                        }
                        case UNBLOCKFUNDS: {
                            // Expects 3 following messages: the agent's bidding key, the ah key, and the amount
                            String ahKey = in.readLine();
                            String agentKey = in.readLine();
                            String amount = in.readLine();
                            if(!personalKey.equals(ahKey)) {
                                System.out.println("Error: AHouse is sending a false key");
                                sendFailure();
                                out.println("Error: The AHouse key sent is not registered with the bank");
                            }
                            // If the hold is successful send a success response
                            else if (parent.unblockFunds(ahKey, agentKey, Integer.valueOf(amount))) {
                                sendSuccess();
                            }
                            else {
                                sendFailure();
                                out.println("Error removing the hold (Perhaps the account has no holds?)");
                            }
                            break;
                        }
                        default: {
                            System.out.println("Account " + id + " Received incorrect message format.");
                            sendFailure();
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String getPersonalKey() {
        return personalKey;
    }

    public void sendBalance() {
        out.println(accountBalance);
    }

    public void sendSuccess() {
        out.println(BankMessages.SUCCESS);
    }

    public void sendFailure() {
        out.println(BankMessages.FAILURE);
    }

    public int getId() {
        return id;
    }

    public String getAccountInformation() {
        return name + " " + hostName + " " + portNumber + " " + personalKey;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getHoldsAmount() {
        return holdsAmount;
    }

    public void setHoldsAmount(int holdsAmount) {
        this.holdsAmount = holdsAmount;
    }
}
