package agroPortalProfiling.util;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MakeListTripleNameSpaceAndDataType {
	
	// Création d'une liste des liens dans un triplet
	public static ArrayList<UriAndUriAndUriAndNumber> makeList(Model model) {
    
		String prefix = AgroPortalProfilingConf.queryPrefix;
	
		ArrayList<UriAndUriAndUriAndNumber> ListResources = new ArrayList<UriAndUriAndUriAndNumber>();

		// Requête SPARQL modifiée pour extraire les namespaces et datatype des objets non-IRI
		String sparqlQuery = prefix +
		 	" SELECT ?namespaceS ?namespaceP ?datatype (COUNT(*) AS ?count) WHERE { " +
            "       ?s ?p ?o . " +
            "       FILTER(isIRI(?s) && isIRI(?p)) " + // Vérifier que le sujet et le prédicat sont des IRI
			"       FILTER(isLiteral(?o)) " + // Ne traiter que les triplets dont les objets sont des littéraux
			"       FILTER(STR(?o) != STR(owl:Ontology))" +
			"       FILTER(STR(?p) != STR(owl:imports))" +
			"       BIND(REPLACE(STR(?s), '([/#][^/#]*)$', '') AS ?namespaceTempS) " +
            "       BIND(CONCAT(?namespaceTempS, SUBSTR(STR(?s), STRLEN(?namespaceTempS) + 1, 1)) AS ?namespaceS1) " +
            "       BIND(IF(REGEX(STR(?s), '^http://purl.obolibrary.org/obo/[A-Za-z]+_\\\\d+$'), " +
            "            CONCAT('http://purl.obolibrary.org/obo', " + //
			"             LCASE(SUBSTR(STR(?s), 31, STRLEN(STRBEFORE(SUBSTR(STR(?s), 31), '_')))),'.owl'),  " +
            "             ?namespaceS1) AS ?namespaceS) " +
            "       BIND(REPLACE(STR(?p), '([/#][^/#]*)$', '') AS ?namespaceTempP) " +
            "       BIND(CONCAT(?namespaceTempP, SUBSTR(STR(?p), STRLEN(?namespaceTempP) + 1, 1)) AS ?namespaceP1) " +
            "       BIND(IF(REGEX(STR(?p), '^http://purl.obolibrary.org/obo/[A-Za-z]+_\\\\d+$'), " +
            "            CONCAT('http://purl.obolibrary.org/obo', " + //
			"             LCASE(SUBSTR(STR(?p), 31, STRLEN(STRBEFORE(SUBSTR(STR(?p), 31), '_')))),'.owl'),  " +
            "             ?namespaceP1) AS ?namespaceP) " +
            "       BIND(DATATYPE(?o) AS ?datatype) " + // Extraire le datatype de l'objet littéral
            " } " +
            " GROUP BY ?namespaceS ?namespaceP ?datatype " +
            " ORDER BY DESC(?count) ";
 
		 // Exécuter la requête SPARQL
		 Query query2 = QueryFactory.create(sparqlQuery);
		 QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
		 ResultSet result2 = qe2.execSelect();
 
		 // Extraction des namespaces et datatypes
		 while (result2.hasNext()) {
			 QuerySolution querySolution = result2.next();
			 String namespaceS = querySolution.getLiteral("namespaceS").getString();
			 String namespaceP = querySolution.getLiteral("namespaceP").getString();
			 String datatype = querySolution.getResource("datatype").getURI(); // Récupérer le datatype en tant que URI
			 Integer count = querySolution.getLiteral("count").getInt();
			 
			 ListResources.add(new UriAndUriAndUriAndNumber(namespaceS, namespaceP, datatype, count));
			 //System.out.println(" " + namespaceS +" "+ namespaceP +" " + datatype + ", Count: " + count);
		 }
		 // ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
		 return ListResources;
	}
}
