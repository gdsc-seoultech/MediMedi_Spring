package seoultech.gdsc.mediMedi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seoultech.gdsc.mediMedi.entity.Test;
import seoultech.gdsc.mediMedi.repository.TestRepository;

@Service
public class TestService {
    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public int insertTest(Test test) {
        return testRepository.save(test).getId();
    }

    public Test selectTest(int id) {
        return testRepository.findById(id).get();
    }
}
