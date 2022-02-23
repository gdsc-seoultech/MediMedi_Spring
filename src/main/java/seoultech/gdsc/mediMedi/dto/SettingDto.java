package seoultech.gdsc.mediMedi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SettingDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private String email;
        private String name;
        private Boolean voiceUsage;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Request {
        private String token;
        private String email;
        private String name;
        private Boolean voiceUsage;
    }
}
