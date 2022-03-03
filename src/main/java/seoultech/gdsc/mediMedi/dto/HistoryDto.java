package seoultech.gdsc.mediMedi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HistoryDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ListResponse {
        private int id;
        private String name;
        private String date;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class DetailResponse {
        private String date;
        private String name;
        private String entp;
        private String effect;
        private String usingMethod;
        private String caution;
        private String notice;
        private String interact;
        private String sideEffect;
        private String storageMethod;
    }
}
