package seoultech.gdsc.mediMedi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seoultech.gdsc.mediMedi.dto.HistoryDto;
import seoultech.gdsc.mediMedi.response.BasicResponse;
import seoultech.gdsc.mediMedi.response.SuccessResponse;
import seoultech.gdsc.mediMedi.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /*
    history 목록 조회
     */
    @GetMapping("/list/{token}")
    public BasicResponse getHistory(@PathVariable String token) {
        List<HistoryDto.ListResponse> res = historyService.historyList(token);
        return new SuccessResponse<>(res);
    }

    /*
    history 세부 조회
     */
    @GetMapping("/detail/{id}")
    public BasicResponse getDetailHistory(@PathVariable int id) {
        HistoryDto.DetailResponse res = historyService.historyDetail(id);
        return new SuccessResponse<>(res);
    }
}
