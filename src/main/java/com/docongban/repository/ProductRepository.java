package com.docongban.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.docongban.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Query(value = "select * from product where status=0", nativeQuery = true)
	List<Product> getAllByStatus0();
	
	@Modifying
	@Query(value = "update product set status=1 where id=?1", nativeQuery = true)
	void handleUpdateStatus(String id);
}
