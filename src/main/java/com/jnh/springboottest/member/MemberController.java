package com.jnh.springboottest.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/register")
    public String showRegister(RegisterForm registerForm){
        return "member_create";
    }

    @ToString
    @Getter
    @Setter
    public static class RegisterForm{
        @Size(min=3, max= 25)
        @NotBlank(message = "유저 ID는 필수 항목입니다.")
        private String username;

        @NotBlank(message = "패스워드는 필수 항목입니다.")
        private String password;

        @NotBlank(message = "패스워드 확인은 필수 항목입니다.")
        private String passwordCheck;

        @NotBlank(message = "닉네임은  필수 항목입니다.")
        private String nick;
    }

    @PostMapping("/register")
    public String register(@Valid RegisterForm registerForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "member_create";
        }

        if (!registerForm.getPassword().equals(registerForm.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            return "member_create";
        }

        Member opMember = memberService.findByUsername(registerForm.getUsername());

        try {
            memberService.create(registerForm.getUsername(), registerForm.getPassword(), registerForm.getNick());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.rejectValue("username", "checkedMemberByUsername", "이미 가입된 회원입니다.");
            return "member_create";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("registerError", e.getMessage());
            return "member_create";
        }


        return "redirect:/";
    }


    @Getter
    @Setter
    public static class LoginForm{
        @Size(min=3, max= 25)
        @NotBlank(message = "ID를 입력해주세요.")
        private String username;

        @NotBlank(message = "패스워드를 입력해주세요.")
        private String password;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "member_login";
    }

}
