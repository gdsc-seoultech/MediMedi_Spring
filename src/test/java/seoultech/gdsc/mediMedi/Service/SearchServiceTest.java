package seoultech.gdsc.mediMedi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.mediMedi.MediMediApplicationTests;
import seoultech.gdsc.mediMedi.service.SearchService;

@Transactional
@SpringBootTest
public class SearchServiceTest extends MediMediApplicationTests {

    @Autowired
    private SearchService searchService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void pythonCallTest() {
        String res = searchService.pythonCall("https://medimedi.s3.ap-northeast-2.amazonaws.com/220308_1656_twinklesu914.jpg");
        System.out.println("--test result: " + res);
    }
}
