package fao.org.owl2skos.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fao.org.owl2skos.conversion.OWL2SKOSConverter;
import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.io.RDFFormat;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.navigation.ARTURIResourceIterator;

public class LoadModelExample {

	/**
	 * this method is required since some triple store implementations for OWL ART API sync with their data
	 * folders after a few seconds. For example, data sync in Sesame2 happens every 1000ms (1 second).
	 * 
	 */
	public static void pause() {
		try {
			System.out.println("press a key");
			System.in.read();
			System.out.println("key pressed!");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * general purpose method for loading a model
	 * 
	 * @param owlArtModelFactoryImplClass
	 *            the implementation class for a specific OWL ART triple store implementation
	 * @param dataDirectory
	 *            the directory where persistence data is stored
	 * @return
	 * @throws ModelUpdateException
	 * @throws ModelCreationException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SKOSXLModel loadModel(Class<? extends ModelFactory> owlArtModelFactoryImplClass,
			String dataDirectory) throws ModelUpdateException, ModelCreationException,
			InstantiationException, IllegalAccessException {
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass
				.newInstance());
		SKOSXLModel skosXLModel = fact
				.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc", dataDirectory, true);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);

		skosXLModel.setDefaultSchema(skosXLModel.createURIResource(OWL2SKOSConverter.agrovocSchemeURI));

		return skosXLModel;
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ModelCreationException
	 * @throws ModelUpdateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ModelAccessException
	 * @throws UnsupportedRDFFormatException
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ModelCreationException, ModelUpdateException, FileNotFoundException,
			IOException, ModelAccessException, UnsupportedRDFFormatException {

		// argument resolution
		if (args.length < 1) {
			System.out
					.println("usage:\n"
							+ "java fao.org.owl2skos.examples.LoadModelExample <skosConvertedFile> [Data_directory] [OWLART Implementing Class]");
			return;
		}

		String owlArtModelFactoryImplClassName;
		if (args.length > 2)
			owlArtModelFactoryImplClassName = args[2];
		else
			owlArtModelFactoryImplClassName = OWL2SKOSConverter.SesameModelFactoryImplClassName;

		String dataDir;
		if (args.length > 1)
			dataDir = args[1];
		else
			dataDir = "ModelData";

		Class<? extends ModelFactory> owlArtModelFactoryImplClass = (Class<? extends ModelFactory>) Class
				.forName(owlArtModelFactoryImplClassName);

		SKOSXLModel skosXLModel = loadModel(owlArtModelFactoryImplClass, dataDir);

		File inputFile = new File(args[0]);

		// this should be used to load the file produced through the agrovoc-owl2skos conversion routine,
		// though it can be used for any RDF file
		skosXLModel.addRDF(inputFile, "http://aims.fao.org/aos/agrovoc/", RDFFormat
				.guessRDFFormatFromFile(inputFile));

		System.out.println("default scheme: " + skosXLModel.getDefaultSchema());

		ARTURIResourceIterator it = skosXLModel.listTopConceptsInScheme(skosXLModel.getDefaultSchema(), true);
		System.out.println("top concepts:");
		while (it.streamOpen()) {
			System.out.println(it.getNext());
		}

		// this is just put to pause and wait for a key press by a user, just in case the underlying triple
		// store implementations syncronizes output every few milliseconds. For example, Sesame2
		// Implementations syncronizes every second. So, without a pause, the repository would never be
		// persisted
		pause();

	}

}
