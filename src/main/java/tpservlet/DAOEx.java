/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpservlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import simplejdbc.DAO;
import simplejdbc.DAOException;

/**
 *
 * @author pedago
 */
public class DAOEx extends DAO {
    
    public DAOEx(DataSource dataSource) {
        super(dataSource);
    }
    
    public List<String> getAllStates() throws DAOException {
        List<String> states = new ArrayList<>();
        
        String sql = "SELECT DISTINCT(STATE) FROM CUSTOMER ORDER BY STATE";
	try (Connection connection = myDataSource.getConnection();
            Statement stmt = connection.createStatement()) {
            
            try (ResultSet rs = stmt.executeQuery(sql)) {
		while (rs.next()) {
                    String state_name = rs.getString("STATE");
                    states.add(state_name);
		}
            }
	}  catch (SQLException ex) {
            Logger.getLogger("DAOEx").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        
        return states;
    }
}
