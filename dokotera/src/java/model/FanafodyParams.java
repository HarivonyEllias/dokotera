package model;

import dbaccess.PGSQLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FanafodyParams {
    private int fanafodyParamsId;
    private int fanafodyId;
    private String fanafodyNom;
    private double fanafodyPrix;
    private int paramsId;
    private String paramsNom;
    private double effet;

    // Constructors with setters

    public FanafodyParams(int fanafodyParamsId,int fanafodyId,String fanafodyNom,double fanafodyPrix,int paramsId,String paramsNom,double effet) {
        setFanafodyParamsId(fanafodyParamsId);
        setFanafodyId(fanafodyId);
        setFanafodyNom(fanafodyNom);
        setFanafodyPrix(fanafodyPrix);
        setParamsId(paramsId);
        setParamsNom(paramsNom);
        setEffet(effet);
    }

    private FanafodyParams() {
    }

    // Setters
    public void setFanafodyParamsId(int fanafodyParamsId) {
        this.fanafodyParamsId = fanafodyParamsId;
    }

    public void setFanafodyId(int fanafodyId) {
        this.fanafodyId = fanafodyId;
    }

    public void setFanafodyNom(String fanafodyNom) {
        this.fanafodyNom = fanafodyNom;
    }

    public void setParamsId(int paramsId) {
        this.paramsId = paramsId;
    }

    public void setParamsNom(String paramsNom) {
        this.paramsNom = paramsNom;
    }

    public void setEffet(double effet) {
        this.effet = effet;
    }

    // Additional methods for getting values

    public int getFanafodyParamsId() {
        return fanafodyParamsId;
    }

    public int getFanafodyId() {
        return fanafodyId;
    }

    public String getFanafodyNom() {
        return fanafodyNom;
    }

    public int getParamsId() {
        return paramsId;
    }

    public String getParamsNom() {
        return paramsNom;
    }

    public double getEffet() {
        return effet;
    }
    
    public double getFanafodyPrix() {
        return fanafodyPrix;
    }

    public void setFanafodyPrix(double fanafodyPrix) {
        this.fanafodyPrix = fanafodyPrix;
    }
    public static FanafodyParams[] heal(int[] paramsIds, int[] values, double prix) throws Exception {
        // Assuming you have a method to obtain the database connection
        try (Connection connection = PGSQLConnection.getConnection()) {
            List<FanafodyParams> result = new ArrayList<>();

            // Fetch all records from the database
            List<FanafodyParams> allRecords = fetchAllRecords(connection);

            // Filter records in memory based on paramsIds, values, and prix
            for (FanafodyParams fanafodyParams : allRecords) {
                if (matchesCriteria(fanafodyParams, paramsIds, values, prix)) {
                    result.add(fanafodyParams);
                }
            }

            return result.toArray(new FanafodyParams[0]);
        }
    }

    private static List<FanafodyParams> fetchAllRecords(Connection connection) throws Exception {
        List<FanafodyParams> records = new ArrayList<>();

        // Assuming you have a method to execute the query and retrieve the records
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM v_fanafody_params")) {
                while (resultSet.next()) {
                    FanafodyParams fanafodyParams = new FanafodyParams();
                    // Set values from the ResultSet to your FanafodyParams object
//                    fanafodyParams.setFanafodyParamsId(resultSet.getInt("id"));
                    fanafodyParams.setFanafodyId(resultSet.getInt("fanafody_id"));
                    fanafodyParams.setFanafodyNom(resultSet.getString("fanafody_nom"));
                    fanafodyParams.setFanafodyPrix(resultSet.getDouble("fanafody_prix"));
                    fanafodyParams.setParamsId(resultSet.getInt("params_id"));
                    fanafodyParams.setParamsNom(resultSet.getString("params_nom"));
                    fanafodyParams.setEffet(resultSet.getDouble("effet"));

                    records.add(fanafodyParams);
                }
            }
        }

        return records;
    }

    private static boolean matchesCriteria(FanafodyParams fanafodyParams, int[] paramsIds, int[] values, double prix) {
        // Check if paramsId and values match the criteria
        for (int i = 0; i < paramsIds.length; i++) {
            if (fanafodyParams.getParamsId() == paramsIds[i] && fanafodyParams.getEffet() >= values[i]) {
                // Check if fanafodyPrix is less than or equal to prix
                if (fanafodyParams.getFanafodyPrix() <= prix) {
                    return true;  // This record matches the criteria
                }
            }
        }
        return false;
    }
}
