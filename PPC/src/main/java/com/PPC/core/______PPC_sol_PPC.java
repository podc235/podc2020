package com.PPC.core;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class ______PPC_sol_PPC extends Contract {
    private static final String BINARY = "6080604052600060065560006007556000600a556000600b556000600c5534801561002957600080fd5b50600080546001600160a01b031916331781556017805460ff1916905561155890819061005690396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c8063996a4d6711610097578063e2179b8e11610066578063e2179b8e1461021f578063ebb94f3014610227578063f7cbf8e814610262578063fd3ab2821461026a57610100565b8063996a4d67146101ed5780639ae8886a146101f5578063b8c9d3651461020f578063e07783a11461021757610100565b80637136fa43116100d35780637136fa431461016a578063724a3728146101725780637ac0df421461019b5780638558c895146101ca57610100565b806319b5c4a71461010557806345c39a681461010f5780635df16f571461013e5780635ec2c7bf14610146575b600080fd5b61010d610272565b005b61010d6004803603608081101561012557600080fd5b50803590602081013590604081013590606001356103e6565b61010d6104b0565b61014e61056c565b604080516001600160a01b039092168252519081900360200190f35b61010d61057b565b61010d6004803603606081101561018857600080fd5b50803590602081013590604001356108a7565b61010d600480360360808110156101b157600080fd5b508035906020810135906040810135906060013561097a565b61010d600480360360408110156101e057600080fd5b5080359060200135610a3a565b61010d610ad2565b6101fd610cce565b60408051918252519081900360200190f35b6101fd610cd4565b61010d610cda565b6101fd611176565b61010d600480360360c081101561023d57600080fd5b5080359060208101359060408101359060608101359060808101359060a0013561117c565b61010d611408565b6101fd611491565b60038060175460ff16600681111561028657fe5b146102c25760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b6000546001600160a01b0316331461030b5760405162461bcd60e51b815260040180806020018281038252602a815260200180611498602a913960400191505060405180910390fd5b6000805b6007548110156103d4576000818152600860205260409020600180548490811061033557fe5b60009182526020808320909101548354600181810186559484528284200180546001600160a01b0319166001600160a01b0390921691909117905583825260089052604090208154938201939091908490811061038e57fe5b6000918252602080832090910154835460018181018655948452919092200180546001600160a01b0319166001600160a01b03909216919091179055918201910161030f565b50506017805460ff1916600417905550565b60048060175460ff1660068111156103fa57fe5b146104365760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b3360009081526005602090815260408220805460018082018355828552929093209283018890558054808301825583018790558054808301825583018690558054808301909155909101839055600c805482019081905511156104a957601780546005919060ff19166001835b02179055505b5050505050565b60018060175460ff1660068111156104c457fe5b146105005760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b60018054808201825560008290527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319163317905560068054909101908190556003101561056957601780546002919060ff19166001835b02179055505b50565b6000546001600160a01b031681565b6014546001600081815260086020526000805160206114e483398151915280546009939081106105a757fe5b60009182526020808320909101546001600160a01b03168352820192909252604001812080549091906105d657fe5b906000526020600020015460096000600860006001815260200190815260200160002060018154811061060557fe5b60009182526020808320909101546001600160a01b0316835282019290925260400190208054600390811061063657fe5b906000526020600020015460096000600860006001815260200190815260200160002060008154811061066557fe5b60009182526020808320909101546001600160a01b031683528201929092526040018120805490919061069457fe5b90600052602060002001546009600060086000600181526020019081526020016000206000815481106106c357fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460039081106106f457fe5b90600052602060002001540301038161070957fe5b06600d556000808052600860205260008051602061150483398151915280546009929190829061073557fe5b60009182526020808320909101546001600160a01b0316835282019290925260400190208054600390811061076657fe5b9060005260206000200154600960006008600080815260200190815260200160002060008154811061079457fe5b60009182526020808320909101546001600160a01b031683528201929092526040019020805460019081106107c557fe5b60009182526020909120015418600e819055601454600d54600282049202816107ea57fe5b061161084757604080516020808252600f908201526e20b634b1b29034b9903934b1b432b960891b8183015290517f2836fc1569f2cf0eafa6bae027d7b993edbe7762df0ab247aecfcf12f412244d9181900360600190a1610898565b604080516020808252600d908201526c2137b11034b9903934b1b432b960991b8183015290517f2836fc1569f2cf0eafa6bae027d7b993edbe7762df0ab247aecfcf12f412244d9181900360600190a15b6017805460ff19166006179055565b6000546001600160a01b031633146108f05760405162461bcd60e51b815260040180806020018281038252602a815260200180611498602a913960400191505060405180910390fd5b60068060175460ff16600681111561090457fe5b146109405760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b5060408051602080820195909552808201939093526060808401929092528051808403909201825260809092019091528051910120601055565b60008060175460ff16600681111561098e57fe5b146109ca5760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b6000546001600160a01b03163314610a135760405162461bcd60e51b815260040180806020018281038252602a815260200180611498602a913960400191505060405180910390fd5b6013859055601484905560158390556016829055601780546001919060ff191682806104a3565b60068060175460ff166006811115610a4e57fe5b14610a8a5760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b50600f80546001818101835560008390527f8d1108e10bcb7c27dddfc02ed9d693a074039d026cf4ea4240b40f7d581ac8029182019490945581549384019091559190910155565b60068060175460ff166006811115610ae657fe5b14610b225760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b6014546001600090815260086020526000805160206114e4833981519152805460099291908290610b4f57fe5b60009182526020808320909101546001600160a01b03168352820192909252604001902080546001908110610b8057fe5b9060005260206000200154600960006008600060018152602001908152602001600020600081548110610baf57fe5b60009182526020808320909101546001600160a01b03168352820192909252604001902080546004908110610be057fe5b90600052602060002001540381610bf357fe5b066011556014546001600081815260086020526000805160206114e48339815191528054600993908110610c2357fe5b60009182526020808320909101546001600160a01b03168352820192909252604001902080546001908110610c5457fe5b9060005260206000200154600960006008600060018152602001908152602001600020600181548110610c8357fe5b60009182526020808320909101546001600160a01b03168352820192909252604001902080546004908110610cb457fe5b90600052602060002001540381610cc757fe5b0660125550565b60135481565b60165481565b6001600090815260086020526000805160206114e4833981519152805460099291908290610d0457fe5b60009182526020808320909101546001600160a01b0316835282810193909352604090910181208180526008909252600080516020611504833981519152805460099291908290610d5157fe5b60009182526020808320909101546001600160a01b03168352820192909252604001902080546002908110610d8257fe5b6000918252602080832090910154835460018181018655948452828420015591815260089091526000805160206114e4833981519152805460099291908290610dc757fe5b60009182526020808320909101546001600160a01b0316835282810193909352604090910181208180526008909252600080516020611504833981519152805460099291908290610e1457fe5b60009182526020808320909101546001600160a01b0316835282019290925260400181208054909190610e4357fe5b60009182526020808320909101548354600181018555938352818320909301929092558080526008909152600080516020611504833981519152805460099291908290610e8c57fe5b60009182526020808320909101546001600160a01b0316835282810193909352604090910181206001825260089092526000805160206114e4833981519152805460099291908290610eda57fe5b60009182526020808320909101546001600160a01b03168352820192909252604001902080546001908110610f0b57fe5b60009182526020808320909101548354600181810186559484528284200155828252600890526000805160206114e48339815191528054600993908110610f4e57fe5b60009182526020808320909101546001600160a01b0316835282810193909352604090910181208180526008909252600080516020611504833981519152805460099291906001908110610f9e57fe5b60009182526020808320909101546001600160a01b0316835282019290925260400181208054909190610fcd57fe5b60009182526020808320909101548354600181810186559484528284200155828252600890526000805160206114e4833981519152805460099390811061101057fe5b60009182526020808320909101546001600160a01b031683528281019390935260409091018120818052600890925260008051602061150483398151915280546009929190600190811061106057fe5b60009182526020808320909101546001600160a01b0316835282019290925260400190208054600290811061109157fe5b600091825260208083209091015483546001818101865594845282842001558180526008905260008051602061150483398151915280546009939081106110d457fe5b60009182526020808320909101546001600160a01b031683528281019390935260409091018120600180835260089093526000805160206114e48339815191528054919360099392811061112457fe5b60009182526020808320909101546001600160a01b0316835282019290925260400190208054600190811061115557fe5b60009182526020808320909101548354600181018555938352912090910155565b60155481565b60058060175460ff16600681111561119057fe5b146111cc5760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b336000908152600460209081526040808320548352600890915281208054600992919082906111f757fe5b60009182526020808320909101546001600160a01b0316835282810193909352604091820181208054600181018255908252838220018a9055338152600483528181205481526008909252812080546009929190829061125357fe5b60009182526020808320909101546001600160a01b03168352828101939093526040918201812080546001810182559082528382200189905533815260048352818120548152600890925281208054600992919082906112af57fe5b60009182526020808320909101546001600160a01b03168352828101939093526040918201812080546001818101835591835284832001899055338252600484528282205482526008909352908120805460099390811061130c57fe5b60009182526020808320909101546001600160a01b03168352828101939093526040918201812080546001818101835591835284832001889055338252600484528282205482526008909352908120805460099390811061136957fe5b60009182526020808320909101546001600160a01b0316835282810193909352604091820181208054600181810183559183528483200187905533825260048452828220548252600890935290812080546009939081106113c657fe5b60009182526020808320909101546001600160a01b03168352828101939093526040909101812080546001810182559082529190200191909155505050505050565b60028060175460ff16600681111561141c57fe5b146114585760405162461bcd60e51b81526004018080602001828103825260228152602001806114c26022913960400191505060405180910390fd5b60078054336000908152600460205260409020819055600190810191829055101561056957601780546003919060ff1916600183610563565b6014548156fe6f6e6c79207468652061756374696f6e6565722063616e206163636573732074686973206d6574686f646d6574686f64206e6f7420617661696c61626c652061742074686973207374617465ad67d757c34507f157cacfa2e3153e9f260a2244f30428821be7be64587ac55f5eff886ea0ce6ca488a3d6e336d6c0f75f46d19b42c06ce5ee98e42c96d256c7a265627a7a7231582043c522589f7f2bc5cb9688f447373f50b7b43f6b184d503a63a07138783d593764736f6c634300050b0032";

    public static final String FUNC_MAPNOTARIES = "mapNotaries";

    public static final String FUNC_VALUESUBMISSION = "valueSubmission";

    public static final String FUNC_REGISTERNOTARIES = "registerNotaries";

    public static final String FUNC_AUCTIONEER = "auctioneer";

    public static final String FUNC_AUCTIONEEREXCHANGE = "auctioneerExchange";

    public static final String FUNC_POSTHASH = "postHash";

    public static final String FUNC_SETUPPARAMETERS = "setupParameters";

    public static final String FUNC_POSTCHALLENGE = "postChallenge";

    public static final String FUNC_GETHELPVALUES = "getHelpValues";

    public static final String FUNC_P = "p";

    public static final String FUNC_H = "h";

    public static final String FUNC_NOTARIESEXCHANGE = "notariesExchange";

    public static final String FUNC_G = "g";

    public static final String FUNC_INTERMEDIATEVALUES = "intermediateValues";

    public static final String FUNC_REGISTERAGENTS = "registerAgents";

    public static final String FUNC_Q = "q";

    public static final Event WINNER_EVENT = new Event("winner", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected ______PPC_sol_PPC(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ______PPC_sol_PPC(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ______PPC_sol_PPC(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ______PPC_sol_PPC(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> mapNotaries() {
        final Function function = new Function(
                FUNC_MAPNOTARIES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> valueSubmission(BigInteger _u, BigInteger _v, BigInteger _c1, BigInteger _c2) {
        final Function function = new Function(
                FUNC_VALUESUBMISSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_u), 
                new org.web3j.abi.datatypes.generated.Uint256(_v), 
                new org.web3j.abi.datatypes.generated.Uint256(_c1), 
                new org.web3j.abi.datatypes.generated.Uint256(_c2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> registerNotaries() {
        final Function function = new Function(
                FUNC_REGISTERNOTARIES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> auctioneer() {
        final Function function = new Function(FUNC_AUCTIONEER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> auctioneerExchange() {
        final Function function = new Function(
                FUNC_AUCTIONEEREXCHANGE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> postHash(BigInteger _1, BigInteger _2, BigInteger _3) {
        final Function function = new Function(
                FUNC_POSTHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_1), 
                new org.web3j.abi.datatypes.generated.Uint256(_2), 
                new org.web3j.abi.datatypes.generated.Uint256(_3)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setupParameters(BigInteger _p, BigInteger _q, BigInteger _g, BigInteger _h) {
        final Function function = new Function(
                FUNC_SETUPPARAMETERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_p), 
                new org.web3j.abi.datatypes.generated.Uint256(_q), 
                new org.web3j.abi.datatypes.generated.Uint256(_g), 
                new org.web3j.abi.datatypes.generated.Uint256(_h)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> postChallenge(BigInteger _x, BigInteger _y) {
        final Function function = new Function(
                FUNC_POSTCHALLENGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_x), 
                new org.web3j.abi.datatypes.generated.Uint256(_y)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getHelpValues() {
        final Function function = new Function(
                FUNC_GETHELPVALUES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> p() {
        final Function function = new Function(FUNC_P, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> h() {
        final Function function = new Function(FUNC_H, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> notariesExchange() {
        final Function function = new Function(
                FUNC_NOTARIESEXCHANGE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> g() {
        final Function function = new Function(FUNC_G, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> intermediateValues(BigInteger _t1, BigInteger _t2, BigInteger _t3, BigInteger _t4, BigInteger _t5, BigInteger _t6) {
        final Function function = new Function(
                FUNC_INTERMEDIATEVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_t1), 
                new org.web3j.abi.datatypes.generated.Uint256(_t2), 
                new org.web3j.abi.datatypes.generated.Uint256(_t3), 
                new org.web3j.abi.datatypes.generated.Uint256(_t4), 
                new org.web3j.abi.datatypes.generated.Uint256(_t5), 
                new org.web3j.abi.datatypes.generated.Uint256(_t6)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> registerAgents() {
        final Function function = new Function(
                FUNC_REGISTERAGENTS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> q() {
        final Function function = new Function(FUNC_Q, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<WinnerEventResponse> getWinnerEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WINNER_EVENT, transactionReceipt);
        ArrayList<WinnerEventResponse> responses = new ArrayList<WinnerEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WinnerEventResponse typedResponse = new WinnerEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.str = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WinnerEventResponse> winnerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WinnerEventResponse>() {
            @Override
            public WinnerEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WINNER_EVENT, log);
                WinnerEventResponse typedResponse = new WinnerEventResponse();
                typedResponse.log = log;
                typedResponse.str = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WinnerEventResponse> winnerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WINNER_EVENT));
        return winnerEventFlowable(filter);
    }

    @Deprecated
    public static ______PPC_sol_PPC load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ______PPC_sol_PPC(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ______PPC_sol_PPC load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ______PPC_sol_PPC(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ______PPC_sol_PPC load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ______PPC_sol_PPC(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ______PPC_sol_PPC load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ______PPC_sol_PPC(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<______PPC_sol_PPC> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(______PPC_sol_PPC.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<______PPC_sol_PPC> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(______PPC_sol_PPC.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<______PPC_sol_PPC> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(______PPC_sol_PPC.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<______PPC_sol_PPC> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(______PPC_sol_PPC.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class WinnerEventResponse {
        public Log log;

        public String str;
    }
}
