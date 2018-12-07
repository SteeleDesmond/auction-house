package ah.auction;

import ah.bank.BankService;
import ah.shared.enums.AuctionHouseMessages;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Bidder implements Runnable{
    private String name;
    boolean loggedIn = true;
    private PrintWriter out;
    private BufferedReader in;
    private AuctionHouseService house;

    public Bidder(){

    }

    public Bidder(String callMe, PrintWriter out, BufferedReader in, AuctionHouseService house){
        this.name = callMe;
        this.in=in;
        this.out = out;
        this.house=house;
        System.out.println("new bidder created! "+name);
    }

    @Override
    public void run(){
        System.out.println("run entered - bidder");
        String input;
        while(loggedIn){
            //System.out.println("Loop");
            try{
                input = in.readLine();
                if(input!= null){
                    System.out.println("Request received from account " + name+ ": " + input);
                    AuctionHouseMessages msg = AuctionHouseMessages.valueOf(input);
                    switch(msg){
                        case GETAUCTIONS:
                            out.println(house.getInventory());
                            break;
                        case QUIT:
                            break;
                    }


                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
