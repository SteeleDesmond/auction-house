package ah;

import ah.agent.Agent;
import ah.auction.AuctionHouse;
import ah.bank.Bank;

import java.util.ArrayList;

public class Main {
    Bank bank;

    ArrayList<AuctionHouse> ahList = new ArrayList<>();

    ArrayList<Agent> agentList = new ArrayList<>();

    Main(){

        //make bank
        bank = new Bank(2000);

        //make 3 auctionhouse
        for (int i = 0; i < 3; i++) {

            AuctionHouse auctionHouse = new AuctionHouse(
                    "ah" + i, 5, 2000, 2001+ i);
            ahList.add(auctionHouse);

        }

        //make 3 agents
        for (int i = 0; i < 3; i++) {

            Agent agent = new Agent();
            agentList.add(agent);

        }


    }


    public static void main(String[] args) {

        new Main();

    }
}
