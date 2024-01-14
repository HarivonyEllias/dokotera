/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dbaccess.PGSQLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hariv
 */

public class FanafodyParamsHealer {

    // Assuming FanafodyParams class has appropriate constructor
    public static class FanafodyParams {
        private int id;
        private int idfanafody;
        private int idparams;
        private double effet;

        
        // Constructor, getters, and setters

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdfanafody() {
            return idfanafody;
        }

        public void setIdfanafody(int idfanafody) {
            this.idfanafody = idfanafody;
        }

        public int getIdparams() {
            return idparams;
        }

        public void setIdparams(int idparams) {
            this.idparams = idparams;
        }

        public double getEffet() {
            return effet;
        }

        public void setEffet(double effet) {
            this.effet = effet;
        }
    }

    private Connection connection; // Initialize this with your database connection

    public FanafodyParamsHealer(Connection connection) {
        try {
            this.connection = PGSQLConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FanafodyParamsHealer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static FanafodyParams[] heal(int[] paramsIds, int[] values, double prix) throws SQLException {
        List<FanafodyParams> result = new ArrayList<>();

        // Build the SQL query dynamically based on the provided parameters
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM fanafody_params fp ");
        queryBuilder.append("JOIN defs d ON fp.iddefs = d.id ");
        queryBuilder.append("WHERE d.idparams IN (");

        for (int i = 0; i < paramsIds.length; i++) {
            queryBuilder.append(paramsIds[i]);

            if (i < paramsIds.length - 1) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(") AND fp.effet >= ? AND fp.effet >= ?");
        // Assuming you have a 'prix' column in your table and you want to filter by it
        queryBuilder.append(" AND prix <= ?");

        try (PreparedStatement statement = PGSQLConnection.getConnection().prepareStatement(queryBuilder.toString())) {
            statement.setDouble(values.length, prix);

            // Set parameter values in the PreparedStatement
            for (int i = 0; i < values.length; i++) {
                statement.setDouble(i + 1, values[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FanafodyParams fanafodyParams = new FanafodyParams();
                    // Set values from the ResultSet to your FanafodyParams object
                    fanafodyParams.setId(resultSet.getInt("id"));
                    fanafodyParams.setIdfanafody(resultSet.getInt("idfanafody"));
                    fanafodyParams.setIdparams(resultSet.getInt("idparams"));
                    fanafodyParams.setEffet(resultSet.getDouble("effet"));

                    result.add(fanafodyParams);
                }
            }
        }

        return result.toArray(new FanafodyParams[0]);
    }

    // Additional methods for closing the connection, handling exceptions, etc.
}
