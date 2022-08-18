package com.ejsoft.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejsoft.entity.ToDoEntity;
import com.ejsoft.persistency.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//controller는 service를 주입받고
@Service
//final로 선언된 인스턴스 속성을 외부로 주입받도록 해주는 어노테이션
@RequiredArgsConstructor
//로그 기록을 위한 객체 주입 - logger가 자동 주입
@Slf4j
public class ToDoServiceImpl implements ToDoService{
	//서비스는 repository를 주입받음
	private final ToDoRepository toDoRepository;
	
	//유효성 검사를 위한 메서드
	private void validate(final ToDoEntity entity) {
		if(entity==null) {
			log.warn("Entity는 null 알 수 없음");
			throw new RuntimeException("Entity cannot be null");
		}
		
		if(entity.getUserId()==null) {
			log.warn("알 수 없는 사용자");
			throw new RuntimeException("Unknown User");
		}
	}
	
	@Override
	public List<ToDoEntity> create(ToDoEntity entity) {
		//유효성 검사
		validate(entity);
		//데이터 삽입
		toDoRepository.save(entity);
		//로그 출력
		log.info("Entity Id:{} is saved", entity.getId());
		//userId에 해당하는 전체 데이터 리턴
		return toDoRepository.findByUserId(entity.getUserId());
	}

	@Override
	public List<ToDoEntity> retrieve(String userId) {
		//userId에 해당하는 전체 데이터 리턴
		return toDoRepository.findByUserId(userId);
	}

	@Override
	public List<ToDoEntity> update(ToDoEntity entity) {
		//유효성 검사
		validate(entity);
		//데이터의 존재 여부를 확인 - 수정하기 전에 삭제됐을 수도 있으니까
		final Optional<ToDoEntity> original = toDoRepository.findById(entity.getId());
		//데이터가 존재하는 경우에만 작업 수행
		original.ifPresent(
				todo -> {
					todo.setTitle(entity.getTitle());
					todo.setDone(entity.isDone());
					toDoRepository.save(todo);
				}
		);
		//userId에 해당하는 전체 데이터 리턴
		return retrieve(entity.getUserId());
	}

	@Override
	public List<ToDoEntity> delete(ToDoEntity entity) {
		//유효성 검사
		validate(entity);
		try {
			toDoRepository.delete(entity);
		}catch(Exception e) { //데이터가 없는데 삭제하라고 한 경우 예외 발생
			log.error("삭제 실패");
		}
		return retrieve(entity.getUserId());
	}
	
}
