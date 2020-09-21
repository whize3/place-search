package com.whize.search.controller.v1;

import com.whize.search.advice.exception.CSigninFailedException;
import com.whize.search.config.security.JwtTokenProvider;
import com.whize.search.dto.main.UserDto;
import com.whize.search.entity.User;
import com.whize.search.model.response.SingleResult;
import com.whize.search.repo.UserJpaRepo;
import com.whize.search.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID", required = true) @RequestBody UserDto dto) {
        User user = userJpaRepo.findByUid(dto.getId()).orElseThrow(CSigninFailedException::new);
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new CSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));

    }
}
