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

    public String insertTest(Test test) {
        testRepository.save(test);
        return "success";
    }

    public Test selectTest(int id) {
        Test test = testRepository.getById(id);
        return test;
    }
}
