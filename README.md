# Mobile Android app for master-thesis
<p align="center">
 <img src="pictures/mobile_app.png"/>
</p>

### Description


This project is part of my master-thesis, which you can check [here](https://github.com/filipmacek/master-thesis).

Mobile Android app is digital twin for users within our ecosystem `Movement`. 
It helps user with execution of routes and generates location data as user moves.
After the user registers on web app, smart contract writes data about user registration on blockchain.
Then user can access mobile app with username and password he specified in registration form.
Also, to get location data, user need to give permission to Android app.
At startup, application connects with  Infura RPC endpoint to Ethereum testnet Kovan and
takes all the data it needs for successfully initialization.
After the user enters his username and password, app checks if data matches with data it got from smart contract.
If username and two times hashed password matches, app confirms identity of user and gives him permission to 
enter into new Android fragment that will act as dashboard.

On dashboard view has are 3 different tabs.

#### Routes
List of all the available routes with start and end location points, status and descriptions.
User can press `INFO` button to check on Google Maps where these two points are.
If the wants to start this route, he will press `ACCEPT`

#### Nodes
It shows list of registered Chainlink nodes with api addresses and url endpoints so app knows where to send location data.
These nodes are EC2 servers running in Aws cloud. If it's responding to pings, we will show green box with connected state.

#### Account
It show personal data about user and its routes.

***

Here on first picture, there are three routes, with all metadata and status. Some of them are avaiable to users to completed, but 
some of them are already completed.
On the second picture we could see two nodes or linux servers that act as `DataAdapter` for our Chainlink nodes.
Each nodes has IP address, endpoint and oracle address of corresponding Chainlink oracle contract.
The goal was to simulate decentralized oracle network with these two nodes.


<p align='center'>
<img align='center' src="pictures/mobile_app_dashboard.png"/>
</p>

<p align='center'>
<em>Picture 1.1: Mobile App Dashboard with tabs Routes and Nodes</em>
</p>


## Agent of smart contract

Special object class called `Agent` in mobile app is responsible for writing of target data to smart contract.
It writes `StartRouteEvent`, `EndRouteEvent` structs of data, and runs function `RequestRouteStatus`, so that Chainlink nodes
could check if user did complete routes properly.
Calling the function of smart contract, and writing all the data in blockchain is done with the private key which strictly belongs to `Agent`.
Private key in written in special folder so it could get compiled in app and stay a secret.
> Because some functions in smart contract are only permitted to run by agent, we inserted at smart contract creation address of 
> agent which belongs to this private key as global variable.

Bellow you can see constructor of our Solidity smart contract `Movement` where he accepts the address of Agent, and corresponding modifier function
which we are using to restrict access to certain function only belonging to agent address.

```solidity
address private agent;

constructor(address _agent)public Ownable(){
      agent = _agent;
 }
    
// modifiers
modifier onlyAgent(){
    require(msg.sender == agent);
     _;
}
    
```

## How does it work

After user had accepted the route, new Android fragment opens up and new process starts to run and collect
location GPS data from mobile phone in the background.
After each location change,we will send new location data point to IP address of `Data Adapter` of Chainlink node.
Let's describe pars of this view.
#### Dashboard
This is the main tool for user running routes. It has all the relevant data which user needs to safely complete selected route:
- current location and number of location changes generated by background location process
- status of routes, time, speed and travelled distance 
- information if start and end location is visited
- Status of Chainlink nodes, activity, ip address and how many location changes they received

#### Map
Google Maps which shows  start and end location data points with pinpoint. Also, around each point there is circle with 
radius od 20 meters which is condition we set up for acknowledging that user did visit this point.


<p align='center'>
<img align='center' src="pictures/mobile_app_route.png"/>
</p>

<p align='center'>
<em>Picture 1.2: Live route layout and notification when finished</em>
</p>



Application will notify user when he completes the route, which means visiting first start location then  an end location.
All the data will be sent to Data Adapter of Chainlink nodes, and we will write `EndRouteEvent` on blockchain as a confirmation
that user did visit end location.
Smart contract will then start to parse and process location data with help of Chainlink node and write result to blockchain.



## Live
Here you could see GIFs from each state change when user started executing route.

### 1. At start
When user starts executing route, program first check if user visited start location.
<p align='center'>
  <img src="video/at_start.gif" width="40%" height="49%"/>
</p>


### 2. During execution of route
Here you can see in this fast forwarded gif, how user is approaching the end location.
As user walk location changes are recorded on Android app, which sends these data points to `DataAdapter` IP address 
of Chainlink node.
<p align='center'>
  <img src="video/during.gif" width="40%" height="50%"/>
</p>


### 3. At the end
At the last phase of route execution, we are waiting for user to visit the end location.
After he is in radius of 20m from end data point, app exits from route execution dashboard and notifies user, \
In the background our `Agent` entity on app notifies smart contract on blockchain that route is finished.
<p align='center'>
  <img src="video/at_the_end.gif" width="40%" height="50%"/>
</p>