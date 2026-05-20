Name: Miguel Morales León
ID: ER-2046

---

## Question 2.1 — How to verify correct interaction order with a hand-written mock?

To verify that `loadExpenses` interacts with the database in the correct order
(`connect` → `queryAll` → `close`) using a hand-written mock, we would need to add
state to `MyDatabase`: an internal list that records the name of every method called,
in order. Each method (`connect`, `queryAll`, `close`) would append its own name to
that list before doing its real work. After the interaction, the test would assert
that the recorded sequence equals `["connect", "queryAll", "close"]`.

This works, but it is **not easy or clean**:

- Every new interaction scenario requires a new field or a new helper method in the mock class.
- The mock has to be updated manually whenever the interface changes.
- The assertion logic mixes verification concerns into the production-test boundary.

This is exactly why the **Mockito library** exists: it generates mock objects at
runtime, tracks every method call automatically, and exposes `verify()` and
`InOrder` to express complex interaction expectations in a single readable line —
without any hand-written state machine.

---

## Question 5.1 — Does the order of expectations for the same method with different arguments matter?

**Yes, the order matters significantly.**

Mockito matches stubs in **reverse registration order** (LIFO — last registered wins).
When `getExpensesByCategory` is called, Mockito walks the stub list from the most
recently registered to the oldest and returns the result of the first stub whose
argument matcher matches the actual argument.

Consequence:

- If `anyString()` is registered **last**, it has the highest priority and matches
  every call — the specific `"Home"` and `"Car"` stubs registered earlier are
  never reached. All categories return an empty list and the sum is 0 for every
  category, including "Home" and "Car".

- If `anyString()` is registered **first** (before the specific stubs), the specific
  stubs for `"Home"` and `"Car"` have higher priority because they were registered
  later. Only categories that do not match any specific stub fall through to the
  `anyString()` default and return an empty list.

**Correct registration order:**
```java
when(repository.getExpensesByCategory(anyString())).thenReturn(Collections.emptyList()); // default first
when(repository.getExpensesByCategory("Home")).thenReturn(List.of(home1, home2));         // specific last → higher priority
when(repository.getExpensesByCategory("Car")).thenReturn(List.of(car1));                  // specific last → higher priority
```
