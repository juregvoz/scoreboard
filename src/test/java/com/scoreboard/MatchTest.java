package com.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

  @Test
  void startNewMatch() {
    // arrange
    String awayTeam = "Brazil";
    String homeTeam = "Argentina";

    // act
    Match match = new Match(homeTeam, awayTeam);

    // assert
    Assertions.assertAll(
        () -> {
          assertEquals(match.getHomeTeam(), homeTeam);
          assertEquals(match.getAwayTeam(), awayTeam);
          assertEquals(match.getScore(), List.of(0, 0));
          assertEquals(match.getStatus(), MatchStatus.IN_PROGRESS);
          assertNotEquals(match.getStartTime(), LocalDateTime.now());
        });
  }

  @Test
  void startNewMatch_IllegalArgumentException() {
    // act
    Executable executable = () -> new Match("Brazil", "Brazil");

    // assert
    assertThrows(IllegalArgumentException.class, executable);
  }
}
