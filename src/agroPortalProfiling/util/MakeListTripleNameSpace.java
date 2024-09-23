package agroPortalProfiling.util;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MakeListTripleNameSpace {
	
	// Création d'une liste des liens dans un triplet
	public static ArrayList<UriAndUriAndUriAndNumber> makeList(Model model) {
    
		String prefix = AgroPortalProfilingConf.queryPrefix;
	
		ArrayList<UriAndUriAndUriAndNumber> ListResources = new ArrayList<UriAndUriAndUriAndNumber>();

		 // SPARQL Query pour extraire les namespaces des sujets, prédicats et objets
		 String sparqlQuery = prefix +
		 " SELECT ?namespaceS ?namespaceP ?namespaceO (COUNT(*) AS ?count) WHERE { " +
		 "       ?s ?p ?o . " +
		 "       FILTER(isIRI(?s) && isIRI(?o) ) " +
		 "       BIND(REPLACE(STR(?s), '([/#][^/#]*)$', '') AS ?namespaceTempS) " +
		 "       BIND(CONCAT(?namespaceTempS, SUBSTR(STR(?s), STRLEN(?namespaceTempS) + 1, 1)) AS ?namespaceS) " +
		 "       BIND(REPLACE(STR(?p), '([/#][^/#]*)$', '') AS ?namespaceTempP) " +
		 "       BIND(CONCAT(?namespaceTempP, SUBSTR(STR(?p), STRLEN(?namespaceTempP) + 1, 1)) AS ?namespaceP) " +
		 "       BIND(REPLACE(STR(?o), '([/#][^/#]*)$', '') AS ?namespaceTempO) " +
		 "       BIND(CONCAT(?namespaceTempO, SUBSTR(STR(?o), STRLEN(?namespaceTempO) + 1, 1)) AS ?namespaceO) " +
		 " } " +
		 " GROUP BY ?namespaceS ?namespaceP ?namespaceO " +
		 " ORDER BY DESC(?count) ";
 
		 // Exécuter la requête SPARQL
		 Query query2 = QueryFactory.create(sparqlQuery);
		 QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
		 ResultSet result2 = qe2.execSelect();
 
		 // Extraction des namespaces
		 while (result2.hasNext()) {
			 QuerySolution querySolution = result2.next();
			 String namespaceS = querySolution.getLiteral("namespaceS").getString();
			 String namespaceP = querySolution.getLiteral("namespaceP").getString();
			 String namespaceO = querySolution.getLiteral("namespaceO").getString();
			 Integer count = querySolution.getLiteral("count").getInt();
			 
			 ListResources.add(new UriAndUriAndUriAndNumber(namespaceS, namespaceP, namespaceO, count));
			 System.out.println(" " + namespaceS +" "+ namespaceP +" " + namespaceO + ", Count: " + count);
		 }
		 ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
		 return ListResources;
	}

}