Name: Miguel Morales León
ID: ER-2046

---

## Question 2.1 — How to change the operation of the mock object to verify that the interaction of the loadExpenses method with the database mock object was correct, i.e. first a connection to the database was opened (connect), then data was downloaded (queryAll), and finally the connection was terminated (close)?

You'd add a list inside MyDatabase that saves the name of each method as it gets called. After the interaction you just check the list is [connect, queryAll, close]. It works but it's annoying to do by hand for every case, that's why Mockito with InOrder is way easier.

---

## Question 5.1 — Does the order of expectations for the same method matched by different arguments matter?

Yes it does. Mockito picks the last registered stub that matches, so if you put anyString() at the end it always wins and never reaches the "Home" or "Car" ones. You have to put anyString() first and the specific ones after.
