package kr.ac.kopo.guestbook2024.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder //초기화
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자를 자동으로 생성
@NoArgsConstructor //기본 생성자를 자동으로 생성
@ToString // 객체를 문자열로 표현할 때 각 필드의 값을 쉽게 확인가능
public class Guestbook extends  BaseEntity {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //이걸 사용하면 자동으로 1씩 증가
    private Long gno;

    @Column(length = 100, nullable = false) //nullable = false 널값을 허용하지 않음
    private String title; //제목

    @Column(length = 1500, nullable = false)
    private String content; //내용

    @Column(length = 100, nullable = false)
    private String writer; //

    public  void changeTitle(String title){
        this.title = title;

    }

    public void changeContent(String content){
        this.content = content;
    }
}
