Author: Steele Desmond - sdesmondemail@gmail.com
# Auction House
This program is designed to encompass an auction house simulation that is available over the network. It implements a client/server model that allows users(Agents) to connect to a Bank and Auction House. Users can create a bank account and bid on auctions at an Auction house.

## Versions (Jar files)

Version 1.0 -- Final design

- cs351-ah-agent-1.0.jar --> Run the Agent client
 
- cs351-ah-bank-server-1.0.jar --> Run the Bank Server
  
- cs351-auction-house-server-1.0.jar --> Run the Auction House Server

## How to use this program

### Quickstart

1. Start the Bank jar file first. Enter a port number to run it on

2. Start the Auction House and Agent jars next, in either order

- Auction House first takes a port to start on, then it connects to the bank.

- Agent connects to the bank on startup. It can request information from the bank
to connect to auction houses.

Most interactions and testing happen from the Agent command line client.

## Implemented Features

### Agent

The Agent is implemented with a simple command line client interface. It allows 
for interacting with the Bank and auction houses. It is given a 
BankProxy on startup and it uses BankProxy and AuctionHouseProxy to communicate
over the network.

The Agent can request a list of auction houses from the Bank. It receives a list 
of all registered auction houses with their name, hostname, port number, and 
account key for transferring funds. The Agent can then connect to an Auction House
using the given information and request additional information from the Auction House.

### Bank

The bank is implemented according to the requirements listed in the docs
directory. The bank listens on a given port number for incoming requests. All 
incoming connections are delegated to the Account class for that specific 
connection. The Bank uses a thread pool and adds Account threads to the pool when
connections are made. The Account contains the bidding key used by agents, 
and the connection information in auction house accounts used by agents.

Features:
- Check balance
- Deposit funds
- Withdraw funds
- Get list of auction houses
- transfer funds
- block / unblock funds

### Auction House

The Auction House was created similarly to Bank. The connections and Service 
classes were both setup similarly, with the main difference being that the 
auction house connects to the bank as a client on startup. The Auction House
Service is given a thread pool similarly and delegates work to a Bidder class
that is representative of the Account class.

## Testing and Debugging

Most testing is done via the Agent command line client. Requests are sent via
Strings or enums in this program and can be seen and tested in:

BankProxy --> Account

AuctionHouseProxy --> Bidder

## Unfinished features

The functionality of bidding in the Auction House has not been fully implemented. 
Blocking and unblocking funds is fully functional and Auction Houses are able to
connect to Agents as well as the Bank. The Auction House receives a bidding key
given to it by the Agent when it connects. The bank generates the bidding key. 
The Auction House can return a list of auctions to the Agent, and the bid 
connections have been set up through the Auction House proxy and the Bidder.

### Unused features 

The Agent is simple and is built only to fulfill requirements. There are additional
classes in the agent package that were initially created for a larger-scale design
that have not been utilized.

The Bank was initially designed to use two different Account types, AgentAccount and
AuctionHouseAccount. The finished design ended up using only Account for both 
account types, however, the objects were left to add additional functionality later.

The Bank was initially designed with a BankClient as a 4th jar that also communicated
over the network. The BankClient would act similarly to Agent in that it would 
run an AuctionHouseController. It would act as the front-end for the Auction House.
The code was left in case we wanted to convert back later on, but the finished design
used a runnable thread in the AuctionHouseService that acted as a small built-in
controller for the service.

## Contributors

Steele Desmond

## Contributions

- Initial setup of the gitlab repository with structured branches and git best practices
- Setting up and maintaining the issue board for issue tracking, work delegation, and milestone tracking
- Designing, building, documenting all startup socket connections between Agent, Bank, and Auction House
    - Each part of the program is given a main class object which is used as the launching point for the jar files
    - The programs use NotificationServer and CommunicationService to handle most socket connections / delegate proxies
- Building Bank
    - The Bank communicates with BankProxy objects through Account threads
- Building, documenting BankProxy and AuctionHouseProxy functionality
    - BankProxy is used by Agent (AgentController) and Auction House (AuctionHouseService) to communicate with the Bank over the network
    - AuctionHouseProxy is used by Agent to communicate with the Auction House
    - BankProxy and AuctionHouseProxy both communicate to their corresponding services via enums (located in the shared.enums folder)
- Building the AgentController command line interface
    - The connections to auction house are done manually via command line
- Constructing AuctionHouseService startup (connecting to bank), incoming connection handling (from agents), hold/block funds, register agent keys
- Writing design descriptions documents
- Writing design diagrams
- Writing README
- Adding jar files

## Bugs

n/a (See unfinished features)

## Project Design Concerns

Connections are made via command line and hostname / port number. There is some error 
checking here but often times if the wrong input is entered it will crash the program. 

## Developers' Notes

https://docs.google.com/document/d/1g8cQIcQ2JYmx-QWd7C_oqIhSvFnrmEUZ0OAb5I_fZ5E/edit?usp=sharing

draw.io diagrams

https://drive.google.com/file/d/1R9ep1hhV5iWY0i7YRwn7KqxiLaS_arjm/view?usp=sharing


design descriptions

https://docs.google.com/document/d/1FyfJzHByWXw8o1K62CU7n1IFqQKIu_jqgnYAY5C8vKI/edit?usp=sharing

