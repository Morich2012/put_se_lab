Name: Miguel Morales León
ID: ER-2046

---

## Question 2.1

Tendríamos que añadir una lista en MyDatabase que guarde el nombre de cada método que se llama y en qué orden. Luego en el test compruebas que la lista es [connect, queryAll, close]. Funciona pero es un rollo hacerlo a mano para cada caso, por eso Mockito con InOrder es mucho más cómodo.

---

## Question 5.1

Sí importa. Mockito coge el último stub registrado que coincida, así que si pones anyString() al final, ese siempre gana y nunca llega a los de "Home" o "Car". Hay que poner anyString() primero y los específicos después.
