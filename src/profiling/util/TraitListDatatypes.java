package profiling.util;

import java.util.ArrayList;


public class TraitListDatatypes {
	static Integer commonDatatypes = 0;
	// Recherche des LPT liés aux datatypes
	public static ArrayList<Lpt> makeList(ArrayList<Uri> listDatatypesSource, ArrayList<Uri> listDatatypesTarget) {
		
		ArrayList<Lpt> listLpts = new ArrayList<Lpt>();
		Boolean noDatatypesSource	= false;
		Boolean noDatatypesTarget	= false;
		Integer listDatatypesSourceSize = listDatatypesSource.size(); 
		Integer listDatatypesTargetSize = listDatatypesTarget.size(); 
		Integer minListDatatypesSize = 0;


		if (listDatatypesSourceSize == 0) {
			noDatatypesSource	= true;
		}
		if (listDatatypesTargetSize == 0) {
			noDatatypesTarget	= true;
		}
	
		if (listDatatypesSourceSize > listDatatypesTargetSize) {
			minListDatatypesSize = listDatatypesTargetSize; 
		} else {
			minListDatatypesSize = listDatatypesSourceSize; 
		}

		// Si pas de datatypes pour le jeux source ou target 
		if (noDatatypesSource || noDatatypesTarget) {
			// LPT 1.1.3: Predicate value best practice problem
			// Any problems encountered in the search for similarity due to non-compliance
			//  with good practice (No tag for languages, missing property value, non-unique
			//  value for ID properties, ...).
			listLpts.add(new Lpt("LPT1.1.3"));
		}

		// Si au moins un datatypes pour les datasets source et target
		if (!noDatatypesSource & !noDatatypesTarget) {
			// On compare les datatypes des jeux de données 
			listDatatypesSource.forEach((sourceDatatype) -> {
				listDatatypesTarget.forEach((targetDatatype) -> {
					if (sourceDatatype.getUri().toString().equals(targetDatatype.getUri().toString())) {
						commonDatatypes ++;
					}			
				});
			});
			System.out.println("MinlistDatatypesSize : " + minListDatatypesSize);
			System.out.println("CommonDatatypes : " + commonDatatypes);
			if (commonDatatypes == minListDatatypesSize) {

			} else {
				// LPT 1.1.1.2: Predicate value format value type	
				listLpts.add(new Lpt("LPT1.1.1.2"));
			}
		}
	
		return listLpts;
	}

	
}