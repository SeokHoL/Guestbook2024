package kr.ac.kopo.guestbook2024.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //슈퍼클래스로 사용될거다. 테이블이 만들어지면 안됨
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {

    @CreatedDate //등록될때만 날짜와 시간만 저장됨
    @Column(name = "regdate",updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;



    
}
