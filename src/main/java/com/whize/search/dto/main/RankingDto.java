package com.whize.search.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RankingDto {
    private String keyword;
    private String hit;
}
