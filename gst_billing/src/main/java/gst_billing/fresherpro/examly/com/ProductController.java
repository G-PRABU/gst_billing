package gst_billing.fresherpro.examly.com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

    private ArrayList<Billing> bills = new ArrayList<>(); 
    private Integer productCount = 1;
    private Double totalBill = 0.00;

	@PostMapping(path="/add")
	public String addProduct(@RequestParam String productName,@RequestParam Double productPrice,
			@RequestParam Integer productGst) {
		Product p = new Product();
		p.setProductName(productName);
		p.setProductPrice(productPrice);
		p.setProductGst(productGst);
		productRepository.save(p);		
		return "redirect:/";
	}
	
    @PostMapping(path="/add/bill/id")
	public String addBill(@RequestParam Integer id,@RequestParam Integer quantity) {
		if(productRepository.findById(id).isPresent()){
            Billing b = new Billing();
            b.setProduct(productRepository.findByProductCode(id));
            b.setQuantity(quantity);
            b.setId(productCount++);
            bills.add(b);
            totalBill += b.getTotal();
        }		
		return "redirect:/billing";
	}
    
    
    @PostMapping(path="/add/bill/name")
	public String addBill(@RequestParam String name,@RequestParam Integer quantity) {
        if(productRepository.findByProductName(name)!=null){
            Billing b = new Billing();
            b.setProduct(productRepository.findByProductName(name));
            b.setQuantity(quantity);
            b.setId(productCount++);
            bills.add(b);
            totalBill += b.getTotal();
        }		
		return "redirect:/billing";
	}

    @RequestMapping("/bill/remove/{id}")
	public String removeBill(@PathVariable Integer id) {
		for(int i=0;i<bills.size();i++){
            if(bills.get(i).getId()==id){
                totalBill -= bills.get(i).getTotal();
                bills.remove(i);
            }
        }
        return "redirect:/billing";
	}

    @RequestMapping("/billing")
    public String billing(Model model){
        model.addAttribute("bills",bills);
        model.addAttribute("total",totalBill);
        return "billing";
    }
    

	@RequestMapping("/{id}")
	public String getProduct(@PathVariable Integer id,Model model) {
		model.addAttribute("product", productRepository.findByProductCode(id));
		return "product";
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Integer id) {
	    productRepository.deleteByProductCode(id);
		return "redirect:/";
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
		return "redirect:/";
	}
	
	@GetMapping(path="/")
	public String getAllProducts(Model model){
		model.addAttribute("products",productRepository.findAll());
		return "products";
	}
}
