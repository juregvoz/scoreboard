package com.scoreboard;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Match {

    @NonNull
    private String homeTeam;

    @NonNull
    private String awayTeam;

    private List<Integer> score;

    private MatchStatus status;

    private LocalDateTime startTime;
}
