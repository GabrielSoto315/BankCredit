package com.Bank.BankCredit.Service.Implements;

import com.Bank.BankCredit.Models.Entities.Response.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public Mono<Product> FindProduct(Product product){
        String url = "http://localhost:18085/api/Products/";
        Flux<Product> oProductFlux = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Product.class)
                .filter(x -> x.getName().equals(product.getName()) && x.getClientType().equals(product.getClientType()));
        oProductFlux.subscribe(products -> log.info(products.toString()));
        return oProductFlux.hasElements().flatMap(isAvailable -> {
            if (isAvailable){
                Mono<Product> oProductMono = oProductFlux.single();
                oProductMono.subscribe(products -> log.info(products.toString()));
                return oProductMono;
            }
            else {
                return Mono.empty();
            }
        });
    }
}
