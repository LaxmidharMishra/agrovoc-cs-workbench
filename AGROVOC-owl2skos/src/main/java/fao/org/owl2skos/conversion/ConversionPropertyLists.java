package fao.org.owl2skos.conversion;

import java.util.Collections;
import java.util.HashSet;

import com.google.common.collect.Collections2;

import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;

/**
 * This class provides static collections of properties to be converted. They are grouped according to their
 * nature (see document text files in CONVERSION-NOTES directory in this same project). Apart from the
 * collections contained in this class, specific management has to be done for:
 * <ul>
 * <li>ENTITY ANNOTATIONs, providing reified descriptions for concepts: to be converted as they are,
 * respecting their full structure (image, descriptions, their associated datatype properties etc...)</li>
 * <li>SKOS-converted properties</li>
 * <li>rdfs:comment and rdfs:label added later</li>
 * </ul>
 * 
 * @author Armando Stellato <stellato@info.uniroma2.it>
 */
public class ConversionPropertyLists {

	// ***********
	// CONCEPTS
	// ***********

	// object properties
	public static HashSet<OWLObjectProperty> conceptToConcept = new HashSet<OWLObjectProperty>();

	// datatype properties
	public static HashSet<OWLDatatypeProperty> conceptToPlainLiterals = new HashSet<OWLDatatypeProperty>();
	public static HashSet<OWLDatatypeProperty> conceptToTypeStrings = new HashSet<OWLDatatypeProperty>();
	public static HashSet<OWLDatatypeProperty> conceptToTypeDates = new HashSet<OWLDatatypeProperty>();

	// *****************
	// LEXICALIZATIONS
	// *****************

	// object properties
	public static HashSet<OWLObjectProperty> lexicalizationToLexicalization = new HashSet<OWLObjectProperty>();

	// datatype properties
	public static HashSet<OWLDatatypeProperty> lexicalizationToPlainLiterals = new HashSet<OWLDatatypeProperty>();
	public static HashSet<OWLDatatypeProperty> lexicalizationToTypeStrings = new HashSet<OWLDatatypeProperty>();
	public static HashSet<OWLDatatypeProperty> lexicalizationToTypeDates = new HashSet<OWLDatatypeProperty>();
	public static HashSet<OWLDatatypeProperty> lexicalizationToTypeInts = new HashSet<OWLDatatypeProperty>();

	// ************************************************
	// LIST OF DISCARDED OBJECT PROPERTIES (INVERSEOF)
	// ************************************************

	public static HashSet<OWLObjectProperty> discardedObjectProperties = new HashSet<OWLObjectProperty>();

	public static void initialize(OWLModel owlModel) {

		System.out.println("Initialization of property conversion lists: STARTED");

		// ***********
		// CONCEPTS
		// ***********

		// object properties

		conceptToConcept.add(owlModel.getOWLObjectProperty("hasMappedDomainConcept"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("belongsToCategory"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isParentOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasParent"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasType"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("typeOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("actsUpon"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("affects"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("afflicts"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("benefitsFrom"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("bibliographicRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("causativeRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("causes"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("compose"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("controls"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("developsFrom"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("developsInto"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("essiveRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("follows"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("formerlyIncludedIn"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("formerlyIncludes"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("greaterThan"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("growsln"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasAntonym"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasBiologicalControlAgent"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasBreedingMethod"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasClass"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasComponent"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasComposition"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasControlMethod"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasCropingSystem"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasCultivationProcess"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasDisease"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasDisorder"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasFamily"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasGenus"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasGoalOrProcess"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasGreatGroup"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasHost"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasInfectionPart"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasMember"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasNaturalEnemy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasObjectOfActivity"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasOrder"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPart"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPathogen"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPest"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPhysiologicalFunction"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPortion"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPostProductionPractice"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPractice"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasProduct"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPropagationMaterial"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasPropagationProcess"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasProperty"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasRelatedType"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasScopenoteRelatedToConcept"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasSpecies"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasSubOrder"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasSubstitute"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasSymptom"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasTaxonomicLevel"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasTheme"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasVariety"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasWeed"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includes"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesFamily"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesGenus"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesGreatGroup"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesOrder"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesSpecies"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesSubGroup"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesSubprocess"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("includesSubspecies"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("indicates"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("influences"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("instrumentalRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isAGrowthEnvironmentFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isAchievedByMeansOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isActedUponBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isAffectedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isAfflictedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isBeneficialFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isBiologicalControlAgentOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isBreedingMethodOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isCausedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isComponentOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isComposedOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isCompositionOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isControlMethodOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isControlledBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isCropingSystemOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isCultivationProcessOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isDerivedFrom"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isDiseaseFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isDisorderOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isHostFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isIncludedIn"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isInfectedPartOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isInfluencedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isInputFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isMadeFrom"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isMeansFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isMeasuredBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isMemberOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isNaturalEnemyOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isObjectOfActivity"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isOtherLanguageEditionOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isOutputFrom"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPartOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPathogenOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPerformedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPerformedByMeansOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPhysiologicalFunctionOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPortionOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPostProductionPracticeFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPracticeFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPreventedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isProcessFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isProducedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPropagationMaterialOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPropagationProcessOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPropertyOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isPublishedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isReferencedInConceptScopenote"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isRelatedTypeOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isSourceOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isSpatiallyIncludedIn"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isStudiedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isSubprocessOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isSubstituteFor"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isTaxonomicLevelOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isThemeOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isUseOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isUsedAs"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isUsedIn"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isUsedToMake"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("isWeedOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("makeUseOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("measures"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("partitiveRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("performs"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("pestOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("precedes"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("prevents"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("produces"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("productOf"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("publishes"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("quantitativeRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("smallerThan"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("spatialRelationships"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("spatiallyIncludes"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("study"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("surroundedBy"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("surrounds"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("taxonomicRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("temporalRelationship"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("usesProcess"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("usingValue"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("valueUsedIn"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasParent"));
		conceptToConcept.add(owlModel.getOWLObjectProperty("hasType"));

		// datatype properties

		conceptToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasCallNumber"));
		conceptToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasEditorialNote"));
		conceptToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasScopeNote"));

		conceptToTypeStrings.add(owlModel.getOWLDatatypeProperty("isPartOfSubvocabulary"));
		conceptToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasDate"));
		conceptToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasStatus"));

		conceptToTypeDates.add(owlModel.getOWLDatatypeProperty("hasDateCreated"));
		conceptToTypeDates.add(owlModel.getOWLDatatypeProperty("hasDateLastUpdated"));

		// *****************
		// LEXICALIZATIONS
		// *****************

		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("correspondsTo"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasAbbreviation"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasAcronym"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasBroaderSynonym"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasChemicalFormula"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasDialectalVariant"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasLocalName"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasNarrowerSynonym"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasNearSynonym"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasOldName"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasRelatedTerm"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasScientificName"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasScopenoteRelatedToTerm"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasSymbol"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasSynonym"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasTradeName"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasTranslation"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("hasTransliteration"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isAbbreviationOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isAcronymOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isChemicalFormulaOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isDialectalVariantOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isLocalNameOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isOldNameOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isSymbolFor"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isTermReferencedInScopenote"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isTradeNameOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("isTransliterationOf"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("sameAs"));
		lexicalizationToLexicalization.add(owlModel.getOWLObjectProperty("scientificNameOf"));

		lexicalizationToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasPlural"));
		lexicalizationToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasSingular"));
		lexicalizationToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasSpellingVariant"));
		lexicalizationToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasStemmedForm"));
		lexicalizationToPlainLiterals.add(owlModel.getOWLDatatypeProperty("hasTermVariant"));

		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasTermType"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCodeISO3Country"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCode"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCodeAsc"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCodeAsfa"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCodeFaoPa"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCodeFishery3Alpha"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasCodeTaxonomic"));
		lexicalizationToTypeStrings.add(owlModel.getOWLDatatypeProperty("hasStatus"));

		lexicalizationToTypeDates.add(owlModel.getOWLDatatypeProperty("hasDateCreated"));
		lexicalizationToTypeDates.add(owlModel.getOWLDatatypeProperty("hasDateLastUpdated"));

		lexicalizationToTypeInts.add(owlModel.getOWLDatatypeProperty("hasCodeAgrovoc"));
		lexicalizationToTypeInts.add(owlModel.getOWLDatatypeProperty("hasCodeFaoterm"));

		// ************************************************
		// LIST OF DISCARDED OBJECT PROPERTIES (INVERSEOF)
		// ************************************************

		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isReferencedInConceptScopenote"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isTradeNameOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isTermReferencedInScopenote"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isAffectedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isActedUponBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isImageOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isSpatiallyIncludedIn"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("scientificNameOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPhysiologicalFunctionOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isAfflictedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isHostFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("indicates"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isBiologicalControlAgentOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPerformedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesGreatGroup"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("formerlyIncludedIn"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isUseOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPropagationMaterialOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isInputFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isSourceOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isRelatedTypeOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isAchievedByMeansOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("belongsToScheme"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPreventedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isSymbolFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isStudiedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("greaterThan"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("hasNarrowerSynonym"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isCropingSystemOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("productOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isThemeOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isMeasuredBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("typeOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPartOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("correspondsTo"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("valueUsedIn"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesGenus"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isDefinitionOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPostProductionPracticeFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isCultivationProcessOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isCausedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesFamily"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPathogenOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isAbbreviationOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPortionOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isParentOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesOrder"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isLocalNameOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isSubstituteFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isAGrowthEnvironmentFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesSubGroup"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isProducedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesSpecies"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isControlledBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("belongsToCategory"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isWeedOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("includesSubspecies"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isProcessFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isUsedIn"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isSubprocessOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("surrounds"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isUsedToMake"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isChemicalFormulaOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPublishedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("compose"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isObjectOfActivity"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isBeneficialFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isAcronymOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isNaturalEnemyOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("pestOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isInfectedPartOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isMemberOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isDialectalVariantOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isTaxonomicLevelOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isDisorderOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isTransliterationOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isIncludedIn"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPerformedByMeansOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPropagationProcessOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isInfluencedBy"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isOldNameOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPropertyOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isDiseaseFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isControlMethodOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isPracticeFor"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isBreedingMethodOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isCompositionOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isComponentOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("developsFrom"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("precedes"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isSubCategoryOf"));
		discardedObjectProperties.add(owlModel.getOWLObjectProperty("isLexicalizationOf"));

		// ************************************************
		// REMOVING REDUNDANT (INVERSEOF) PROPERTIES FROM OBJECT PROPERTY LISTS
		// ************************************************

		for (OWLObjectProperty prop : discardedObjectProperties) {
			conceptToConcept.remove(prop);
			lexicalizationToLexicalization.remove(prop);
		}

		System.out.println("Initialization of property conversion lists: ENDED");

	}

}
