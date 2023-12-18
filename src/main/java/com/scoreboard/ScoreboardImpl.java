package com.scoreboard;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardImpl implements Scoreboard {

  private final List<Match> board;

  public ScoreboardImpl() {
    this.board = new ArrayList<>();
  }

  public Match startNewMatch(String homeTeam, String awayTeam) {
    Match match = new Match(homeTeam, awayTeam);
    checkTeamsAvailability(homeTeam, awayTeam);
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
    return optionalMatch.orElseThrow(() -> new RuntimeException("Match not found!"));
  }

  private void checkTeamsAvailability(String homeTeam, String awayTeam) {
    Optional<String> optionalTeam =
        board.stream()
            .map(m -> List.of(m.getHomeTeam(), m.getAwayTeam()))
            .flatMap(Collection::stream)
            .filter(s -> s.equals(homeTeam) || s.equals(awayTeam))
            .findAny();
    optionalTeam.ifPresent(
        t -> {
          throw new IllegalArgumentException("Team already plays in another match!");
        });
  }
}
