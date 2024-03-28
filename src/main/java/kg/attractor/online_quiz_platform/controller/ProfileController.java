package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
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
    @PostMapping("registration")
    public HttpStatus createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUserAndReturnId(userDto);
        return HttpStatus.OK;
    }
}
