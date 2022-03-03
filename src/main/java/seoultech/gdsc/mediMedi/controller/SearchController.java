package seoultech.gdsc.mediMedi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.mediMedi.dto.SearchDto;
import seoultech.gdsc.mediMedi.response.BasicResponse;
import seoultech.gdsc.mediMedi.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /*
    약 검색
     */
    @PostMapping("")
    public BasicResponse getSearch(@RequestBody SearchDto.Request req) {
        BasicResponse res = searchService.imageSearch(req);
        return res;
    }

}
