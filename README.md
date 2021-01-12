

## Homework 
0. Extend findAll endpoint to solve the following tasks.
   Use non-required RequestParams to send data.
1. Add customer name field to ExchangeData
2. Add a new customer request param to findAll and search for it with LIKE
So if the input John Testington, then the response should be:
   - John Testington
   - John Jr. Testington
   - John Archibald Testington
Hint, replace whitespaces with %.
3. Add search for exchanged price interval. E.g.:
Hint: use between, don't check the unit price, only the result amount matters

