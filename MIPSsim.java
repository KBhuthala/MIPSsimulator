/* On my honor, I have neither given nor received unauthorized aid on this assignment */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MIPSsim {
	
	MIPSsim newLoc;
	String instruction, offsetValue, immedte, rtRgstr, rdRgstr, rsRgstr, baAddrs, address;
	int insAddrs;
	    
		 public MIPSsim() {
			 insAddrs = 0; instruction = ""; address = ""; newLoc = null; baAddrs = "";rsRgstr = "";rtRgstr = "";rdRgstr = "";offsetValue = ""; immedte = "";			   			    			    
			    	      }
			  		  
		 public MIPSsim(int insAd, String Ins,String ads,MIPSsim np, String baAd, String rsRg,String rtRg,String rdRg, String offVal, String imm ) {
				insAddrs = insAd; instruction = Ins;address = ads;newLoc = np; baAddrs = baAd;rsRgstr = rsRg; rtRgstr = rtRg;rdRgstr = rdRg;offsetValue = offVal;immedte = imm;			    											   			    			    					    
		        }
   	
		public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile=null;
		if (0 < args.length) 
		{
	      inputFile = new File(args[0]);
	    }
		ArrayList<String> inputInst = new ArrayList<String>();
		BufferedReader bfr = null;
	    try {
	    		// Reads input file(32 bits at once) with the filename specified in cmd
	            String lineOfInst;
	            bfr = new BufferedReader(new FileReader(inputFile));
	            while ((lineOfInst = bfr.readLine()) != null) 
	            {
	            inputInst.add(lineOfInst.substring(0,32));  
	           
	            }
	        } 
	    catch (IOException e) { e.printStackTrace(); } 
	    int InstAddress = 256; //Start address of the instruction words
	    ArrayList<String> disassembledInsts = new ArrayList<String>();
	    ArrayList<Integer> intValues = new ArrayList<Integer>();
	    
	  
	    
	    try {
	    	// Initialize the output files to write
			PrintWriter writer = new PrintWriter("disassembly.txt");
			PrintWriter simulate = new PrintWriter("simulation.txt");
			
	    
        String currentInst, category, opCode, instIndex, rsReg, rtReg, rdReg, immValue, baseReg, sa, offset = null;
        String disassembledInst = "";
        String instruction = "";
        String lastInstruction = "";
        
        MIPSsim point = new MIPSsim();
        MIPSsim thisLoc = point;
       
        
	    for (int i = 0; i < inputInst.size(); i++) 
	    { 
	    	currentInst =  inputInst.get(i);
	    	category = currentInst.substring(0,2);
	    	if(lastInstruction != "BREAK") {
	    	if(category.equals("01"))
	    		 {	    		 
	    			opCode = currentInst.substring(2,6);
	    			if(opCode.equals("0000"))
	    			{
	    				instruction = "J"; //Jump Instruction
	    				instIndex = currentInst.substring(6,32);
	    				int decimalValue = Integer.parseInt(instIndex, 2);
	    				String targetAddr = Integer.toString(4*decimalValue);
	    				String jTarget = "#" + targetAddr;
	    				disassembledInst = "J "+jTarget;
	    				writer.println(currentInst + "\t"+ Integer.toString(InstAddress) + "\t" + disassembledInst);
	    				
	    				MIPSsim tempLoc = new MIPSsim(InstAddress, "J", targetAddr, null, "", "", "", "","", "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    
	    			}
	    			else if(opCode.equals("0001"))
	    			{
	    				instruction = "JR"; //Jump Register instruction
	    				rsReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rsReg, 2);
		    			String rsReg_Value = Integer.toString(decimalValue);		                    
		                String rsReg_Name = "R" + rsReg_Value;
		                disassembledInst = "JR "+rsReg_Name;
	    				writer.println(currentInst + "\t"+ Integer.toString(InstAddress) + "\t" + disassembledInst);
	    				
	    				MIPSsim tempLoc = new MIPSsim(InstAddress, "JR", "",null, "", rsReg_Value, "", "", "","");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	    						                
	    			}
	    			else if(opCode.equals("0010"))
	    			{
	    				instruction = "BEQ"; // Branch on equal instruction 
	    				rsReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rsReg, 2);
		    			String rsReg_Value = Integer.toString(decimalValue);		                    
		                String rsReg_Name = "R" + rsReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
		                 
			            offset = currentInst.substring(16,32); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(offset, 2);
			    		String offset_Value = Integer.toString(4*decimalValue);
		                String offsetShow = "#" + offset_Value; 
		                    
		                disassembledInst = "BEQ " + rsReg_Name + ", " + rtReg_Name + ", " + offsetShow;
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "BEQ","", null,"",rsReg_Value,rtReg_Value,"",offset_Value,"");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    
	    			
	    			}
	    			else if(opCode.equals("0011"))
	    			{
	    				instruction = "BLTZ"; //Branch if less than zero
	    				rsReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rsReg, 2);
		    			String rsReg_Value = Integer.toString(decimalValue);		                    
		                String rsReg_Name = "R" + rsReg_Value; 
		                    		           		                 
			            offset = currentInst.substring(16,32); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(offset, 2);
			    		String offset_Value = Integer.toString(4*decimalValue);
		                String offsetShow = "#" + offset_Value; 
		                    
		                disassembledInst = "BLTZ " + rsReg_Name +   ", " + offsetShow; 
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "BLTZ", "",null, "",rsReg_Value,"","", offset_Value, "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	    			
	    			}
	    			else if(opCode.equals("0100"))
	    			{
	    				instruction = "BGTZ"; //Branch if greater than zero
	    				rsReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rsReg, 2);
		    			String rsReg_Value = Integer.toString(decimalValue);		                    
		                String rsReg_Name = "R" + rsReg_Value; 
		                    		           		                 
			            offset = currentInst.substring(16,32); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(offset, 2);
			    		String offset_Value = Integer.toString(4*decimalValue);
		                String offsetShow = "#" + offset_Value; 
		                    
		                disassembledInst = "BGTZ " + rsReg_Name +   ", " + offsetShow; 
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "BGTZ", "",null, "",rsReg_Value,"","", offset_Value, "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    }
	    			
	    			
	    			else if(opCode.equals("0101"))
	    			{
	    				instruction = "BREAK"; 
	    				disassembledInst = "BREAK";
	                    writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
	                    
	                    MIPSsim tempLoc = new MIPSsim(InstAddress, "BREAK", "",null,"", "", "", "", "", "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    }
	    
	                   
	    			else if(opCode.equals("0110"))
	    			{
	    				instruction = "SW";//Store word
	    				baseReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(baseReg, 2);
		    			String baseReg_Value = Integer.toString(decimalValue);		                    
		                String baseReg_Name = "R" + baseReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
		                 
			            offset = currentInst.substring(16,32); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(offset, 2);
			    		String offset_Value = Integer.toString(decimalValue);
		                String offsetShow = offset_Value; 
		                    
		                disassembledInst = "SW " + rtReg_Name + ", " + offsetShow + "(" + baseReg_Name + ")";
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "SW","",null, baseReg_Value,"",rtReg_Value,"", offset_Value, "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    }
	    			
	    			else if(opCode.equals("0111"))
	    			{
	    				instruction = "LW"; //Load word
	    				baseReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(baseReg, 2);
		    			String baseReg_Value = Integer.toString(decimalValue);		                    
		                String baseReg_Name = "R" + baseReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
		                 
			            offset = currentInst.substring(16,32); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(offset, 2);
			    		String offset_Value = Integer.toString(decimalValue);
		                String offsetShow = offset_Value; 
		                    
		                disassembledInst = "LW " + rtReg_Name + ", " + offsetShow + "(" + baseReg_Name + ")";
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "LW","",null, baseReg_Value,"",rtReg_Value,"", offset_Value, "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    
	    			
	    			}
	    			else if(opCode.equals("1000"))
	    			{
	    				instruction = "SLL";//Shift word left logical
	    				rdReg = currentInst.substring(16,21); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rdReg, 2);
		    			String rdReg_Value = Integer.toString(decimalValue);		                    
		                String rdReg_Name = "R" + rdReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
		                 
			            sa = currentInst.substring(21,26); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(sa, 2);
			    		String sa_Value = Integer.toString(decimalValue);
		                String saShow = "#" + sa_Value; 
		                
		                disassembledInst =  "SLL " + rdReg_Name + ", " + rtReg_Name + ", " + saShow;
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		            	
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "SLL", "",null,"", "", rtReg_Value, rdReg_Value, "",sa_Value);
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	    				
	    			}
	    			else if(opCode.equals("1001"))
	    			{
	    				instruction = "SRL"; //SHift word right logical
	    				rdReg = currentInst.substring(16,21); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rdReg, 2);
		    			String rdReg_Value = Integer.toString(decimalValue);		                    
		                String rdReg_Name = "R" + rdReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
		                 
			            sa = currentInst.substring(21,26); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(sa, 2);
			    		String sa_Value = Integer.toString(decimalValue);
		                String saShow = "#" + sa_Value; 
		                
		                disassembledInst =  "SRL " + rdReg_Name + ", " + rtReg_Name + ", " + saShow;
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "SRL", "",null,"", "", rtReg_Value, rdReg_Value, "",sa_Value);
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
		            	
	    			}
	    			else if(opCode.equals("1010"))
	    			{
	    				instruction = "SRA"; // Shift word right logical
	    				rdReg = currentInst.substring(16,21); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rdReg, 2);
		    			String rdReg_Value = Integer.toString(decimalValue);		                    
		                String rdReg_Name = "R" + rdReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
		                 
			            sa = currentInst.substring(21,26); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(sa, 2);
			    		String sa_Value = Integer.toString(decimalValue);
		                String saShow = "#" + sa_Value; 
		                
		                disassembledInst =  "SRA " + rdReg_Name + ", " + rtReg_Name + ", " + saShow;
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		                
		                MIPSsim tempLoc = new MIPSsim(InstAddress, "SRA", "",null,"", "",  rtReg_Value, rdReg_Value, "",sa_Value);
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
		            	
	    			}
	    			else if(opCode.equals("1011"))
	    			{
	    				instruction = "NOP"; //No operation
	    				disassembledInst = "NOP";
	                    writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
	                    
	                    MIPSsim tempLoc = new MIPSsim(InstAddress, "NOP", "",null,"", "", "", "", "", "");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                    
	    			}
	    		 }
	    	else if(category.equals("11"))
	    	     {
		    			opCode = currentInst.substring(2,6);
			    		if(opCode.equals("0000"))
			    		{
			    		    instruction = "ADD";
			    		}
			    		else if(opCode.equals("0001"))
			    		{
			    			instruction = "SUB";
			    		}
			    		else if(opCode.equals("0010"))
			    		{
			    			instruction = "MUL";
			    		}
			    		else if(opCode.equals("0011"))
			    		{
			    			instruction = "AND"; 
			    		}
			    		else if(opCode.equals("0100"))
			    		{
			    			instruction = "OR"; // OR immediate
			    		}
			    		else if(opCode.equals("0101"))
			    		{
			    			instruction = "XOR"; // XOR immediate
			    		}
			    		else if(opCode.equals("0110"))
			    		{
			    			instruction = "NOR"; // NOR immediate
			    		}
			    		else if(opCode.equals("0111"))
			    		{
			    			instruction = "SLT"; //Set on Less than
			    		}
			    		else if(opCode.equals("1000"))
			    		{
			    			instruction = "ADDI"; // Add immediate
			    		}
			    		else if(opCode.equals("1001"))
			    		{
			    			instruction = "ANDI"; // AND immediate
			    		}
			    		else if(opCode.equals("1010"))
			    		{
			    			instruction = "ORI"; // OR immediate
			    		}
			    		else if(opCode.equals("1011"))
			    		{
			    			instruction = "XORI"; //XOR immediate
			    		} 
			    		rsReg = currentInst.substring(6,11); 
	    				int decimalValue = 0;
	    				decimalValue = Integer.parseInt(rsReg, 2);
		    			String rsReg_Value = Integer.toString(decimalValue);		                    
		                String rsReg_Name = "R" + rsReg_Value;
		                    
		                rtReg = currentInst.substring(11,16); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rtReg, 2);
			    		String rtReg_Value = Integer.toString(decimalValue);		                    
			            String rtReg_Name = "R" + rtReg_Value;
			            
			            if(opCode.equals("1011") || opCode.equals("1010") || opCode.equals("1001") || opCode.equals("1000"))
			            {
		            	immValue = currentInst.substring(16,32); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(immValue, 2);
			    		String immValue_Value = Integer.toString(decimalValue);
		                String immValueShow = "#" + immValue_Value; 
		            	
		                disassembledInst = instruction + " " + rtReg_Name + ", " + rsReg_Name + ", " + immValueShow;
		                writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
		            	
		                MIPSsim tempLoc = new MIPSsim(InstAddress, instruction, "",null,"", rsReg_Value, rtReg_Value,"", "", immValue_Value);
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
		                
			            }
			            else {
			            rdReg = currentInst.substring(16,21); 
		    			decimalValue = 0;
		    			decimalValue = Integer.parseInt(rdReg, 2);
			    		String rdReg_Value = Integer.toString(decimalValue);		                    
			            String rdReg_Name = "R" + rdReg_Value;
			            
			            disassembledInst = instruction + " " + rdReg_Name + ", " + rsReg_Name + ", " + rtReg_Name;
			            writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + disassembledInst);
			            
			            MIPSsim tempLoc = new MIPSsim(InstAddress, instruction, "",null, "", rsReg_Value, rtReg_Value, rdReg_Value,"","");
	                    if(thisLoc != null) {
	                        while(thisLoc.newLoc != null) {
	                            thisLoc = thisLoc.newLoc;
	                        }
	                    }	                  
	                    thisLoc.newLoc = tempLoc;
	                    thisLoc = thisLoc.newLoc;
	                     
			            }
		    	     }
			            lastInstruction = instruction;
		    } else {
	    				int intValue;
	    				if(currentInst.substring(0,1).equals("1")) {
		    				String binaryIp = "";
		    				String result = " ";
				    		binaryIp = currentInst.substring(1,32);
				    		result = binaryIp.replace("0", "w"); 
				    		result = result.replace("1", "0"); 
				    		result = result.replace("w", "1");
				    		int decimalValue = Integer.parseInt(result, 2);
				    		decimalValue = decimalValue+1;
				    		intValue = (decimalValue)*(-1);
		    			} else { 
				    		String binaryIp = currentInst.substring(1,32);              
				    		int decimalValue = Integer.parseInt(binaryIp, 2);             
			                intValue = decimalValue; 
		    			}
	    				intValues.add(intValue);
	    				writer.println(currentInst + "\t" + Integer.toString(InstAddress) + "\t" + Integer.toString(intValue));		    						    		            
	    			}
	    	InstAddress = InstAddress + 4;	
	    	disassembledInsts.add(disassembledInst);	    		
	    }
	    writer.close();
	   
	    
		    String last_instruction = "";
		    int insAds = 256;
		    ArrayList<Integer> regValues = new ArrayList<Integer>();
		    for(int i=0; i<=31; i++) {
		        regValues.add(0);
		    }
		   	   
		    for (int i = 0; i < disassembledInsts.size(); i++) {
		    	if(last_instruction != "BREAK") {
		    		last_instruction = disassembledInsts.get(i);	    		
			        insAds = insAds + 4;
		    	}	    	
		    }
	    thisLoc = point.newLoc;	
	    if(thisLoc.newLoc != null) {	    
	        int cycleNum = 1;
	        while(thisLoc != null) {	            
	            String thisInstruction = thisLoc.instruction;		            
	            int thisInsAdrs = thisLoc.insAddrs;
	            simulate.println("--------------------");
	            simulate.append("Cycle:" + Integer.toString(cycleNum) + "\t" + (Integer.toString(thisInsAdrs)) + "\t");
	            if(thisInstruction.equals("J")) {	
	            	MIPSsim iniLoc = point;
	                String insLoc = thisLoc.address;
	                int iLoc = Integer.parseInt(insLoc);	            
	                while(iniLoc.newLoc.insAddrs != iLoc) { // Check if the address location is equal to the address specified in the J instruction
	                    iniLoc = iniLoc.newLoc; //if not, address specified is set
	                }
	                thisLoc = iniLoc; 
	                simulate.print("J #" + insLoc + "\n");
	            } else if(thisInstruction.equals("JR")) {
	            	MIPSsim iniLoc = point;	               
	                String rsRegLoc = thisLoc.rsRgstr;	                
					int rsLoc = regValues.get(Integer.parseInt(rsRegLoc)); 
					while(iniLoc.newLoc.insAddrs != rsLoc) { //Check if the address location is equal to the value in RS register in the JR instruction
	                    iniLoc = iniLoc.newLoc;
	                }
	                thisLoc = iniLoc;		       	                               
	                simulate.print("JR R" + rsRegLoc + "\n");	                
	            } else if(thisInstruction.equals("BEQ")) {
	                
	                String offsetValue = thisLoc.offsetValue;
	                int oLoc = Integer.parseInt(offsetValue);	              
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = regValues.get(Integer.parseInt(rtRegLoc)); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = regValues.get(Integer.parseInt(rsRegLoc)); 
	                
	                int newLocinsAddrs = thisLoc.insAddrs + oLoc; 
	                
	                if(rtLoc == rsLoc) { //check if the contents of GPR rs and GPR rt are equal,if so branch to the effective target address
	                    MIPSsim iniLoc = point;
	                    while(iniLoc.newLoc.insAddrs != newLocinsAddrs) { 
	                        iniLoc = iniLoc.newLoc;
	                    }
	                    thisLoc = iniLoc.newLoc; 
	                }
	                
	                simulate.print("BEQ R" + rsRegLoc + ", R" + rtRegLoc + ", #" + offsetValue + "\n");	                
	            } 
	            else if(thisInstruction.equals( "BLTZ")) {
	                
	                String offsetValue = thisLoc.offsetValue;
	                int oLoc = Integer.parseInt(offsetValue); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = regValues.get(Integer.parseInt(rsRegLoc)); 
	                
	                int newLocinsAddrs = thisLoc.insAddrs + oLoc; 
	                
	                if(rsLoc < 0) { // Check if the contents of GPR rs is less than zero,if so branch to the effective target address
	                    MIPSsim iniLoc = point;
	                    while(iniLoc.newLoc.insAddrs != newLocinsAddrs) { 
	                        iniLoc = iniLoc.newLoc;
	                    }
	                    thisLoc = iniLoc.newLoc; 
	                }
	                
	                simulate.print("BLTZ R" + rsRegLoc + ", #" + offsetValue + "\n");
	                
	            }
	            else if(thisInstruction.equals( "BGTZ")) {
	                
	                String offsetValue = thisLoc.offsetValue;
	                int oLoc = Integer.parseInt(offsetValue); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = regValues.get(Integer.parseInt(rsRegLoc)); 
	                
	                int newLocinsAddrs = thisLoc.insAddrs + oLoc; 
	                
	                if(rsLoc > 0) { // Check if the contents of GPR rs is greater than zero,if so branch to the effective target address
	                    MIPSsim iniLoc = point;
	                    while(iniLoc.newLoc.insAddrs != newLocinsAddrs) { 
	                        iniLoc = iniLoc.newLoc;
	                    }
	                    thisLoc = iniLoc.newLoc; 
	                }                
	                simulate.print("BGTZ R" + rsRegLoc + ", #" + offsetValue + "\n");
	                
	            } else if(thisInstruction.equals( "BREAK")) {
	                
	                simulate.print("BREAK\n");
	                
	            } else if(thisInstruction.equals( "SW")) {
	                
	                String baAddrs = thisLoc.baAddrs;
	                int baLoc = Integer.parseInt(baAddrs); 
	                String offsetValue = thisLoc.offsetValue;
	                int oLoc = Integer.parseInt(offsetValue);
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                int temp = ((oLoc - insAds + regValues.get(baLoc))/4);
	                int temp1 = regValues.get(rtLoc);
	                intValues.set(temp, temp1); // stores the value of rt into memory location of base+offset
	                
	                simulate.print("SW R" + rtRegLoc + ", " + offsetValue + "(R" + baAddrs + ")\n");
	                
	            } else if(thisInstruction.equals("LW")) {
	                
	                String baAddrs = thisLoc.baAddrs;
	                
	                int baLoc = Integer.valueOf(baAddrs); 
	                String offsetValue = thisLoc.offsetValue;
	                int oLoc = Integer.valueOf(offsetValue); 
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                
	                	               
	                int temp = intValues.get((oLoc -insAds + regValues.get(baLoc))/4);
	                regValues.set(rtLoc, temp); // Loads the value of address(base+offset) to rt
	                
	                simulate.print("LW R" + rtRegLoc + ", " + offsetValue + "(R" + baAddrs + ")\n");
	                
	            } else if(thisInstruction.equals("SLL")) {
	                
	                String saValue = thisLoc.immedte;	                
	                int saLoc = Integer.valueOf(saValue); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.valueOf(rdRegLoc); 
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                int temp1=regValues.get(rtLoc);
	                int temp = (temp1 << saLoc);	  //word in rt is shifted left logical by sa amount             
	                regValues.set(rdLoc, temp);
	                
	                simulate.print("SLL R" + rdRegLoc + ", R" + rtRegLoc + ", #" + saLoc + "\n");
	                
	            } else if(thisInstruction.equals("SRL")) {
	                
	                String saValue = thisLoc.immedte;	                
	                int saLoc = Integer.valueOf(saValue); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.valueOf(rdRegLoc); 
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                int temp1=regValues.get(rtLoc);
	                int temp = (temp1 >>> saLoc);	  //word in rt is shifted right logical by sa amount              
	                regValues.set(rdLoc, temp);
	                
	                simulate.print("SRL R" + rdRegLoc + ", R" + rtRegLoc + ", #" + saLoc + "\n");
	                
	            } else if(thisInstruction.equals("SRA")) {
	                
	                String saValue = thisLoc.immedte;	                
	                int saLoc = Integer.valueOf(saValue); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.valueOf(rdRegLoc); 
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                int temp1=regValues.get(rtLoc);
	                int temp = (temp1 >> saLoc);	      //word in rt is shifted right arithmetic(sign bit) by sa amount         
	                regValues.set(rdLoc, temp);
	                
	                simulate.print("SRA R" + rdRegLoc + ", R" + rtRegLoc + ", #" + saLoc + "\n");
	                
	            } else if(thisInstruction.equals("NOP")) {
	                
	                simulate.print("NOP\n");
	                
	            } else if(thisInstruction.equals("ADD")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                	                
	                int temp = regValues.get(rsLoc) + regValues.get(rtLoc); 
	                regValues.set(rdLoc, temp); // values in rt and rs are added and result is stored in rd
	                
	                simulate.write("ADD R" + rdRegLoc + ", R" + rsRegLoc + ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals("SUB")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                	               
	                int temp = regValues.get(rsLoc) - regValues.get(rtLoc);
	                regValues.set(rdLoc, temp); //value of rt is subtracted from rs and result is stored in rd
	                
	                simulate.print("SUB R" + rdRegLoc + ", R" + rsRegLoc + ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals( "MUL")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                
	                int temp = regValues.get(rtLoc)*regValues.get(rsLoc);
	                regValues.set(rdLoc, temp); //values of rt and rs are multiplied and result is stored in rd
	                
	                simulate.printf("MUL R" + rdRegLoc + ", R" + rsRegLoc + ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals( "AND")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                 
	                int temp = regValues.get(rsLoc)&regValues.get(rtLoc);
	                regValues.set(rdLoc, temp); // Bitwise logical AND is performed between values of rs and rt , result is stored in rd
	                
	                simulate.print("AND R" + rdRegLoc + ", R" + rsRegLoc + ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals( "OR")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                	                
	                int temp = regValues.get(rsLoc)|regValues.get(rtLoc);
	                regValues.set(rdLoc, temp); // Bitwise logical OR is performed between values of rs and rt , result is stored in rd
	                
	                simulate.print("OR R" + rdRegLoc + ", R" + rsRegLoc + ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals( "XOR")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                	                
	                int temp = regValues.get(rsLoc)^regValues.get(rtLoc); 
	                regValues.set(rdLoc, temp);   // Bitwise logical XOR is performed between values of rs and rt , result is stored in rd
	                
	                simulate.print("XOR R" + rdRegLoc + ", R" + rsRegLoc + ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals( "NOR")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                	               
	                int temp = ~(regValues.get(rsLoc)|regValues.get(rtLoc)); 
	                regValues.set(rdLoc, temp); //  Bitwise logical NOR is performed between values of rs and rt , result is stored in rd
	                
	                simulate.print("NOR R" + rdRegLoc + ", R" + rsRegLoc+ ", R" + rtRegLoc + "\n");
	                
	            }else if(thisInstruction.equals( "SLT")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String rdRegLoc = thisLoc.rdRgstr;
	                int rdLoc = Integer.parseInt(rdRegLoc); 
	                
	                if(regValues.get(rsLoc) < regValues.get(rtLoc)) // if the value of rs < rt, then store 1 in rd else store 0 in rd
	                {
	                regValues.set(rdLoc, 1);
	            	} else {
	                	regValues.set(rdLoc, 0);
	                }
	                
	                simulate.print("SLT R" + rdRegLoc + ", R" + rsRegLoc+ ", R" + rtRegLoc + "\n");
	                
	            } else if(thisInstruction.equals( "ADDI")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String immedte = thisLoc.immedte;
	                int oLoc = Integer.parseInt(immedte); 
	                
	                int temp = regValues.get(rsLoc) + oLoc;	      //(rsvalue +immediate) value is stored in rd       
	                regValues.set(rtLoc, temp);
	                
	                simulate.print("ADDI R" + rtRegLoc + ", R" + rsRegLoc + ", #" + immedte + "\n");
	                
	            } else if(thisInstruction.equals( "ANDI")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String immedte = thisLoc.immedte;
	                int oLoc = Integer.parseInt(immedte); 
	               
	                int temp = regValues.get(rsLoc)&oLoc; //(rs value AND immediate) value is stored in rd
	                regValues.set(rtLoc, temp);
	                
	                simulate.print("ANDI R" + rtRegLoc + ", R" + rsRegLoc + ", #" + immedte + "\n");
	                
	            } else if(thisInstruction.equals( "ORI")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String immedte = thisLoc.immedte;
	                int oLoc = Integer.parseInt(immedte); 
	                
	                int temp = regValues.get(rsLoc)|oLoc; //(rs value OR immediate) value is stored in rd
	                regValues.set(rtLoc, temp);
	                
	                simulate.print("ORI R" + rtRegLoc + ", R" + rsRegLoc + ", #" + immedte + "\n");
	                
	            } else if(thisInstruction.equals("XORI")) {
	                
	                String rtRegLoc = thisLoc.rtRgstr;
	                int rtLoc = Integer.parseInt(rtRegLoc); 
	                String rsRegLoc = thisLoc.rsRgstr;
	                int rsLoc = Integer.parseInt(rsRegLoc); 
	                String immedte = thisLoc.immedte;
	                int oLoc = Integer.parseInt(immedte); 
	                
	                int temp = regValues.get(rsLoc)^oLoc; //(rs value XOR immediate) value is stored in rd
	                regValues.set(rtLoc, temp);
	                
	                simulate.print("XORI R" + rtRegLoc + ", R" + rsRegLoc + ", #" + immedte + "\n");
	                
	            }	            	            	            
	            simulate.println("");
	    	    simulate.println("Registers");
	    	    
	            simulate.println("R00:\t" + Integer.toString(regValues.get(0)) + "\t" + Integer.toString(regValues.get(1)) + "\t" +	
	            Integer.toString(regValues.get(2)) + "\t" + Integer.toString(regValues.get(3)) + "\t" +
	            Integer.toString(regValues.get(4)) + "\t" + Integer.toString(regValues.get(5)) + "\t" +
	            Integer.toString(regValues.get(6)) + "\t" + Integer.toString(regValues.get(7)));
	            simulate.println("R08:\t" + Integer.toString(regValues.get(8)) + "\t" + Integer.toString(regValues.get(9)) + "\t" +
	            Integer.toString(regValues.get(10)) + "\t" + Integer.toString(regValues.get(11)) + "\t" +
	            Integer.toString(regValues.get(12)) + "\t" + Integer.toString(regValues.get(13)) + "\t" +
	            Integer.toString(regValues.get(14)) + "\t" + Integer.toString(regValues.get(15)));
	            simulate.println("R16:\t" + Integer.toString(regValues.get(16)) + "\t" + Integer.toString(regValues.get(17)) + "\t" +
	            Integer.toString(regValues.get(18)) + "\t" + Integer.toString(regValues.get(19)) + "\t" +
	            Integer.toString(regValues.get(20)) + "\t" + Integer.toString(regValues.get(21)) + "\t" +
	            Integer.toString(regValues.get(22)) + "\t" + Integer.toString(regValues.get(23)));
	            simulate.println("R24:\t" + Integer.toString(regValues.get(24)) + "\t" + Integer.toString(regValues.get(25)) + "\t"+
	            Integer.toString(regValues.get(26)) + "\t" + Integer.toString(regValues.get(27)) + "\t"+
	            Integer.toString(regValues.get(28)) + "\t" + Integer.toString(regValues.get(29)) + "\t"+
	            Integer.toString(regValues.get(30)) + "\t" + Integer.toString(regValues.get(31)));
	            
	            simulate.println("");
	            simulate.println("Data");
	            simulate.println(Integer.toString(insAds) + ":\t" + Integer.toString(intValues.get(0)) + "\t" + Integer.toString(intValues.get(1)) + "\t"+
	            Integer.toString(intValues.get(2)) + "\t" + Integer.toString(intValues.get(3)) + "\t"+
	            Integer.toString(intValues.get(4)) + "\t" + Integer.toString(intValues.get(5)) + "\t"+
	            Integer.toString(intValues.get(6)) + "\t" + Integer.toString(intValues.get(7)));
	            if(intValues.size()>8) {
	            simulate.println(Integer.toString(insAds + 32) + ":\t" + Integer.toString(intValues.get(8)) + "\t" + Integer.toString(intValues.get(9)) + "\t"+
	            Integer.toString(intValues.get(10)) + "\t" + Integer.toString(intValues.get(11)) + "\t"+
	            Integer.toString(intValues.get(12)) + "\t" + Integer.toString(intValues.get(13)) + "\t"+
	            Integer.toString(intValues.get(14)) + "\t" + Integer.toString(intValues.get(15)));
	         	simulate.println(Integer.toString(insAds + 64) + ":\t" + Integer.toString(intValues.get(16)) + "\t" + Integer.toString(intValues.get(17)) + "\t"+
	            Integer.toString(intValues.get(18)) + "\t" + Integer.toString(intValues.get(19)) + "\t"+
	            Integer.toString(intValues.get(20)) + "\t" + Integer.toString(intValues.get(21)) + "\t"+
	            Integer.toString(intValues.get(22)) + "\t" + Integer.toString(intValues.get(23))); 
	         	simulate.println(""); 
	            }
	         	thisLoc = thisLoc.newLoc;
	            ++cycleNum;	            	            
	            }     
	        }
	  	        	    
	    simulate.close();
	    
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
}	