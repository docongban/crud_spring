package com.docongban.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.docongban.entity.Category;
import com.docongban.entity.Product;
import com.docongban.repository.CategoryRepository;
import com.docongban.repository.ProductRepository;

@Controller
public class HomeController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@GetMapping("")
	public String viewHome() {
		return "index";
	}

	@GetMapping("/view")
	public String viewProduct(Model model) {

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		return "view";
	}

	@GetMapping("/insert")
	public String viewInsert(Model model) {

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		return "insert";
	}

	@PostMapping("/insert")
	public String handleInsert(Model model, @ModelAttribute("product") Product product) {

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		Optional<Product> optionalProduct = productRepository.findById(product.getId());
		String productString = optionalProduct.toString();
		if (!productString.equals("Optional.empty")) {
			model.addAttribute("error", "Trùng mã sản phẩm");
			return "insert";
		}

		product.setStatus(0);
		productRepository.save(product);

		return "redirect:/view";
	}

	@Transactional
	@GetMapping("/updateStatus/{id}")
	public String handleUpdateStatus(Model model, @PathVariable String id) {

		productRepository.handleUpdateStatus(id);

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		return "view";
	}

	@GetMapping("/updateProduct/{id}")
	public String getproductUpdate(Model model, @PathVariable String id) {

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		Product product = productRepository.findById(id).get();

		model.addAttribute("product", product);

		return "insert";
	}
	
	@PostMapping("/update")
	public String handleUpdate(Model model, @ModelAttribute("product") Product product) {

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		productRepository.save(product);

		return "redirect:/view";
	}
	
	@GetMapping("/deleteProduct/{id}")
	public String getproductDelete(Model model, @PathVariable String id) {

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		Product product = productRepository.findById(id).get();

		model.addAttribute("product", product);
		model.addAttribute("categoryName", categoryRepository.findById(product.getCategoryId()).get().getTitle());

		return "delete";
	}
	
	@GetMapping("/deletePro/{id}")
	public String handleDeleteProduct(Model model, @PathVariable String id) {
		
		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);
		
		productRepository.deleteById(id);
		
		return "redirect:/view";
	}
}
