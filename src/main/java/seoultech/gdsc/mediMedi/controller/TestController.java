package seoultech.gdsc.mediMedi.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.mediMedi.entity.Test;
import seoultech.gdsc.mediMedi.service.TestService;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class IntId{
        private int id;
    }

    @PostMapping("")
    public String postTest(@RequestBody() Test test){
        return testService.insertTest(test);
    }

    @GetMapping("")
    public String getTest(@RequestBody() IntId intId) {
        Test res = testService.selectTest(intId.getId());
        return "name: " + res.getName() + ", email: " + res.getEmail();
    }
}
