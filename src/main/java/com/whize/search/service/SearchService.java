package com.whize.search.service;

import com.whize.search.advice.exception.CApiCallException;
import com.whize.search.model.search.KaKaoKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final RestTemplate restTemplate;

    @Value("${spring.api.kakao.authorization}")
    private String authorization;
    @Value("${spring.api.kakao.host}")
    private String host;
    @Value("${spring.api.kakao.url.keyword}")
    private String keywordUrl;


    public KaKaoKeyword keyword(String keyword, Integer page) {

        StringBuilder url = new StringBuilder();
        url.append(host);
        url.append(keywordUrl);
        url.append("?query=").append(keyword);
        url.append("&size=10");
        if(page != null )url.append("&page=").append(page);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorization);

        HttpEntity entity = new HttpEntity(headers);

        try {
            ResponseEntity<KaKaoKeyword> response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, KaKaoKeyword.class);
            if(response.getStatusCode() == HttpStatus.OK){
                KaKaoKeyword body = response.getBody();
                int pageable_count = body.getMeta().getPageable_count();
                int total_page = (int) Math.ceil((double)pageable_count/10);
                body.getMeta().setTotal_page(total_page);
                return body;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new CApiCallException();
        }
        throw new CApiCallException();
    }
}
