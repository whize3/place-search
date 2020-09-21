package com.whize.search.dto.main;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String id;
    private String password;
}
