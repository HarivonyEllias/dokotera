/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package querytools;

/**
 *
 * @author hariv
 */
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;

public class WhereClauseGenerator {

    public static String generateWhereClause(int[] paramsIds, int[] values) {
        if (paramsIds.length != values.length) {
            throw new IllegalArgumentException("Number of paramsIds and values must be the same.");
        }

        StringBuilder whereClause = new StringBuilder("WHERE ");
        whereClause.append("p.id IN (");

        for (int i = 0; i < paramsIds.length; i++) {
            whereClause.append(paramsIds[i]);
            if (i < paramsIds.length - 1) {
                whereClause.append(", ");
            }
        }

        whereClause.append(") AND (");

        for (int i = 0; i < paramsIds.length; i++) {
            whereClause.append("(")
                    .append(values[i])
                    .append(" BETWEEN d.minim AND d.maxim)");

            if (i < paramsIds.length - 1) {
                whereClause.append(" AND ");
            }
        }

        whereClause.append(");");

        return whereClause.toString();
    }
}

