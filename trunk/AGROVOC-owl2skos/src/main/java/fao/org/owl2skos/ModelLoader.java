/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License");  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * http//www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * The Original Code is AGROVOC-owl2skos.
 *
 * The Initial Developer of the Original Code is University of Roma Tor Vergata.
 * Portions created by University of Roma Tor Vergata are Copyright (C) 2010.
 * All Rights Reserved.
 *
 * AGROVOC-owl2skos was developed by the Artificial Intelligence Research Group
 * (ai-nlp.info.uniroma2.it) at the University of Roma Tor Vergata
 * Current information about AGROVOC-owl2skos can be obtained at 
 * http//ai-nlp.info.uniroma2.it/software/...
 *
 */

/*
 * Contributor(s): Armando Stellato stellato@info.uniroma2.it
 */
package fao.org.owl2skos;

import fao.org.owl2skos.conversion.OWL2SKOSConverter;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.model.ARTURIResource;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.SKOSXLModel;

/**
 * facility class for loading an empty SKOSXL model inside a given data directory
 * 
 * @author Armando Stellato <stellato@info.uniroma2.it>
 */
public class ModelLoader {

	/**
	 * as for {@link #loadSKOSModel(String, String, boolean)} with first argument ==
	 * {@link OWL2SKOSConverter#SesameModelFactoryImplClassName}, second argument == "ModelData" and third one
	 * == <code>false</code>.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ModelCreationException
	 * @throws ModelUpdateException
	 */
	public static SKOSXLModel loadSKOSModel() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ModelCreationException, ModelUpdateException {
		return loadSKOSModel(OWL2SKOSConverter.SesameModelFactoryImplClassName, "ModelData", false);
	}

	/**
	 * as for {@link #loadSKOSModel(String, String, boolean)} with first argument ==
	 * {@link OWL2SKOSConverter#SesameModelFactoryImplClassName} and second argument = "ModelData".
	 * 
	 * @param persistence
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ModelCreationException
	 * @throws ModelUpdateException
	 */
	public static SKOSXLModel loadSKOSModel(boolean persistence) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, ModelCreationException, ModelUpdateException {
		return loadSKOSModel(OWL2SKOSConverter.SesameModelFactoryImplClassName, "ModelData", persistence);
	}

	/**
	 * as for {@link #loadSKOSModel(String, String, boolean)} with first argument ==
	 * {@link OWL2SKOSConverter#SesameModelFactoryImplClassName}.
	 * 
	 * @param persistence
	 * @param dataDirectory
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ModelCreationException
	 * @throws ModelUpdateException
	 */
	public static SKOSXLModel loadSKOSModel(String dataDirectory, boolean persistence)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			ModelCreationException, ModelUpdateException {
		return loadSKOSModel(OWL2SKOSConverter.SesameModelFactoryImplClassName, dataDirectory, persistence);
	}

	/**
	 * loads an empty SKOSXL model inside directory <code>dataDirectory</code>, by using
	 * <code>owlArtModelFactoryImplClassName</code> implementation class for {@link ModelFactory}
	 * 
	 * @param persistence
	 * @param owlArtModelFactoryImplClassName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ModelCreationException
	 * @throws ModelUpdateException
	 */
	@SuppressWarnings("unchecked")
	public static SKOSXLModel loadSKOSModel(String owlArtModelFactoryImplClassName, String dataDirectory,
			boolean persistence) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ModelCreationException, ModelUpdateException {

		Class<? extends ModelFactory> owlArtModelFactoryImplClass = (Class<? extends ModelFactory>) Class
				.forName(owlArtModelFactoryImplClassName);
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass
				.newInstance());
		SKOSXLModel skosXLModel = fact.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc", dataDirectory,
				persistence);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);
		
		ARTURIResource agrovocSchema = skosXLModel.addSKOSConceptScheme(OWL2SKOSConverter.agrovocSchemeURI);
		skosXLModel.setDefaultSchema(agrovocSchema);
		
		return skosXLModel;
	}

}
