package gst_billing.fresherpro.examly.com;

class Billing {
    private Product product;
    private Integer quantity;
    private Double total;
    private Integer id;

    public void setId(Integer id){
        this.id = id;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
        this.total = ((quantity*product.getProductPrice())/100)*product.getProductGst()+(quantity*product.getProductPrice());
    }

    public Product getProduct(){
        return product;
    }  

    public Integer getQuantity(){
        return quantity;
    }

    public Double getTotal(){
        return total;
    }

    public Integer getId(){
        return id;
    }
}