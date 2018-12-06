package ah.auction;

import ah.shared.BankProxy;
import ah.shared.CommunicationService;
import ah.shared.NotificationServer;

import java.io.IOException;

public class AHTest {

    AHTest(){
        try {
            CommunicationService communicationService = new CommunicationService();
            BankProxy bankProxy = communicationService.connectToBankServer(
                    "localhost", 2000, "auction house");

            NotificationServer notificationServer = new NotificationServer(2001, "auction");

            bankProxy.sendMsg("auction house connected");
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public static void main(String[] args) {

        new AHTest();
    }
}
