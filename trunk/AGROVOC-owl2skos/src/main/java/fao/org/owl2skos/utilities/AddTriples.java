package fao.org.owl2skos.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import fao.org.owl2skos.conversion.OWL2SKOSConverter;
import it.uniroma2.art.owlart.agraphimpl.factory.ARTModelFactoryAllegroGraphImpl;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.RDFModel;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.models.UnsupportedModelConfigurationException;
import it.uniroma2.art.owlart.models.conf.BadConfigurationException;
import it.uniroma2.art.owlart.models.conf.ModelConfiguration;

public class AddTriples {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ModelCreationException,
			UnsupportedModelConfigurationException, ModelUpdateException, IOException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, BadConfigurationException {

		if (args.length < 2) {
			System.out
					.println("usage:\n"
							+ "java fao.org.owl2skos.utilities.AddTriples <model_configfile>\n"
							+ "\t<model_configfile>  : config file for the chosen RDFModel Implementation \n"
							+ "\t<triplesfile>                      : file containing triples to be added to the rdf repository \n");
			return;
		}

		String model_configFile = args[0];
		String triplesFile = args[1];

		String owlArtModelFactoryImplClassName;

		if (args.length > 2)
			owlArtModelFactoryImplClassName = args[2];
		else
			owlArtModelFactoryImplClassName = ARTModelFactoryAllegroGraphImpl.class.getName();

		// OWL ART (AllegroGraph Implementation) SKOSXLMODEL LOADING
		Class<? extends ModelFactory<? extends ModelConfiguration>> owlArtModelFactoryImplClass = Utilities
				.getModelFactory(owlArtModelFactoryImplClassName);
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass
				.newInstance());

		ModelConfiguration mcfg = fact
				.createModelConfigurationObject(new File(model_configFile));
		SKOSXLModel skosXLModel = fact.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc", "ModelData", true,
				mcfg);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);

		addTriples(skosXLModel, new File(triplesFile));
	}

	public static void addTriples(RDFModel model, File triplesFile) throws IOException, ModelUpdateException {
		BufferedReader br = new BufferedReader(new FileReader(triplesFile));
		String line;
		while ((line = br.readLine()) != null) {
			String[] triple = line.split("\\|");
			model.addTriple(model.createURIResource(triple[0].trim()), model.createURIResource(triple[1].trim()), model.createURIResource(triple[2].trim()));			
		}
	}

}
