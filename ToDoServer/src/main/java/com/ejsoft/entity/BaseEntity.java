package com.ejsoft.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

//테이블을 직접 생성하지 않고 다른 테이블의 공통된 속성만 제공
@MappedSuperclass
//JPA 감시 옵션이 적용되어야 한다.
@EntityListeners(value= {AuditingEntityListener.class})
//속성을 만들면 getter 메서드를 자동 생성
@Getter
abstract public class BaseEntity {
	//생성된 시간을 이용
	@CreatedDate
	//컬럼 이름은 regdate로 하고 수정은 못함
	//이 설정이 없으면 속성이름이 컬럼 이름이 됨
	@Column(name="regdate", updatable=false)
	//속성 설정
	private LocalDateTime regDate;
	
	//마지막 수정 시간을 기록
	@LastModifiedDate
	//update해야하므로 updatable=false 제거
	@Column(name="moddate")
	private LocalDateTime modDate;
}
