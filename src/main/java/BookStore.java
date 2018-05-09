import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class BookStore {
	private double cost;
	private List<Integer> books;

	public static void main(String[] args) {
	BookStore cart;

 	cart = new BookStore();
 	cart.books = new ArrayList<Integer>();

 	cart.books.add(1);
 	cart.books.add(1);
 	cart.books.add(1);
 	cart.books.add(2);
 	cart.books.add(2);
 	cart.books.add(3);
 	cart.books.add(3); 	
 	cart.books.add(3);
 	cart.books.add(3);
  	cart.books.add(4);
 	cart.books.add(4);
 	cart.books.add(5);
 	cart.books.add(5);
  	cart.books.add(5);  	
  	cart.cost = cart.calculateBasketCost(cart.books);

	}

	public double calculateBasketCost(List<Integer> books) {
		int bookFrequencies[] = new int[5];
		Set<Integer> uniqueSet = new HashSet<Integer>(books);
		int k = 0;

		for (int temp: uniqueSet){
		//i = Collections.frequency(books, temp);
		bookFrequencies[k] = Collections.frequency(books, temp);;
		k=k+1;

		}
		Arrays.sort(bookFrequencies);
		int set[] = new int[5]; //set is an int array that tells about the number of sets of 5,4,3,2 and 1 (respectively) that exist in the cart
		
		int numToSub; //number to subtract
		for(int j=0; j<5; j++){
			if(bookFrequencies[j]!=0){
			set[j] = set[j] + bookFrequencies[j];
			numToSub = bookFrequencies[j];
			for (int l=0; l<5; l++){ //the number is subtacted from every element in bookFrequencies[]
				bookFrequencies[l] = bookFrequencies[l] - numToSub; //subtracts the sets of books that have already been counted in set[]
			}
		} //end if

		}//end for
		
		return costFromArray(set);
	}

	public double costFromArray(int[] set){
		double cost;
		double discountPct;
		int[] discountArr;
		int initcost;
		int temp;

		cost = 0;
		discountArr = new int[] {25,20,10,5,0}; //discount percentages

		//convert sets of 3 and 5 to two sets of 4
		if(set[0]>set[2]) //set[0] = number of sets of 5, and set[2] = number of sets of 3
			temp = set[2];
		else
			temp = set[0];
		set[1] = set[1] + temp * 2;
		set[0] = set[0] - temp;
		set[2] = set[2] - temp;

		for (int i = 0; i < 5; i++) {
			initcost = set[i] * 8 * (5-i); //cost of 5-i (5,4,3,2,1) books since it's a set of 5,4,3,2,1 respectively
			discountPct = discountArr[i] * 0.01;
			cost = cost + initcost - discountPct * initcost; //final cost
		}
		return cost;
	}
}