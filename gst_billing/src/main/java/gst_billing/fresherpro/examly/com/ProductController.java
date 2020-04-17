package gst_billing.fresherpro.examly.com;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@RequestMapping(path="/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping(path="/add")
	public String addProduct(@RequestParam String productName,@RequestParam Double productPrice,
			@RequestParam Integer productGst) {
		Product p = new Product();
		p.setProductName(productName);
		p.setProductPrice(productPrice);
		p.setProductGst(productGst);
		productRepository.save(p);		
		return "redirect:/product/all";
	}
	
	@RequestMapping("/{id}")
	public String getProduct(@PathVariable Integer id,Model model) {
		model.addAttribute("product", productRepository.findByProductCode(id));
		return "product";
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Integer id) {
	    productRepository.deleteByProductCode(id);
		return "redirect:/product/all";
	}
	
	@PostMapping(path="/update")
	public String updateProduct(@RequestParam Integer productCode,@RequestParam String productName,@RequestParam Double productPrice,
			@RequestParam Integer productGst) {
		Product p = new Product();
		p.setProductCode(productCode);
		p.setProductName(productName);
		p.setProductGst(productGst);
		p.setProductPrice(productPrice);
		productRepository.save(p);
		return "redirect:/product/all";
	}
	
	@GetMapping(path="/all")
	public String getAllProducts(Model model){
		model.addAttribute("products",productRepository.findAll());
		return "products";
	}

}
