package put.io.testing.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FailureOrErrorTest {


    @Test
    void test1() {
        System.out.println("class FailureOrErrorTest");
        System.out.println("Test1");
        assertEquals(1, 2);
    }

    @Test
    void test2() {
        System.out.println("Test2");
        throw new RuntimeException("unexpected exception");
    }

    @Test
    void test3() {
        try {
            System.out.println("Test3 "+"\n");
            assertEquals(1, 2);
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println(t.getClass().getName());
        }
    }
}