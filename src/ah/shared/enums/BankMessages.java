package ah.shared.enums;

public enum BankMessages {

    // Client to Bank
    DEPOSIT,
    WITHDRAW,
    GETBALANCE,
    GETAUCTIONHOUSES,
    GETBIDDINGKEY,
    TRANSFERFUNDS,
    BLOCKFUNDS,
    UNBLOCKFUNDS,

    // Bank to Client
    SUCCESS,
    FAILURE
}