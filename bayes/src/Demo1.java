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

import norsys.netica.*;
import norsys.neticaEx.aliases.Node;
     
     
public class Demo1 {

  public static void main (String[] args){
    try {
	System.out.println ("\nWelcome to Netica-J !\n");
	
	      String path = System.getProperty("java.library.path");
	      System.out.println(path);
	//   }
//	}
	      
	      /*
	      public static void main (String[] args){
	        try {
	    	System.out.println ("\nWelcome to Netica-J !\n");
	    	
	    	      String path = System.getProperty("java.library.path");
	    	      System.out.println(path);
	    	//   }
//	    	}

	    	Node.setConstructorClass ("norsys.neticaEx.aliases.Node");
	    	//Environ env = 
	    	new Environ (null);

	    	Net   net          = new Net();

	    	Node  visitAsia    = new Node ("VisitAsia",    "visit,no_visit",    net);
	    	Node  tuberculosis = new Node ("Tuberculosis", "present,absent",    net);
	    	Node  smoking      = new Node ("Smoking",      "smoker,nonsmoker",  net);
	    	Node  cancer       = new Node ("Cancer",       "present,absent",    net);
	    	Node  tbOrCa       = new Node ("TbOrCa",       "true,false",        net);
	    	Node  xRay         = new Node ("XRay",         "abnormal,normal",   net);

	    	tuberculosis.addLink (visitAsia);
	    	cancer.addLink (smoking);
	    	tbOrCa.addLink (tuberculosis);
	    	tbOrCa.addLink (cancer);
	    	xRay.addLink (tbOrCa);

	    	float[] asiaProbs = { 0.01F, 0.99F };
	    	int[] asiaParentStates = null;
	    	visitAsia.setCPTable ( asiaParentStates, asiaProbs );

	    	tuberculosis.setCPTable ("visit",    0.05, 0.95);
	    	tuberculosis.setCPTable ("no_visit", 0.01, 0.99);
	    	
	    	smoking.setCPTable (0.5, 0.5);

	    	cancer.setCPTable ("smoker",    0.1,   0.9);
	    	cancer.setCPTable ("nonsmoker", 0.01,  0.99);
	    	
	    	tbOrCa.setEquation ("TbOrCa (Tuberculosis, Cancer) = Tuberculosis || Cancer");
	    	tbOrCa.equationToTable (1, false, false);

	    	//               TbOrCa     abnormal normal
	    	xRay.setCPTable ("true",    0.98,    0.02);
	    	xRay.setCPTable ("false",   0.05,    0.95);

	    	net.compile();

	    	double belief = tuberculosis.getBelief ("present");          
	    	System.out.println ("\nThe probability of tuberculosis is " + belief);

	    	xRay.finding().enterState ("abnormal");
	    	belief = tuberculosis.getBelief ("present");          
	    	System.out.println ("\nGiven an abnormal X-ray,\n"+
	    			    "the probability of tuberculosis is " + belief);

	    	visitAsia.finding().enterState ("visit");
	    	belief = tuberculosis.getBelief ("present");          
	    	System.out.println ("\nGiven an abnormal X-ray and a visit to Asia,\n" +
	    			    "the probability of tuberculosis is " + belief );

	    	cancer.finding().enterState ("present");
	    	belief = tuberculosis.getBelief ("present");          
	    	System.out.println ("\nGiven an abnormal X-ray, Asia visit, and lung cancer,\n" +
	    			    "the probability of tuberculosis is " + belief + "\n");

	    	net.finalize();   // not strictly necessary, but a good habit
	        }
	        catch (Exception e) {
	    	e.printStackTrace();
	        }
	      }
	    }
	    */
	      
	      
	      

	Node.setConstructorClass ("norsys.neticaEx.aliases.Node");
	//Environ env = 
	new Environ (null);

	Net   net          = new Net();

	Node  BSC    	= new Node ("BSC",  "Normal,Fault",    net);
	Node  BTS1 		= new Node ("BTS1", "Normal,Fault,Alarm",    net);
	Node  BTS2      = new Node ("BTS2", "Normal,Fault,Alarm",  net);
//	Node  ML1       = new Node ("ML1",       "Normal,Fault",    net);
//	Node  ML2       = new Node ("ML2",       "Normal,Fault",        net);
//	Node  xRay         = new Node ("XRay",         "abnormal,normal",   net);

	BSC.addLink(BTS1);
	//ML1.addLink (BTS1);
	BTS1.addLink (BTS2);
	//ML2.addLink (BTS2);
	//ML2.addLink (ML1);
	//xRay.addLink (ML2);

	float[] BSCProbs = { 0.99F, 0.01F };
	int[] aBSCParentStates = null;
	BSC.setCPTable ( aBSCParentStates, BSCProbs );
	
	BTS1.setCPTable ("Normal", 0.9,   0.1, 0);
	BTS1.setCPTable ("Fault", 0.05,  0.05, 0.9);
	//ML1.setCPTable ("Alarm", 0.9,  0.1);
	
	BTS2.setCPTable ("Normal", 0.9,   0.1, 0);
    BTS2.setCPTable ("Fault", 0.05,  0.05, 0.9);
	BTS2.setCPTable ("Alarm", 0.05, 0.05,  0.9);
	
	
	net.compile();

	double belief = BTS1.getBelief ("present");          
	System.out.println ("\nThe probability of tuberculosis is " + belief);

	BTS2.finding().enterState ("Alarm");
	belief = BTS1.getBelief ("Fault");          
	System.out.println ("Probability of BTS1 fault:  " + belief);

	net.finalize();  
    }
    catch (Exception e) {
	e.printStackTrace();
    }
  }
}

