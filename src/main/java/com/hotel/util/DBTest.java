package com.hotel.util;
import java.sql.Connection;
public class DBTest {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            try (Connection con = DBConnection.getConnection()) {
                System.out.println(
                    "Oracle database connected successfully!");
            }

        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
