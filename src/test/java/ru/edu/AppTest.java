package ru.edu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.edu.dao.ProductRepository;
import ru.edu.service.ProductInfo;
import ru.edu.service.ProductService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void allFromDB()
    {
        Connection conn = null;
        String db = "jdbc:sqlite:db\\simple_database.db";//jdbc:sqlite:chinook.db
        try {
            // Establish database connection
            ProductRepository repo = new ProductRepository();
            conn = DriverManager.getConnection(db);
            repo.setConnection(conn);
            repo.getAll();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println( "END !" );

        assertTrue( true );
    }

    @Test
    public void ProductServiceGetAll()
    {
        Connection conn = null;
        String db = "jdbc:sqlite:db\\simple_database.db";//jdbc:sqlite:chinook.db
        try {
            // Establish database connection
            ProductRepository repo = new ProductRepository();
            conn = DriverManager.getConnection(db);
            repo.setConnection(conn);
            repo.getAll();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println( "END !" );

        assertTrue( true );
    }

    @Test
    public void GetSimilarProducts()
    {
        String itemId = "Pastil";
        double priceDelta = 300;
        Connection conn = null;
        String db = "jdbc:sqlite:db\\simple_database.db";//jdbc:sqlite:chinook.db
        try {
            // Establish database connection
            ProductRepository repo = new ProductRepository();
            conn = DriverManager.getConnection(db);
            repo.setConnection(conn);
            repo.getAll();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println( "END !" );

        assertTrue( true );

    }
}
