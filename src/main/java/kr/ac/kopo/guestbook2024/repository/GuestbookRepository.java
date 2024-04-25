package kr.ac.kopo.guestbook2024.repository;


import kr.ac.kopo.guestbook2024.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook,Long>, QuerydslPredicateExecutor<Guestbook> {
    // JpaRepository<Guestbook,Long> jpa레파지토리는 반드시 상속 , <> 안에는 entity클래스명, @id 자료형
}
