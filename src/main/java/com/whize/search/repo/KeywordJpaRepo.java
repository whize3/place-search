package com.whize.search.repo;

import com.whize.search.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordJpaRepo extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findKeywordByQuery(String query);
    List<Keyword> findTop10ByOrderByHitDesc();

    @Modifying(clearAutomatically = true)
    @Query("update Keyword k set k.hit=k.hit+1 where k.query= :query")
    int incrementKeywordHit(@Param("query") String query);

}
