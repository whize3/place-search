package com.whize.search.service;

import com.whize.search.advice.exception.CApiCallException;;
import com.whize.search.model.search.PlaceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public abstract class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    public abstract PlaceSource getSource(String query, Integer page);

    protected abstract HttpEntity setHttpEntity();

    protected abstract String setUrl(String url, Integer page);

    protected ResponseEntity<PlaceSource> callSource(String url, HttpEntity entity){
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, PlaceSource.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CApiCallException();
        }
    };

}
