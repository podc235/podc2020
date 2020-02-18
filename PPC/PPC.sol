pragma solidity ^0.5.1;

contract PPC {

    /* party variables */
    address payable public auctioneer;         // address of the auctioneer that conducts the auction
    address payable[] private notaries;         // list of the available notaries
    address Alice;
    address Bob;
    mapping (address => uint256) agentIDs;      // maps Alice and Bob to their identifiers
    mapping (address => uint256[])  agentValues;    // maps identifiers to the random representation 
    uint256 private notaryCount = 0;        // variable to keep count of notaries
    uint256 private agentCount = 0;        // variable to keep count of agents
    mapping (uint256 => address[]) notaryToAgent;     // maps a notary to an agent
    mapping (address => uint256[]) notaryValues;    // mapping which stores agent values per
    uint256 allocate = 0;       // variable to allocate notaries
    uint256 auctioneerList=0;

    
    /* protocol variables */
    uint256 public p;       // big prime
    uint256 public q;       // prime dividing p-1
    uint256 public g;       // pedersen generator
    uint256 public h;       // we do not publish the secret key
    
    /* state controller */
    enum contractState {SETUP, NOTARYAllocation, AGENTRegistration, NOTARYtoAGENT, SUBMISSION, COMPUTING, BROADCAST, PAY, DONE}
    contractState currState;    // variable to keep track of the current state
    
    
    /* contract modifiers */
    
    modifier checkCurrState(contractState state) {
        require(currState == state, "method not available at this state");
        _;
    }
    
    modifier onlyAuctioneer() {
        require (msg.sender == auctioneer, "only the auctioneer can access this method");
        _;
    }
    
    event winner (
        string str
    );
    
    /* PPC protocol */
    
    // auctioneer will invoke the contract
    constructor() public {
        auctioneer = msg.sender;
        currState = contractState.SETUP;          // change the state
    }
    
    // auctioneer will set up the protocol
    function setupParameters (uint256 _p, uint256 _q, uint256 _g, uint256 _h) checkCurrState(contractState.SETUP) onlyAuctioneer() public {
        p = _p;
        q = _q;
        g = _g;
        h = _h;
        currState = contractState.NOTARYAllocation;       // change the state
    }
    
    // notary registration
    function registerNotaries (address payable _notary) checkCurrState(contractState.NOTARYAllocation) public {
        notaries.push(_notary);
        notaryCount++;
        currState = contractState.AGENTRegistration;      // change the state
    }
    
    // agent registration
    function registerAgents (address _agent) checkCurrState(contractState.AGENTRegistration) public {
        // auctioneer maps the address to a random identifier
        agentIDs[_agent] = agentCount;
        agentCount++;
        currState = contractState.NOTARYtoAGENT;
    }
    
    // map notaries to agents; this function assumes that notaries > 2*agents
    function mapNotaries () checkCurrState(contractState.NOTARYtoAGENT) onlyAuctioneer() public {
        uint256 j = 0;
        for (uint256 i=0; i<agentCount; i++) {
            notaryToAgent[i].push(notaries[j]);
            j++;
            notaryToAgent[i].push(notaries[j]);
            j++;
        }
        currState = contractState.SUBMISSION;
    }
    
    // agents submit their values for comparison
    function valueSubmission (uint256 _u, uint256 _v, uint256 _c1, uint256 _c2) checkCurrState(contractState.SUBMISSION) public {
        agentValues[msg.sender].push(_u);
        agentValues[msg.sender].push(_v);
        agentValues[msg.sender].push(_c1);
        agentValues[msg.sender].push(_c2);
        currState = contractState.COMPUTING;
    }
    
    // information exchange between notaries and agents
    function intermediateValues (uint256 _t1, uint256 _t2, uint256 _t3, uint256 _t4) checkCurrState(contractState.COMPUTING) public {
        notaryValues[notaryToAgent[agentIDs[msg.sender]][0]].push(_t1);  // [0][0][0] has u_1, [0][1][0] has v1
        notaryValues[notaryToAgent[agentIDs[msg.sender]][0]].push(_t2);
        notaryValues[notaryToAgent[agentIDs[msg.sender]][1]].push(_t3);
        notaryValues[notaryToAgent[agentIDs[msg.sender]][1]].push(_t4);
    }
    
    // information exchange between notaries
    function notariesExchange () checkCurrState(contractState.COMPUTING) public {
        // 1st set of notaries
        notaryValues[notaryToAgent[1][0]][2] = notaryValues[notaryToAgent[0][0]][0];    // sending u_1, n_2
        notaryValues[notaryToAgent[0][0]][2] = notaryValues[notaryToAgent[1][0]][1];    // sending d_1, d_2
        
        // 2nd set
        notaryValues[notaryToAgent[1][1]][2] = notaryValues[notaryToAgent[0][1]][0];  
        notaryValues[notaryToAgent[0][1]][2] = notaryValues[notaryToAgent[1][1]][1];
    }
    
    // notaries to auctioneer
    function auctioneerExchange () checkCurrState(contractState.COMPUTING) public {
        // values are in 
        uint256 val;
        uint256 D;
        val = (notaryValues[notaryToAgent[1][0]][2] - notaryValues[notaryToAgent[1][0]][0] + 
                        notaryValues[notaryToAgent[1][1]][2] - notaryValues[notaryToAgent[1][1]][2] ) % q;
        D = (notaryValues[notaryToAgent[0][0]][1] ^ notaryValues[notaryToAgent[0][0]][2]);
        
        if ((D*val)%q <= q/2) emit winner("Alice is richer");
        else emit winner("Bob is richer");
        
    }
    
    
    /* getter functions for the notaries to pull data */
    
} // contract ends

