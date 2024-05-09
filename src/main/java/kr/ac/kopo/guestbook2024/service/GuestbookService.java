package kr.ac.kopo.guestbook2024.service;

import kr.ac.kopo.guestbook2024.dto.GuestbookDTO;
import kr.ac.kopo.guestbook2024.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO dto); //명세(영수증처럼)만 해놓은거임. 여기서 구현x
    
    default Guestbook dtoToEntity(GuestbookDTO dto){      //매개변수 dto를 받아서 Entity로 바꾼다

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    } 

}
