package model;

import dbaccess.PGSQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import querytools.WhereClauseGenerator;

public class AretinaParams {
    private int aretinaId;
    private String aretinaNom;
    private String paramsNom;
    private double minim;
    private double maxim;

    // Constructor with setters

    public AretinaParams(int aretinaId,String aretinaNom,String paramsNom,double minim,double maxim) {
        setAretinaId(aretinaId);
        setAretinaNom(aretinaNom);
        setParamsNom(paramsNom);
        setMinim(minim);
        setMaxim(maxim);
    }

    // Setters

    public void setAretinaId(int aretinaId) {
        this.aretinaId = aretinaId;
    }

    public void setAretinaNom(String aretinaNom) {
        this.aretinaNom = aretinaNom;
    }

    public void setParamsNom(String paramsNom) {
        this.paramsNom = paramsNom;
    }

    public void setMinim(double minim) {
        this.minim = minim;
    }

    public void setMaxim(double maxim) {
        this.maxim = maxim;
    }

    // Additional methods for getting values

    public int getAretinaId() {
        return aretinaId;
    }

    public String getAretinaNom() {
        return aretinaNom;
    }

    public String getParamsNom() {
        return paramsNom;
    }

    public double getMinim() {
        return minim;
    }

    public double getMaxim() {
        return maxim;
    }

    public AretinaParams() {
    }
    
    
    public static AretinaParams[] guess(int[] paramsIds,int[] values,double age)throws Exception{
        ArrayList<AretinaParams> ls = new ArrayList<>();
        String whereclause = WhereClauseGenerator.generateWhereClause(paramsIds, values);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = PGSQLConnection.getConnection();
            String query = "SELECT\n" +
            "    ap.id AS aretina_params_id,\n" +
            "    a.id AS aretina_id,\n" +
            "    a.nom AS aretina_nom,\n" +
            "    d.id AS defs_id,\n" +
            "    p.id AS params_id,\n" +
            "    p.nom AS params_nom,\n" +
            "    d.minim,\n" +
            "    d.maxim\n" +
            "FROM\n" +
            "    aretina a\n" +
            "JOIN aretina_params ap ON a.id = ap.idaretina\n" +
            "JOIN defs d ON ap.iddefs = d.id\n" +
            "JOIN params p ON d.idparams = p.id "+whereclause;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AretinaParams a = new AretinaParams();
                a.setAretinaId(resultSet.getInt("aretina_id"));
                a.setAretinaNom(resultSet.getString("aretina_nom"));
                if(ls.isEmpty()){
                    ls.add(a);
                }
                if(!a.getAretinaNom().equalsIgnoreCase(ls.get(ls.size()-1).getAretinaNom() )){
                    ls.add(a);
                }
//                    ls.add(a);

            }
            return ls.toArray(new AretinaParams[ls.size()]);
        } finally {
            // Close resources in a finally block
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
