package seoultech.gdsc.mediMedi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.mediMedi.dto.UserDto;
import seoultech.gdsc.mediMedi.response.BasicResponse;
import seoultech.gdsc.mediMedi.response.SuccessResponse;
import seoultech.gdsc.mediMedi.serializer.EmptyJsonResponse;
import seoultech.gdsc.mediMedi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public BasicResponse postUser(@RequestBody UserDto.Join req) {
        userService.join(req);
        return new SuccessResponse<>(new EmptyJsonResponse());
    }

    @DeleteMapping("")
    public BasicResponse deleteUser(@RequestBody UserDto.Withdraw wd) {
        userService.withdraw(wd);
        return new SuccessResponse<>(new EmptyJsonResponse());
    }

    @GetMapping("/{token}")
    public BasicResponse isJoined(@PathVariable String token) {
        Boolean result = userService.checkJoined(token);
        UserDto.IsJoined res = new UserDto.IsJoined();
        res.setIsJoined(result);
        return new SuccessResponse<>(res);
    }


}
