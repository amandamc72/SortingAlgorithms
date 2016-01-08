/*
 * Amanda McCarty
 * COSC 311
 * Assignment #4
 * 12/8/2014
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Node
{
	private int iData; // data item (key)
	
	public Node(int key) // constructor
	{ iData = key; }
	
	public int getKey()
	{ return iData; }

} // end class Node

public class HeapSort
{
	Scanner in = new Scanner(System.in);
	private Node[] heapArray;
	private int maxSize; // size of array
	private int currentSize; // number of items in array
	
	public HeapSort(int mx) // constructor
	{
		maxSize = mx;
		currentSize = 0;
		heapArray = new Node[maxSize];
	}
	
	public Node remove() // delete item with max key
	{ // (assumes non-empty list)
		Node root = heapArray[0];
		heapArray[0] = heapArray[--currentSize];
		trickleDown(0);
		return root;
	} // end remove()
	
	public void trickleDown(int index)
	{
		int largerChild;
		Node top = heapArray[index]; // save root
		while(index < currentSize/2) // not on bottom row
		{
			int leftChild = 2*index+1;
			int rightChild = leftChild+1;
			// find larger child
			if(rightChild < currentSize && // right ch exists?
					heapArray[leftChild].getKey() <
					heapArray[rightChild].getKey())
				largerChild = rightChild;
			else
				largerChild = leftChild;
			// top >= largerChild?
			if(top.getKey() >= heapArray[largerChild].getKey())
				break;
			// shift child up
			heapArray[index] = heapArray[largerChild];
			index = largerChild; // go down
		} // end while
		heapArray[index] = top; // root to index
	} // end trickleDown()
	
	public void outputFile()
	{	
		System.out.println("Enter file name for output: ");
		String file = in.next();
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(new FileOutputStream(file));
			
			for(int j=0; j<maxSize; j++)
			{
				outStream.println(heapArray[j].getKey());
			}		
		} catch (IOException e) {
			System.out.println("Could not access file");
		} finally {
			outStream.close();
		}
		System.out.println("Saved!");
	}
	
	public void insertAt(int index, Node newNode)
	{ heapArray[index] = newNode; }
	
	public void incrementSize()
	{ currentSize++; }
	
} // end class Heap

