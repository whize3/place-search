package com.whize.search.model.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class KaKaoKeyword {

    private List<Documents> documents;
    private Meta meta;

    @Getter
    @Setter
    public static class Meta {
        int total_count;
        int pageable_count;
        int total_page; //custom
        boolean is_end;
        RegionInfo same_name;
    }

    @Getter
    @Setter
    public static class RegionInfo {
        String[] region;
        String keyword;
        String selected_region;
    }

    @Getter
    @Setter
    public static class Documents {
        private String id;
        private String place_name;
        private String category_name;
        private String category_group_code;
        private String category_group_name;
        private String phone;
        private String address_name;
        private String road_address_name;
        private Double x;
        private Double y;
        private String place_url;
        private String distance;
    }

}
