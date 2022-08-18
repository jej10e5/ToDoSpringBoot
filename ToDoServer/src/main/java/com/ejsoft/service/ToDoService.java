package com.ejsoft.service;

import java.util.List;

import com.ejsoft.entity.ToDoEntity;

public interface ToDoService {
	
	//데이터 삽입
	public List<ToDoEntity> create(final ToDoEntity entity);
	
	//데이터 조회
	public List<ToDoEntity> retrieve(final String userId);
	
	//데이터 수정
	public List<ToDoEntity> update(final ToDoEntity entity);
	
	//데이터 삭제
	public List<ToDoEntity> delete(final ToDoEntity entity);
	
}
