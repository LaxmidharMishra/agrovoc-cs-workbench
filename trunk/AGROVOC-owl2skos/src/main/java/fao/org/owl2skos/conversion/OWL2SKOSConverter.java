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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.model.RDFSLiteral;

public class OWL2SKOSConverter {

	public static final String SesameModelFactoryImplClassName = "it.uniroma2.art.owlart.sesame2impl.factory.ARTModelFactorySesame2Impl";
	public static final String agrovocBaseURI = "http://aims.fao.org/aos/agrovoc";
	public static final String agrovocDefNamespace = agrovocBaseURI + "/";

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

		// logger.info("hasLexicalization: " + hasLexicalization.getBrowserText());
	}

	public void convert() throws ModelUpdateException, ModelAccessException {

		// adds the agrovoc scheme as the unique scheme (and default scheme for SKOS API)
		initializeAgrovocScheme();

		// does a first round for rootConcepts, since they need to be reported as topConcepts for the above
		// scheme
		Collection<OWLNamedClass> rootConcepts = convertRootConcepts();

		// recursive descent along the tree, converting all concepts
		for (OWLNamedClass cls : rootConcepts) {
			exploreConcept(cls);
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
	public void convertInstance(OWLIndividual ind, ARTURIResource skosConcept) throws ModelUpdateException,
			ModelAccessException {

		// **************************************
		// LEXICALIZATION CONVERSION
		// *************************************		
		
		Collection<OWLIndividual> lexicalizations = ind.getPropertyValues(Vocabulary.hasLexicalization);
		for (OWLIndividual lexicalization : lexicalizations) {

			// label
			Collection<RDFSLiteral> labels = lexicalization.getLabels();
			if (labels.size() != 1)
				throw new IllegalStateException(
						"there should be only one label per Lexicalization!, while here we have: " + labels);
			ARTURIResource skosLex = getSKOSXLLabelFromOWLCNOUN(lexicalization);
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
			
			// DATATYPE RELATIONSHIPS FOR LEXICALIZATON

			// hasCodeAgrovoc code
			convertPlainDatatypeINTProperty(lexicalization, skosLex, Vocabulary.hasCodeAgrovoc);

			// hasCodeFaoterm code
			convertPlainDatatypeINTProperty(lexicalization, skosLex, Vocabulary.hasCodeFaoterm);

			// TODO is it concrete?
			// hasCode code
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasCode);

			// hasCodeFaoPa code
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasCodeFaoPa);

			// hasCodeAsc code
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasCodeAsc);

			// hasCodeAsfa code
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasCodeAsfa);

			// hasCodeTaxonomic code
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasCodeTaxonomic);

			// TODO this is no more in the vocabulary I think! I removed this cause it is throwing a null
			// pointer exception due to a null reference
			// hasFishery3AlphaCode code
			// logger.info("predicate hasFishery3AlphaCode: " + Vocabulary.hasFishery3AlphaCode);
			// convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasFishery3AlphaCode);

			// hasSpellingVariant
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasSpellingVariant);

			// hasDateCreated
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasDateCreated);

			// hasDateLastUpdated
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasDateLastUpdated);

			// hasStatus
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasStatus);

			// hasTermType
			convertPlainDatatypeStringProperty(lexicalization, skosLex, Vocabulary.hasTermType);

			// OBJECT PROPERTY RELATIONSHIPS FOR LEXICALIZATON

			// hasTranslation
			convertPlainObjectProperty(lexicalization, skosLex, Vocabulary.hasTranslation, ResType.label);

			// TODO is it concrete?
			// hasRelatedTerm
			convertPlainObjectProperty(lexicalization, skosLex, Vocabulary.hasRelatedTerm, ResType.label);

			// isAbbreviationOf
			convertPlainObjectProperty(lexicalization, skosLex, Vocabulary.isAbbreviationOf, ResType.label);

			// hasAcronym
			convertPlainObjectProperty(lexicalization, skosLex, Vocabulary.hasAcronym, ResType.label);

			// hasSynonym
			convertPlainObjectProperty(lexicalization, skosLex, Vocabulary.hasSynonym, ResType.label);

			// hasBroaderSynonym
			convertPlainObjectProperty(lexicalization, skosLex, Vocabulary.hasBroaderSynonym, ResType.label);

			// **************************************
			// END OF LEXICALIZATION CONVERSION!
			// *************************************
			
		}

		// **************************************
		// DIRECT CONCEPT PROPERTIES CONVERSION
		// *************************************

		// DATATYPE PROPERTIES
		
		// hasScopeNote
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasScopeNote);
		
		// hasEditorialNote
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasEditorialNote);
		
		// takenFromSource
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.takenFromSource);
		
		// hasSourceLink
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasSourceLink);
		
		// hasImageLink
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasImageLink);
		
		// hasImageSource
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasImageSource);
		
		// hasNumber
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasNumber);
		
		// hasDate
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasDate);
		
		// isSpatiallyIncludedInCity
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.isSpatiallyIncludedInCity);
		
		// isSpatiallyIncludedInState
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.isSpatiallyIncludedInState);
		
		// isPartOfSubvocabulary
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.isPartOfSubvocabulary);

		// hasIssn
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasIssn);
		
		// hasIssnL
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasIssnL);
		
		// isHoldBy
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.isHoldBy);
		
		// hasCallNumber
		convertPlainDatatypeStringProperty(ind, skosConcept, Vocabulary.hasCallNumber);
		
		
		
		
		// OBJECT PROPERTIES
				         	
		// isSpatiallyIncludedIn
		convertPlainObjectProperty(ind, skosConcept, Vocabulary.isSpatiallyIncludedIn, ResType.instance);	           	
		            	
		// isPartOf
		convertPlainObjectProperty(ind, skosConcept, Vocabulary.isPartOf, ResType.instance);   	
		               	
		// isPublishedBy
		convertPlainObjectProperty(ind, skosConcept, Vocabulary.isPublishedBy, ResType.instance);
                 	
		// isOtherLanguageEditionOf
		convertPlainObjectProperty(ind, skosConcept, Vocabulary.isOtherLanguageEditionOf, ResType.instance);
		
		// follows
		convertPlainObjectProperty(ind, skosConcept, Vocabulary.follows, ResType.instance);
		
		// precedes
		convertPlainObjectProperty(ind, skosConcept, Vocabulary.precedes, ResType.instance);
		

		
	}

	void convertPlainDatatypeStringProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate) throws ModelUpdateException {
		Collection<String> values = owlSubject.getPropertyValues(predicate);
		for (String value : values) {
			skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
					.createLiteral(value.toString(), XmlSchema.STRING));
		}
	}

	void convertPlainDatatypeINTProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate) throws ModelUpdateException {
		Collection<Integer> values = owlSubject.getPropertyValues(predicate);
		for (Integer value : values) {
			skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), skosXLModel
					.createLiteral(value.toString(), XmlSchema.INT));
		}
	}

	void convertPlainObjectProperty(RDFResource owlSubject, ARTURIResource skosSubject,
			RDFProperty predicate, ResType resType) throws ModelUpdateException {
		Collection<RDFResource> values = owlSubject.getPropertyValues(predicate);
		for (RDFResource value : values) {
			ARTURIResource object = null;
			if (resType == ResType.label)
				object = getSKOSXLLabelFromOWLCNOUN(value);
			else if (resType == ResType.instance)
				object = getSKOSConceptFromOWLInstance(value);
			skosXLModel.addTriple(skosSubject, skosXLModel.createURIResource(predicate.getURI()), object);
		}
	}

	public void exploreConcept(OWLNamedClass cls) throws ModelUpdateException, ModelAccessException {
		Collection<OWLIndividual> instances = cls.getInstances(false);
		if (instances.size() != 1)
			throw new IllegalStateException(
					"no domain class in the OWL AGROVOC Model should have other than one instance, while: "
							+ cls + " has these instances: " + instances);
		OWLIndividual inst = instances.iterator().next();
		ARTURIResource skosConcept = skosXLModel.createURIResource(cls.getURI());
		convertInstance(inst, skosConcept);

		Collection<OWLNamedClass> subConcepts = cls.getSubclasses(false);

		for (OWLNamedClass subCls : subConcepts) {
			skosXLModel.addConcept(subCls.getURI(), skosConcept);
			exploreConcept(subCls);
		}
	}

	public void saveConversion(File outputFile) throws IOException, ModelAccessException,
			UnsupportedRDFFormatException {
		skosXLModel.writeRDF(outputFile, RDFFormat.guessRDFFormatFromFile(outputFile), NodeFilters.MAINGRAPH);
	}

	protected void initializeAgrovocScheme() throws ModelUpdateException {
		ARTURIResource agrovocSchema = skosXLModel.addSKOSConceptScheme(agrovocBaseURI + "/"
				+ "agrovocScheme");
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

}
