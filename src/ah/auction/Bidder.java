package ah.auction;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Bidder implements Runnable{
    private String name;
    boolean loggedIn = true;
    private PrintWriter out;
    private BufferedReader in;

    public Bidder(){

    }

    public Bidder(String callMe, PrintWriter out, BufferedReader in){
        this.name = callMe;
        this.in=in;
        this.out = out;
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

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
