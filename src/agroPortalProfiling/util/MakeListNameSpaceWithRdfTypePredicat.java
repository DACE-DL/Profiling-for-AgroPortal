package agroPortalProfiling.util;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MakeListNameSpaceWithRdfTypePredicat {
	
	// Création d'une liste des liens dans un triplet
	public static ArrayList<UriAndUriAndNumber> makeList(Model model) {
    
		String prefix = AgroPortalProfilingConf.queryPrefix;
	
		ArrayList<UriAndUriAndNumber> ListResources = new ArrayList<UriAndUriAndNumber>();

		 // SPARQL Query 
		 String sparqlQuery = prefix +
		 " SELECT ?namespaceS ?namespaceO (COUNT(*) AS ?count) WHERE { " +
		 "       ?s rdf:type ?o . " +
		 "       FILTER(isIRI(?s) && isIRI(?o) ) " +
		 " 		 FILTER(!STRSTARTS(STR(?o), STR(rdf:)))" +
		 " 		 FILTER(!STRSTARTS(STR(?o), STR(owl:)))" +
		 "       BIND(REPLACE(STR(?s), '([/#][^/#]*)$', '') AS ?namespaceTempS) " +
		 "       BIND(CONCAT(?namespaceTempS, SUBSTR(STR(?s), STRLEN(?namespaceTempS) + 1, 1)) AS ?namespaceS1) " +
		 "       BIND(IF(REGEX(STR(?s), '^http://purl.obolibrary.org/obo/[A-Za-z]+_\\\\d+$'), " +
		 "            CONCAT('http://purl.obolibrary.org/obo', " + //
		 "             LCASE(SUBSTR(STR(?s), 31, STRLEN(STRBEFORE(SUBSTR(STR(?s), 31), '_')))),'.owl'),  " +
		 "             ?namespaceS1) AS ?namespaceS) " +
		 "       BIND(REPLACE(STR(?o), '([/#][^/#]*)$', '') AS ?namespaceTempO) " +
		 "       BIND(CONCAT(?namespaceTempO, SUBSTR(STR(?o), STRLEN(?namespaceTempO) + 1, 1)) AS ?namespaceO1) " +
		 "       BIND(IF(REGEX(STR(?o), '^http://purl.obolibrary.org/obo/[A-Za-z]+_\\\\d+$'), " +
		 "            CONCAT('http://purl.obolibrary.org/obo', " + //
		 "             LCASE(SUBSTR(STR(?o), 31, STRLEN(STRBEFORE(SUBSTR(STR(?o), 31), '_')))),'.owl'),  " +
		 "             ?namespaceO1) AS ?namespaceO) " +
		 " } " +
		 " GROUP BY ?namespaceS ?namespaceO " +
		 " ORDER BY DESC(?count) ";
 
		 // Exécuter la requête SPARQL
		 Query query2 = QueryFactory.create(sparqlQuery);
		 QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
		 ResultSet result2 = qe2.execSelect();
 
		 // Extraction des namespaces
		 while (result2.hasNext()) {
			 QuerySolution querySolution = result2.next();
			 String namespaceS = querySolution.getLiteral("namespaceS").getString();
			 String namespaceO = querySolution.getLiteral("namespaceO").getString();
			 Integer count = querySolution.getLiteral("count").getInt();
			 
			 ListResources.add(new UriAndUriAndNumber(namespaceS, namespaceO, count));
			 // System.out.println("Links: " + namespaceS +" " + namespaceO + ", Count: " + count);
		 }
		 // ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
		 return ListResources;
	}
}