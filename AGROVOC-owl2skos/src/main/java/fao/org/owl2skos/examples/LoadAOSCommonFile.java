package fao.org.owl2skos.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fao.org.owl2skos.ModelLoader;
import fao.org.owl2skos.conversion.OWL2SKOSConverter;
import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.io.RDFFormat;
import it.uniroma2.art.owlart.model.ARTURIResource;
import it.uniroma2.art.owlart.model.NodeFilters;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.navigation.ARTLiteralIterator;
import it.uniroma2.art.owlart.navigation.ARTResourceIterator;
import it.uniroma2.art.owlart.navigation.ARTURIResourceIterator;

public class LoadAOSCommonFile {

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
							+ "java fao.org.owl2skos.examples.LoadModelExample <skosConvertedFile> [persistence] [Data_directory] [OWLART Implementing Class] [aos file]");
			return;
		}

		SKOSXLModel skosXLModel;		
		if (args.length > 3)
			skosXLModel = ModelLoader.loadSKOSModel(args[3], args[2], Boolean.parseBoolean(args[1]));
		else if (args.length > 2)
			skosXLModel = ModelLoader.loadSKOSModel(args[2], Boolean.parseBoolean(args[1]));
		else if (args.length > 1)
			skosXLModel = ModelLoader.loadSKOSModel(Boolean.parseBoolean(args[1]));
		else
			skosXLModel = ModelLoader.loadSKOSModel();

		File inputFile = new File(args[0]);

		String inputAOSCommonFileName;
		if (args.length>4)
			inputAOSCommonFileName = args[4];
		else
			inputAOSCommonFileName = "Input/aos.owl";
		File inputAOSCommonFile = new File(inputAOSCommonFileName);
		
		// this should be used to load the file produced through the agrovoc-owl2skos conversion routine,
		// though it can be used for any RDF file
		skosXLModel.addRDF(inputFile, "http://aims.fao.org/aos/agrovoc/", RDFFormat
				.guessRDFFormatFromFile(inputFile));

		String aosCommonFileBaseURI_Namespace = "http://aims.fao.org/aos/common/";
		ARTURIResource aosCommonGraph = skosXLModel.createURIResource(aosCommonFileBaseURI_Namespace);

		skosXLModel.addRDF(inputAOSCommonFile, aosCommonFileBaseURI_Namespace, RDFFormat.guessRDFFormatFromFile(inputAOSCommonFile),
				skosXLModel.createURIResource(aosCommonFileBaseURI_Namespace));
		
		System.out.println("default scheme: " + skosXLModel.getDefaultSchema());

		ARTURIResourceIterator itURI = skosXLModel.getOWLModel().listObjectProperties(false, aosCommonGraph);
		System.out.println("objects properties from the aos common vocabulary:");
		while (itURI.streamOpen()) {
			System.out.println(itURI.getNext());
		}
		itURI.close();
		
		// don't remember exactly which is the prefix for common, just use "aos" here 
		skosXLModel.setNsPrefix(aosCommonFileBaseURI_Namespace, "aos");
		
		// simulating a wname coming from the UI
		String qname = "aos:isSpatiallyIncludedIn";
		System.out.println("\nprefixed name (qname) for spatiallyIncludedin: " + qname);
		
		String isSpatiallyIncludedInPropertyName = skosXLModel.expandQName(qname);
		ARTURIResource isSpatiallyIncludedInProperty = skosXLModel.createURIResource(isSpatiallyIncludedInPropertyName);
		System.out.println("\nproperty obtained from qname: " + isSpatiallyIncludedInProperty);
		
		ARTLiteralIterator itLit = skosXLModel.getOWLModel().listLabels(isSpatiallyIncludedInProperty, true, aosCommonGraph);
		System.out.println("\nlabels for object property isSpatiallyIncludedIn:");
		while (itLit.streamOpen()) {
			System.out.println(itLit.getNext());
		}
		itLit.close();		
		
		ARTResourceIterator itRes = skosXLModel.getOWLModel().listPropertyDomains(isSpatiallyIncludedInProperty, true, aosCommonGraph);
		System.out.println("\ndomain declarations for object property isSpatiallyIncludedIn:");
		while (itRes.streamOpen()) {
			System.out.println(itRes.getNext());
		}
		itRes.close();	
		
		ARTURIResource invProp = skosXLModel.getOWLModel().getInverseProperty(isSpatiallyIncludedInProperty, true, aosCommonGraph);
		System.out.println("\ninverse property for object property isSpatiallyIncludedIn: " + invProp);	

		// this is just put to pause and wait for a key press by a user, just in case the underlying triple
		// store implementations syncronizes output every few milliseconds. For example, Sesame2
		// Implementations syncronizes every second. So, without a pause, the repository would never be
		// persisted
		pause();

	}
}
