import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Tomas Perez Molina
 */
public class Main2 {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5).map(e -> e * 2).forEach(
                e -> {
                    System.out.println(e);
                    System.out.println(e * 2);
                    System.out.println(Math.max(e , 5));
                }
        );
    }
}
