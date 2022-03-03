package seoultech.gdsc.mediMedi.Repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.mediMedi.MediMediApplicationTests;
import seoultech.gdsc.mediMedi.entity.Medi;
import seoultech.gdsc.mediMedi.repository.MediRepository;

import java.util.HashSet;
import java.util.List;


@Transactional
@SpringBootTest
public class MediRepositoryTest extends MediMediApplicationTests {

    @Autowired
    private MediRepository mediRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void getAllByNameTest() throws JsonProcessingException {
        HashSet<Medi> res = mediRepository.getAllByNameLike("%타이레놀%");
        System.out.println(objectMapper.writeValueAsString(res));
    }




}
