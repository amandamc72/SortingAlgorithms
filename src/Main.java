/*
 * Amanda McCarty
 * COSC 311
 * Assignment #4
 * 12/8/2014
 */
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main 
{	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
			
		int choice;
		do{
		System.out.println("Enter file name to sort: (dataAscend.txt, dataDescend.txt, dataRandom.txt) ");
		String file = in.next();
		int maxSize = 10000;
		MergeSort mergeArray = new MergeSort(maxSize); 
		QuickSort quickArray = new QuickSort(maxSize);
		HeapSort heapArray = new HeapSort(maxSize);
		Scanner inStream = null;
		
		try {

			inStream = new Scanner(new FileInputStream(file)); // read  data from file 

			while (inStream.hasNextLine()) // checks to see if the file as another  line
			{  
				for (int i = 0; i < maxSize; i++) // fill array
				{	
					int dataElements = inStream.nextInt(); 
					mergeArray.insert(dataElements);
					quickArray.insert(dataElements);
					
					Node newNode = new Node(dataElements);
					
					heapArray.insertAt(i, newNode);
					heapArray.incrementSize();
				}
			}
		} catch (FileNotFoundException e) { // catch any exceptions 
			System.out.println("Error accessing file");
			System.out.println("Ending program");
		} catch (NoSuchElementException e) {
		} finally {
			inStream.close(); // close stream  
		}
		
		
		System.out.println("Choose your sort!"
							+ "\n1. MergeSort"
							+ "\n2. QuickSort"
							+ "\n3. HeapSort"
							+ "\n0. Exit");

		choice = in.nextInt();
		switch (choice)
		{
			case 1: // merge 
			{
				long startTime = System.nanoTime();
				mergeArray.mergeSort();
				long endTime = System.nanoTime();
				long duration = (endTime - startTime);
				System.out.println("MergeSort took " + duration + " nanoseconds for " + file + " file");
				mergeArray.outputFile();
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("OutputTimes.txt", true)))) {
				    out.println(file + "\tMergeSort\t" + duration);
				}catch (IOException e) {
				    System.out.println("Oops..Something went wrong"); 
				}
				break;
			}
			case 2: // quick
			{
				long startTime = System.nanoTime();
				quickArray.quickSort(); // quicksort them
				long endTime = System.nanoTime();
				long duration = (endTime - startTime);
				System.out.println("QuickSort took " + duration + " nanoseconds for " + file + " file");
				quickArray.outputFile();
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("OutputTimes.txt", true)))) {
				    out.println(file + "\tQuickSort\t" + duration);
				}catch (IOException e) {
				    System.out.println("Oops..Something went wrong"); 
				}
	
				break;
			}
			case 3: // heap
			{
				long startTime = System.nanoTime();
				for(int j = maxSize/2-1; j >= 0; j--) // make random array into heap
					heapArray.trickleDown(j);
				for(int i = maxSize-1; i >= 0; i--) // remove from heap and
				{ // store at array end
					Node biggestNode = heapArray.remove();
					heapArray.insertAt(i, biggestNode);
				}	
				long endTime = System.nanoTime();
				long duration = (endTime - startTime);
				System.out.println("HeapSort took  " + duration + " nanoseconds for " + file + " file");
				heapArray.outputFile();
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("OutputTimes.txt", true)))) {
				    out.println(file + "\tHeapSort\t" + duration);
				}catch (IOException e) {
				    System.out.println("Oops..Something went wrong"); 
				}
	
				break;
			}
			default:
		}
		} while(choice != 0);
	}
} 

