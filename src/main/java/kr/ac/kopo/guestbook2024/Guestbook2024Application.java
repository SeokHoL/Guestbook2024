package kr.ac.kopo.guestbook2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //날짜와 시간을 자동으로 추가할려면  이걸 추가해야됨. 반드시!
public class Guestbook2024Application {

    public static void main(String[] args) {
        SpringApplication.run(Guestbook2024Application.class, args);
    }

}
