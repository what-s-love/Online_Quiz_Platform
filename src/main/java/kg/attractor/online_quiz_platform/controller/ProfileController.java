package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.UserCreateDto;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @PostMapping("registration")
    public HttpStatus createUser(@RequestBody @Valid UserCreateDto userDto) {
        userService.createUser(userDto);
        return HttpStatus.OK;
    }
}
