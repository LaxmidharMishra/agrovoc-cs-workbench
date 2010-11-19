package fao.org.owl2skos;

import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.models.UnsupportedModelConfigurationException;
import it.uniroma2.art.owlart.models.conf.BadConfigurationException;
import it.uniroma2.art.owlart.models.conf.ModelConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import edu.stanford.smi.protege.model.Project;
import fao.org.owl2skos.conversion.OWL2SKOSConverter;
import fao.org.owl2skos.protege.ProtegeModelLoader;

public class Main {

	/**
	 * this main method converts a ProtegeDB owl repository for AGROVOC into a SKOS version of AGROVOC,
	 * following mappings established by the AGROVOC OWL2SKOS working group at FAO.<br/>
	 * The method takes two arguments (plus an optional third one):
	 * <ul>
	 * <li>first argument points to the config file for the protege DB</li>
	 * <li>second argument provides an output file for conversion</li>
	 * <li>third (optional) argument specifies which OWL ART Implementation class (ModelFactory
	 * implementation) will be used during the process</li>
	 * </ul>
	 * 
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ModelCreationException
	 * @throws ModelUpdateException
	 * @throws ModelAccessException
	 * @throws UnsupportedRDFFormatException
	 * @throws BadConfigurationException 
	 * @throws UnsupportedModelConfigurationException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, ModelCreationException, ModelUpdateException,
			ModelAccessException, UnsupportedRDFFormatException, BadConfigurationException, UnsupportedModelConfigurationException {
		if (args.length < 2) {
			System.out
					.println("usage:\n"
							+ "java fao.org.owl2skos.Main <ProtegeDBConfigFile> <conversionOutputFile> <persistence> <Data_directory> [OWLART Implementing Class]");
			return;
		}
		
		// PROTEGE PROJECT LOADING
		ProtegeModelLoader loader = new ProtegeModelLoader();
		Project protProject = loader.loadProtegeProject(args[0]);
		
		boolean persistance  = Boolean.parseBoolean(args[2]);
		String dataDirectory = args[3];

		// OWL ART SKOSXLMODEL LOADING
		String owlArtModelFactoryImplClassName;
		if (args.length > 4)
			owlArtModelFactoryImplClassName = args[4];
		else
			owlArtModelFactoryImplClassName = OWL2SKOSConverter.SesameModelFactoryImplClassName;

		Class<? extends ModelFactory> owlArtModelFactoryImplClass = (Class<? extends ModelFactory>) Class
				.forName(owlArtModelFactoryImplClassName);
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass
				.newInstance());
		
		ModelConfiguration mcfg = fact.createModelConfigurationObject();
		mcfg.setParameter("directTypeInference", "false");
		mcfg.setParameter("rdfsInference", "false");
		
		SKOSXLModel skosXLModel = fact.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc", dataDirectory, persistance, mcfg);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);

		// CONVERSION
		OWL2SKOSConverter converter = new OWL2SKOSConverter(protProject, skosXLModel);
		String ntFile = args[1];
		String rdfFile = ntFile.replace(".nt", ".rdf");
		File ntOutputFile = new File(ntFile);
		File rdfOutputFile = new File(rdfFile);
		Date d1 = new Date();
		System.out.println("CONVERSION STARTED AT : " + d1);
		converter.convert();
		Date d2 = new Date();
		System.out.println("CONVERSION ENDED AT : " + d2);
		System.out.println("TOTAL TIME FOR CONVERSION: " + RunConversion.getTimeDifference(d2, d1));
		converter.saveConversion(ntOutputFile);
		converter.saveConversion(rdfOutputFile);
		System.out.println("TOTAL TIME : " + RunConversion.getTimeDifference(new Date(), d1));
	}
}
