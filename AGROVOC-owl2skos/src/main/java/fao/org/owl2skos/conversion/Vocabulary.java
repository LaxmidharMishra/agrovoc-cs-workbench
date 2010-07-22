package fao.org.owl2skos.conversion;

import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.RDFProperty;

public class Vocabulary {

	
	public static final String c_domain_conceptName       						= "c_domain_concept";        
	public static final String c_lexicalizationName       						= "c_lexicalization";        
	public static final String c_definitionName           						= "c_definition";            
	public static final String c_imageName                						= "c_image";                 
	public static final String c_geographic_conceptName   						= "c_geographic_concept";    
	public static final String c_scientific_termName      						= "c_scientific_term";       
	public static final String c_nounName                 						= "c_noun";                  
	public static final String c_categoryName             						= "c_category";              
	public static final String c_classification_schemeName						= "c_classification_scheme"; 
	
	
	public static final String hasTranslationName           	= "hasTranslation";           
	public static final String hasStatusName                  	= "hasStatus";                
	public static final String hasRelatedConceptName          	= "hasRelatedConcept";        
	public static final String hasRelatedTermName             	= "hasRelatedTerm";           
	public static final String hasLexicalizationName          	= "hasLexicalization";        
	public static final String isLexicalizationOfName         	= "isLexicalizationOf";       
	public static final String hasDateCreatedName             	= "hasDateCreated";           
	public static final String hasDateLastUpdatedName         	= "hasDateLastUpdated";       
	public static final String hasCodeName                    	= "hasCode";                  
	public static final String isMainLabelName                	= "isMainLabel";              
	public static final String hasDefinitionName              	= "hasDefinition";            
	public static final String isDefinitionOfName             	= "isDefinitionOf";           
	public static final String hasCodeAgrovocName             	= "hasCodeAgrovoc";           
	public static final String hasCodeFaoPaName               	= "hasCodeFaoPa";             
	public static final String hasCodeAscName                 	= "hasCodeAsc";               
	public static final String hasCodeAsfaName                	= "hasCodeAsfa";              
	public static final String hasCodeTaxonomicName           	= "hasCodeTaxonomic";         
	public static final String hasCodeFaotermName             	= "hasCodeFaoterm";           
	public static final String hasFishery3AlphaCodeName       	= "hasFishery3AlphaCode";     
	public static final String hasSpellingVariantName         	= "hasSpellingVariant";       
	public static final String hasScopeNoteName               	= "hasScopeNote";             
	public static final String hasEditorialNoteName           	= "hasEditorialNote";         
	public static final String hasCategoryName                	= "hasCategory";              
	public static final String takenFromSourceName            	= "takenFromSource";          
	public static final String hasSourceLinkName              	= "hasSourceLink";            
	public static final String hasImageName                   	= "hasImage";                 
	public static final String isImageOfName                  	= "isImageOf";                
	public static final String hasImageLinkName               	= "hasImageLink";             
	public static final String hasImageSourceName             	= "hasImageSource";           
	public static final String hasMappedDomainConceptName     	= "hasMappedDomainConcept";   
	public static final String isSubCategoryOfName            	= "isSubCategoryOf";          
	public static final String hasSubCategoryName             	= "hasSubCategory";           
	public static final String belongsToSchemeName            	= "belongsToScheme";          
	public static final String hasName                        	= "has";                      
	public static final String isName                         	= "is";                       
	public static final String SubCategoryName                	= "SubCategory";              
	public static final String SubCategoryOfName              	= "SubCategoryOf";            
	public static final String isAbbreviationOfName           	= "isAbbreviationOf";         
	public static final String hasAcronymName                 	= "hasAcronym";               
	public static final String hasSynonymName                 	= "hasSynonym";               
	public static final String isReferencedInAnnotationOfName 	= "isReferencedInAnnot";      
	public static final String thesaurusRelationshipName     	= "thesaurusRelationship";    
	public static final String subclassOfName                	= "subclassOf";               
	public static final String hasSubclassName               	= "hasSubclass";              
	public static final String isInstanceOfName              	= "isInstanceOf";             
	public static final String hasInstanceName               	= "hasInstance";              
	public static final String isSpatiallyIncludedInName     	= "isSpatiallyIncludedIn";    
	public static final String isPartOfName                  	= "isPartOf";                 
	public static final String isPublishedByName             	= "isPublishedBy";            
	public static final String hasNumberName                 	= "hasNumber";                
	public static final String hasDateName                   	= "hasDate";                   
	public static final String isSpatiallyIncludedInCityName 	= "isSpatiallyIncludedInCity"; 
	public static final String isSpatiallyIncludedInStateName	= "isSpatiallyIncludedInState";
	
	public static final String hasTermTypeName					= "hasTermType";
	public static final String hasBroaderSynonymName			= "hasBroaderSynonym";
	
	public static final String isPartOfSubvocabularyName		= "isPartOfSubvocabulary"	 ;
	public static final String hasIssnName						= "hasIssn"					 ;
	public static final String hasIssnLName                  	= "hasIssnL"                 ;
	public static final String isHoldByName                  	= "isHoldBy"                 ;
	public static final String hasCallNumberName      			= "hasCallNumber"      		 ;
	public static final String isOtherLanguageEditionOfName 	= "isOtherLanguageEditionOf" ;
	public static final String followsName                   	= "follows"                  ;
	public static final String precedesName                 	= "precedes"                 ;
	
	
	public static OWLNamedClass c_domain_concept;        
	public static OWLNamedClass c_lexicalization;        
	public static OWLNamedClass c_definition;            
	public static OWLNamedClass c_image;                 
	public static OWLNamedClass c_geographic_concept;    
	public static OWLNamedClass c_scientific_term;       
	public static OWLNamedClass c_noun;                  
	public static OWLNamedClass c_category;              
	public static OWLNamedClass c_classification_scheme; 
	
	
	public static RDFProperty	hasTranslation          ;
	public static RDFProperty	hasStatus               ;
	public static RDFProperty	hasRelatedConcept       ;
	public static RDFProperty	hasRelatedTerm          ;
	public static RDFProperty	hasLexicalization       ;
	public static RDFProperty	isLexicalizationOf      ;
	public static RDFProperty	hasDateCreated          ;
	public static RDFProperty	hasDateLastUpdated      ;
	public static RDFProperty	hasCode                 ;
	public static RDFProperty	isMainLabel             ;
	public static RDFProperty	hasDefinition           ;
	public static RDFProperty	isDefinitionOf          ;
	public static RDFProperty	hasCodeAgrovoc          ;
	public static RDFProperty	hasCodeFaoPa            ;
	public static RDFProperty	hasCodeAsc              ;
	public static RDFProperty	hasCodeAsfa             ;
	public static RDFProperty	hasCodeTaxonomic        ;
	public static RDFProperty	hasCodeFaoterm          ;
	public static RDFProperty	hasFishery3AlphaCode    ;
	public static RDFProperty	hasSpellingVariant      ;
	public static RDFProperty	hasScopeNote            ;
	public static RDFProperty	hasEditorialNote        ;
	public static RDFProperty	hasCategory             ;
	public static RDFProperty	takenFromSource         ;
	public static RDFProperty	hasSourceLink           ;
	public static RDFProperty	hasImage                ;
	public static RDFProperty	isImageOf               ;
	public static RDFProperty	hasImageLink            ;
	public static RDFProperty	hasImageSource          ;
	public static RDFProperty	hasMappedDomainConcept  ;
	public static RDFProperty	isSubCategoryOf         ;
	public static RDFProperty	hasSubCategory          ;
	public static RDFProperty	belongsToScheme         ;
	public static RDFProperty	has                     ;
	public static RDFProperty	is                      ;
	public static RDFProperty	SubCategory             ;
	public static RDFProperty	SubCategoryOf           ;
	public static RDFProperty	isAbbreviationOf        ;
	public static RDFProperty	hasAcronym              ;
	public static RDFProperty	hasSynonym              ;
	public static RDFProperty	isReferencedInAnnot     ;
	public static RDFProperty	thesaurusRelationship      ;
	public static RDFProperty	subclassOf                 ;
	public static RDFProperty	hasSubclass                ;
	public static RDFProperty	isInstanceOf               ;
	public static RDFProperty	hasInstance                ;
	public static RDFProperty	isSpatiallyIncludedIn      ;
	public static RDFProperty	isPartOf                   ;
	public static RDFProperty	isPublishedBy              ;
	public static RDFProperty	hasNumber                  ;
	public static RDFProperty	hasDate                    ; 
	public static RDFProperty	isSpatiallyIncludedInCity  ; 
	public static RDFProperty	isSpatiallyIncludedInState ; 
	
	public static RDFProperty	hasTermType;
	public static RDFProperty	hasBroaderSynonym;
	
	public static RDFProperty isPartOfSubvocabulary	 ;
	public static RDFProperty hasIssn				;
	public static RDFProperty hasIssnL                ;
	public static RDFProperty isHoldBy                ;
	public static RDFProperty hasCallNumber      	;
	public static RDFProperty isOtherLanguageEditionOf;
	public static RDFProperty follows                 ;
	public static RDFProperty precedes                ;

	
	
	public static void initialize(OWLModel owlModel) {
		
		c_domain_concept        = owlModel.getOWLNamedClass(c_domain_conceptName       );
		c_lexicalization        = owlModel.getOWLNamedClass(c_lexicalizationName       );
		c_definition            = owlModel.getOWLNamedClass(c_definitionName           );
		c_image                 = owlModel.getOWLNamedClass(c_imageName                );
		c_geographic_concept    = owlModel.getOWLNamedClass(c_geographic_conceptName   );
		c_scientific_term       = owlModel.getOWLNamedClass(c_scientific_termName      );
		c_noun                  = owlModel.getOWLNamedClass(c_nounName                 );
		c_category              = owlModel.getOWLNamedClass(c_categoryName             );
		c_classification_scheme = owlModel.getOWLNamedClass(c_classification_schemeName);

		
		hasStatus                =   owlModel.getOWLDatatypeProperty(hasStatusName                   );
		hasDateCreated           =   owlModel.getOWLDatatypeProperty(hasDateCreatedName              );
		hasDateLastUpdated       =   owlModel.getOWLDatatypeProperty(hasDateLastUpdatedName          );
		hasCode                  =   owlModel.getOWLDatatypeProperty(hasCodeName                     );
		isMainLabel              =   owlModel.getOWLDatatypeProperty(isMainLabelName                 );
		hasCodeAgrovoc           =   owlModel.getOWLDatatypeProperty(hasCodeAgrovocName              );
		hasCodeFaoPa             =   owlModel.getOWLDatatypeProperty(hasCodeFaoPaName                );
		hasCodeAsc               =   owlModel.getOWLDatatypeProperty(hasCodeAscName                  );
		hasCodeAsfa              =   owlModel.getOWLDatatypeProperty(hasCodeAsfaName                 );
		hasCodeTaxonomic         =   owlModel.getOWLDatatypeProperty(hasCodeTaxonomicName            );
		hasCodeFaoterm           =   owlModel.getOWLDatatypeProperty(hasCodeFaotermName              );
		hasFishery3AlphaCode     =   owlModel.getOWLDatatypeProperty(hasFishery3AlphaCodeName        );
		hasSpellingVariant       =   owlModel.getOWLDatatypeProperty(hasSpellingVariantName          );
		hasScopeNote             =   owlModel.getOWLDatatypeProperty(hasScopeNoteName                );
		hasEditorialNote         =   owlModel.getOWLDatatypeProperty(hasEditorialNoteName            );
		takenFromSource          =   owlModel.getOWLDatatypeProperty(takenFromSourceName             );
		hasSourceLink            =   owlModel.getOWLDatatypeProperty(hasSourceLinkName               );
		hasImageLink             =   owlModel.getOWLDatatypeProperty(hasImageLinkName                );
		hasImageSource           =   owlModel.getOWLDatatypeProperty(hasImageSourceName              );
		hasNumber                =   owlModel.getOWLDatatypeProperty(hasNumberName                 	);
		hasDate                  =   owlModel.getOWLDatatypeProperty(hasDateName                   	);
		isSpatiallyIncludedInCity =  owlModel.getOWLDatatypeProperty(isSpatiallyIncludedInCityName 	);
		isSpatiallyIncludedInState = owlModel.getOWLDatatypeProperty(isSpatiallyIncludedInStateName	);
		
		hasTermType = owlModel.getOWLDatatypeProperty(hasTermTypeName	);

		isPartOfSubvocabulary	 = owlModel.getOWLDatatypeProperty(isPartOfSubvocabularyName	);
		hasIssn					 = owlModel.getOWLDatatypeProperty(hasIssnName					);
		hasIssnL                 = owlModel.getOWLDatatypeProperty(hasIssnLName                	);
		isHoldBy                 = owlModel.getOWLDatatypeProperty(isHoldByName                	);
		hasCallNumber      		 = owlModel.getOWLDatatypeProperty(hasCallNumberName      		);
		
		
		hasTranslation           =   owlModel.getOWLObjectProperty(hasTranslationName           	  );		
		hasRelatedConcept        =   owlModel.getOWLObjectProperty(hasRelatedConceptName           );
		hasRelatedTerm           =   owlModel.getOWLObjectProperty(hasRelatedTermName              );
		hasLexicalization        =   owlModel.getOWLObjectProperty(hasLexicalizationName           );
		isLexicalizationOf       =   owlModel.getOWLObjectProperty(isLexicalizationOfName          );		
		hasDefinition            =   owlModel.getOWLObjectProperty(hasDefinitionName               );
		isDefinitionOf           =   owlModel.getOWLObjectProperty(isDefinitionOfName              );
		hasCategory              =   owlModel.getOWLObjectProperty(hasCategoryName                 );
		hasImage                 =   owlModel.getOWLObjectProperty(hasImageName                    );
		isImageOf                =   owlModel.getOWLObjectProperty(isImageOfName                   );
		hasMappedDomainConcept   =   owlModel.getOWLObjectProperty(hasMappedDomainConceptName      );
		isSubCategoryOf          =   owlModel.getOWLObjectProperty(isSubCategoryOfName             );
		hasSubCategory           =   owlModel.getOWLObjectProperty(hasSubCategoryName              );
		belongsToScheme          =   owlModel.getOWLObjectProperty(belongsToSchemeName             );
		has                      =   owlModel.getOWLObjectProperty(hasName                         );
		is                       =   owlModel.getOWLObjectProperty(isName                          );
		SubCategory              =   owlModel.getOWLObjectProperty(SubCategoryName                 );
		SubCategoryOf            =   owlModel.getOWLObjectProperty(SubCategoryOfName               );
		isAbbreviationOf         =   owlModel.getOWLObjectProperty(isAbbreviationOfName            );
		hasAcronym               =   owlModel.getOWLObjectProperty(hasAcronymName                  );
		hasSynonym               =   owlModel.getOWLObjectProperty(hasSynonymName                  );
		isReferencedInAnnot      =   owlModel.getOWLObjectProperty(isReferencedInAnnotationOfName  );
		thesaurusRelationship    =   owlModel.getOWLObjectProperty(thesaurusRelationshipName     	);
		subclassOf               =   owlModel.getOWLObjectProperty(subclassOfName                	);
		hasSubclass              =   owlModel.getOWLObjectProperty(hasSubclassName               	);
		isInstanceOf             =   owlModel.getOWLObjectProperty(isInstanceOfName              	);
		hasInstance              =   owlModel.getOWLObjectProperty(hasInstanceName               	);
		isSpatiallyIncludedIn    =   owlModel.getOWLObjectProperty(isSpatiallyIncludedInName     	);
		isPartOf                 =   owlModel.getOWLObjectProperty(isPartOfName                  	);
		isPublishedBy            =   owlModel.getOWLObjectProperty(isPublishedByName             	);
		
		hasBroaderSynonym        =   owlModel.getOWLObjectProperty(hasBroaderSynonymName          	);
		

		isOtherLanguageEditionOf = owlModel.getOWLObjectProperty(isOtherLanguageEditionOfName	);
		follows                  = owlModel.getOWLObjectProperty(followsName                 	);
		precedes                 = owlModel.getOWLObjectProperty(precedesName                	);	
		
	}
	
	

}
