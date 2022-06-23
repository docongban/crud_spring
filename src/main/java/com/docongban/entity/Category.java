package com.docongban.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//là 1 đối tượng
@Entity

// tự genernate ra bảng category trong db
@Table(name = "category")
@Data
public class Category {

	
	//là ID khóa chính
	@Id
	//tự tăng
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	
	public Category() {
		
	}
	
	public Category(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
