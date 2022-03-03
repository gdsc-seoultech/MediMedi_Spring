package seoultech.gdsc.mediMedi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Request {
        private String token;
        private String imageUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class SuccessResponse {
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class FailResponse {
        private String text;
    }
}
