//
//  LINE READER. Read lines from a file.
//
//
//    Created by Shreeganesh Bhat
//    21 Mar 16
//

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;


//  LINE READER. Iterator. Read lines from a file as STRINGs.

class LineReader
{
    	private String         line;    //  Line waiting to be returned by NEXT.
   	private BufferedReader reader;  //  Where to read LINEs from.
    
	//  Constructor. Make a new instance of LINE READER. It reads LINEs from a file
	//  whose pathname is PATH.
    
    	public LineReader(String path)
    	{
        	try
        	{
            		reader = new BufferedReader(new FileReader(path));
            		line = reader.readLine();
        	}
        	catch (IOException ignore)
        	{
            		throw new IllegalArgumentException("Can't open '" + path + "'.");
        	}
    	}
    
	//  HAS NEXT. Test if a LINE is waiting to be returned by NEXT.
    
   	public boolean hasNext()
    	{
        	return line != null;
    	}
    
	//  NEXT. Return the current LINE from PATH and advance to the next LINE, if it
	//  exists.
    
    	public String next()
    	{
        	try
        	{
            		String temp = line;
            		line = reader.readLine();
            		return temp;
        	}
        	catch (IOException ignore)
        	{
            		throw new IllegalStateException("Can't read any more lines.");
        	}
    	}
    
	//  MAIN. Open the file whose pathname is the 0th argument on the command line.
	//  Copy that file to SYSTEM.OUT twice. This is included here only for testing,
	//  so LINE READER can run as a stand-alone program.
    
	public static void main(String [] args)
    	{
        	int BIT_ARR_SIZ = 2500;   // Size of a BitArray

        	BloomFilter filter = new BloomFilter(BIT_ARR_SIZ);
        
        	if (args.length >= 1)
        	{
			int words_added = 0 ; 
     			System.out.println("Started adding words to BitArray \n");
            		LineReader reader = new LineReader(args[0]);
            		while (reader.hasNext())
            		{
                		String word = reader.next();
                		filter.add(word);
  				words_added += 1 ;
  				System.out.println("Number of Words added = \n" + words_added);
			}
		
			System.out.println("All words added \n");
			System.out.println("Bit array size = "+ BIT_ARR_SIZ + " bits");
			System.out.println("Number of hash functions used = 4\n");
     
			LineReader reader_search = new LineReader("basic.txt");
            		int misspelled_word_count = 0;
            		while (reader_search.hasNext()) 
            		{
                		String word = reader_search.next();
                		if(filter.isIn(word))
                		{
                 			;   
                		}	
                		else
                		{
                    			misspelled_word_count += 1;
                    			System.out.println(word);
                		}
            		}
     			System.out.println("Started testing if each word is in the instance of BloomFilter.....\n");
            		System.out.println("Accuraccy(probability that the method isIn will report a word is in the set, when it is not) of the instance of BloomFilter = \n"+ filter.accuracy());
	        	System.out.println("Number of words for which function 'isIn' returned false  = " + misspelled_word_count);
        	}
    	}
}

//
//  BitArray. Implements an array of bits.
//
//    Shreeganesh Bhat
//    8 April 16
//

class BitArray
{
    	public static int[] BitArray;
    
    	/* 
    	Constructor. If M < 0, then throw an IllegalArgumentException. 
    	Otherwise, make a new int array that can hold M bits, to be used by the 
    	other methods of this class. All the ints in the array must be 
    	initialized to 0 
    	*/
    
    	BitArray(int M) throws IllegalArgumentException
    	{
        	if(M<0)
        	{
            		throw new IllegalArgumentException("Invalid M");
        	}
        	else
        	{
            		float length = (float)(M/32.0);
            		this.BitArray = new int[(int)Math.ceil(length)];
        	}
    	}
    
    	// If n < 0 or n ? M, then throw an IndexOutOfRangeException. 
    	// Otherwise get the bit at index n, using the array that was 
    	// made by the constructor. If that bit is 0, then return false, 
   	// otherwise return true.
    	public boolean get(int nth_bit)
    	{
        	int arrayLength = this.BitArray.length; 
        	if((nth_bit<0) || ((arrayLength*32) <= nth_bit))
        	{
            		throw new IndexOutOfBoundsException("out of range");
        	}
        	else
        	{
            		int index = nth_bit%32;
            		int array_index = nth_bit/32;
            		int BitStatus = (this.BitArray[array_index] >> index) & 1;
            		if(BitStatus == 1)
                		return true;
            		else
                		return false;
        	}	
    	}
    
    	// If n < 0 or n ? M, then throw an IndexOutOfRangeException. 
    	// Otherwise set the bit at index n to 1, using the array that was 
    	// made by the constructor.
    	public void set(int nth_bit)
    	{
        	int arrayLength = this.BitArray.length; 
        	if((nth_bit<0) || ((arrayLength*32) <= nth_bit))
        	{
            		throw new IndexOutOfBoundsException("Index " + nth_bit 
                                                    + "out of range");
        	}
        	else
        	{
            		int index = nth_bit%32;
            		int array_index = nth_bit/32;
            		this.BitArray[array_index] |= (1 << index) ;
        	}
    	}
  
}

//  BloomFilter. uses an instance of BitArray and several hash functions to 
//  probabilistically represent a large set of strings.
//
//  Shreeganesh Bhat
//  8 April 16
//

class BloomFilter 
{
    
    	private BitArray B1;
   	private int M;  // maximum size of the bit array
    
    	public BloomFilter(int M) throws IllegalArgumentException 
	{
        	if(M<0) 
		{
            		throw new IllegalArgumentException("Invalid M");
        	} 
		else 
		{
            		B1 = new BitArray(M);
            		this.M = M;
        	}
    	}
    
    	// Hash function 1
    	private int h1(String w)
    	{
        	return Math.abs(w.hashCode()) % this.M;
    	}    
    
    	// Hash function 2
    	private int h2(String w)
    	{
        	char ch[];
        	ch = w.toCharArray();
        	int i, sum;
        	for (sum=0, i=0; i < w.length(); i++)
            		sum += ch[i];
        		
		return sum % this.M;
    	} 
    
    	// Hash function 3
    	private int h3(String w)
    	{
        	return Math.abs(w.hashCode()*3) % this.M;
    	}  

    	// Hash function 4
    	private int h4(String w)
    	{
        	return Math.abs(w.hashCode()+25) % this.M;
    	}

 
	/* Add the word w to the set, using the BitArray made by the constructor, 
	and the hash methods h1, h2 ..., hj.*/
    	public void add(String w)
    	{
        	B1.set(h1(w));
        	B1.set(h2(w));
        	B1.set(h3(w));
 		B1.set(h4(w));
    	}
    
    	/* Test if the word w is in the set, using the BitArray made by the constructor, and 
	the hash methods h1, h2 ..., hj. Return true if w may   
	be in the set. Return false if w is definitely not in the set.*/
    	public boolean isIn(String w)
    	{
        	boolean x = B1.get(h1(w));  
        	boolean y = B1.get(h2(w));
        	boolean z = B1.get(h3(w));  
 		boolean q = B1.get(h4(w));
        
        	if(q && x && y && z)
            		return true;
        	else
            		return false;
    	}
    
	/* Returns the probability that the method isIn will report a 
 	word is in the set, when it is not */
    	public double accuracy()
    	{
        	return Math.pow((1 - Math.exp((float)(-4.0*850/this.M))),4); // Number of hash functions = 4  
    	}
}
