package kr.ac.kopo.guestbook2024.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook2024.dto.GuestbookDTO;
import kr.ac.kopo.guestbook2024.dto.PageRequestDTO;
import kr.ac.kopo.guestbook2024.dto.PageResultDTO;
import kr.ac.kopo.guestbook2024.entity.Guestbook;
import kr.ac.kopo.guestbook2024.entity.QGuestbook;
import kr.ac.kopo.guestbook2024.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    @Override
    public Long register(GuestbookDTO dto) {

        Guestbook entity = dtoToEntity(dto);
        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        //검색기능 추가
        BooleanBuilder booleanBuilder  = getSearch(requestDTO); /*where절의 조건식*/
        Page<Guestbook> result = repository.findAll(booleanBuilder,pageable); /*조건식이 포함된 select문*/
        Function<Guestbook, GuestbookDTO> fn = (entity ->entityToDto(entity));
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
      Optional<Guestbook> result =repository.findById(gno); //주어진 ID를 이용해 데이터베이스에서 해당 엔티티를 찾습니다. //gno는 검색할 엔티티의 ID 값입니다

        return result.isPresent()? entityToDto(result.get()) : null; //받는게 하나라 삼항연사자 사용.
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity = result.get();  //get()을 통해 Optional 포장을 벗김
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);  //제목과 내용을 바꾸고 레파지토리에 다시 업데이트 함.

        }


    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanExpression booleanExpression =qGuestbook.gno.gt(0L);

        booleanBuilder.and(booleanExpression);

        //화면에서 검색 조건을 선택하지 않은 경우 (검색 타입=null 및 검색어는 입력이 안된경우)
        if(type == null || keyword.trim().length()==0 || type.trim().length()==0){ //trim() 메서드는 문자열에서 앞뒤에 있는 공백 문자를 제거
            return booleanBuilder;
        }

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){ //contains-> t가 포함되는경우~
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){ //contains-> c가 포함되는경우~
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){ //contains-> w가 포함되는경우~
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);



        return booleanBuilder;
    }
}
