package com.scoreboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
        () -> assertEquals(homeTeam, match.getHomeTeam()),
        () -> assertEquals(List.of(0, 0), match.getScore()),
        () -> assertEquals(List.of(match), scoreboard.getBoard()));
  }

  @Test
  void startNewMatch_IllegalArgumentExceptions() {
    // arrange
    Match match = scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    Executable executable = () -> scoreboard.startNewMatch(homeTeam, "Brazil");

    // assert
    Assertions.assertAll(
        () -> assertThrows(IllegalArgumentException.class, executable),
        () -> assertEquals(List.of(match), scoreboard.getBoard()));
  }

  @Test
  void updateScore() {
    // arrange
    scoreboard.startNewMatch(homeTeam, awayTeam);

    // act
    scoreboard.updateScore(homeTeam, 1, awayTeam, 2);

    // assert
    assertEquals(List.of(1, 2), scoreboard.findMatch(homeTeam, awayTeam).getScore());
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
        () -> assertEquals(List.of(), scoreboard.getBoard()),
        () -> assertEquals(MatchStatus.FINISHED, match.getStatus()));
  }

  @Test
  void getSummary() {
    // arrange
    List<Map<String, Object>> matches =
        List.of(
            Map.of(
                "homeTeam", "Sweden", "homeTeamScore", 3, "awayTeam", "Turkey", "awayTeamScore", 2),
            Map.of(
                "homeTeam", "Brazil", "homeTeamScore", 1, "awayTeam", "China", "awayTeamScore", 3),
            Map.of(
                "homeTeam", "Italy", "homeTeamScore", 0, "awayTeam", "Germany", "awayTeamScore", 1),
            Map.of(
                "homeTeam", "Japan", "homeTeamScore", 2, "awayTeam", "Mexico", "awayTeamScore", 2));

    matches.forEach(
        match -> {
          scoreboard.startNewMatch(
              match.get("homeTeam").toString(), match.get("awayTeam").toString());

          scoreboard.updateScore(
              match.get("homeTeam").toString(),
              (int) (match.get("homeTeamScore")),
              match.get("awayTeam").toString(),
              (int) (match.get("awayTeamScore")));

          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });

    // act
    List<String> summary = scoreboard.getSummary();

    // assert
    assertEquals(
        List.of(
            "Sweden 3 - Turkey 2",
            "Japan 2 - Mexico 2",
            "Brazil 1 - China 3",
            "Italy 0 - Germany 1"),
        summary);
  }
}
