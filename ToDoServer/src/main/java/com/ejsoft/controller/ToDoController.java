package com.ejsoft.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejsoft.dto.ResponseDTO;
import com.ejsoft.dto.ToDoDTO;
import com.ejsoft.entity.ToDoEntity;
import com.ejsoft.service.ToDoService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("todo") //이제 모든 url에 /todo가 붙음
@Slf4j
public class ToDoController {
	@Autowired
	private ToDoService toDoService;
	
	//데이터 삽입 요청
	//localhost/todo 를 post 방식으로 요청 했을 때 처리
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody ToDoDTO dto){
		try {
			//임시 유저 아이디 생성
			String temporaryUseId = "temporary-user";
			//삽입할 entity 생성
			ToDoEntity entity = ToDoDTO.toEntity(dto);
			//아이디 설정
			entity.setId(null);
			entity.setUserId(temporaryUseId);
			
			//삽입
			List<ToDoEntity> entities = toDoService.create(entity);
			log.error(entities.toString());
			//응답을 만들기 위해서 ToDoEntity의 List를 
			//ToDoDTO의 List로 변환
			//이 문법(람다와 스트림)이 jdk 1.8 부터 지원
			List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
			log.error(dtos.toString());
			//응답 객체를 생성
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
			log.error(response.toString());
			//REST 응답 객체로 만들기
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			String error = e.getLocalizedMessage();
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
		
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId="temporary-user";
		//서비스를 실행해서 조건에 맞는 Entity 가져오기
		List<ToDoEntity> entities = toDoService.retrieve(temporaryUserId);
		//Entity를 DTO로 변환
		List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//응답 데이터 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	//수정
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody ToDoDTO dto){
		String temporaryUserId = "temporary-user";
		ToDoEntity entity = ToDoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		List<ToDoEntity> entities = toDoService.update(entity);
		//Entity를 DTO로 변환
		List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//응답 데이터 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	//삭제
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody ToDoDTO dto){
		//넘겨 받은 DTO를 가지고 Entity를 생성
		ToDoEntity entity = ToDoDTO.toEntity(dto);
		entity.setUserId("temporary-user");
		//삭제 작업 수행
		List<ToDoEntity> entities = toDoService.delete(entity);
		//Entity를 DTO로 변환
		List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//응답 데이터 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	
}
