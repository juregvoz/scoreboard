package com.scoreboard;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreboardImpl implements Scoreboard {

  private final List<Match> board;

  public ScoreboardImpl() {
    this.board = new ArrayList<>();
  }

  public Match startNewMatch(String homeTeam, String awayTeam) {
    Match match = new Match(homeTeam, awayTeam);
    board.add(match);
    return match;
  }

  public void updateScore(
      String homeTeam, Integer homeTeamScore, String awayTeam, Integer awayTeamScore) {
    if (homeTeamScore < 0 || awayTeamScore < 0) {
      throw new IllegalArgumentException("Score cannot be negative!");
    }
    findMatch(homeTeam, awayTeam).setScore(List.of(homeTeamScore, awayTeamScore));
  }

  public void finishMatch(String homeTeam, String awayTeam) {
    findMatch(homeTeam, awayTeam).setStatus(MatchStatus.FINISHED);
    board.removeIf(match -> match.getStatus().equals(MatchStatus.FINISHED));
  }

  public List<Match> getBoard() {
    return this.board;
  }

  public List<Match> getOrderedBoard() {
    final Comparator<Match> compareStartTime =
        (m1, m2) -> m2.getStartTime().compareTo(m1.getStartTime());
    return board.stream()
        .sorted(Comparator.comparing(Match::getScoreSum).reversed().thenComparing(compareStartTime))
        .toList();
  }

  public List<String> getSummary() {
    return getOrderedBoard().stream().map(Match::getScoreString).toList();
  }

  private Match findMatch(String homeTeam, String awayTeam) {
    Optional<Match> optionalMatch =
        board.stream()
            .filter(m -> m.getHomeTeam().equals(homeTeam))
            .filter(m -> m.getAwayTeam().equals(awayTeam))
            .findAny();
    return optionalMatch.orElseThrow(() -> new RuntimeException("Match not found"));
  }
}
