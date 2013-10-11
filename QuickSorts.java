
//package line only needed by netbeans
//package quicksorts;

import java.util.*;

/*
 * @author Nick Carlson
 */

public class QuickSorts {

    //main driver of the program
    public static void main(String[] args) {
        //takes input, calls sorts, and prints output
        getInputAndRunSorts();
    }
    
    /* accepts user input, determines input acceptability, and either terminates or runs sorts
        based on input acceptability */
    public static void getInputAndRunSorts(){
        boolean flag = true;
        boolean test;
        int size = 0;
        int twoCount;
        int tempTwoCount = 0;
        int tempThreeCount = 0;
        int threeCount;
        Scanner reader = new Scanner(System.in);
        
        //get input from user
        //if input unacceptable, tell the user why not
        System.out.println("Please enter an integer greater than 0 for the list size:");
        try{
        size = reader.nextInt();
        }catch(InputMismatchException e){
            flag = false;
            System.out.println("");
            System.out.println("Unacceptable input - you must enter an integer greater than 0.");
        }
        
        //if input is acceptable, run sorts
        //if input unacceptable, tell the user why not
        if(flag == true){
            if(size > 0){
                
                //create list of user-specified size and fill with random integers
                int list1[] = new int[size];
                int list2[] = new int[size];
                fillLists(list1,list2,size);
                
                //run two-way sort on a timer and compute milliseconds and seconds
                long startTime1 = System.nanoTime();
                twoCount = twoWayQuickSort(list1,0,list1.length-1,tempTwoCount);
                long endTime1 = System.nanoTime();
                long duration1 = endTime1 - startTime1;
                double elapsedMilliTime1 = (double) duration1 / 1000000.0;
                double elapsedSecTime1 = (double) duration1 / 1000000000.0;
                
                //print header
                System.out.println("");
                System.out.println("Quicksort with 2-Way Partitioning completed in " + elapsedMilliTime1 + " milliseconds, which is also " + elapsedSecTime1 + " seconds.");
                
                //run brute-force sort test, inform user of sort success or failure
                test = testSort(list1);
                if(test == true){
                    System.out.println("The sorted order of the list has been verified.");
                    System.out.println("All " + size + " integers have been placed in sorted order.");
                    System.out.println(twoCount + " swaps were performed.");
                }else{
                    System.out.println("The list is not correctly sorted!");
                    System.exit(0);
                }
                
                //run three-way sort on a timer and compute milliseconds and seconds
                long startTime2 = System.nanoTime();
                threeCount = threeWayQuickSort(list2,0,list2.length-1,tempThreeCount);
                long endTime2 = System.nanoTime();
                long duration2 = endTime2 - startTime2;
                double elapsedMilliTime2 = (double) duration2 / 1000000.0;
                double elapsedSecTime2 = (double) duration2 / 1000000000.0;
                
                //print header
                System.out.println("");
                System.out.println("Quicksort with 3-Way Partitioning completed in " + elapsedMilliTime2 + " milliseconds, which is also " + elapsedSecTime2 + " seconds.");
                
                //run brute-force sort test, inform user of sort success or failure
                test = testSort(list2);
                if(test == true){
                    System.out.println("The sorted order of the list has been verified.");
                    System.out.println("All " + size + " integers have been placed in sorted order.");
                    System.out.println(threeCount + " swaps were performed.");
                }else{
                    System.out.println("The list is not correctly sorted!");
                    System.exit(0);
                }
            }else{
                //inform user of special cases for unacceptable input
                if(size == 0){
                    System.out.println("");
                    System.out.println("Unacceptable input - there is nothing to sort!");
                }else{
                    System.out.println("");
                    System.out.println("Unacceptable input - negative list sizes are not accepted!");
                }
            }
        }
    }
    
    //fill integer array of user-specified size with random integers
    public static void fillLists(int list1[], int list2[], int size){
        double temp;
        
        //fill 1st list with random integers
        for(int i = 0; i < list1.length; i++){
            temp = Math.random() * size;
            list1[i] = (int) temp;
        }
        
        //copy 1st list into 2nd list
        System.arraycopy(list1,0,list2,0,size);
        
        //ensure lists are equal
        if(Arrays.equals(list1,list2)){
            System.out.println("");
            System.out.println("The unsorted arrays are identical.");
        }else{
            System.out.println("The unsorted arrays are different.");
            System.exit(0);
        }
    }
    
    //brute-force test to ensure list is in sorted order (ascending)
    public static boolean testSort(int list[]){
        boolean test = true;
        for(int i = 0; i < list.length-1; i++){
            if((list[i] <= list[i+1]) == false){
                test = false;
            }
        }
        return test;
    }
    
    //performs quick-sort with two-way partitioning
    public static int twoWayQuickSort(int list[], int beg, int end, int count){
        int pivot, leftP, rightP;
        
        //if list has items to sort
        if(beg < end){
            //pick pivot
            pivot = list[beg];
            leftP = beg+1;
            rightP = end;
            
            //while pivot swap not found
            while(leftP <= rightP){
                //while value at left pointer < pivot
                while((leftP <= end) && (list[leftP] <= pivot)){
                    ++leftP;
                }
                //while value at right pointer > pivot
                while((rightP >= beg) && (list[rightP] > pivot)){
                    --rightP;
                }
                //left and right pointers may need to be swapped
                if(leftP < rightP){
                    swap(list,leftP,rightP);
                    count++;
                }
            }
            
            //swap pivot and rightP
            /*the pivot, formerly at the beginning of the list, is now considered
                the center for the purpose of creating two sublists */
            swap(list,beg,rightP);
            count++;
            
            //sort left sublist
            twoWayQuickSort(list,beg,rightP-1,count);
            //sort right sublist
            twoWayQuickSort(list,rightP+1,end,count);
        }
        //return number of swaps
        return count;
    }
    
    //performs three phase swap for elements of an integer array
    public static void swap(int list[], int i, int j){
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }
    
    //performs quick-sort with three-way partitioning
    public static int threeWayQuickSort(int list[], int beg, int end, int count){
        int pivot, leftP, rightP, i;
        
        //if list has items to sort
        if(beg < end){
            //pick pivot
            leftP = beg;
            i = beg+1;
            rightP = end;
            pivot = list[beg];
            
            //while swaps are possible
            while(i <= rightP){
                //if value at list[i] is smaller than leftP, swap with leftP
                if(list[i] < pivot){
                    swap(list,leftP++,i++);
                    count++;
                //if value at list[i] is larger than leftP, swap with rightP
                }else if(list[i] > pivot){
                    swap(list,i,rightP--);
                    count++;
                //if list[i] is equal to leftP, increment i
                }else{
                    i++;
                }
            }
            
            //sort left sublist
            threeWayQuickSort(list,beg,leftP-1,count);
            //sort right sublist
            threeWayQuickSort(list,rightP+1,end,count);
        }
        //return number of swaps
        return count;
    }
    
}
