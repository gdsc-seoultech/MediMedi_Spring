package seoultech.gdsc.mediMedi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seoultech.gdsc.mediMedi.dto.SearchDto;
import seoultech.gdsc.mediMedi.entity.History;
import seoultech.gdsc.mediMedi.entity.Medi;
import seoultech.gdsc.mediMedi.entity.User;
import seoultech.gdsc.mediMedi.repository.HistoryRepository;
import seoultech.gdsc.mediMedi.repository.MediRepository;
import seoultech.gdsc.mediMedi.repository.UserRepository;
import seoultech.gdsc.mediMedi.response.BasicResponse;
import seoultech.gdsc.mediMedi.response.FailResponse;
import seoultech.gdsc.mediMedi.response.SuccessResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class SearchService {

    private final MediRepository mediRepository;
    private final ModelMapper modelMapper;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Autowired
    public SearchService(MediRepository mediRepository,
                         ModelMapper modelMapper,
                         HistoryRepository historyRepository,
                         UserRepository userRepository) {
        this.mediRepository = mediRepository;
        this.modelMapper = modelMapper;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    /*
    url 찾아서 프로세스 빌더에 전달
    프로세스 빌더에서 text 나오면 db 검색 함수로 전달
    db검색 함수에서 결과 있으면 -> 히스토리 저장함수 호출, success 결과 return
    없으면 fail 결과 return
     */
    public BasicResponse imageSearch(SearchDto.Request req) {
//        String textFromImg = pythonCall(req.getImageUrl());
        String textFromImg = "타이레놀, 아세트아미노펜, 일반의약품, 500밀리그람, 밀리그람, "; //임시 string
        String[] textArr = textFromImg.split(", ");
        HashSet<Medi> searchRes = new HashSet<>();
        for (String text: textArr) {
            if (searchRes.isEmpty()) {
                searchRes.addAll(mediRepository.getAllByNameLike("%"+text+"%"));
            } else {
                HashSet<Medi> semiRes = new HashSet<>();
                for (Medi m: searchRes) {
                    if (m.getName().contains(text)) {
                        semiRes.add(m);
                    }
                }
                if (!semiRes.isEmpty()){
                    searchRes = semiRes;
                }
            }
        }
        // 결과 여러개인 경우 글자수로 가장 짧은 것
        Medi finalRes = new Medi();
        int minLen = 100;
        for (Medi m : searchRes) {
            if (m.getName().length() < minLen) {
                finalRes = m;
                minLen = m.getName().length();
            }
        }
        if (minLen == 100) {
            return new FailResponse<>("해당 약을 찾지 못해, 인식한 글자를 알려드립니다", new SearchDto.FailResponse(textFromImg));
        } else {
            // history
            User user = userRepository.getUserByToken(req.getToken());
            Optional<History> pastHistory = historyRepository.getByUserAndMedi(user, finalRes);
            if (pastHistory.isPresent()) {
                History his = pastHistory.get();
                his.setModifiedAt(LocalDateTime.now());
                historyRepository.save(his);
            } else {
                History his = new History();
                his.setMedi(finalRes);
                his.setUser(user);
                historyRepository.save(his);
            }
            // controller
            SearchDto.SuccessResponse res = modelMapper.map(finalRes, SearchDto.SuccessResponse.class);
            return new SuccessResponse<>(res);
        }
    }

    /*
    프로세스 빌더
     */
    public String pythonCall(String url) {
        String command = "C:\\Users\\twinklesu\\AppData\\Local\\Programs\\Python\\Python38\\python.exe"; //파이썬 path
        String arg1 = "C:\\Users\\twinklesu\\PycharmProjects\\baekjoon_py\\java_connect_test.py"; // 실행 파일 주소
        String text = "";
        try {
            ProcessBuilder builder = new ProcessBuilder(command, arg1); // 프로세스 생성
            Process process = builder.start(); // 프로세스 시작
            OutputStream output = process.getOutputStream(); // cmd에 글 적기 위해 스트림 생성
            output.write(url.getBytes(StandardCharsets.UTF_8)); // cmd통해 값 전달
            output.close(); // 스트림 종료
            int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
            if (exitVal != 0) {
                System.out.println("프로세스 빌더 비정상 종료");
            }
            InputStream input = process.getInputStream(); // cmd에 적힌 글자 읽어옴
            text = new String(input.readAllBytes(), StandardCharsets.UTF_8); // 바이트 -> 스트링
            System.out.println("from ml:" + text); // 자바에 출력(확인)
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return text;
    }
}
