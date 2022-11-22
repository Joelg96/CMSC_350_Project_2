/* Joel Goode - CMSC 350 - Project 2 11/8/22
 * Method named checkSorted
 * Implement Comparable
 * Implements the Comparator interface
 */

import java.util.Comparator;
import java.util.List;

/*utility class that contains two overloaded implementations of a method named checkSorted,
which determines whether a List object, supplied as a parameter, is in strictly
ascending order */
public class OrderedList {
    public static<T extends Comparable<? super T>> boolean checkSorted(List<T> list) {
        return checkSorted(list, (leftSide, rightSide) -> leftSide.compareTo(rightSide));
    }

    public static <T> boolean checkSorted(List<T> list, Comparator<? super T> comparator) {

        if (list != null && !list.isEmpty() && comparator != null){
            T previous = null;

            for (T current : list) {

                if (previous == null){
                    previous = current;
                }
                else{
                    if (comparator.compare(previous, current) >= 0){
                        return false;
                    }
                }
            }
            return true;
        }
        else{
            throw new RuntimeException("Error, List and the comparator cannot be null or empty!");
        }
    }
}