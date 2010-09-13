package fao.org.owl2skos;

import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.SKOSXLModel;

import java.io.File;
import java.io.IOException;

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
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, ModelCreationException, ModelUpdateException,
			ModelAccessException, UnsupportedRDFFormatException {
		if (args.length < 2) {
			System.out
					.println("usage:\n"
							+ "java fao.org.owl2skos.Main <ProtegeDBConfigFile> <conversionOutputFile> [OWLART Implementing Class]");
			return;
		}

		// PROTEGE PROJECT LOADING
		ProtegeModelLoader loader = new ProtegeModelLoader();
		Project protProject = loader.loadProtegeProject(args[0]);

		// OWL ART SKOSXLMODEL LOADING
		String owlArtModelFactoryImplClassName;
		if (args.length > 2)
			owlArtModelFactoryImplClassName = args[2];
		else
			owlArtModelFactoryImplClassName = OWL2SKOSConverter.SesameModelFactoryImplClassName;

		Class<? extends ModelFactory> owlArtModelFactoryImplClass = (Class<? extends ModelFactory>) Class
				.forName(owlArtModelFactoryImplClassName);
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass
				.newInstance());
		SKOSXLModel skosXLModel = fact.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc", "ModelData", false);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);

		// CONVERSION

		OWL2SKOSConverter converter = new OWL2SKOSConverter(protProject, skosXLModel);
		File outputFile = new File(args[1]);
		converter.convert();
		converter.saveConversion(outputFile);

	}
}
