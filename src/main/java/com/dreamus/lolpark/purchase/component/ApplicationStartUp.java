package com.dreamus.lolpark.purchase.component;

import com.dreamus.lolpark.purchase.domain.Product;
import com.dreamus.lolpark.purchase.domain.Purchase;
import com.dreamus.lolpark.purchase.domain.User;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartUp implements CommandLineRunner {

    private final ResourceLoader resourceLoader;

    private final EntityManager entityManager;

    private final String INSERT_PRODUCT_SQL = "insert into product(id, name, price, is_del, updated_at, created_at) " +
        "values (?, ?, ?, ?, ?, ?) ";

    private final String INSERT_USER_SQL = "insert into user(id, name, created_at, updated_at) " +
        "values (?, ?, ?, ?) ";

    private final String INSERT_PURCHASE_SQL = "insert into purchase(id, user_id, product_id, price, updated_at, created_at) " +
        "values (?, ?, ?, ?, ?, ?) ";

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        List<User> users = readLine(getCsvReader("user.csv"), this::convertUser);
        List<Product> products = readLine(getCsvReader("product.csv"), this::convertProduct);

        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Integer, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        List<Purchase> purchases = readLine(getCsvReader("purchase.csv"), (line) -> Purchase.builder()
            .id(Integer.valueOf(line[0].trim()))
            .user(userMap.get(Integer.valueOf(line[1].trim())))
            .product(productMap.get(Integer.valueOf(line[2].trim())))
            .price(Integer.valueOf(line[3].trim()))
            .build()
        );

        LocalDateTime now = LocalDateTime.now();

        if (users.size() > 0) {
            for (User user : users) {
                entityManager.createNativeQuery(INSERT_USER_SQL)
                    .setParameter(1, user.getId())
                    .setParameter(2, user.getName())
                    .setParameter(3, now)
                    .setParameter(4, now)
                    .executeUpdate();
            }
        }

        if (products.size() > 0) {
            for (Product product : products) {
                entityManager.createNativeQuery(INSERT_PRODUCT_SQL)
                    .setParameter(1, product.getId())
                    .setParameter(2, product.getName())
                    .setParameter(3, product.getPrice())
                    .setParameter(4, product.getIsDel())
                    .setParameter(5, now)
                    .setParameter(6, now)
                    .executeUpdate();
            }
        }

        if (purchases.size() > 0) {
            for (Purchase purchase : purchases) {
                entityManager.createNativeQuery(INSERT_PURCHASE_SQL)
                    .setParameter(1, purchase.getId())
                    .setParameter(2, purchase.getUser().getId())
                    .setParameter(3, purchase.getProduct().getId())
                    .setParameter(4, purchase.getPrice())
                    .setParameter(5, now)
                    .setParameter(6, now)
                    .executeUpdate();
            }
        }
    }

    private CSVReader getCsvReader(String fileName) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(resourceLoader.getResource("classpath:" + fileName).getInputStream(), "UTF-8");
        return new CSVReader(inputStreamReader);
    }

    private <T> List<T> readLine(CSVReader csvReader, Function<String[], T> function) throws Exception {
        List<T> result = new ArrayList<>();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            result.add(function.apply(line));
        }
        return result;
    }

    private User convertUser(String[] line) {
        return User.builder()
            .id(Integer.valueOf(line[0].trim()))
            .name(line[1])
            .build();
    }

    private Product convertProduct(String[] line) {
        return Product.builder()
            .id(Integer.valueOf(line[0].trim()))
            .name(line[1])
            .price(Integer.valueOf(line[2].trim()))
            .build();
    }
}
