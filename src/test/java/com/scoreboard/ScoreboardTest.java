package com.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardTest {

  public Scoreboard scoreboard;

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
          assertEquals(match.getHomeTeam(), homeTeam);
          assertEquals(match.getScore(), List.of(0, 0));
          assertEquals(scoreboard.getBoard(), List.of(match));
        });
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
          assertEquals(score, List.of(1, 2));
        });
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
          assertEquals(scoreboard.getBoard(), List.of());
          assertEquals(match.getStatus(), MatchStatus.FINISHED);
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

    Match match1 = scoreboard.startNewMatch(homeTeam1, homeTeam1);
    TimeUnit.MILLISECONDS.sleep(1);
    Match match2 = scoreboard.startNewMatch(homeTeam2, homeTeam2);
    TimeUnit.MILLISECONDS.sleep(1);
    Match match3 = scoreboard.startNewMatch(homeTeam3, homeTeam3);
    TimeUnit.MILLISECONDS.sleep(1);
    Match match4 = scoreboard.startNewMatch(homeTeam4, homeTeam4);
    TimeUnit.MILLISECONDS.sleep(1);

    scoreboard.updateScore(homeTeam1, 3, awayTeam1, 2);
    scoreboard.updateScore(homeTeam2, 1, awayTeam2, 3);
    scoreboard.updateScore(homeTeam3, 0, awayTeam3, 1);
    scoreboard.updateScore(homeTeam4, 2, awayTeam4, 2);

    // act
    List<Match> matches = scoreboard.getOrderedBoard();
    List<String> summary = scoreboard.getSummary();

    // assert
    Assertions.assertAll(
        () -> {
          assertEquals(matches.get(0), match1);
          assertEquals(matches.get(1), match4);
          assertEquals(matches.get(2), match2);
          assertEquals(matches.get(3), match3);
          assertEquals(
              summary,
              List.of(
                  "Slovenia 3 - Turkey 2",
                  "Japan 2 - Mexico 2",
                  "Brazil 1 - Pakistan 3",
                  "Italy 0 - Germany 1"));
        });
  }
}
