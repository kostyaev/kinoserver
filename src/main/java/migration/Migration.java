package migration;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.management.modelmbean.XMLParseException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Migration {
	
	private static Logger logger = Logger.getLogger(Migration.class);
	
	private DataSource dataSource;

    private IMigrationAction action;
	
	private String name;
    private String contactInfo;
    
    public Migration(Element node, DataSource dataSource) throws XMLParseException {
        name = node.getAttribute("name");
        if (name == null || name.trim().length() == 0) {
        	throw new XMLParseException("Migration can not have empty name");
        }
        
        contactInfo = node.getAttribute("contactInfo");
        this.dataSource = dataSource;
        
        NodeList list = node.getElementsByTagName("runSQL");
        if (list.getLength() != 1) {
        	throw new XMLParseException("Syntax error in migration: " + name);
        } else {
        	Element element = (Element) list.item(0);
            action = new RunSQLAction(element);
        }
    }

    public boolean wasExecuted() {
        try (Connection conn = dataSource.getConnection();
        	 PreparedStatement stmt = conn.prepareStatement(
        			 "SELECT 1 FROM migrations WHERE name = '" + name + "'");
        	 ResultSet rs = stmt.executeQuery()) {
        	
            return rs.next();
        } catch (SQLException e) {
            throw new Error("Cannot use \"migrations\" table", e);
        }
    }
    
    public void run() {
        logger.info("Executing migration: " + name);
        if (action.run()) {
            markExecuted();
        } else {
            logger.info("Migration \"" + name + "\" rolled back");
        }
    }

    private void markExecuted() {
        try (Connection conn = dataSource.getConnection();
        	 PreparedStatement stmt = conn.prepareStatement(
        		"INSERT INTO migrations (name, contactInfo, completed) values (?,?,?)")) {
            
            stmt.setString(1, name);
            stmt.setString(2, contactInfo);
            stmt.setDate(3, new Date(System.currentTimeMillis()));
            
            try {
            	conn.setAutoCommit(false);
                stmt.executeUpdate();
                conn.commit();
                logger.info("Completed migration: " + name);
            } catch (SQLException e) {
            	conn.rollback();
            	logger.error("Cannot mark migration \"" + name + "\" as executed");
            }
        } catch (SQLException e) {
            throw new Error("Cannot use \"migrations\" table", e);
        }
    }
    
    private class RunSQLAction implements IMigrationAction {
        
        private final List<String> tasks = new ArrayList<String>();

        public RunSQLAction(Element element) {
            NodeList list = element.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    StringTokenizer tok = new StringTokenizer(node.getNodeValue(), ";");
                    while (tok.hasMoreTokens()) {
                        tasks.add(tok.nextToken().replaceAll("\r\n", "").trim());
                    }
                }
            }
        }

        @Override
        public boolean run() {
            try (Connection conn = dataSource.getConnection();
            	 Statement stmt = conn.createStatement()) {
            	
            	try {
            		conn.setAutoCommit(false);
                    for (String task: tasks) {
                        if (task.length() == 0) continue;
                        logger.info("Executing SQL:\n" + task);
                        stmt.executeUpdate(task);
                    }
                    conn.commit();
            	} catch (SQLException e) {
            		conn.rollback();
                    logger.error("Cannot execute SQL migration", e);
                    return false;
            	}
            } catch (SQLException e) {
                logger.error("Cannot execute SQL migration", e);
                return false;
            }
            return true;
        }

    }

}
