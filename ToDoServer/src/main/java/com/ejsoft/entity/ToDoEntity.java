package com.ejsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//빌더 패턴들 적용
//생성자를 이용해서 인스턴스를 생성하지 않고
//build()를 이용해서 인스턴스를 생성하기 위해서 사용
@Builder
//getter setter toString 자동 생성
//이 어노테이션을 이용할 때 EqaulsAndHashCode를 추가하지 않으면 경고발생
@Data
//Equals는 해시 코드를 비교해서 동일성 여부를 판단하는 메서드
@EqualsAndHashCode(callSuper=false)

//매개변수가 없는 생성자(Default Constructor)를 생성
@NoArgsConstructor
//모든 속성을 매개변수로 갖는 생성자
@AllArgsConstructor

//관계형 데이터베이스와 매핑되는 클래스
@Entity
//테이블 이름 설정
@Table(name="todo")
public class ToDoEntity extends BaseEntity{
	//기본키로 생성
	@Id
	//키값을 자동으로 생성하는 옵션
	//system-uuid는 프로그램이 자동생성해주는 유일 무이한 값
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	//AUTO : 데이터베이스 종류에 따라 JPA가 결정
	//Oracle 이면 Sequence 다른 종류면 auto)increment
	
	//IDENTITY : auto_increment
	//SEQUENCE : sequence
	
	//TABLE : 기본키를 위한 별도의 테이블을 생성
	private String id;
	
	@Column(length=100, nullable=false)
	private String userId;
	
	//nullable을 안주면 알아서 null을 포함
	@Column(length=500,nullable=false)
	private String title;
	
	@Column(nullable=false)
	private boolean done;
}


















