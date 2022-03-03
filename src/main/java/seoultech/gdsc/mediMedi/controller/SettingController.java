package seoultech.gdsc.mediMedi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.mediMedi.dto.SettingDto;
import seoultech.gdsc.mediMedi.response.BasicResponse;
import seoultech.gdsc.mediMedi.response.FailResponse;
import seoultech.gdsc.mediMedi.response.SuccessResponse;
import seoultech.gdsc.mediMedi.serializer.EmptyJsonResponse;
import seoultech.gdsc.mediMedi.service.SettingService;

import java.util.Optional;

@RestController
@RequestMapping("/api/setting")
public class SettingController {

    private final SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    /*
    설정 정보 조회
     */
    @GetMapping("/{token}")
    public BasicResponse getSetting(@PathVariable String token) {
        Optional<SettingDto.Response> res = settingService.getSetting(token);
        if (res.isPresent()) {
            return new SuccessResponse<>(res.get());
        }
        return new FailResponse<>("존재하지 않는 사용자입니다", new EmptyJsonResponse());
    }
}
