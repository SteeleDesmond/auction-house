package ah.bank;

import ah.shared.enums.BankMessages;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * The Account class is used for methods that both AuctionHouse Accounts and Agent Accounts can use.
 */
public class Account implements Runnable {

    private int id;
    private boolean accountIsOpen;
    private int accountBalance;
    private BankService parent;
    private PrintWriter out;
    private BufferedReader in;
    private String personalKey = new KeyGenerator().getKey();

    public Account() {

    }

    public Account(int Accountid, BankService parent, PrintWriter out, BufferedReader in) {
        this.id = Accountid;
        this.parent = parent;
        this.out = out;
        this.in = in;
        accountBalance = 0;
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
                    System.out.println("Request received from account " + id + " :" + input);
                    BankMessages message = BankMessages.valueOf(input);
                    switch(message) {
                        case GETBALANCE: {
                            sendSuccess();
                            sendBalance();
                            break;
                        }
                        case DEPOSIT: {
                            input = in.readLine(); // Get the deposit amount
                            accountBalance += Integer.parseInt(input);
                            sendSuccess();
                            sendBalance();
                            break;
                        }
                        case WITHDRAW: {
                            int amount = Integer.parseInt(in.readLine()); // Get the withdrawal amount
                            if(accountBalance - amount < 0) {
                                sendFailure();
                                System.out.println("Account " + id + " would overdraft! Withdrawal was denied.");
                            }
                            else {
                                accountBalance -= amount;
                                sendSuccess();
                                sendBalance();
                            }
                            break;
                        }
                        case GETAUCTIONHOUSES: {

                            break;
                        }
                        case GETBIDDINGKEY: {
                            sendSuccess();
                            out.println(personalKey);
                            break;
                        }
                        case TRANSFERFUNDS: {

                            break;
                        }
                        case BLOCKFUNDS: {
                            break;
                        }
                        case UNBLOCKFUNDS: {
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
}
