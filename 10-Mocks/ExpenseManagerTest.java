package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import put.io.students.fancylibrary.service.FancyService;

public class ExpenseManagerTest {

    // Task 4: calculateTotal sums all expenses returned by the repository mock
    @Test
    void calculateTotal() {
        IExpenseRepository repository = mock(IExpenseRepository.class);
        FancyService service = mock(FancyService.class);

        Expense e1 = new Expense(); e1.setAmount(100);
        Expense e2 = new Expense(); e2.setAmount(200);
        Expense e3 = new Expense(); e3.setAmount(300);

        when(repository.getExpenses()).thenReturn(List.of(e1, e2, e3));

        ExpenseManager manager = new ExpenseManager(repository, service);

        assertEquals(600, manager.calculateTotal());
    }

    // Task 5: calculateTotalForCategory uses argument matchers to return category-specific lists
    // Question 5.1: order of expectations matters — anyString() must be registered FIRST so that
    // the more-specific "Home"/"Car" stubs (registered last) have higher priority in Mockito's
    // LIFO matching. If anyString() were last, it would override all specific stubs and every
    // category would return an empty list.
    @Test
    void calculateTotalForCategory() {
        IExpenseRepository repository = mock(IExpenseRepository.class);
        FancyService service = mock(FancyService.class);

        Expense home1 = new Expense(); home1.setAmount(500);
        Expense home2 = new Expense(); home2.setAmount(300);
        Expense car1  = new Expense(); car1.setAmount(1000);

        // anyString() registered first → acts as default/fallback
        when(repository.getExpensesByCategory(anyString())).thenReturn(Collections.emptyList());
        // Specific stubs registered last → take priority over anyString()
        when(repository.getExpensesByCategory("Home")).thenReturn(List.of(home1, home2));
        when(repository.getExpensesByCategory("Car")).thenReturn(List.of(car1));

        ExpenseManager manager = new ExpenseManager(repository, service);

        assertEquals(800,  manager.calculateTotalForCategory("Home"));
        assertEquals(1000, manager.calculateTotalForCategory("Car"));
        assertEquals(0,    manager.calculateTotalForCategory("Food"));
        assertEquals(0,    manager.calculateTotalForCategory("Sport"));
    }

    // Task 6: calculateTotalInDollars uses thenAnswer for dynamic PLN→USD conversion
    @Test
    void calculateTotalInDollars() throws ConnectException {
        IExpenseRepository repository = mock(IExpenseRepository.class);
        FancyService service = mock(FancyService.class);

        Expense e1 = new Expense(); e1.setAmount(400);
        when(repository.getExpenses()).thenReturn(List.of(e1));

        // $1 = 4 PLN → dynamic calculation via thenAnswer (Task 6 step 9)
        when(service.convert(anyDouble(), eq("PLN"), eq("USD")))
                .thenAnswer(invocation -> {
                    double amount = invocation.getArgument(0);
                    return amount / 4.0;
                });

        ExpenseManager manager = new ExpenseManager(repository, service);

        assertEquals(100, manager.calculateTotalInDollars());
    }

    // Task 6: calculateTotalInDollars returns -1 when FancyService throws ConnectException
    @Test
    void calculateTotalInDollarsOnConnectException() throws ConnectException {
        IExpenseRepository repository = mock(IExpenseRepository.class);
        FancyService service = mock(FancyService.class);

        Expense e1 = new Expense(); e1.setAmount(400);
        when(repository.getExpenses()).thenReturn(List.of(e1));

        when(service.convert(anyDouble(), eq("PLN"), eq("USD")))
                .thenThrow(new ConnectException("Simulated connection failure"));

        ExpenseManager manager = new ExpenseManager(repository, service);

        assertEquals(-1, manager.calculateTotalInDollars());
    }
}
