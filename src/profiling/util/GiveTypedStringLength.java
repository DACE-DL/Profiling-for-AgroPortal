package profiling.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;

public class GiveTypedStringLength {
	static Integer n = 0;
	static double l = 0;
	// Création d'une liste des propriétés et de leur usage dans un triplet
	public static double giveLength(Model model, ArrayList<UriAndNumber> listProperty) {
		double nNumber = 0;
		
		List<RDFNode> listObject = new ArrayList<>();
	
		listProperty.forEach((property) -> {
			Resource s2 = null;
			Resource o2 = null;
			Selector selector1 = new SimpleSelector(s2, model.createProperty(property.getUri()), o2) ;
			StmtIterator stmtIte1 = model.listStatements(selector1);
			stmtIte1.forEach((stmObj) -> {
				listObject.add(stmObj.getObject());
			});
			stmtIte1.close();
        });

		// Calcul of the length average
		listObject.forEach((object) -> {
			if (object.isLiteral()) {
				if (object.asLiteral().getDatatype() == XSDDatatype.XSDstring ) {
					//System.out.println("n : " + n);
					//System.out.println("literal : " + object.asLiteral().toString());
					//System.out.println("length : " + object.asLiteral().toString().length());
					n ++;
					l = l + object.asLiteral().toString().length();
				}			
			} 
		});

		if (n!=0) {
			nNumber = l/n;
		}
		
		return nNumber;
	}	
}