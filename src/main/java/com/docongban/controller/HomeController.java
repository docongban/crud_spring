package com.docongban.controller;

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

	//dẫn đến trang home
	@GetMapping("")
	public String viewHome() {
		
		//trả về trang index
		return "index";
	}

	//dẫn đến trang view
	@GetMapping("/view")
	public String viewProduct(Model model) {

		//lấy tất cả sản phẩm có status=0
		List<Product> products = productRepository.getAllByStatus0();

		//add vào addtribute để đẩy lên html
		model.addAttribute("products", products);

		return "view";
	}

	//dẫn đến trang insert
	@GetMapping("/insert")
	public String viewInsert(Model model) {

		// lấy tất cả category trong DB
		List<Category> categories = categoryRepository.findAll();

		// add vào addtribute để hiển thị trong select option
		model.addAttribute("categories", categories);

		return "insert";
	}

	// thực hiện hành động insert vào DB
	@PostMapping("/insert")
	public String handleInsert(Model model, @ModelAttribute("product") Product product) {

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		//check mã sản phẩm đã tồn tại chưa
		Optional<Product> optionalProduct = productRepository.findById(product.getId());
		String productString = optionalProduct.toString();
		if (!productString.equals("Optional.empty")) {
			model.addAttribute("error", "Trùng mã sản phẩm");
			return "insert";
		}

		// nếu chưa tồn tại thì set status mặc định là 0
		product.setStatus(0);
		
		// lưu vào db
		productRepository.save(product);

		// trả ngược về trang view
		return "redirect:/view";
	}

	
	//anotation để thực hiện hành vi thay đổi record trong db
	@Transactional
	//thực hiện update status =1
	@GetMapping("/updateStatus/{id}")
	public String handleUpdateStatus(Model model, @PathVariable String id) {

		// thực hiện update
		productRepository.handleUpdateStatus(id);

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		return "view";
	}

	//lấy ra chi tiết 1 sản phẩm theo id để thực hiện update
	@GetMapping("/updateProduct/{id}")
	public String getproductUpdate(Model model, @PathVariable String id) {

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		Product product = productRepository.findById(id).get();

		model.addAttribute("product", product);

		return "insert";
	}
	
	// thực hiện hành vi update
	@PostMapping("/update")
	public String handleUpdate(Model model, @ModelAttribute("product") Product product) {

		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		productRepository.save(product);

		return "redirect:/view";
	}
	
	//lấy ra chi tiết sản phẩm để delete theo id
	@GetMapping("/deleteProduct/{id}")
	public String getproductDelete(Model model, @PathVariable String id) {

		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("categories", categories);

		Product product = productRepository.findById(id).get();

		model.addAttribute("product", product);
		model.addAttribute("categoryName", categoryRepository.findById(product.getCategoryId()).get().getTitle());

		return "delete";
	}
	
	// thực hiện hành vi delete theo id
	@GetMapping("/deletePro/{id}")
	public String handleDeleteProduct(Model model, @PathVariable String id) {
		
		List<Product> products = productRepository.getAllByStatus0();

		model.addAttribute("products", products);
		
		productRepository.deleteById(id);
		
		return "redirect:/view";
	}
}
