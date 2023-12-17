package com.scoreboard;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Match {

  public Match(String homeTeam, String awayTeam) {
    if (homeTeam.equals(awayTeam)) {
      throw new IllegalArgumentException("Team cannot play against itself!");
    }
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

  public Integer getScoreSum() {
    return score.get(0) + score.get(1);
  }

  public String getScoreString() {
    return String.format("%s %s - %s %s", homeTeam, score.get(0), awayTeam, score.get(1));
  }
}
