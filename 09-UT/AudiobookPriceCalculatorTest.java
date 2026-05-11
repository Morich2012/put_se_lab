package put.io.testing.audiobooks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AudiobookPriceCalculatorTest {

    private AudiobookPriceCalculator calculator;
    private Audiobook audiobook;

    @BeforeEach
    void setUp() {
        calculator = new AudiobookPriceCalculator();
        audiobook = new Audiobook("Clean Code", 30.0);
    }

    @Test
    void testSubscriberPaysNothing() {
        Customer customer = new Customer("Alice", Customer.LoyaltyLevel.STANDARD, true);
        assertEquals(0.0, calculator.calculate(customer, audiobook));
    }

    @Test
    void testSilverCustomerGets10PercentDiscount() {
        Customer customer = new Customer("Bob", Customer.LoyaltyLevel.SILVER, false);
        assertEquals(27.0, calculator.calculate(customer, audiobook));
    }

    @Test
    void testGoldCustomerGets20PercentDiscount() {
        Customer customer = new Customer("Carol", Customer.LoyaltyLevel.GOLD, false);
        assertEquals(24.0, calculator.calculate(customer, audiobook));
    }

    @Test
    void testStandardCustomerPaysFullPrice() {
        Customer customer = new Customer("Dave", Customer.LoyaltyLevel.STANDARD, false);
        assertEquals(30.0, calculator.calculate(customer, audiobook));
    }
}