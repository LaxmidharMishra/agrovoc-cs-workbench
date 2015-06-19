Back to the [Main](Main.md)

# Open things #

  * We added a new attribute for lexicalizations --> r\_has\_stemmed\_form (sub relationship of r\_has\_term\_variant). This is for storing the stemmed version of each term. Needs to be tested if it works in the workbench
  * A term can only have one main label. If a user wants to check another term to be main label in the same language, the system should ask if he really wants to make this term the main label, since there is already a main label for the concept. If he doesn't confirm, the old main label stays.
  * It should be possible to uncheck a term to be a main label

  * We need to implement a check that no particular term code (for example has\_AGROVOC\_term\_code) is duplicated, i.e. term codes must be unique (like URIs)


# Ideas #