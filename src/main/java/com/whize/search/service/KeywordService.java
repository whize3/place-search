package com.whize.search.service;

import com.whize.search.config.CacheKey;
import com.whize.search.consumer.OptionalConsumer;
import com.whize.search.entity.Keyword;
import com.whize.search.repo.KeywordJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeywordService {

    private final KeywordJpaRepo keywordJpaRepo;

    @Transactional
    public void updateKeyword(String query) {
        log.error("조회수 업데이트: "+query);
        Keyword newKeyword = Keyword.builder()
                .query(query)
                .hit(1)
                .build();

        Optional<Keyword> keyword = keywordJpaRepo.findKeywordByQuery(query);

        OptionalConsumer.of(keyword)
                .ifPresent(s ->keywordJpaRepo.incrementKeywordHit(s.getQuery()))
                .ifNotPresent(() -> keywordJpaRepo.save(newKeyword));
    }

    @Cacheable(value = CacheKey.KEYWORD, unless = "#result == null")
    public List<Keyword> ranking() {
        return keywordJpaRepo.findTop10ByOrderByHitDesc();
    }
}
