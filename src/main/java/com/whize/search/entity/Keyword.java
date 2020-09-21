package com.whize.search.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "keyword")
public class Keyword implements Serializable {

    @Id // pk
    @Column(nullable = false)
    private String query;
    @Column(nullable = false, columnDefinition = "integer default 1")
    private Integer hit;

}
