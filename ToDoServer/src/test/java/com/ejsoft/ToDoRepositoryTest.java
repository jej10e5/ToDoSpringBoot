package com.ejsoft;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ejsoft.entity.ToDoEntity;
import com.ejsoft.persistency.ToDoRepository;


@SpringBootTest
public class ToDoRepositoryTest {
@Autowired
ToDoRepository toDoRepository;
	//삽입 확인
	//@Test
	public void testToDoInsert(){
		ToDoEntity entity =
		ToDoEntity.builder()
			.userId("orange")
			.title("세번째 데이터")
			.done(false)
			.build();
		//데이터 삽입
		toDoRepository.save(entity);		
	}
	
	
	//@Test
	public void testToDoMany() {
		//테이블의 데이터 전체 가져오기
		/*
		List<ToDoEntity> list = toDoRepository.findAll();
		for(ToDoEntity entity : list) {
			System.out.println(entity);
		}*/
		
		//페이징
		//첫번째는 페이지 번호 0부터 시작
		//두번째는 페이지 당 데이터 개수 2개씩
		//2개씩 1번 페이지
		//Pageable pageable = PageRequest.of(0,2);
		
		//페이징에 정렬까지
		Sort sort = Sort.by("title").descending();
		Pageable pageable = PageRequest.of(0,2,sort);
		Page<ToDoEntity> list = toDoRepository.findAll(pageable);
		for(ToDoEntity entity : list) {
			System.out.println(entity);
		}
	}
	
	//기본키를 이용한 데이터 1개 가져오기
	//@Test
	public void testFindOne() {
		//Optional 은 null이 가능한 자료형
		Optional <ToDoEntity> optional = toDoRepository.findById("40288ab282ab10420182ab104cfa0000");
		
		//데이터가 존재하는 경우
		if(optional.isPresent()) {
			ToDoEntity entity = optional.get();
			System.out.println(entity);
		}
		//데이터가 존재하지 않는 경우
		else {
			System.out.println("데이터가 존재하지 않음");
		}
	}
	
	//데이터 수정 - save 메서드 이용
	//@Test
	public void testModify() {
		//삽입할 데이터 생성
		ToDoEntity entity =
				ToDoEntity.builder()
					.id("40288ab282ab10420182ab104cfa0000")
					.userId("아담")
					.title("수정된 데이터")
					.done(false)
					.build();
		//데이터 수정
		ToDoEntity result =toDoRepository.save(entity);
		System.out.println(result);
				
	}
	
	//데이터 삭제
	//delete(Entity) 와 deleteById(id)
	//@Test
	public void testDelete() {
		toDoRepository.deleteById("40288ab282ab10420182ab104cfa0000");
	}
	
	//id가 아닌 userId로 조회
	//ToDoRepository에 findByUserId 메서드 추가
	//@Test
	public void testNameMethod() {
		List<ToDoEntity> list = toDoRepository.findByUserId("banana");
		System.out.println(list);
	}
}
