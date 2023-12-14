package com.scoreboard;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Match {

  public Match(String homeTeam, String awayTeam) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.score = List.of(0, 0);
    this.status = MatchStatus.IN_PROGRESS;
    this.startTime = LocalDateTime.now();
  }

  private String homeTeam;

  private String awayTeam;

  private List<Integer> score;

  private MatchStatus status;

  private LocalDateTime startTime;
}
