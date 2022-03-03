package seoultech.gdsc.mediMedi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seoultech.gdsc.mediMedi.dto.HistoryDto;
import seoultech.gdsc.mediMedi.entity.History;
import seoultech.gdsc.mediMedi.entity.User;
import seoultech.gdsc.mediMedi.repository.HistoryRepository;
import seoultech.gdsc.mediMedi.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HistoryService(HistoryRepository historyRepository,
                          UserRepository userRepository,
                          ModelMapper modelMapper){
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<HistoryDto.ListResponse> historyList(String token){
        User user = userRepository.getUserByToken(token);
        List<History> histories = historyRepository.getAllByUserOrderByModifiedAtDesc(user);
        // 매핑
        List<HistoryDto.ListResponse> res = histories.stream().map(history -> {
            HistoryDto.ListResponse resDto = new HistoryDto.ListResponse();
            resDto.setName(history.getMedi().getName());
            resDto.setDate(history.getModifiedAt().format(DateTimeFormatter.ofPattern("M월 d일 HH:mm")));
            return resDto;
        }).collect(Collectors.toList());
        return res;
    }

    /*
    history 상세 조회
     */
    public HistoryDto.DetailResponse historyDetail(int id){
        History history = historyRepository.getById(id);
        HistoryDto.DetailResponse historyDto = modelMapper.map(history.getMedi(), HistoryDto.DetailResponse.class);
        historyDto.setDate(history.getModifiedAt().format(DateTimeFormatter.ofPattern("M월 d일 HH:mm")));
        return historyDto;
    }
}
