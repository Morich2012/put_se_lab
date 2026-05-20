Name: Miguel Morales León
ID: ER-2046
# Mocks – Lab Questions

---

## Question 2.1
> How to change the operation of the mock object to verify that the interaction of the `loadExpenses` method with the database mock object was correct, i.e. first a connection to the database was opened (`connect`), then data was downloaded (`queryAll`), and finally the connection was terminated (`close`)?

You would add a list inside `MyDatabase` that records the name of each method as it gets called. After the interaction, the test checks that the list equals `[connect, queryAll, close]`. It works, but it's annoying to maintain by hand every time the interface changes — that's exactly why Mockito with `InOrder` is much easier.

---

## Question 5.1
> Does the order of expectations for the same method matched by different arguments matter?

**Yes, it does.** Mockito picks the last registered stub that matches the call. So if `anyString()` is registered last, it always wins and the specific stubs for `"Home"` and `"Car"` are never reached — every category returns an empty list.

The correct order is:

| Order | Stub | Priority |
|-------|------|----------|
| 1st (registered first) | `anyString()` | Lowest — fallback |
| 2nd | `"Home"` | Higher |
| 3rd (registered last) | `"Car"` | Highest |
