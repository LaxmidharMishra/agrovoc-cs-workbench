Back to the [Main](Main.md)

# Introduction #

This page is to discuss latest changes in the OWL model and about the URIs


# OWL Model changes #

We have added a new relationship
  * hasStemmedForm: subrelationship of datatype relationship hasTermVariant


# URIs #

The final base URI of the ontology is:

http://www.fao.org/aims/aos/agrovoc

all **concepts** will have the following form:

http://www.fao.org/aims/aos/agrovoc/c_1234
with the 'fake' instance:
http://www.fao.org/aims/aos/agrovoc/i_1234 (used for the relationships and lexicalizations)

Lexicalizations (terms) will then have the follwing URIs:
http://www.fao.org/aims/aos/agrovoc/i_en_1234
http://www.fao.org/aims/aos/agrovoc/i_fr_1234
http://www.fao.org/aims/aos/agrovoc/i_th_1234
......

If there are more terms in one language, the URIs of each subsequent term will be as follows:
http://www.fao.org/aims/aos/agrovoc/i_en_1234_1 (synonym)
http://www.fao.org/aims/aos/agrovoc/i_en_1234_2 (synonym)
...

All **relationships** will use the English term as a basis for the relationship, and the URI will be of the form
http://www.fao.org/aims/aos/agrovoc/hasPest
http://www.fao.org/aims/aos/agrovoc/means
http://www.fao.org/aims/aos/agrovoc/hasLexicalization
...

so it uses the Camel Case of the English term (small letters, beginning of each word capitalized, words attached)


## Creation of the URIs for new concepts ##

The ideal option would be to have a counter in the DB that assigns a new number every time and gets incremented by 1
But there has been problems with duplicate keys
--> concurrent access: more users might be assigned the same number
--> Ton experimented: and then switched to random number

Also what do we do for automatic creation of AGROVOC term code in hasAgrovocTermCode ?
Taken from the concept URI? but how to do with descriptors and non descriptors, scientific terms, etc. ? What term code do they get?