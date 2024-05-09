package kr.ac.kopo.guestbook2024.repository; // repository 저장소

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook2024.entity.Guestbook;
import kr.ac.kopo.guestbook2024.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTest {
    
    @Autowired //자동주입
    private  GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i->{   // IntStream.rangeClosed 정수 스트림을 생성 , forEach 메서드를 사용하여 각 숫자에 대해 람다 표현식을 실행
            Guestbook guestbook = Guestbook.builder()
                    .title("Title====" + i)
                    .content("content====" + i)
                    .writer("writer " + (i%10+1))
                    .build();
            guestbookRepository.save(guestbook);  //엔티티는 데이터베이스와 연결해주는것. save는 insert와 update 둘다 해줌
        });

    }
     //더미 데이터 삭제 메서드
//    @Test
//    public void deleteAllDummies() {
//        guestbookRepository.deleteAll(); // 모든 Guestbook 엔티티를 삭제합니다.
//    }

    @Test
    public void updateTest(){
       Optional<Guestbook> result =  guestbookRepository.findById(300L);

       if(result.isPresent()){
           Guestbook guestbook =  result.get();

           guestbook.changeTitle("Changed Title ....");   //Guestbook 엔티티에 있는  changeTitle 메소드를 호출 //엔티티는 데이터베이스와 연결객체라고 생각하면됨 //엔티티클래스에 필드,메서드 정의함
           guestbook.changeContent("Changed Content....");  //Guestbook 엔티티에 있는  changeContent 메소드를 호출

           guestbookRepository.save(guestbook); //레파지토리는 저장소. 데이터베이스 저장소에 저장
       }
    }
    
    //단일항목검색
    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword ="1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exp1 = qGuestbook.title.contains(keyword);
        builder.and(exp1);
        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    //다중항목검색 및 제한조건식
    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword ="2";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exp1 = qGuestbook.title.contains(keyword);
        BooleanExpression exp2 = qGuestbook.content.contains(keyword);
        BooleanExpression exp = exp1.or(exp2);
        builder.and(exp);
        builder.and(qGuestbook.gno.gt(100L));
        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }





    

}
