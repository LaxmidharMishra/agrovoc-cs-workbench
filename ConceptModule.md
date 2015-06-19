Back to the [Main](Main.md)
# Assigned to: TON #

# Open points #

  * When relating two concepts or terms, if the relationship is symmetric or has a inverse relationship, the backward direction relationship should be added automatically!
  * Implement a check when entering a new relationship, that this relationship does not already exist in the other direction, i.e. if there is c\_123 r\_affects c\_321 and the user wants to create the relationship c\_321 r\_affects c\_123 the system should throw an error message telling the user that this is not possible.

# Creating new concepts: URI and AGROVOC Term Code #

When a new concept is created, the concept needs to be assigned a URI and each term of the concept needs to be assigned an AGROVOC term code automatically (in order to be backward compatible with the thesaurus).

If a new concept is created, a term is created along with it by default. This term by default is marked as main label initially (the user can change this later on).
For this new term/concept entry, a new random number is created. This random number is used to create the URI of the concept and is used as the term code of the new main label in the chosen language.

Example:

The user creates a new concept and types in the label "beurre" in French (fr). The system created a random number, for example 9187326412.
The following things are created:
(aos stands for http://www.fao.org/aims/aos/agrovoc#)
  * Concept with the uri aos:c\_9187326412
  * Instance of this concept with uri aos:i\_9187326412
  * Instance of c\_noun: with uri aos:i\_fr\_9187326412
  * a relationship instance of hasAGROVOCTermCode from this instance with the value 9187326412
  * The relationships between the concept and the lexicalizion should be instantiated (isLexicalizationOf and hasLexicalization).

The problem now is, what happens with new terms or if main labels change.

Basically, for each new term for a concept, a new random number is generated first and this is used for the URI of this term (instance of c\_noun) and for the value of the hasAGROVOCTermCode relationship.

Then two cases are possible:
  * The term is checked as main label
    * the system needs to check first if there is another term already main label in this language and if so, ask the user if he wants to change the main label, which the user can confirm or not
    * if the term is assigned main label, then the value for the uri of this term and for the hasAGROVOCTermCode value is taken from the uri of the corresponding concept
  * The term is linked to another term with the hasTranslation relationship
    * In this case the term should take over the term code from the term it has been linked to (in AGROVOC translations have the same term code).

So basically, the concept URI stayes always the same.

Main labels derive their uris and values of the hasAGROVOCTermCode property from the URI of the concept

If the status of a term changes
  * from main label to not main label --> assign a new random term code, i.e. the term changes its URI and the value of hasAGROVOCTermCode AND warn the user if no main label exists anymore and ask to add one (if not existing) or choose from available not-main-terms (there must always be only one main label per concept)
  * from not main label to main label --> change the uri to use the number taken from the concept URI and use this also as value of hasAGROVOCTermCode (discard the previous value) Note: if previous main label exists ask the user to confirm to change the previous main label to not being a main label anymore (because in every main language there can only be one main label). In this case the URI and value of hasAGROVOCTermCode of the previous main label must change. It can be assigned the previous number of the term which just became the new main label.
  * Handling changes in translations
    * putting a translation link in between two main labels --> nothing needs to change, since the main labels already have the same AGROVOC term code and URI (derived from the concept URI)
    * putting a translation link between a main label in one language and a non-main label in another language --> nothing changes, since the main label will keep its term code from the concept and the non-main label also must keep, since there shall be another main label in this language that has the AGROVOC code derived from the concept
    * putting a translation link between two terms of the same language --> output an error message and don't do anything
    * putting a translation between two non-main label terms
      * if one didn't have any translation yet and the other had already: --> take over the AGROVOC term code and URI number from the term that already has translations.
      * if both didn't have any translation yet --> pick randomly one of the two and assign the term code to the other
      * if both had already translations before, pick randomly one of the two , take the AGROVOC term code of this term, and use it to reassign the AGROVOC term codes and URIs of all the translations (and recursively the translations of the translations) of the other term.
  * deleting a translation relationship
    * create a new new random number for one of the two terms and assign it to the AGROVOC term code and to reassign the URI,
      * if both terms have no other translation relationship, choose randomly
      * if one of the terms has other translations, choose the one that doesn't have a another translation
      * if both have other translationships, choose randomly one of the terms, but in this case, also reassign recursively all the terms that are connected to this one with hasTranslation.

**Problem here**: If there is a circular translationship relation path, like i\_en\_123 hasTranslation i\_fr\_123 has Translation i\_es\_123 hasTranslation i\_en\_123. Now if I delete the translation between i\_en\_123 and i\_fr\_123 the new assignment would do something like i\_en\_234, i\_fr\_345, i\_es\_345, but the english and spanish are still linked with the hasTranslation relationship! but now have different term codes. How to solve this?
**Suggestion:**
> the system should ask to remove also the connection between en and es (in this case fr and es will keep their number  i\_fr\_123, i\_es\_123) OR to delete between i\_fr\_123, i\_es\_123 and in this case should be  en and es will keep their number  i\_en\_123, i\_es\_123 and fr will change i\_fr\_234

**Important:** For each new assignment of a term code, the system needs to check for uniqueness, i.e. the system needs to check that this term code exists only once in this language and that if the term code exists in other languages, these terms are linked with hasTranslation

# Links #

  * http://vivaldi.cpe.ku.ac.th:8085/agrovoc/



# Ideas #


