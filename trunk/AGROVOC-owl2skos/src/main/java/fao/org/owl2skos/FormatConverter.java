package fao.org.owl2skos;

import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;
import it.uniroma2.art.owlart.io.RDFFormat;
import it.uniroma2.art.owlart.model.NodeFilters;
import it.uniroma2.art.owlart.models.ModelFactory;
import it.uniroma2.art.owlart.models.OWLArtModelFactory;
import it.uniroma2.art.owlart.models.SKOSXLModel;

import java.io.File;
import java.io.IOException;

import edu.stanford.smi.protege.model.Project;
import fao.org.owl2skos.conversion.OWL2SKOSConverter;
import fao.org.owl2skos.protege.ProtegeModelLoader;

public class FormatConverter {



	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, ModelCreationException, ModelUpdateException,
			ModelAccessException, UnsupportedRDFFormatException {
		if (args.length < 2) {
			System.out
					.println("usage:\n"
							+ "java fao.org.owl2skos.FormatConverter <inputfilename> <outputfilename> [OWLART Implementing Class]");
			return;
		}

		// OWL ART SKOSXLMODEL LOADING
		String owlArtModelFactoryImplClassName;
		if (args.length > 2)
			owlArtModelFactoryImplClassName = args[2];
		else
			owlArtModelFactoryImplClassName = OWL2SKOSConverter.SesameModelFactoryImplClassName;

		Class<? extends ModelFactory> owlArtModelFactoryImplClass = (Class<? extends ModelFactory>) Class
				.forName(owlArtModelFactoryImplClassName);
		OWLArtModelFactory fact = OWLArtModelFactory.createModelFactory(owlArtModelFactoryImplClass.newInstance());
		SKOSXLModel skosXLModel = fact.loadSKOSXLModel("http://aims.fao.org/aos/agrovoc",
				"ModelData", false);
		skosXLModel.setDefaultNamespace(OWL2SKOSConverter.agrovocDefNamespace);

		// LOADING PRODUCED DATA IN SOURCE SERIALIZATION FORMAT AND CONVERTING TO OUTPUT FORMAT
		
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);
		skosXLModel.addRDF(inputFile, "http://aims.fao.org/aos/agrovoc", RDFFormat.guessRDFFormatFromFile(inputFile));
		skosXLModel.writeRDF(outputFile, RDFFormat.guessRDFFormatFromFile(outputFile), NodeFilters.MAINGRAPH);
	}
}
