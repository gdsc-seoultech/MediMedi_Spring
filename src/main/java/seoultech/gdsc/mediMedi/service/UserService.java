package seoultech.gdsc.mediMedi.service;

import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seoultech.gdsc.mediMedi.dto.UserDto;
import seoultech.gdsc.mediMedi.entity.User;
import seoultech.gdsc.mediMedi.repository.UserRepository;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /*
    회원가입
     */
    public void join(UserDto.Join req) {
        User newUser = modelMapper.map(req, User.class);
        userRepository.save(newUser);
    }

    /*
    회원 탈퇴
     */
    public void withdraw(UserDto.Withdraw wd) {
        User targetUser = userRepository.getUserByToken(wd.getToken());
        LocalDateTime date = LocalDateTime.now();
        targetUser.setWithdrewAt(date);
        userRepository.save(targetUser);
    }

    /*
    join checking
     */
    public Boolean checkJoined(String token) {
        User user = userRepository.getUserByToken(token);
        return user != null;
    }
}
