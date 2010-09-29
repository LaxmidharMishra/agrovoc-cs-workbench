package fao.org.owl2skos.examples;

import fao.org.owl2skos.ModelLoader;
import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.io.RDFFormat;
import it.uniroma2.art.owlart.model.ARTResource;
import it.uniroma2.art.owlart.model.ARTStatement;
import it.uniroma2.art.owlart.model.ARTURIResource;
import it.uniroma2.art.owlart.model.NodeFilters;
import it.uniroma2.art.owlart.models.DirectReasoning;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.navigation.ARTLiteralIterator;
import it.uniroma2.art.owlart.navigation.ARTResourceIterator;
import it.uniroma2.art.owlart.navigation.ARTStatementIterator;
import it.uniroma2.art.owlart.navigation.ARTURIResourceIterator;
import it.uniroma2.art.owlart.vocabulary.OWL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

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

	public static void describePropertyPrettyPrint(SKOSXLModel skosXLModel, String prefix,
			String propLocalName) throws ModelAccessException {
		String qname = prefix + ":" + propLocalName;
		System.out.println("\nprefixed name (qname) for " + propLocalName + ": " + qname + "\n");

		String propertyName = skosXLModel.expandQName(qname);
		ARTURIResource property = skosXLModel.createURIResource(propertyName);

		describeProperty(skosXLModel, property);

		System.out.println("\n\n\n\nLIST OF STATEMENTS FOR THIS PROPERTY\n");

		ARTStatementIterator it = skosXLModel
				.listStatements(property, NodeFilters.ANY, NodeFilters.ANY, true);
		while (it.streamOpen()) {
			System.out.println(it.getNext());
		}

	}

	public static void describeProperty(SKOSXLModel skosXLModel, ARTURIResource prop)
			throws ModelAccessException {
		ARTResource res;
		System.out.printf("%-40s", prop.getLocalName());
		res = getPropertyDomain(skosXLModel, prop);
		System.out.printf("domain: ");
		if (res != null) {
			System.out.printf("%-70s", prettyPrintDomainRange(skosXLModel, res) + "\t");
		} else
			System.out.printf("%-70s", "");

		res = getPropertyRange(skosXLModel, prop);
		System.out.print("range: ");
		if (res != null) {
			System.out.printf("%-70s", prettyPrintDomainRange(skosXLModel, res) + "\t");
		} else
			System.out.printf("%-70s", "");
		System.out.println();
	}

	protected static String prettyPrintDomainRange(SKOSXLModel skosXLModel, ARTResource resource)
			throws ModelAccessException {
		if (resource.isBlank()) {
			if (skosXLModel.hasType(resource, OWL.Res.DATARANGE, true)) {
				StringBuffer buff = new StringBuffer("[");
				ARTLiteralIterator it = skosXLModel.getOWLModel().parseDataRange(resource);
				while (it.streamOpen()) {
					buff.append(it.getNext() + "|");
				}
				it.close();
				buff.append("]");
				return buff.toString();
			} else
				return resource.toString();
		} else {
			return skosXLModel.getQName(resource.asURIResource().getURI());
		}
	}

	/**
	 * this may not be precise, since it only goes up the first branching of the superproperties, so it does
	 * not cover multiple inheritance when discovering property domains from its superproperties
	 * 
	 * @param skosXLModel
	 * @param prop
	 * @return
	 * @throws ModelAccessException
	 */
	protected static ARTResource getPropertyDomain(SKOSXLModel skosXLModel, ARTURIResource prop)
			throws ModelAccessException {
		ARTResource result = null;
		ARTResourceIterator innerIT;
		innerIT = skosXLModel.getOWLModel().listPropertyDomains(prop, true, NodeFilters.MAINGRAPH);
		if (innerIT.streamOpen())
			result = innerIT.getNext();
		innerIT.close();
		if (result != null)
			return result;
		else {
			// System.out.println("trying to get property domain from superproperties");
			ARTURIResourceIterator innerURIIT = ((DirectReasoning) skosXLModel.getOWLModel())
					.listDirectSuperProperties(prop, NodeFilters.MAINGRAPH);
			ARTURIResource superProp = null;

			if (innerURIIT.streamOpen())
				superProp = innerURIIT.getNext();
			innerURIIT.close();
			if (superProp != null && (!superProp.equals(prop))) {
				// System.out.println("trying to get property domain for property: " + prop
				// + " from superproperties: " + superProp);
				return getPropertyDomain(skosXLModel, superProp);
			} else
				return null;
		}
	}

	/**
	 * this may not be precise, since it only goes up the first branching of the superproperties, so it does
	 * not cover multiple inheritance when discovering property ranges from its superproperties
	 * 
	 * @param skosXLModel
	 * @param prop
	 * @return
	 * @throws ModelAccessException
	 */
	protected static ARTResource getPropertyRange(SKOSXLModel skosXLModel, ARTURIResource prop)
			throws ModelAccessException {
		ARTResource result = null;
		ARTResourceIterator innerIT;
		innerIT = skosXLModel.getOWLModel().listPropertyRanges(prop, true, NodeFilters.MAINGRAPH);
		if (innerIT.streamOpen())
			result = innerIT.getNext();
		innerIT.close();
		if (result != null)
			return result;
		else {
			// System.out.println("trying to get property domain from superproperties");
			ARTURIResourceIterator innerURIIT = ((DirectReasoning) skosXLModel.getOWLModel())
					.listDirectSuperProperties(prop, NodeFilters.MAINGRAPH);
			ARTURIResource superProp = null;
			if (innerURIIT.streamOpen())
				superProp = innerURIIT.getNext();
			innerURIIT.close();
			if (superProp != null && (!superProp.equals(prop)))
				return getPropertyRange(skosXLModel, superProp);
			else
				return null;
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
							+ "java fao.org.owl2skos.examples.LoadModelExample [aos file] [skosConvertedFile] [persistence] [Data_directory] [OWLART Implementing Class] ");
			return;
		}

		SKOSXLModel skosXLModel;
		if (args.length > 4)
			skosXLModel = ModelLoader.loadSKOSModel(args[4], args[3], Boolean.parseBoolean(args[2]));
		else if (args.length > 3)
			skosXLModel = ModelLoader.loadSKOSModel(args[3], Boolean.parseBoolean(args[2]));
		else if (args.length > 2)
			skosXLModel = ModelLoader.loadSKOSModel(Boolean.parseBoolean(args[2]));
		else
			skosXLModel = ModelLoader.loadSKOSModel();

		// OPTIONAL LOADING OF DATA FILE
		if (args.length > 1) {
			String inputFileName = args[1];
			System.out.println("loading data too from: " + inputFileName);
			// this should be used to load the file produced through the agrovoc-owl2skos conversion routine,
			// though it can be used for any RDF file
			File inputFile = new File(inputFileName);
			skosXLModel.addRDF(inputFile, "http://aims.fao.org/aos/agrovoc/", RDFFormat
					.guessRDFFormatFromFile(inputFile));
		}

		// LOADING AOS FILE
		// putting it *BEFORE* the load of data file (or not loading the data file at all, makes some
		// domain/range of properties not visible...why?"

		String inputAOSCommonFileName = args[0]; // "Input/aos.owl"
		File inputAOSCommonFile = new File(inputAOSCommonFileName);

		String aosCommonFileBaseURI_Namespace = "http://aims.fao.org/aos/common/";
		ARTURIResource aosCommonGraph = skosXLModel.createURIResource(aosCommonFileBaseURI_Namespace);

		System.out.println("loading data from: " + inputAOSCommonFileName);
		skosXLModel.addRDF(inputAOSCommonFile, aosCommonFileBaseURI_Namespace, RDFFormat
				.guessRDFFormatFromFile(inputAOSCommonFile), aosCommonGraph);

		// INITIALIZATION
		System.out.println("default scheme: " + skosXLModel.getDefaultSchema());
		// don't remember exactly which is the prefix for common, just use "aos" here
		String aosc = "aosc";
		skosXLModel.setNsPrefix(aosCommonFileBaseURI_Namespace, aosc);

		// describePropertyPrettyPrint(skosXLModel, aosc, "hasStatus");

		ARTURIResourceIterator itURI = skosXLModel.getOWLModel().listObjectProperties(false, aosCommonGraph);
		System.out.println("OBJECT properties from the aos common vocabulary:");
		while (itURI.streamOpen()) {
			describeProperty(skosXLModel, itURI.getNext());
		}
		itURI.close();

		itURI = skosXLModel.getOWLModel().listDatatypeProperties(false, aosCommonGraph);
		System.out.println("\n\nDATATYPE properties from the aos common vocabulary:");
		while (itURI.streamOpen()) {
			describeProperty(skosXLModel, itURI.getNext());
		}
		itURI.close();

		itURI = skosXLModel.getOWLModel().listAnnotationProperties(false, aosCommonGraph);
		System.out.println("\n\nANNOTATION properties from the aos common vocabulary:");
		while (itURI.streamOpen()) {
			describeProperty(skosXLModel, itURI.getNext());
		}
		itURI.close();

		HashSet<ARTURIResource> inverses = new HashSet<ARTURIResource>();
		ARTStatementIterator statIT = skosXLModel.listStatements(NodeFilters.ANY, OWL.Res.INVERSEOF,
				NodeFilters.ANY, false, aosCommonGraph);
		System.out.println("\n\npairs of INVERSE properties from the aos common vocabulary:\n");
		while (statIT.streamOpen()) {
			ARTStatement stat = statIT.getNext();
			ARTURIResource subj = stat.getSubject().asURIResource();
			ARTURIResource obj = stat.getObject().asURIResource();
			// first option: subj or obj is the same, since I store both to them; it is necessary because
			// I may invert some of them if I find some "has" or "is" as prefix in their in the name (I keep
			// the "has" as subject, thus I will keep the "has" triples in the conversion, and discard the
			// "is")
			// second condition is because probably Protege has expanded the symmetric properties into
			// inverseof themselves, and I discard these triples too
			if (!inverses.contains(subj) && !subj.equals(obj)) {
				inverses.add(subj);
				inverses.add(obj);
				if (subj.getLocalName().startsWith("is") || obj.getLocalName().startsWith("has"))
					System.out.printf("%-40s|                  %-70s\n", skosXLModel.getQName(obj.getURI()),
							skosXLModel.getQName(subj.getURI()));
				else
					System.out.printf("%-40s|                  %-70s\n", skosXLModel.getQName(subj.getURI()),
							skosXLModel.getQName(obj.getURI()));
			}
		}
		statIT.close();

		/*
		 * 
		 * // simulating a wname coming from the UI String qname = "aosc:isSpatiallyIncludedIn";
		 * System.out.println("\nprefixed name (qname) for spatiallyIncludedin: " + qname);
		 * 
		 * String isSpatiallyIncludedInPropertyName = skosXLModel.expandQName(qname); ARTURIResource
		 * isSpatiallyIncludedInProperty = skosXLModel .createURIResource(isSpatiallyIncludedInPropertyName);
		 * System.out.println("\nproperty obtained from qname: " + isSpatiallyIncludedInProperty);
		 * 
		 * ARTLiteralIterator itLit = skosXLModel.getOWLModel().listLabels(isSpatiallyIncludedInProperty,
		 * true, aosCommonGraph); System.out.println("\nlabels for object property isSpatiallyIncludedIn:");
		 * while (itLit.streamOpen()) { System.out.println(itLit.getNext()); } itLit.close();
		 * 
		 * ARTResourceIterator itRes = skosXLModel.getOWLModel().listPropertyDomains(
		 * isSpatiallyIncludedInProperty, true, aosCommonGraph);
		 * System.out.println("\ndomain declarations for object property isSpatiallyIncludedIn:"); while
		 * (itRes.streamOpen()) { System.out.println(itRes.getNext()); } itRes.close();
		 * 
		 * ARTURIResource invProp =
		 * skosXLModel.getOWLModel().getInverseProperty(isSpatiallyIncludedInProperty, true, aosCommonGraph);
		 * System.out.println("\ninverse property for object property isSpatiallyIncludedIn: " + invProp);
		 * 
		 * // this is just put to pause and wait for a key press by a user, just in case the underlying triple
		 * // store implementations syncronizes output every few milliseconds. For example, Sesame2 //
		 * Implementations syncronizes every second. So, without a pause, the repository would never be //
		 * persisted pause();
		 */

	}
}
