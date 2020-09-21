package com.whize.search.service;

import com.whize.search.advice.exception.CApiCallException;
import com.whize.search.model.search.PlaceSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KakaoSearchService extends SearchService {

    @Value("${spring.api.kakao.authorization}")
    private String authorization;
    @Value("${spring.api.kakao.host}")
    private String host;
    @Value("${spring.api.kakao.url.keyword}")
    private String keywordUrl;

    @Override
    public PlaceSource getSource(String query, Integer page) {
        HttpEntity entity = setHttpEntity();
        String url = setUrl(query, page);
        ResponseEntity<PlaceSource> response = callSource(url, entity);
        if (response.getStatusCode() == HttpStatus.OK) {
            PlaceSource body = response.getBody();
            int pageable_count = body.getMeta().getPageable_count();
            int total_page = (int) Math.ceil((double) pageable_count / 10);
            body.getMeta().setTotal_page(total_page);
            return body;
        }
        throw new CApiCallException();
    }

    @Override
    protected HttpEntity setHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorization);

        return new HttpEntity(headers);
    }

    @Override
    protected String setUrl(String keyword, Integer page) {
        StringBuilder url = new StringBuilder();
        url.append(host);
        url.append(keywordUrl);
        url.append("?query=").append(keyword);
        url.append("&size=10");
        if (page != null) url.append("&page=").append(page);
        return url.toString();
    }
}
