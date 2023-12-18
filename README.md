## Coding exercise - Scoreboard

This is a simple app that provides the implementation of the Live Football World Cup Score Board.

Scoreboard can be initialized as follows:
```
Scoreboard scoreboard = new ScoreboardImpl();
```

From there it's possible to:
- start a match
- update score
- finish a match
- get an ordered summary

To ensure meaningful data there are some restrictions in the code:
- team cannot play against itself
- team cannot play in multiple matches at the same time
- score cannot be negative
- only match that is present on the board can be updated or finished

To build the project run the following command:
```
mvn clean install
```
or in case you don't have Maven on your local machine run:

```
mvnw clean install
```

This will also run the tests. Recommended IDE is IntelliJ IDEA.
