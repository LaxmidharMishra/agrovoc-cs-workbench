Discussion about performance of the tool.

# Introduction #

The performance of the tool has been tested with several modules.

# Details of tests x V0.3 #

|Number of Lang|Size MB|Contains|Module type|
|:-------------|:------|:-------|:----------|
|1             |39     |all EN  |by language|
|2             |53     |all EN TH|by language|
|3             |65     |all EN TH FR|by language|
|4             |79     |all EN TH FR ES|by language|
|5             |92     |all EN TH FR ES AR|by language|
|6             |110    |all EN TH FR ES AR ZH|by language|
|7             |130    |all EN TH FR ES AR ZH JA|by language|
|18            |95     |all lang x substances|by top term|
|18            |46     |all lang x organisms|by top term|
|18            |17     |all lang x resources|by top term|

Final TT for modularization tests:

|4788	EN	methods|
|:--------------|
|6211	EN	Products|
|13586	EN	processes|
|49874	EN	properties|
|49904	EN	organisms|
|50227	EN	groups|
|7778	EN	time   |
|330704	EN	phenomena|
|330705	EN	substances|
|330829	EN	subjects|
|330834	EN	activities|
|330892	EN	entities|
|330919	EN	objects|
|330988	EN	location|
|331061	EN	features|
|330979	EN	events|
|331093	EN	factors|
|9001017	EN	resources|
|330991	EN	strategies|
|330995	EN	stages|
|330998	EN	state|
|330985	EN	systems|
|330493	EN	measure|
|7644	EN	Technology|

Note for Sachit: use this for conversion routine:

java -Xmx1500M -Xss2548k kawa.repl agrovoc-owl.scm -d 2 -m < termcode > agrovoc_< termcode >_level\_2.owl



---


# Wais to improve performances #

**more powerful machine?**less data in the db?
**change db?**configure better the db?

# Persistent model #

Andy has done extensive work on creating the full AGROVOC Concept Server OWL model using JENA. He store the model in MySQL using the Jena persistent model and makes queries using SPARQL.

Currently Ton and Sachit have discovered the following problems with the Sesame framework (the one currently used for the WB):

1- when many users use the system sesame creates duplicated entries in the database. This gives problems on the tool and the model needs to be deleted and reloaded with consequence lost of users modifications.

2- with the sesame API it is not possible to manage all elements of an OWL model, such as domain and range of properties.

Apparently the updated version of sesame still do not solve problem n.2

For these reasons Ton and Sachit are now invertigating on PROTEGE API and JENA.