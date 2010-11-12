package fao.org.owl2skos.conversion;

import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.io.RDFFormat;
import it.uniroma2.art.owlart.model.ARTURIResource;
import it.uniroma2.art.owlart.model.NodeFilters;
import it.uniroma2.art.owlart.models.SKOSXLModel;
import it.uniroma2.art.owlart.vocabulary.XmlSchema;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import edu.stanford.smi.protegex.owl.model.OWLProperty;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.model.RDFSLiteral;
import edu.stanford.smi.protegex.owl.model.RDFSNames;
import fao.org.owl2skos.RunConversion;

public class OWL2SKOSConverter {

	public static final String SesameModelFactoryImplClassName = "it.uniroma2.art.owlart.sesame2impl.factory.ARTModelFactorySesame2Impl";
	public static final String agrovocBaseURI = "http://aims.fao.org/aos/agrovoc";
	public static final String agrovocSchemeURI = agrovocBaseURI + "/" + "agrovocScheme";
	public static final String agrovocDefNamespace = agrovocBaseURI + "/";
	public static final String agrovocDefNamespacePrefix = "agrovoc";

	public static OWLProperty rdfsComment;
	public static OWLProperty rdfsLabel;

	public static enum ResType {
		label, instance
	};

	protected static Logger logger = LoggerFactory.getLogger(OWL2SKOSConverter.class);

	Project protegeProject;
	edu.stanford.smi.protegex.owl.model.OWLModel protOWLModel;
	SKOSXLModel skosXLModel;

	public OWL2SKOSConverter(Project protegeProject, SKOSXLModel skosXLModel) {
		this.protegeProject = protegeProject;
		this.skosXLModel = skosXLModel;
		protOWLModel = (edu.stanford.smi.protegex.owl.model.OWLModel) protegeProject.getKnowledgeBase();

		Vocabulary.initialize(protOWLModel);

		logger.info("cdomainConcept: " + Vocabulary.c_domain_concept);

		rdfsLabel = protOWLModel.getOWLProperty(RDFSNames.Slot.LABEL);
		rdfsComment = protOWLModel.getOWLProperty(RDFSNames.Slot.COMMENT);

		// logger.info("hasLexicalization: " + hasLexicalization.getBrowserText());
	}

	public void convert() throws ModelUpdateException, ModelAccessException {

		ConversionPropertyLists.initialize(protOWLModel);

		// adds the agrovoc scheme as the unique scheme (and default scheme for SKOS API)
		initializeAgrovocScheme();

		// does a first round for rootConcepts, since they need to be reported as topConcepts for the above
		// scheme
		Collection<OWLNamedClass> rootConcepts = convertRootConcepts();

		// **************************************
		// CONCEPTS CONVERSION
		// *************************************
		int count = rootConcepts.size();
		Date d0 = new Date();
		int totalConcepts = countTotalConcepts();
		// recursive descent along the tree, converting all concepts
		for (OWLNamedClass cls : rootConcepts) {
			count = exploreConcept(cls, 0, count, totalConcepts, d0);
		}
	}

	/**
	 * @param ind
	 *            the instance representation of a concept in the AGROVOC OWL Vocabulary (see AGROVOC OWL
	 *            Vocabulary for details)
	 * @param skosConcept
	 *            the representation of the same concept in SKOS API
	 * @throws ModelUpdateException
	 * @throws ModelAccessException
	 */
	@SuppressWarnings("unchecked")
	public void convertInstance(OWLIndividual ind, ARTURIResource skosConcept) throws ModelUpdateException,
			ModelAccessException {

		// **************************************
		// CONCEPT PROPERTIES CONVERSION
		// *************************************

		// convertDatatypePlainLiteralLanguageProperty(ind, skosConcept, rdfsLabel);
		// convertDatatypePlainLiteralLanguageProperty(ind, skosConcept, rdfsComment);

		//System.out.print("|its properties...");
		
		for (OWLObjectProperty prop : ConversionPropertyLists.conceptToConcept) {
			convertObjectProperty(ind, skosConcept, prop, ResType.instance);
		}

		for (OWLDatatypeProperty prop : ConversionPropertyLists.conceptToPlainLiterals) {
			convertDatatypePlainLiteralLanguageProperty(ind, skosConcept, prop);
		}

		for (OWLDatatypeProperty prop : ConversionPropertyLists.conceptToTypeStrings) {
			convertDatatypeStringTypedProperty(ind, skosConcept, prop);
		}

		for (OWLDatatypeProperty prop : ConversionPropertyLists.conceptToTypeDates) {
			convertDatatypeDateTypedProperty(ind, skosConcept, prop);
		}

		// **************************************
		// ENTITY ANNOTATIONS CONVERSION
		// *************************************

		//System.out.print("|its entity annotations...");
		
		// HAS DEFINITION CONVERSION
		Collection<OWLIndividual> hasDefinitions = ind.getPropertyValues(Vocabulary.hasDefinition);
		for (OWLIndividual definition : hasDefinitions) {
			ARTURIResource newDef = convertDefinitionName(definition);

			// adding the hasDefinition triple with modified name
			skosXLModel.addTriple(skosConcept, skosXLModel.createURIResource(Vocabulary.hasDefinition
					.getURI()), newDef);

			// LABELS
			Collection<RDFSLiteral> labels = definition.getLabels();
			
			/*if (labels.size() != 1)
				throw new IllegalStateException(
						"there should be only one label per c_definition!, while here we have: " + labels);

			RDFSLiteral label = labels.iterator().next();
			skosXLModel.addLabel(newDef, label.getString(), label.getLanguage());*/
			
			for(RDFSLiteral label : labels){
				skosXLModel.addLabel(newDef, label.getString(), label.getLanguage());
			}
			// COMMENTS
			/*
			Collection<RDFSLiteral> comments = definition.getComments();
			
			// TODO modified to add multiple comments in different languages - pms
			if (comments.size() != 1)
				throw new IllegalStateException(
						"there should be only one comment per c_definition!, while here we have: " + comments);

			RDFSLiteral comment = comments.iterator().next();
			skosXLModel.addComment(newDef, comment.getString(), comment.getLanguage());
			for(RDFSLiteral comment : comments){
				skosXLModel.addComment(newDef, comment.getString(), comment.getLanguage());
			}
			*/
			
			// OTHER PROPERTIES OF DEFINITION
			convertDatatypeStringTypedProperty(definition, newDef, Vocabulary.takenFromSource);
			convertDatatypeStringTypedProperty(definition, newDef, Vocabulary.hasSourceLink);
			convertDatatypeDateTypedProperty(  definition, newDef, Vocabulary.hasDateCreated);
			convertDatatypeDateTypedProperty(  definition, newDef, Vocabulary.hasDateLastUpdated);			
		}
		
		// NO CONVERSION FOR HAS_IMAGE, SINCE THERE IS NO DATA FOR THAT
		

		// **************************************
		// LEXICALIZATIONS CONVERSION
		// *************************************

		// System.out.print("|its lexicalizations...");
		
		Collection<OWLIndividual> lexicalizations = ind.getPropertyValues(Vocabulary.hasLexicalization);
		for (OWLIndividual lexicalization : lexicalizations) {

			ARTURIResource skosLex = getSKOSXLLabelFromOWLCNOUN(lexicalization);

			// LABELS
			Collection<RDFSLiteral> labels = lexicalization.getLabels();
			if (labels.size() != 1)
				throw new IllegalStateException(
						"there should be only one label per Lexicalization!, while here we have: " + labels);

			RDFSLiteral label = labels.iterator().next();
			skosXLModel.addXLabel(skosLex.getURI(), label.getString(), label.getLanguage());

			// DETERMINING IF IT IS A PREFLABEL OR ALTLABEL

			Collection<Boolean> values = lexicalization.getPropertyValues(Vocabulary.isMainLabel);
			if (values.size() > 1) {
				throw new IllegalStateException(
						"there should be only one isMainLabel value per Lexicalization!, while here we have: "
								+ values);
			} else {
				boolean main;
				if (values.size() == 0)
					main = false;
				else {
					Boolean value = values.iterator().next();
					main = value.booleanValue();
				}

				if (main)
					skosXLModel.setPrefXLabel(skosConcept, skosLex, false);
				else {
					skosXLModel.addAltXLabel(skosConcept, skosLex);
				}
			}

			// LEXICALIZATION PROPERTIES CONVERSION

			for (OWLObjectProperty prop : ConversionPropertyLists.lexicalizationToLexicalization) {
				convertObjectProperty(lexicalization, skosLex, prop, ResType.label);
			}

			for (OWLDatatypeProperty prop : ConversionPropertyLists.lexicalizationToPlainLiterals) {
				convertDatatypePlainLiteralLanguageProperty(lexicalization, skosLex, prop);
			}

			for (OWLDatatypeProperty prop : ConversionPropertyLists.lexicalizationToTypeStrings) {
				convertDatatypeStringTypedProperty(lexicalization, skosLex, prop);
			}

			for (OWLDatatypeProperty prop : ConversionPropertyLists.lexicalizationToTypeDates) {
				convertDatatypeDateTypedProperty(lexicalization, skosLex, prop);
			}

			for (OWLDatatypeProperty prop : ConversionPropertyLists.lexicalizationToTypeInts) {
				convertDatatypeINTTypedProperty(lexicalization, skosLex, prop);
			}

		}
		
		//System.out.println("|concept converted");

	}

	@SuppressWarnings("unchecked")
	void convertDatatypePlainLiteralLanguageProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate) throws ModelUpdateException {
		Collection<?> values = owlSubject.getPropertyValues(predicate);
		for (Object obj : values) {
			if(obj instanceof RDFSLiteral)
			{
				RDFSLiteral value =  (RDFSLiteral) obj;	
				skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
						.createLiteral(value.getString(), value.getLanguage()));
			}
			else if(obj instanceof OWLIndividual)
			{
				OWLIndividual value =  (OWLIndividual) obj;
				Collection<RDFSLiteral> labels = value.getLabels();
				for(RDFSLiteral label : labels){
					skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
							.createLiteral(label.getString(), label.getLanguage()));
				}
				
			}
			else
			{
				System.out.println("predicate: "+predicate.getURI() +"   subject: "+owlSubject.getURI());
				System.out.println("Not converted: "+ obj);
			}
		}
	}

	@SuppressWarnings("unchecked")
	void convertDatatypeStringTypedProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate) throws ModelUpdateException {
		Collection<String> values = owlSubject.getPropertyValues(predicate);
		for (String value : values) {
			skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
					.createLiteral(value.toString(), XmlSchema.Res.STRING));
		}
	}

	// NOT SURE IF VALUE IS A DATE!!!
	@SuppressWarnings("unchecked")
	void convertDatatypeDateTypedProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate) throws ModelUpdateException {
		Collection<String> values = owlSubject.getPropertyValues(predicate);
		for (String value : values) {
			skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
					.createLiteral(value.toString(), XmlSchema.Res.DATE));
		}
	}

	@SuppressWarnings("unchecked")
	void convertDatatypeINTTypedProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate) throws ModelUpdateException {
		Collection<Integer> values = owlSubject.getPropertyValues(predicate);
		for (Integer value : values) {
			skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
					.createLiteral(value.toString(), XmlSchema.Res.INT));
		}
	}

	/**
	 * conversion from/to object properties. The <code>resType</code> argument specifies if the destination
	 * resources involved in the conversion are SKOS-XL labels (label) or skos concepts (instance) and thus
	 * drives the conversion of their URI.<br/>
	 * This method is invoked when a property from the AGROVOC OWL vocabulary is being converted into a native
	 * SKOS(XL) property or in any case, its name is being changed.
	 * 
	 * @param owlSubject
	 *            the subject of the triples in the Protege OWL Model
	 * @param skosSubject
	 *            the subject of the triples in the SKOS Conversion
	 * @param owlPredicate
	 *            the predicate of the triples in the Protege OWL Model
	 * @param skosPredicate
	 *            the predicate of the triples in the SKOS Conversion
	 * @param resType
	 *            specifies if the destination resources involved in the conversion are SKOS-XL labels (label)
	 *            or skos concepts (instance) and thus drives the conversion of their URI
	 * @throws ModelUpdateException
	 */
	@SuppressWarnings("unchecked")
	void convertObjectProperty(RDFResource owlSubject, ARTURIResource skosSubject, RDFProperty owlPredicate,
			ARTURIResource skosPredicate, ResType resType) throws ModelUpdateException {
		Collection<RDFResource> values = owlSubject.getPropertyValues(owlPredicate);
		for (RDFResource value : values) {
			ARTURIResource object = null;
			if (resType == ResType.label)
				object = getSKOSXLLabelFromOWLCNOUN(value);
			else if (resType == ResType.instance)
				object = getSKOSConceptFromOWLInstance(value);
			skosXLModel.addTriple(skosSubject, skosPredicate, object);
		}
	}

	/**
	 * as for
	 * {@link #convertObjectProperty(RDFResource, ARTURIResource, RDFProperty, ARTURIResource, ResType)} with
	 * the <code>predicate</code> being passed as <code>owlPredicate</code> of that method, and this same
	 * predicate being converted to SKOS-XL API resource, with the same URI. <br/>
	 * This method is being used when a property is left as it is in the new vocabulary.
	 * 
	 * @param owlSubject
	 *            the subject of the triples in the Protege OWL Model
	 * @param skosSubject
	 *            the subject of the triples in the SKOS Conversion
	 * @param predicate
	 *            the predicate
	 * @param resType
	 *            specifies if the destination resources involved in the conversion are SKOS-XL labels (label)
	 *            or skos concepts (instance) and thus drives the conversion of their URI
	 * @throws ModelUpdateException
	 */
	void convertObjectProperty(RDFResource owlSubject, ARTURIResource skosSubject, RDFProperty predicate,
			ResType resType) throws ModelUpdateException {
		convertObjectProperty(owlSubject, skosSubject, predicate, skosXLModel.createURIResource(predicate
				.getURI()), resType);
	}

	public int countConcept(OWLNamedClass cls, int count) throws ModelUpdateException, ModelAccessException {
		Collection<OWLNamedClass> subConcepts = cls.getSubclasses(false);
		for (OWLNamedClass subCls : subConcepts) {
			count++;
			count = countConcept(subCls, count);
		}
		return count;
	}
	
	public int countTotalConcepts() throws ModelUpdateException, ModelAccessException {
		Collection<OWLNamedClass> rootConcepts = convertRootConcepts();
		System.out.println("TOTAL TOP CONCEPTS FOUND : " + rootConcepts.size());
		int count = rootConcepts.size();
		// recursive descent along the tree, converting all concepts
		for (OWLNamedClass cls : rootConcepts) {
			count = countConcept(cls, count);
		}
		System.out.println("TOTAL CONCEPTS FOUND : " + count);
		return count;
	}
	
	@SuppressWarnings("unchecked")
		public int exploreConcept(OWLNamedClass cls, int level, int count, int totalCount, Date d0) throws ModelUpdateException, ModelAccessException {
		Collection<OWLIndividual> instances = cls.getInstances(false);
		Date d1 = new Date();
		if (instances.size() != 1)
			throw new IllegalStateException(
					"no domain class in the OWL AGROVOC Model should have other than one instance, while: "
							+ cls + " has these instances: " + instances);
		OWLIndividual inst = instances.iterator().next();
		ARTURIResource skosConcept = skosXLModel.createURIResource(cls.getURI());
		convertInstance(inst, skosConcept);
		Date d2 = new Date();
		Collection<OWLNamedClass> subConcepts = cls.getSubclasses(false);
		
		String timediff = RunConversion.getTimeDifference(d2, d1);
		
		String tabs = "";
		for(int i=0; i<level; i++)
			tabs +="|\t";
		tabs+="|--";
		level++;
		String format = "%5d of %5d - in: %s - ET: %s :: %-80s\n";
		System.out.format( format, count, totalCount, timediff, RunConversion.getTimeDifference(d2, d0) , tabs + "[" + cls.getURI() +"]");

		for (OWLNamedClass subCls : subConcepts) {
			skosXLModel.addConcept(subCls.getURI(), skosConcept);
			count++;
			count = exploreConcept(subCls, level, count, totalCount, d0);
		}
		return count;
	}

	public void saveConversion(File outputFile) throws IOException, ModelAccessException,
			UnsupportedRDFFormatException {
		skosXLModel.writeRDF(outputFile, RDFFormat.guessRDFFormatFromFile(outputFile), NodeFilters.MAINGRAPH);
	}

	protected void initializeAgrovocScheme() throws ModelUpdateException {
		ARTURIResource agrovocSchema = skosXLModel.addSKOSConceptScheme(agrovocSchemeURI);
		skosXLModel.setDefaultSchema(agrovocSchema);
	}

	@SuppressWarnings("unchecked")
	protected Collection<OWLNamedClass> convertRootConcepts() throws ModelUpdateException {
		Collection<OWLNamedClass> rootConcepts = Vocabulary.c_domain_concept.getSubclasses(false);
		for (OWLNamedClass cls : rootConcepts) {
			skosXLModel.addConcept(cls.getURI(), NodeFilters.NONE);
		}
		return rootConcepts;
	}

	protected ARTURIResource getSKOSConceptFromOWLInstance(RDFResource owlInstance) {
		String conceptLocalName = owlInstance.getLocalName().replace("i_", "c_");
		return skosXLModel.createURIResource(skosXLModel.getDefaultNamespace() + conceptLocalName);
	}

	protected ARTURIResource getSKOSXLLabelFromOWLCNOUN(RDFResource owlInstance) {
		String conceptLocalName = owlInstance.getLocalName().replace("i_", "xl_");
		return skosXLModel.createURIResource(skosXLModel.getDefaultNamespace() + conceptLocalName);
	}

	protected ARTURIResource convertDefinitionName(RDFResource owlInstance) {
		String conceptLocalName = owlInstance.getLocalName().replace("i_", "c_");
		return skosXLModel.createURIResource(skosXLModel.getDefaultNamespace() + conceptLocalName);
	}

}
