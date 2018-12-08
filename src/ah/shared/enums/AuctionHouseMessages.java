package ah.shared.enums;

/**
 * AuctionHouseMessages standardizes communication
 */
public enum AuctionHouseMessages {

    // Client to Auction house
    BID,
    GETAUCTIONS,
    QUIT,

    // Auction house to client
    WON,
    PASS,
    REJECT,
    SUCCESS,
    FAILURE
}
