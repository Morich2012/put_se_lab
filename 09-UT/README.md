Name: Miguel Morales León
ID: ER-2046
# Unit Tests – Lab Questions

---

## Question 3.1
> Would the tests stop working if the setUp annotation was changed from `@BeforeEach` to `@BeforeAll`?

Using `@BeforeAll` instead of `@BeforeEach` would likely still work in this case because `Calculator` has no internal state that could be modified between tests. However, it would require both the method and the `calculator` field to be `static`, and only one instance would be shared across all tests. This is generally bad practice, since if the object's state was modified in one test, it could affect the others.

---

## Question 4.1
> Which method will be marked as Failure and which will be marked as Error?

- **test1** is a **Failure** because the assertion is always false.
- **test2** is an **Error** because an unexpected `RuntimeException` is thrown.

---

## Question 4.2
> What type of thrown object does JUnit expect to determine that the test has failed in terms of the Failure category?

After running `test3`, the console printed `org.opentest4j.AssertionFailedError`. This means JUnit marks a test as **Failure** when an `AssertionFailedError` is thrown. This class extends `Error`, not `Exception`, which is why we need to use `Throwable` in the catch block, as it is the only common ancestor of both `Error` and `Exception` in Java.

---

## Question 5.1
> What type of testing is it: black-box or white-box?

It is **white-box** testing, because we are writing tests based on the internal structure of the `calculate` method, not just its inputs and outputs.

---

## Question 5.2
> How many possible paths are there in `calculate`?

There are **4 possible paths**:

| Path | Condition | Result |
|------|-----------|--------|
| 1 | Customer is a subscriber | Returns `0.0` |
| 2 | Not a subscriber + SILVER loyalty | Returns `0.9 * price` |
| 3 | Not a subscriber + GOLD loyalty | Returns `0.8 * price` |
| 4 | Not a subscriber + STANDARD loyalty | Returns full price |
