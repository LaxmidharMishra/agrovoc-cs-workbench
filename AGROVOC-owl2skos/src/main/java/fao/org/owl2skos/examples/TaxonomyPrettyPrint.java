package fao.org.owl2skos.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.io.RDFFormat;
import it.uniroma2.art.owlart.model.ARTLiteral;
import it.uniroma2.art.owlart.model.ARTResource;
import it.uniroma2.art.owlart.model.ARTURIResource;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.navigation.ARTURIResourceIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fao.org.owl2skos.conversion.OWL2SKOSConverter;

/**
 * This class represents a simple example of use of OWL ART API for managing a SKOS-XL RDF repository.<br/>
 * The main method of this class requires one argument; this can be the name of an input file containing the
 * SKOS data to be loaded, or have the value: "preloaded", meaning that the data has already been loaded on a
 * previous run of the class. The program stops by asking the user to press a key. This is necessary since
 * some triple store implementations may wait a while before saving their managed data. For example,
 * Sesame2Implementation of OWL ART API is configured to wait 1 second before saving data on the persistence
 * directory.<br/>
 * Note when using Sesame2: remember to press the key when program stops, otherwise forcing the program to end
 * will leave a lock on the ModelData folder, which needs to be manually removed
 * 
 * @author Sachit Rajbhandari <Sachit.Rajbhandari@fao.org>
 * @author Armando Stellato <stellato@info.uniroma2.it>
 * 
 */
public class TaxonomyPrettyPrint {

	public static final String SesameModelFactoryImplClassName = "it.uniroma2.art.owlart.sesame2impl.factory.ARTModelFactorySesame2Impl";
	public static final String agrovocBaseURI = "http://aims.fao.org/aos/agrovoc";
	public static final String agrovocDefNamespace = agrovocBaseURI + "/";
	public static final String persistenceDirectory = "ModelData";

	protected static Logger logger = LoggerFactory.getLogger(TaxonomyPrettyPrint.class);

	public static void main(String args[]) {

		if (args.length < 1) {
			System.out.println("usage:\n"
					+ "java fao.org.owl2skos.examples.TaxonomyPrettyPrint <inputfilename|\"preloaded\">");
			return;
		}

		try {
			TaxonomyPrettyPrint skosTest = new TaxonomyPrettyPrint();
			SKOSXLModel skosXLModel;
			if (args[0].equals("preloaded")) {
				logger.info("standard model loading");
				skosXLModel = skosTest.loadModel();
			} else {
				logger.info("first time loading the model: taking care of importing data from file: "
						+ args[0]);
				skosXLModel = skosTest.firstLoadModel(args[0]);
			}
			ArrayList<ARTURIResource> topConcepts = skosTest.loadTopConcepts(skosXLModel);
			int level = -1;
			for (ARTURIResource skosConcept : topConcepts) {
				skosTest.printConcept(skosXLModel, skosConcept, level);
				ArrayList<ARTURIResource> narrowerConcepts = skosTest.getNarrowerConcept(skosXLModel,
						skosConcept);
				System.out.println(level + " : " + narrowerConcepts.size());
				skosTest.printChildConcept(skosXLModel, narrowerConcepts, level);
			}

			LoadModelExample.pause();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void printChildConcept(SKOSXLModel skosXLModel, ArrayList<ARTURIResource> skosConcepts, int level)
			throws ModelAccessException {
		level++;
		for (ARTURIResource skosConcept : skosConcepts) {
			printConcept(skosXLModel, skosConcept, level);
			ArrayList<ARTURIResource> narrowerConcepts = getNarrowerConcept(skosXLModel, skosConcept);
			printChildConcept(skosXLModel, narrowerConcepts, level);
		}
	}

	public SKOSXLModel firstLoadModel(String filename) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ModelCreationException, ModelUpdateException, FileNotFoundException,
			IOException, ModelAccessException, UnsupportedRDFFormatException {

		SKOSXLModel skosXLModel = loadModel();

		// in this case the model is being loaded for the first time (that is, created), so we need to
		// declare a new schema
		// skosXLModel.addSKOSConceptScheme(OWL2SKOSConverter.agrovocSchemeURI);
		// note that this method does not need to be invoked, since the schema is already present in the
		// loaded data; what is important is to make it the defaultSchema in SKOS API.

		// first time this program is run, data is loaded from an external file, and loaded inside the main
		// graph
		File inputFile = new File(filename);
		skosXLModel.addRDF(inputFile, "http://aims.fao.org/aos/agrovoc", RDFFormat
				.guessRDFFormatFromFile(inputFile));

		return skosXLModel;
	}

	@SuppressWarnings("unchecked")
	public SKOSXLModel loadModel() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ModelCreationException, ModelUpdateException, FileNotFoundException,
			IOException, ModelAccessException, UnsupportedRDFFormatException {
		// OWL ART SKOSXLMODEL LOADING
		Class<? extends ModelFactory> owlArtModelFactoryImplClass = (Class<? extends ModelFactory>) Class
				.forName(SesameModelFactoryImplClassName);
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass
				.newInstance());

		SKOSXLModel skosXLModel = fact.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc",
				persistenceDirectory, true);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);

		skosXLModel.setDefaultSchema(skosXLModel.createURIResource(OWL2SKOSConverter.agrovocSchemeURI));

		return skosXLModel;
	}

	public ArrayList<ARTURIResource> loadTopConcepts(SKOSXLModel skosXLModel) throws ModelAccessException {
		// ARTURIResourceIterator itr = skosXLModel.listTopConceptsInScheme(skosXLModel.getDefaultSchema());
		ARTURIResourceIterator itr = skosXLModel.listConceptsInScheme(skosXLModel.getDefaultSchema());
		ArrayList<ARTURIResource> topConcepts = new ArrayList<ARTURIResource>();
		while (itr.hasNext()) {
			ARTURIResource skosConcept = itr.next();
			if (skosXLModel.isTopConcept(skosConcept, skosXLModel.getDefaultSchema()))
				topConcepts.add(skosConcept);

		}
		return topConcepts;
	}

	public ArrayList<ARTURIResource> getBroaderConcept(SKOSXLModel skosXLModel, ARTURIResource skosConcept)
			throws ModelAccessException {
		ArrayList<ARTURIResource> broaderConcepts = new ArrayList<ARTURIResource>();
		// now we have two booleans: transitive is set to false as before, to get only direct relationships,
		// while inference is true, so that both broader and inverse narrower relationships are considered
		ARTURIResourceIterator itr = skosXLModel.listNarrowerConcepts(skosConcept, false, true);
		while (itr.hasNext()) {
			ARTURIResource broaderConcept = itr.next();
			if (skosXLModel.isInScheme(skosConcept, skosXLModel.getDefaultSchema()))
				broaderConcepts.add(broaderConcept);

		}
		return broaderConcepts;
	}

	public ArrayList<ARTURIResource> getNarrowerConcept(SKOSXLModel skosXLModel, ARTURIResource skosConcept)
			throws ModelAccessException {
		ArrayList<ARTURIResource> narrowerConcepts = new ArrayList<ARTURIResource>();
		// now we have two booleans: transitive is set to false as before, to get only direct relationships,
		// while inference is true, so that both narrower and inverse broader relationships are considered
		ARTURIResourceIterator itr = skosXLModel.listNarrowerConcepts(skosConcept, false, true);
		while (itr.hasNext()) {
			ARTURIResource narrowerConcept = itr.next();
			if (skosXLModel.isInScheme(skosConcept, skosXLModel.getDefaultSchema()))
				narrowerConcepts.add(narrowerConcept);
		}
		return narrowerConcepts;
	}

	public String getTabPosition(int level) {
		String tab = "";
		for (int i = 0; i < level; i++) {
			tab = tab + "\t";
		}
		return tab;
	}

	public void printConcept(SKOSXLModel skosXLModel, ARTURIResource skosConcept, int level)
			throws ModelAccessException {
		ARTResource xLabel = skosXLModel.getPrefXLabel(skosConcept, "en");
		ARTLiteral label = skosXLModel.getLiteralForm(xLabel);
		System.out.println(getTabPosition(level) + skosConcept + " : " + label);
	}
}
