package ru.cybern.kinoserver.migration;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.management.modelmbean.XMLParseException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class MigrationsExecutor {
	
	private static Logger logger = Logger.getLogger(MigrationsExecutor.class);
	
	private static final String MIGRATIONS_FILE = "migrations.xml";
	
	@Resource(mappedName = "java:jboss/datasources/PostgreSQLDS")
	private DataSource dataSource;
	
	@PostConstruct
	private void runMigrations() throws Exception {
		DocumentBuilder xml = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = xml.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream(MIGRATIONS_FILE));
        NodeList migrationElements = doc.getElementsByTagName("ru/cybern/kinoserver/migration");
        logger.info("Start migrations...");
        checkMigrationsTable();
        for(int i = 0; i < migrationElements.getLength(); i++) {
        	Element element = (Element) migrationElements.item(i);
        	try {
                Migration migration = new Migration(element, dataSource);
                if(!migration.wasExecuted()) {
                    migration.run();
                }
        	} catch (XMLParseException e) {
            	logger.error("XML error in " + MigrationsExecutor.MIGRATIONS_FILE, e);
        	}
        }
        logger.info("All migrations completed.");
	}
	
	private void checkMigrationsTable() {
        try (Connection conn = dataSource.getConnection();
        	 PreparedStatement stmt = conn.prepareStatement(
        			 "CREATE TABLE migrations ("
					+ "name VARCHAR(1000) PRIMARY KEY, "
					+ "contactInfo VARCHAR(200) NULL, " 
					+ "completed DATE NOT NULL" 
					+ ")" );) {
        	try {
        		conn.setAutoCommit(false);
            	stmt.execute();
            	conn.commit();
                logger.info("Table \"migrations\" successfully created");
        	} catch (SQLException e) {
        		conn.rollback();
            }
        } catch (SQLException e) {
			logger.error("Error while checking \"migrations\" table", e);
		} 
	}

}
