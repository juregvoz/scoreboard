package com.scoreboard;

public class ScoreboardApplication {

  public static void main(String[] args) {
    Scoreboard scoreboard = new ScoreboardImpl();
    scoreboard.startNewMatch("Turkey", "Brazil");
    System.out.println(scoreboard.getSummary());
  }
}
