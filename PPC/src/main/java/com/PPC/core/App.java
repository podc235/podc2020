
package com.PPC.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.math.BigInteger;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.math.BigDecimal;
import java.util.function.*;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;
// imports for gui application
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
// import for randomness
import java.util.Random;
// imports for cryptos
import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;
// arrray list
import java.util.ArrayList;
import java.util.List;
// read/write file
import java.io.File;
import java.io.IOException;

public class App {
	static private final Logger log = LoggerFactory.getLogger(App.class);
	// global datatypes
	static ______PPC_sol_PPC CTcontract;
	Credentials credAuctioneer;
	static List<Credentials> credNotaries = new ArrayList<Credentials>();
	static List<Credentials> credAgents = new ArrayList<Credentials>();
	static Web3j web3j;
	static Web3ClientVersion web3ClientVersion;
	static String contractAddress;
	static BigInteger p,q,g,h;
	
	//static RSACertification rsa_certification_created;
	static KeyPair master_pair;
	static int cert_count = 0;
	static PrivateKey master_private_key;
	static PublicKey master_public_key;
	static KeyPair requestor_pair;
	static PrivateKey requestor_private_key;
	static PublicKey requestor_public_key;
	static int total_workers;
	
	// data structure to store all the certificates
	static List<TransactionReceipt> txList = new ArrayList<TransactionReceipt>();
	static List<String> certificate_list = new ArrayList<String>();
	static List<String> workerInput_list = new ArrayList<String>();
	static List<String> response_cipher_list = new ArrayList<String>();

	/* ---- Private keys for input of the agents */
	String[] notData = { "621505C39CDD78D8F74E7BF71DFF01824DFF9C5CCBA386C24A6A350376CB9A0B",
						"5088B89727FD9A90A8134E03F0B8ECB3BF6D7F250CE524B0ECEA563E4E8394D2",
						"016F3CE8B03CE4B1889A56869E1C3851709615C1EB83E8950B48EC43B6A9FE87",
						"C4C146F93647F86FB967E42F773D39E0231C04B76A90F86476E4DDC83B95DDE4"
		};
		
	String[] agentData = { "8040BF35FCE29390647B6724E6076EB055B9472C049EA4A366A8AF3F429A0F6E",
		                 "482C564BCE9F663430228A40309A174C3219C3D7E657593AA85DEED2074239E5"
	};
	
	String aucData = "E055286653578D77B36BA59A47EF57FADF9CD5B99A8A3A6C9E6A1A3B60809B20";
	 

	public static void main(String[] args) throws Exception {
		// generating the master key pair which will be constant
		// throughout the protocol
		// for simplicity, we only have one pair (cost will be the same regardless)
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(512, new SecureRandom());   // 2048 bits is the security parameter
		/* generate master keys of RA */
		master_pair = generator.generateKeyPair();
		master_private_key = master_pair.getPrivate();
		master_public_key = master_pair.getPublic();

		/* generate requestor key pair
		requestor_pair = generator.generateKeyPair();
		requestor_private_key = requestor_pair.getPrivate();
		requestor_public_key = requestor_pair.getPublic();
		*/

		// starting web3j connection
		// Start Web3 connection
		web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/e732435ba40d4d2c948ab4a9d3eace97"));
		web3ClientVersion = web3j.web3ClientVersion().send();
		log.info("Web3j version used: " + web3ClientVersion.getWeb3ClientVersion());

		// generate system parameters
		genParameters();

		new App().init(); // start PPC
	}

	public void init() throws Exception {
		// deploy the contract
		log.info("Deploying the contract..");
		credAuctioneer = Credentials.create(aucData); // get the auctioneer
		CTcontract = ______PPC_sol_PPC.deploy(web3j, credAuctioneer, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT).send();
        contractAddress = CTcontract.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);
		
		// setup parameters
		log.info("Setting up the parameters.. "); 
		txList.add(CTcontract.setupParameters( p, q, g, h ).send());

		/* get the notaries
		*/
		log.info("Waiting for notaries to register..");
		// first get the credentials
		for (int i=0; i<4; i++) {	
			credNotaries.add(Credentials.create(notData[i]));		
			______PPC_sol_PPC CTcontract_notary = ______PPC_sol_PPC.load(contractAddress, web3j, credNotaries.get(i), 
					DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
			log.info("Notary Registered: ");
			txList.add(CTcontract_notary.registerNotaries().send());
		} 

		/* register Alice and Bob
		*/
		log.info("Registering Alice and Bob..");
		______PPC_sol_PPC CTcontract_agent;
		for (int i=0; i<2; i++) {	
			credAgents.add(Credentials.create(agentData[i]));	
			CTcontract_agent = ______PPC_sol_PPC.load(contractAddress, web3j, credAgents.get(i), 
					DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
			txList.add(CTcontract_agent.registerAgents().send());
		} 
		
		// map agents to notaries
		CTcontract= ______PPC_sol_PPC.load(contractAddress, web3j, credAuctioneer, 
					DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
		log.info("Mapping Alice and Bob to notaries to complete the setup.. ");
		txList.add(CTcontract.mapNotaries().send());

		// moving to computation
		computation();

	} // init block

	public void computation() throws Exception {
		log.info("Waiting for Alice and Bob to submit values.. ");
		// Alice			
		______PPC_sol_PPC CTcontract_agent = ______PPC_sol_PPC.load(contractAddress, web3j, credAgents.get(0), 
					DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
		txList.add(CTcontract_agent.valueSubmission(
				BigInteger.valueOf(350), BigInteger.valueOf(2), BigInteger.valueOf(250), BigInteger.valueOf(2)
		).send());
		
		// Bob
		CTcontract_agent = ______PPC_sol_PPC.load(contractAddress, web3j, credAgents.get(1), 
		DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
		txList.add(CTcontract_agent.valueSubmission(
			BigInteger.valueOf(300), BigInteger.valueOf(3), BigInteger.valueOf(299), BigInteger.valueOf(3)
		).send());
		
		log.info("Waiting for Alice and Bob to send values to notaries... ");

		// Alice			
		CTcontract_agent = ______PPC_sol_PPC.load(contractAddress, web3j, credAgents.get(0), 
					DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
		txList.add(CTcontract_agent.intermediateValues(
				BigInteger.valueOf(350), BigInteger.valueOf(2), genInt(128), BigInteger.valueOf(250), BigInteger.valueOf(2), genInt(128)
		).send());

		// Bob
		CTcontract_agent = ______PPC_sol_PPC.load(contractAddress, web3j, credAgents.get(1), 
		DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
		txList.add(CTcontract_agent.intermediateValues(
			BigInteger.valueOf(300), BigInteger.valueOf(3), genInt(128), BigInteger.valueOf(299), BigInteger.valueOf(3), genInt(128)
		).send());	



		log.info("Notaries share first round of values with each other.. "); 
		txList.add(CTcontract.notariesExchange().send());

		log.info("Notaries share values with central server.. ");
		txList.add(CTcontract.auctioneerExchange().send());
		
		log.info("Richer party emitter as an event.. ");

		ppcVerify();
		// gasCal();

	} // computation block

	public void gasCal() throws Exception {
		BigInteger totalCost = BigInteger.valueOf(0);

		for (int i=0; i<txList.size(); i++) {
			totalCost = totalCost.add(txList.get(i).getGasUsed());
		}
		System.out.println(totalCost);
		System.out.println("===========================");
		System.exit(0);
	} // gas block

	//
	//
	/* 
			~~~ PPC Verification ~~~~
	*/
	//
	//

	public void ppcVerify() throws Exception {
		log.info("Verifier posts its challenge.. ");
		txList.add(CTcontract.postChallenge(genInt(128), genInt(128)).send());

		log.info("CS posts the hash of the values..");
		txList.add(CTcontract.postHash(genInt(128), genInt(128), genInt(128)).send());

		log.info("CS gets the help values.. ");
		txList.add(CTcontract.getHelpValues().send());
		// get the gas
		gasCal();
	}


	//
	//
	/* 
			~~~ Functions to generate Big Integers ~~~~
	*/
	//
	//

	// returns a n-bit big integer
	public static BigInteger genInt(int n) {
		Random rand = new Random();
        return new BigInteger(128, rand);
	}

	// function to generate n-bit prime
	public static int isPrime(BigInteger x) {
		if (x.isProbablePrime(1)) return 0;
		else return 1;
	}  

	// function to generate parameters
	public static void genParameters() throws Exception {
		int flag = 1;
		BigInteger val = BigInteger.valueOf(0);
		while(flag == 1) {
			val = genInt(256);
			if (isPrime(val)==0) flag = 0;
			else flag = 1;
		}
		p = val;
		flag = 1;
		BigInteger pM = p.subtract(BigInteger.valueOf(1));
		while(flag == 1) {
			val = genInt(256);
			//if (isPrime(val)==0 && p.divideAndRemainder(val)[1]==BigInteger.valueOf(0) ) flag = 0;
		    if (isPrime(val)==0) flag = 0;
			else flag = 1;
		}
		q = val;
		g = BigInteger.valueOf(2);
		h = g.modPow(BigInteger.valueOf(3), p);
	}


} // class block
