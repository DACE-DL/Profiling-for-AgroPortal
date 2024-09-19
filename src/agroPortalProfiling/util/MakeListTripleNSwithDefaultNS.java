package agroPortalProfiling.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class MakeListTripleNSwithDefaultNS {
	
	// Création d'une liste avec les trois noms de domaine du sujet,
	//  du predicat et de l'objet des triplets du graphe. 
	public static ArrayList<UriAndUriAndUriAndNumber> makeList(ArrayList<UriAndUriAndUriAndNumber> listTripleNameSpace, ArrayList<UriAndString> listModelPrefixNameSpace) {

		ArrayList<UriAndUriAndUriAndNumber> ListResources = new ArrayList<>();
		// Trouver le prefix par défault (si il existe !)
		String defaultNameSpace = "";
		for (UriAndString uriAndString : listModelPrefixNameSpace) {
			if (uriAndString.getStr().toString().equals("")) {
				defaultNameSpace = uriAndString.getUri().toString();
				break;
			}
		}	
		if (!defaultNameSpace.equals("")) {
			String defaultNS = defaultNameSpace;
			System.out.println("defaultNS : " + defaultNS);
			listTripleNameSpace.forEach((tripleNameSpace) -> {
				if (tripleNameSpace.getUri1().toString().equals(defaultNS) ||
					tripleNameSpace.getUri2().toString().equals(defaultNS) ||
					tripleNameSpace.getUri3().toString().equals(defaultNS)) {
					ListResources.add(tripleNameSpace);
				}	
			});
		
			ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
		}
		return ListResources;
	}

}