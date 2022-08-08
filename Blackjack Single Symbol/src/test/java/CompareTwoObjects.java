import org.jetbrains.annotations.NotNull;

import java.util.Deque;

/**
 * For now there are comparison for Deque Objects.
 */
public class CompareTwoObjects {
    /**
     * As Deque is complicated, two Deque are considered equal if and only if they are the
     * same Object. This method attempts to compare two Deque and will return True if they
     * have the same contents.
     *
     * @param firstDeque    The first Deque to be compared.
     * @param secondDeque   The second Deque to be compared.
     * @return  If the two Deque have the same content.
     * @param <E>   The type stored in the two Deque.
     */
    public static <E> boolean thatAreDeque(@NotNull Deque<E> firstDeque, Deque<E> secondDeque){
        while (!firstDeque.isEmpty()){
            E firstElement = firstDeque.pollFirst();
            E secondElement = secondDeque.pollFirst();
            if (!firstElement.equals(secondElement))
                return false;
        }
        return secondDeque.isEmpty();
    }
}
