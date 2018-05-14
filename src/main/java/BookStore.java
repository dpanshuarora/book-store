import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BookStore {
	private static final int BOOKCOST = 8;
	private static double cost;
	public static HashMap<Integer, Double> discountPctMap = new HashMap<>();
	static {
		discountPctMap.put(1,0.0);
		discountPctMap.put(2,0.05);
		discountPctMap.put(3,0.1);
		discountPctMap.put(4,0.2);
		discountPctMap.put(5,0.25);
	}

	public static void main(String[] args) {
		BookStore cart = new BookStore();
        List<Integer> books = Arrays.asList(1,2,3,4,5,1,2,3,4,5,1);

  		cost = cart.calculateBasketCost(books);
  		System.out.println(cost);
	}

	public double calculateBasketCost(List<Integer> books) {
		ArrayList<Integer> bookFrequencies = new ArrayList<Integer>(5);
		Set<Integer> uniqueSet = new HashSet<>(books);

		for (int bookId: uniqueSet){
		bookFrequencies.add(Collections.frequency(books, bookId));
		}

		Collections.sort(bookFrequencies);
		int set[] = new int[bookFrequencies.size()]; //set tells about the number of sets of 1,2,3,4,5 (respectively) that exist in the cart

		int numberToSubtract;
		int size = bookFrequencies.size();
		for(int i = 0; i < size; i++){
			if(bookFrequencies.get(i)!=0){
				set[size-i-1] = bookFrequencies.get(i);
				numberToSubtract = bookFrequencies.get(i);
				for (int j = 0; j < size; j++){ //the number is subtracted from every element in bookFrequencies[]
					bookFrequencies.set(j, bookFrequencies.get(j) - numberToSubtract); //subtracts the sets of books that have already been counted in set[]
			}
		}

		}

		double finalCost = costOfEachSet(set);
		for (int i = 0; i < set.length; i++) {
			if(i+2 < set.length){
				for( int j = i+2; j < set.length; j++){
					finalCost = optimiseDiscount(i,j, set, finalCost);
			}
		}

		}
		return finalCost;

	}

	public double optimiseDiscount(int initLower, int initUpper, int[] set, double cost) {
		
		int min;
		for (int lower = initLower, upper = initUpper; lower < upper; lower++, upper--){
			
			min = set[initLower];
			
			set[initUpper] -= min;
			set[initLower] -= min;
			set[upper-1] += min;
			set[lower+1] += min;

			cost = Math.min(costOfEachSet(set), cost);
		}
		return cost;

	}

	public double costOfEachSet(int[] set) {

		int initcost;
		int setNum = 1;
		double cost = 0;
		for (int setFrequency : set) {
			initcost = setFrequency * BOOKCOST * (setNum);
			cost += initcost - discountPctMap.get(setNum) * initcost;
			setNum++;
		}

		return cost;
	}
}