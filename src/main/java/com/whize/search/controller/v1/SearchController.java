package com.whize.search.controller.v1;

import com.whize.search.entity.Keyword;
import com.whize.search.model.response.ListResult;
import com.whize.search.model.response.SingleResult;
import com.whize.search.model.search.KaKaoKeyword;
import com.whize.search.service.KeywordService;
import com.whize.search.service.ResponseService;
import com.whize.search.service.SearchService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@Api(tags = {"3. Search"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class SearchController {

    private final SearchService searchService;
    private final ResponseService responseService;
    private final KeywordService keywordService;


    @GetMapping("/search/keyword")
    SingleResult<KaKaoKeyword> keyword(@RequestParam String query, @RequestParam(required = false) Integer page){
        keywordService.updateKeyword(query);
        return responseService.getSingleResult(searchService.keyword(query, page));
    }

    @GetMapping("/search/keyword/rank")
    ListResult<Keyword> ranking(){
        return responseService.getListResult(keywordService.ranking());
    }



}
