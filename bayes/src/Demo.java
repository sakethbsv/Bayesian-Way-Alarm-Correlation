/* 
 * Demo.java
 *
 * Example use of Netica-J to build a Bayes net and use it for inference.
 *
 * Copyright (C) 1992-2007 by Norsys Software Corp.
 * The software in this file may be copied, modified, and/or included in 
 * derivative works without charge or obligation.
 * This file contains example software only, and Norsys makes no warranty that 
 * it is suitable for any particular purpose, or without defects.
 */

import java.util.ArrayList;
import norsys.netica.*;
import norsys.neticaEx.aliases.Node;
     
public class Demo {

	
	public static void main (String[] args){
	    try {
		//System.out.println ("\nWelcome to Netica-J !\n");
		
		      String path = System.getProperty("java.library.path");
		  //    System.out.println(path);
		//   }
//		}

		Node.setConstructorClass ("norsys.neticaEx.aliases.Node");
		//Environ env = 
		new Environ (null);

		Net   net          = new Net();

		Node  BSC    	= new Node ("BSC",  "Normal,Fault",    net);
		Node  BTS1 		= new Node ("BTS1", "Normal,Alarm",    net);
		Node  BTS2      = new Node ("BTS2", "Normal,Alarm",  net);
		Node  BTS3      = new Node ("BTS3", "Normal,Alarm",  net);
		Node  ML1       = new Node ("ML1",  "Normal,Fault",    net);
		Node  ML2       = new Node ("ML2",  "Normal,Fault",    net);
		Node  ML3       = new Node ("ML3",  "Normal,Fault",    net);
//		Node  xRay         = new Node ("XRay",         "abnormal,normal",   net);
		
		
		
		ML1.addLink (BSC);
		BTS1.addLink(ML1);
		BTS1.addLink(BSC);
		ML2.addLink (BTS1);
		BTS2.addLink (ML2);
		BTS2.addLink (BTS1);
		ML3.addLink(BTS2);
		BTS3.addLink(ML3);
		BTS3.addLink(BTS2);
		
		//ML2.addLink (ML1);
		//xRay.addLink (ML2);
		Node MLinks[] = {ML2, ML3}; 

		float[] BSCProbs = { 0.99F, 0.01F };
		int[] aBSCParentStates = null;
		BSC.setCPTable ( aBSCParentStates, BSCProbs );
		
		ML1.setCPTable ("Normal", 0.5,   0.5);
		ML1.setCPTable ("Fault", 0.5, 0.5);
		
		
		for(Node ml : MLinks){
			
			ml.setCPTable ("Normal", 0.5,   0.5);
			ml.setCPTable ("Alarm", 0.5, 0.5);
			
		}
		
		Node BTSArr[] = {BTS1, BTS2, BTS3}; 

		for(Node BTS : BTSArr){
		
		int[] aparentStates =  new int[2];
	    aparentStates[0] = 0;
	    aparentStates[1] = 0;
	    float[] aprobsRow1 = {0.95F, 0.05F};
	    BTS.setCPTable (aparentStates, aprobsRow1);
	    
	    aparentStates[0] = 0;
	    aparentStates[1] = 1;
	    float[] aprobsRow2 = {0.05F, 0.95F};
	    BTS.setCPTable (aparentStates, aprobsRow2);
	         
	    aparentStates[0] = 1;
	    aparentStates[1] = 0;
	    float[] aprobsRow4 = {0.05F, 0.95F};
	    BTS.setCPTable (aparentStates, aprobsRow4);
	    
	    aparentStates[0] = 1;
	    aparentStates[1] = 1;
	    float[] aprobsRow5 = {0.05F, 0.95F};
	    BTS.setCPTable (aparentStates, aprobsRow5);
	    
		}
		
	    net.compile();

		BTS2.finding().enterState ("Alarm");
	    BTS1.finding().enterState ("Alarm");
		BTS3.finding().enterState ("Alarm");
		
		double belief = ML1.getBelief ("Fault");          
		System.out.println ("Probability of ML1 fault if alarm from both is got:  " + belief);
		
		//double belief = BTS1.getBelief ("Fault");          
		//System.out.println ("Probability of BTS1 fault if alarm from both is got:  " + belief);
		
		belief = ML2.getBelief ("Fault");          
		System.out.println ("Probability of ML2 fault if alarm from both is got:  " + belief);
		
		belief = ML3.getBelief ("Fault");          
		System.out.println ("Probability of ML3 fault if alarm from both is got:  " + belief);
		

		net.finalize();   
	    }
	    catch (Exception e) {
		e.printStackTrace();
	    }
	  }
	}

