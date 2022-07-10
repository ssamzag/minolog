package com.minolog.api;

import com.minolog.api.member.dto.MemberRequest;
import com.minolog.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class MinoApplication implements CommandLineRunner {
    private final MemberService memberService;

    public static void main(String[] args) {
        SpringApplication.run(MinoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        memberService.save(MemberRequest.builder().userId("ssamzag").password("1234").nickName("페이커센빠이").build());
    }


    static class login {


    }

}
