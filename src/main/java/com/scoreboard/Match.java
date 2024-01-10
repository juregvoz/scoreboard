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
    this.homeTeamScore = 0;
    this.awayTeamScore = 0;
    this.status = MatchStatus.IN_PROGRESS;
    this.startTime = LocalDateTime.now();
  }

  private String homeTeam;

  private String awayTeam;

  private Integer homeTeamScore;

  private Integer awayTeamScore;

  private MatchStatus status;

  private LocalDateTime startTime;

  public Integer getScoreSum() {
    return homeTeamScore + awayTeamScore;
  }

  public String getScoreString() {
    return String.format("%s %s - %s %s", homeTeam, homeTeamScore, awayTeam, awayTeamScore);
  }

  public List<Integer> getScore() {
    return List.of(this.homeTeamScore, this.awayTeamScore);
  }

  public void setScore(Integer homeTeamScore, Integer awayTeamScore) {
    this.awayTeamScore = awayTeamScore;
    this.homeTeamScore = homeTeamScore;
  }
}
