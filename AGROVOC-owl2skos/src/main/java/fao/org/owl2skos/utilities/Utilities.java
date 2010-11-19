package fao.org.owl2skos.utilities;

import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.conf.ModelConfiguration;

public class Utilities {

	@SuppressWarnings("unchecked")
	public static Class<? extends ModelFactory<? extends ModelConfiguration>> getModelFactory(String owlArtModelFactoryImplClassName) throws ClassNotFoundException {
		Class<? extends ModelFactory<? extends ModelConfiguration>> owlArtModelFactoryImplClass = (Class<? extends ModelFactory<? extends ModelConfiguration>>) Class
		.forName(owlArtModelFactoryImplClassName);
		return owlArtModelFactoryImplClass;
	}
	
}
