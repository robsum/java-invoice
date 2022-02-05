package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    // two implementations
    private Collection<Product> products;
    private Map<Product, Integer> productsMap;

    public Invoice() {
        products = new ArrayList<>();
        productsMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        productsMap.put(product, productsMap.getOrDefault(product, 0) + 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be less or equal to 0.");
        }
        for (int i = 0; i < quantity; ++i) {
            products.add(product);
        }
        productsMap.put(product, productsMap.getOrDefault(product, 0) + quantity);
    }

    public BigDecimal getSubtotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products) {
            sum = sum.add(product.getPrice());
        }
        BigDecimal sumMap = BigDecimal.ZERO;
        for (Product product : productsMap.keySet()) {
            sumMap = sumMap.add(new BigDecimal(productsMap.get(product)).multiply(product.getPrice()));
        }
        return sumMap;
    }

    public BigDecimal getTax() {
        BigDecimal tax = BigDecimal.ZERO;
        for (Product product : products) {
            tax = tax.add(product.getTaxPercent().multiply(product.getPrice()));
        }
        BigDecimal taxMap = BigDecimal.ZERO;
        for (Product product : productsMap.keySet()) {
            taxMap = taxMap.add(new BigDecimal(productsMap.get(product)).multiply(product.getTaxPercent()).multiply(product.getPrice()));
        }
        return taxMap;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : products) {
            total = total.add(product.getPriceWithTax());
        }
        BigDecimal totalMap = BigDecimal.ZERO;
        for (Product product : productsMap.keySet()) {
            totalMap = totalMap.add(new BigDecimal(productsMap.get(product)).multiply(product.getPriceWithTax()));
        }
        return totalMap;
    }
}
