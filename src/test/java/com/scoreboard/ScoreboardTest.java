package com.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {

  Scoreboard scoreboard;

  @BeforeEach
  void setup() {
    this.scoreboard = new ScoreboardImpl();
  }

  String homeTeam = "Slovenia";
  String awayTeam = "Turkey";

  @Test
  void startNewMatch() {
    // arrange

    // act
    Match match = scoreboard.startNewMatch(homeTeam, awayTeam);

    // assert
    Assertions.assertAll(
        () -> {
          assertEquals(homeTeam, match.getHomeTeam());
          assertEquals(List.of(0, 0), match.getScore());
          assertEquals(List.of(match), scoreboard.getBoard());
        });
  }

  @Test
  void startNewMatch_IllegalArgumentExceptions() {
    // arrange
    Match match = scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    Executable executable = () -> scoreboard.startNewMatch(homeTeam, "Brazil");

    // assert
    assertThrows(IllegalArgumentException.class, executable);
    assertEquals(List.of(match), scoreboard.getBoard());
  }


  @Test
  void updateScore() {
    // arrange
    scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    scoreboard.updateScore(homeTeam, 1, awayTeam, 2);

    // assert
    List<Integer> score = scoreboard.getBoard().get(0).getScore();
    Assertions.assertAll(
        () -> {
          assertEquals(List.of(1, 2), score);
        });
  }

  @Test
  void updateScore_IllegalArgumentException() {
    // arrange
    scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    Executable executable = () -> scoreboard.updateScore(homeTeam, -1, awayTeam, 2);

    // assert
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void updateScore_MatchNotFound() {
    // arrange
    scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    Executable executable = () -> scoreboard.updateScore(homeTeam, 1, homeTeam, 2);

    // assert
    assertThrows(RuntimeException.class, executable);
  }

  @Test
  void finishMatchInProgress() {
    // arrange
    Match match = scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    scoreboard.finishMatch(homeTeam, awayTeam);

    // assert
    Assertions.assertAll(
        () -> {
          assertEquals(List.of(), scoreboard.getBoard());
          assertEquals(MatchStatus.FINISHED, match.getStatus());
        });
  }

  @Test
  void getSummary() throws InterruptedException {
    // arrange
    String homeTeam1 = "Slovenia";
    String awayTeam1 = "Turkey";
    String homeTeam2 = "Brazil";
    String awayTeam2 = "Pakistan";
    String homeTeam3 = "Italy";
    String awayTeam3 = "Germany";
    String homeTeam4 = "Japan";
    String awayTeam4 = "Mexico";

    Match match1 = scoreboard.startNewMatch(homeTeam1, awayTeam1);
    TimeUnit.MILLISECONDS.sleep(1);
    Match match2 = scoreboard.startNewMatch(homeTeam2, awayTeam2);
    TimeUnit.MILLISECONDS.sleep(1);
    Match match3 = scoreboard.startNewMatch(homeTeam3, awayTeam3);
    TimeUnit.MILLISECONDS.sleep(1);
    Match match4 = scoreboard.startNewMatch(homeTeam4, awayTeam4);
    TimeUnit.MILLISECONDS.sleep(1);

    scoreboard.updateScore(homeTeam1, 3, awayTeam1, 2);
    scoreboard.updateScore(homeTeam2, 1, awayTeam2, 3);
    scoreboard.updateScore(homeTeam3, 0, awayTeam3, 1);
    scoreboard.updateScore(homeTeam4, 2, awayTeam4, 2);

    // act
    List<Match> orderedBoard = scoreboard.getOrderedBoard();
    List<String> summary = scoreboard.getSummary();

    // assert
    Assertions.assertAll(
        () -> {
          assertEquals(match1, orderedBoard.get(0));
          assertEquals(match4, orderedBoard.get(1));
          assertEquals(match2, orderedBoard.get(2));
          assertEquals(match3, orderedBoard.get(3));
          assertEquals(
              List.of(
                  "Slovenia 3 - Turkey 2",
                  "Japan 2 - Mexico 2",
                  "Brazil 1 - Pakistan 3",
                  "Italy 0 - Germany 1"),
              summary);
        });
  }
}
