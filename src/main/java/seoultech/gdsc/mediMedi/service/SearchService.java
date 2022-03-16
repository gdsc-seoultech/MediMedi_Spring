package seoultech.gdsc.mediMedi.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

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
        String textFromImg = pythonCall(req.getImageUrl());
        String[] textArr = textFromImg.split(", ");
        HashSet<Medi> searchRes = new HashSet<>();
        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("연고");
        stopWords.add("안약");


        for (String text: textArr) {
            if (stopWords.contains(text)) {
                continue;
            }
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

        String command = "C:\\Users\\twinklesu\\AppData\\Local\\Programs\\Python\\Python38\\python.exe "; //파이썬 path
        String args1 = "C:\\medi\\MediMedi_DL\\detect.py"; // 실행 파일 주소

        String[] splitUrl = url.split("/");
        String dir = splitUrl[splitUrl.length-1].split("\\.")[0];

        try {
            ProcessBuilder builder = new ProcessBuilder(command, args1, "--source", url); // 프로세스 생성
            System.out.println("begin first python execution");
            builder.redirectErrorStream(true);
            builder.directory(new File("C:\\medi\\MediMedi_DL"));
            Process process = builder.start(); // 프로세스 시작
            Reader reader = new InputStreamReader(process.getInputStream());
            int ch;
            while ((ch = reader.read()) != -1)
                System.out.print((char) ch); // error output stream 출력
            reader.close();
            int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
            if (exitVal != 0) {
                System.out.println("프로세스 빌더 비정상 종료");
            }
            System.out.println("end first python execution"); // 자바에 출력(확인)
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }

        String arg2 = "C:\\medi\\MediMedi_DL\\recogn.py";
        String text = "";
        try {
            ProcessBuilder builder = new ProcessBuilder(command, arg2, "--source", url); // 프로세스 생성
            System.out.println("begin second python execution");
            builder.directory(new File("C:\\medi\\MediMedi_DL"));
            Process process = builder.inheritIO().start(); // 프로세스 시작
            int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
            if (exitVal != 0) {
                System.out.println("프로세스 빌더 비정상 종료");
            }
            System.out.println("end second python execution");

        } catch (IOException | InterruptedException e1) {
             e1.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("C:\\medi\\MediMedi_DL\\download\\" + dir + "\\result.txt")
            );
            String str;
            while ((str = reader.readLine()) != null) {
                text = str;
            }
            reader.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }


        return text;
    }
}
