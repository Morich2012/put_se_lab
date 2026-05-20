package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import put.io.students.fancylibrary.database.IFancyDatabase;

public class ExpenseRepositoryTest {

    // Task 1 & 2: loadExpenses with Mockito mock and InOrder verification
    @Test
    void loadExpenses() {
        // Task 2: create Mockito mock for IFancyDatabase
        IFancyDatabase db = mock(IFancyDatabase.class);
        when(db.queryAll()).thenReturn(Collections.emptyList());

        // Task 1 & 2: inject mock into repository, run method under test
        ExpenseRepository repository = new ExpenseRepository(db);
        repository.loadExpenses();

        // Task 2: verify correct call order — connect → queryAll → close
        InOrder inOrder = inOrder(db);
        inOrder.verify(db).connect();
        inOrder.verify(db).queryAll();
        inOrder.verify(db).close();

        // Task 1: assert that the loaded list of expenses is empty
        assertTrue(repository.getExpenses().isEmpty());
    }

    // Task 3: saveExpenses — verify persist is called exactly once per expense
    @Test
    void saveExpenses() {
        IFancyDatabase db = mock(IFancyDatabase.class);
        ExpenseRepository repository = new ExpenseRepository(db);

        // Add 5 expenses to the repository
        for (int i = 0; i < 5; i++) {
            repository.addExpense(new Expense());
        }

        repository.saveExpenses();

        // Verify persist was called exactly 5 times (not 7) with any Expense instance
        verify(db, times(5)).persist(any(Expense.class));
    }
}
