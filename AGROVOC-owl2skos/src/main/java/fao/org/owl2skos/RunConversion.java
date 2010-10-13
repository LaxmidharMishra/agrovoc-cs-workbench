package fao.org.owl2skos;

import edu.stanford.smi.protege.model.Project;
import fao.org.owl2skos.examples.LoadAOSCommonFile;
import fao.org.owl2skos.protege.ProtegeModelLoader;
import it.uniroma2.art.owlart.exceptions.ModelAccessException;
import it.uniroma2.art.owlart.exceptions.ModelCreationException;
import it.uniroma2.art.owlart.exceptions.ModelUpdateException;
import it.uniroma2.art.owlart.exceptions.UnsupportedRDFFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class RunConversion {

	public static void main(String[] args){
		
		String protegeDbConfigFile = "";
		String rootDir = "";
		String aosFile = "";
		String persistance1 = "";
		String persistance2 = "";
		String owlartImplClass = null;
		
		if (args.length < 5) 
		{
			System.out.println("usage:\n" +
			"java fao.org.owl2skos.RunConversion.Main <ProtegeDBConfigFile> <root-dir> <aos-file> <persistence-1> <persistence-2> [OWLART Implementing Class] \n" +
			"\t<ProtegeDBConfigFile>  : path to database configuration file \n" +
			"\t<root-dir>                     : path to root directory \n" +
			"\t<aos-file>                      : path to aos file\n" +
			"\t<persistance-1>                : 'true' or 'false\n" +
			"\t<persistance-2>                : 'true' or 'false'");
			return;
		}
		
		protegeDbConfigFile  	= args[0];
		rootDir 				= args[1];
		aosFile 				= args[2];
		persistance1			= args[3];
		persistance2			= args[4];
		if(args.length == 6)
			owlartImplClass = args[5];
		
		Properties props = new java.util.Properties();
		FileReader fileReader;
		try {
			fileReader = new FileReader(protegeDbConfigFile);
			props.load(fileReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dbTableName = props.getProperty("dbTableName");
		
		if(manageDir(rootDir, dbTableName) < 1)
		{
			System.out.println("Error cleaning/creating directory: " + dbTableName);
			return;
		}
		String format = "%-30s:%s\n";
		System.out.println("CONVERSION STARTING - " + new Date());
		System.out.format(format, "<ProtegeDBConfigFile>", protegeDbConfigFile);
		System.out.format(format, "<root-dir>", rootDir);
		System.out.format(format, "<aos file>", aosFile);
		System.out.format(format, "<persistence-1>", persistance1);
		System.out.format(format, "<persistence-2>", persistance2);
		System.out.format(format, "Processing Database", dbTableName);
		
		String[] args1 = {protegeDbConfigFile, rootDir+"/"+dbTableName+"/memorystore.nt", persistance1, rootDir+"/"+dbTableName};
		String[] args2 = {aosFile, rootDir+"/"+dbTableName+"/memorystore.nt", persistance2, rootDir+"/"+dbTableName};
		
		try {
			Date d1 = new Date();
			Main.main(args1);
			Date d2 = new Date();
			System.out.println("FIRST PHASE ENDED - TIME ELAPSED : " +  getTimeDifference(d2,d1));
			LoadAOSCommonFile.main(args2);
			Date d3 = new Date();
			
			System.out.println("\n");
			System.out.println("FIRST PHASE ENDED - TIME ELAPSED : " +  getTimeDifference(d2,d1));
			System.out.println("SECOND PHASE ENDED - TIME ELAPSED : " +  getTimeDifference(d3,d2));
			System.out.println("TOTAL TIME ELAPSED : " +  getTimeDifference(d3,d1));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Get formatted time difference between two dates
	 * @param end
	 * @param start
	 * @return
	 */
	public static String getTimeDifference(Date end, Date start)
    {
        long lstart = start.getTime();
        long lend = end.getTime();

        int msecsIn = (int) (lend - lstart); 
        int secsIn = msecsIn/1000;
        int hours = secsIn / 3600,
        remainder = secsIn % 3600,
        minutes = remainder / 60,
        seconds = remainder % 60;
    
        return ( (hours < 10 ? "0" : "") + hours
        + ":" + (minutes < 10 ? "0" : "") + minutes
        + ":" + (seconds< 10 ? "0" : "") + seconds );

    }
	/**
	 * Manage directory
	 * @param rootDir
	 * @param dirName
	 * @return
	 */
	public static int manageDir(String rootDir, String dirName)
	{
		String target = rootDir+"/"+dirName;
		File file=new File(target);
		return file.isDirectory()? empty(target) : mkdir(target);   
	}
	/**
	 * Create a directory
	 * @param target
	 * @return
	 */
	public static int mkdir(String target)
	{
		boolean success = (new File(target)).mkdir();
		if(!success)
		{
			System.out.println("Failed creating directory: " + target);
			return -1;
		}
		else
			return 1;
	}
	/**
	 * Empty a given directory
	 * @param directory
	 */
	public static int empty(String directory)
	{
		File dir = new File(directory);
		if (!dir.exists()) {
			System.out.println(directory + " does not exist");
			return -1;
		}

		String[] info = dir.list();
		for (int i = 0; i < info.length; i++) 
		{
			File n = new File(directory + File.separator + info[i]);
			if (!n.isFile()) // skip ., .., other directories too
				continue;
			System.out.println("removing " + n.getPath());
			if (!n.delete())
				System.err.println("Couldn't remove " + n.getPath());
		}
		return 1;
	}
}
