package com.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

  String homeTeam = "Argentina";
  String awayTeam = "Brazil";

  @Test
  void startNewMatch() {
    // act
    Match match = new Match(homeTeam, awayTeam);

    // assert
    Assertions.assertAll(
        () -> assertEquals(homeTeam, match.getHomeTeam()),
        () -> assertEquals(awayTeam, match.getAwayTeam()),
        () -> assertEquals(List.of(0, 0), match.getScore()),
        () -> assertEquals(0, match.getScoreSum()),
        () -> assertEquals(MatchStatus.IN_PROGRESS, match.getStatus()),
        () -> assertInstanceOf(LocalDateTime.class, match.getStartTime()));
  }

  @Test
  void startNewMatch_IllegalArgumentException() {
    // act
    Executable executable = () -> new Match(homeTeam, homeTeam);

    // assert
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void setScore() {
    // arrange
    Match match = new Match(homeTeam, awayTeam);

    // act
    match.setScore(3, 2);
    List<Integer> score = match.getScore();

    // assert
    assertEquals(List.of(3, 2), score);
  }
}
