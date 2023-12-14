package com.scoreboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MatchTest {

    @Test
    void startNewMatch() {
        String awayTeam = "Brazil";
        String homeTeam = "Argentina";

        Match match = new Match(homeTeam, awayTeam);
        Assertions.assertAll(() -> {
            assertEquals(match.getHomeTeam(), homeTeam);
            assertEquals(match.getAwayTeam(), awayTeam);
            assertEquals(match.getStatus(), MatchStatus.IN_PROGRESS);
            assertNotEquals(match.getStartTime(), LocalDateTime.now());
        });
    }




}
