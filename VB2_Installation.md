# Installation #

VB2.0 is still in internal testing, thus the elements are not shared on a public resource.

For testers:

On our VOCBENCH shared folder on Dropbox, there is a VB2.0 folder which is dedicated to VB2.0 specific installation packages and instructions. We list here below some of them, however, the main folder to be consulted for updated packaging material will be **VB 2.0 Full** (first in the list here).

**VB 2.0 Full**: this folder is for the BETA testers (not for developers). Typically Sachit fills this folder for the FAO team, after packing a new version. This folder _must_ include also proper reference (or a working copy) of the ST server used for this installation. Everything inside this folder has to be _self-consistent_.

**ST**: this folder is for delivering any information related to a new version of the ST server. This is mainly intended for delivery of code/components from the ART group of Tor Vergata to Sachit, for ALPHA testing.


# Current version #

The current version has been updated on 08/03/2013 (see readme.txt in the "VB2.0 Full" folder).

# Known Issues #

After starting, a null pointer exception on the console is normal, as VB always tries to get the current project from ST. It will be fixed as soon as a query method for knowing the status of projects on ST is implemented in ST. However, this is a no-blocking issue for making the VB work.

# CONFIGURATIONS #

In this section we provide different installation configurations for the VOCBENCH. Instead of updating them on the Installation Instructions on the Dropbox folder, we try to maintain that document as static as possible, with references to configurations listed below. Users, and especially testers, are welcome to refer to this page for aligning their configurations.

We distinguish different configuration blocks, where each block is logically defined by the component influenced by its parameters, and sub-blocks for each component may be defined on the basis of the frequency of their updates.
Each **header level 2** will define a block type, and then for each of them, specific configuration instances may be defined, preambled with a **bold** title.


## Full VB2.0 Configuration ##

**Current Configuration**

This section identifies a full set of installation parameters, obtained by combining specific values provided here and sets of config parameters from the sections here below

|Data Configuration| AGROVOC\_SKOS\_VB\_2013-07-12 |
|:-----------------|:------------------------------|
|VBST General      | 26/2/2012                     |
|OWLIM General     | see below, only one config    |
|OWLIM Repository Configuration|OWLIM FullReasoning&Indexing Configuration|

## VBST General ##

**26/2/2012**
|ST.VERSION| 0.8.6 (in any case the ST package is provided inside the installation directory)|
|:---------|:--------------------------------------------------------------------------------|
|ST.VB-BUNDLE.VERSION|0.0.1 available in _INST\_PKG_                                                   |
|VB.VERSION|download from: https://agrovoc-cs-workbench.googlecode.com/files/vocbench.zip    |
|VB.DB.VERSION|download from: http://agrovoc-cs-workbench.googlecode.com/files/administrator20.zip|
|_update notes_|the install doc has been updated to version 0.7 on 25/03/2013                    |

## OWLIM General ##

Very general configuration info about OWLIM. These should change very seldom.

|OWLIM.VERSION|5.2, the zip Fabrizio gave us, licensed for FAO|
|:------------|:----------------------------------------------|
|OWLIM.LICENSE\_FILE|_use the license file in the root of the zip file distributed by Fabrizio. The current name is: **fao1core.license**_. When asked for it in OWLIM repository configuration, insert the full path to the file, by using the standard file separator / (even on Windows)|

## OWLIM Repository Configuration ##

**OWLIM\_Fixed\_OWLRL\_Reasoning&Indexing**
| **PARAMETER** | **VALUE** |
|:--------------|:----------|
|OWLIM.RULESET  |"{pathtofile}/builtin\_owl2-rl-fixed.pie" (_see instruction manual for custom rule files_)|
|OWLIM.TOTAL\_CACHE\_MEMORY|120m       |
|OWLIM.MAIN\_INDEX\_MEMORY|80m        |
|OWLIM.USE\_PREDICATE\_INDICES|True       |
|OWLIM.PREDICATE\_INDEX\_MEMORY|20m        |
|OWLIM.FULL\_TEXT\_MEMORY|20m        |
|OWLIM.FULL\_TEXT\_SEARCH\_INDEXING\_POLICY|ON\_COMMIT |

**OWLIM FullReasoning&Indexing Configuration**
| **PARAMETER** | **VALUE** |
|:--------------|:----------|
|OWLIM.RULESET  |OWL2-RL    |
|OWLIM.TOTAL\_CACHE\_MEMORY|120m       |
|OWLIM.MAIN\_INDEX\_MEMORY|80m        |
|OWLIM.USE\_PREDICATE\_INDICES|True       |
|OWLIM.PREDICATE\_INDEX\_MEMORY|20m        |
|OWLIM.FULL\_TEXT\_MEMORY|20m        |
|OWLIM.FULL\_TEXT\_SEARCH\_INDEXING\_POLICY|ON\_COMMIT |

## Data Configuration ##

**AGROVOC\_SKOS\_VB\_2013-05-07** (current)

|OWLIM.REPOSITORY\_ID|AGROVOC\_SKOS\_VB\_2013-05-07|
|:-------------------|:----------------------------|
|DATA.VERSION        |download: https://agrovoc-cs-workbench.googlecode.com/files/AGROVOC_SKOS_VB_2013-02-05.zip|
|DATA.BASE\_URL      |aims.fao.org/aos/agrovoc/    |
|ST.PROJECT.NAME     |{OWLIM.REPOSITORY\_ID}       |
|ST.PROJECT.REPOSITORYID|{OWLIM.REPOSITORY\_ID}       |

**AGROVOC\_SKOS\_VB\_2013-02-05**

|OWLIM.REPOSITORY\_ID|AGROVOC\_SKOS\_VB\_2013-02-05|
|:-------------------|:----------------------------|
|DATA.VERSION        |download: https://agrovoc-cs-workbench.googlecode.com/files/AGROVOC_SKOS_VB_2013-05-07.zip|
|DATA.BASE\_URL      |aims.fao.org/aos/agrovoc/    |
|ST.PROJECT.NAME     |{OWLIM.REPOSITORY\_ID}       |
|ST.PROJECT.REPOSITORYID|{OWLIM.REPOSITORY\_ID}       |


<a href='Hidden comment: 
= Packages to be downloaded =

The current download for the VOCBENCH war is:

http://agrovoc-cs-workbench.googlecode.com/files/vocbench-2.0.zip

Current tested ST version is 0.8.6. Note that this is being tagged as 0.8.6 on the ST project site: https://code.google.com/p/semanticturkey/.
You can just download it and run: "mvn clean install" on the project (providing maven in installed in your machine).

Then, ... 8TO BE COMPLETED

P.S: even the current version on the trunk (0.8.7-SNAPSHOT) works as well since there is no difference from 0.8.6 except for the new project version.
'></a>