package fao.org.owl2skos.protege;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protegex.owl.database.OWLDatabaseKnowledgeBaseFactory;

public class ProtegeModelLoader {

	protected static Logger logger = LoggerFactory.getLogger(ProtegeModelLoader.class);

	protected final static String dbDriverProp = "dbDriver";
	protected final static String dbBaseUrlProp = "dbBaseUrl";
	protected final static String dbTableNameProp = "dbTableName";
	protected final static String dbUserNameProp = "dbUserName";
	protected final static String dbPasswordProp = "dbPassword";

	public Project loadProtegeProject(String dbDriver, String dbUrl, String dbTableName, String dbUserName,
			String dbPassword) {
		logger.info("creating Database Protege OWLModel: " + dbUrl);
		OWLDatabaseKnowledgeBaseFactory factory = new OWLDatabaseKnowledgeBaseFactory();
		Collection<Object> errors = new ArrayList<Object>();
		Project project = Project.createNewProject(factory, errors);
		OWLDatabaseKnowledgeBaseFactory.setSources(project.getSources(), dbDriver, dbUrl, dbTableName,
				dbUserName, dbPassword);
		project.createDomainKnowledgeBase(factory, errors, true);
		return project;
	}

	public Project loadProtegeProject(File propertyFile) throws IOException {
		Properties props = new java.util.Properties();
		FileReader fileReader = new FileReader(propertyFile);
		props.load(fileReader);
		String dbDriver = props.getProperty(dbDriverProp);
		String dbBaseUrl = props.getProperty(dbBaseUrlProp);
		String dbTableName = props.getProperty(dbTableNameProp);
		String dbUserName = props.getProperty(dbUserNameProp);
		String dbPassword = props.getProperty(dbPasswordProp);
		
		String dbUrl = dbBaseUrl + dbTableName + "?requireSSL=false&useUnicode=true&characterEncoding=UTF-8";
		
		return loadProtegeProject(dbDriver, dbUrl, dbTableName, dbUserName, dbPassword);
	}

	public Project loadProtegeProject(String configFileName) throws IOException {
		File configFile = new File(configFileName);
		return loadProtegeProject(configFile);
	}
	
	
}
