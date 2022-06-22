package ru.edu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.edu.error.CustomException;
import ru.edu.error.Errors;
import ru.edu.service.ProductInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {

    private Connection connection;


    @Autowired
    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public List<ProductInfo> getAll() {

        List<ProductInfo> products = new ArrayList<ProductInfo>();
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id, name, composition, price, categoryId, weight, EI, kol FROM Product");

            return sort(products);

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private ProductInfo extractFromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String composition = resultSet.getString("composition");
        double price = resultSet.getDouble("price");
        String categoryId = resultSet.getString("categoryId");
        double weight = resultSet.getDouble("weight");
        String EI = resultSet.getString("EI");
        int kol = resultSet.getInt("kol");

        return new ProductInfo(id, name, composition, price, categoryId, weight, EI, kol);
    }

    private List<ProductInfo> sort(List<ProductInfo> products) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Product ORDER BY name")) {
            while (resultSet.next()) {
                ProductInfo product = extractFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public ProductInfo getProduct(String itemId) {
        try (PreparedStatement statement = connection.prepareStatement("select * from Product where id = ?")) {
            statement.setString(1, itemId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return extractFromResultSet(resultSet);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to .getProduct id=" + itemId + " error=" + ex.toString(), ex);
        }
    }



    public ProductInfo create(ProductInfo info) {
        try (Statement statement = connection.createStatement()) {
            //Создаем формат запроса -->  Присваеваем вместо %s параметры продукта --> Возвращаем метод поиска продукта по Id
            String script = String.format("insert into product ('id', 'name', 'composition', 'price', 'categoryId' , 'weight', 'EI', 'kol')" +
                            "VALUES " +
                            "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s') ",
                    info.getId(), info.getName(), info.getComposition(),
                    info.getPrice(), info.getCategoryId(), info.getWeight(),
                    info.getEI(), info.getKol());
            int updatedRows = statement.executeUpdate(script);
            System.out.println(".create completed updatedRows=" + updatedRows);

            return getProduct(info.getId());
        } catch (Exception ex) {
            throw new RuntimeException("Failed is method .create" + ex + Errors.DB_ERROR);
        }
    }




    public ProductInfo update(ProductInfo info) {
        //Создаем формат запроса -->  Присваеваем вместо %s параметры продукта --> Возвращаем метод поиска продукта по Id
        try (Statement statement = connection.createStatement()) {
            String script = String.format("update product " +
                            "SET name = '%s', composition = '%s', price = '%s', categoryId = '%s' , weight = '%s', EI = '%s' , kol = '%s'" +
                            "WHERE id = '%s' ",
                    info.getName(), info.getComposition(),
                    info.getPrice(), info.getCategoryId(), info.getWeight(),
                    info.getEI(), info.getKol(), info.getId());
            int updatedRows = statement.executeUpdate(script);
            System.out.println(".update completed updatedRows=" + updatedRows);

            return getProduct(info.getId());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to .update info=" + ex + Errors.DB_ERROR);
        }
    }


    public ProductInfo delete(String itemId) {

        ProductInfo productInfo = getProduct(itemId);
        if (productInfo == null) {
            return null;
        }
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Product WHERE id = ?")) {
            statement.setString(1, itemId);
            int i = statement.executeUpdate();
            return productInfo;
        } catch (Exception ex) {
            throw new CustomException("Error in dao.ProductRepository.delete " +
                    "Failed to delete item id=" + itemId, ex, Errors.DB_ERROR);
        }
    }
}


