package seoultech.gdsc.mediMedi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seoultech.gdsc.mediMedi.dto.SettingDto;
import seoultech.gdsc.mediMedi.entity.User;
import seoultech.gdsc.mediMedi.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class SettingService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SettingService(UserRepository userRepository,
                          ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /*
    설정 정보 조회
     */
    public Optional<SettingDto.Response> getSetting(String token) {
        Optional<User> findUser =  userRepository.findUserByToken(token);
        if (findUser.isPresent()) {
            SettingDto.Response res = modelMapper.map(findUser.get(), SettingDto.Response.class);
            return Optional.of(res);
        } else {
            return Optional.empty();
        }
    }

    /*
    설정 정보 수정
     */
//    public String updateSetting(SettingDto.Request req) {
//        Optional<User> findUser = userRepository.findUserByToken(req.getToken());
//        if (findUser.isPresent()) {
//            if (req.getEmail() != ) {
//
//            }
//            // email 중복 확인
//
//        } else {
//            return "존재하지 않는 사용자입니다";
//        }
//    }
}
