package com.scoreboard;

import java.util.List;

public interface Scoreboard {
  Match startNewMatch(String homeTeam, String awayTeam);

  void updateScore(String homeTeam, Integer homeTeamScore, String awayTeam, Integer awayTeamScore);

  void finishMatch(String homeTeam, String awayTeam);

  List<Match> getBoard();

  List<Match> getOrderedBoard();

  List<String> getSummary();

  Match findMatch(String homeTeam, String awayTeam);
}
