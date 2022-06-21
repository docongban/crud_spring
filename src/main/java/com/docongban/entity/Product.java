package com.docongban.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	private String id;
	private String name;
	private double price;
	private String description;
	private int categoryId;
	private int status;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	

	public Product(String id, String name, double price, String description, int categoryId, int status) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.categoryId = categoryId;
		this.status = status;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
