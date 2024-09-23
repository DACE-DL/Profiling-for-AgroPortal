package agroPortalProfiling.util;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MakeListTriplePredicatNameSpace {

    // Création d'une liste avec les trois noms de domaine du sujet,
    // du predicat et de l'objet des triplets du graphe.
    public static ArrayList<UriAndNumber> makeList(Model model) {

        String prefix = AgroPortalProfilingConf.queryPrefix;

        ArrayList<UriAndNumber> ListResources = new ArrayList<>();

        // SPARQL Query pour extraire les namespaces des sujets, prédicats et objets
        String sparqlQuery = prefix +
        " SELECT ?namespace (COUNT(?namespace) AS ?count) WHERE { " +
        "       ?s ?p ?o . " +
        "       BIND(REPLACE(STR(?p), '([/#][^/#]*)$', '') AS ?namespaceP) " +
        "       BIND(CONCAT(?namespaceP, SUBSTR(STR(?p), STRLEN(?namespaceP) + 1, 1)) AS ?namespace) " +
        " } " +
        " GROUP BY ?namespace " +
        " ORDER BY DESC(?count) ";

        // Exécuter la requête SPARQL
        Query query2 = QueryFactory.create(sparqlQuery);
        QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
        ResultSet result2 = qe2.execSelect();

        // Extraction des namespaces
        while (result2.hasNext()) {
            QuerySolution querySolution = result2.next();
            String namespace = querySolution.getLiteral("namespace").getString();
			Integer count = querySolution.getLiteral("count").getInt();
            
            ListResources.add(new UriAndNumber(namespace, count));
            // System.out.println("Namespace: " + namespace + ", Count: " + count);
        }
        ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
        return ListResources;
    }
}