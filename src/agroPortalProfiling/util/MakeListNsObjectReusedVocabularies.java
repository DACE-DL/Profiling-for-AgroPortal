package agroPortalProfiling.util;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MakeListNsObjectReusedVocabularies {

    // Création d'une liste avec les trois noms de domaine du sujet,
    // du predicat et de l'objet des triplets du graphe.
    public static ArrayList<UriAndNumber> makeList(Model model) {

        String prefix = AgroPortalProfilingConf.queryPrefix;

        ArrayList<UriAndNumber> ListResources = new ArrayList<>();

        // SPARQL Query pour extraire les namespaces des sujets, prédicats et objets
        String sparqlQuery = prefix +
        " SELECT ?namespace (COUNT(?namespaceFound) AS ?count) ?order WHERE { " +
        "  VALUES (?namespace ?order) { " +
        "    ('http://w3id.org/nkos/nkostype#classification_schema' 1) " + // Classification scheme
        "    ('http://purl.org/dc/elements/1.1/' 2) " + // Dublin core (DC)
        "    ('http://purl.org/dc/terms/' 3) " + // Dublin core (DCTERMS)
        "    ('http://omv.ontoware.org/2005/05/ontology#' 4) " + // Ontology Metadata Vocabulary (OMV)
        "    ('http://www.isibang.ac.in/ns/mod#' 5) " + // Metadata for Ontology Description and Publication (MOD 1)
        "    ('https://w3id.org/mod' 6) " + // Metadata for Ontology Description and Publication (MOD 2)
        "    ('http://kannel.open.ac.uk/ontology#' 7) " + // Descriptive Ontology of Ontology Relations (DOOR)
        "    ('http://purl.org/vocommons/voaf#' 8) " + // Vocabulary of a Friend (VOAF)
        "    ('http://rdfs.org/ns/void#' 9) " + // Vocabulary of Interlinked Datasets (VOID)
        "    ('http://biomodels.net/vocab/idot.rdf#' 10) " + // Identifiers.org (IDOT)
        "    ('http://purl.org/vocab/vann/' 11) " + // Vocabulary for annotating vocabulary descriptions (VANN)
        "    ('http://www.w3.org/ns/dcat#' 12) " + // Data Catalog Vocabulary (DCAT)
        "    ('http://www.w3.org/ns/adms#' 13) " + // Asset Description Metadata Schema (ADMS)
        "    ('http://schema.org/' 14) " + // Schema.org (SCHEMA)
        "    ('http://xmlns.com/foaf/0.1/' 15) " + // Friend of a Friend Vocabulary (FOAF)
        "    ('http://usefulinc.com/ns/doap#' 16) " + // Description of a Project (DOAP)
        "    ('http://creativecommons.org/ns#' 17) " + // Creative Commons Rights Expression Language (CC)
        "    ('http://www.w3.org/ns/prov#' 18) " + // Provenance Ontology (PROV)
        "    ('http://purl.org/pav/' 19) " + // Provenance, Authoring and Versioning (PAV)
        "    ('http://www.geneontology.org/formats/oboInOwl#' 20) " + // OboInOwl Mappings (OBOINOWL)
        "    ('http://www.w3.org/ns/sparql-service-description#' 21) " + // SPARQL 1.1 Service Description (SD)
        "    ('http://w3id.org/nkos#' 22) " + // Networked Knowledge Organization Systems Dublin Core Application Profile (NKOS)
        "  } " +
        "  OPTIONAL { " +
        "    { " +
        "      SELECT ?namespaceFound WHERE { " +
        "        ?s ?p ?o . " +
        "        FILTER(isIRI(?o)) " +
        "        FILTER(STR(?o) != 'http://www.w3.org/2002/07/owl#Ontology') " +
        "        FILTER(STR(?p) != 'http://www.w3.org/2002/07/owl#imports') " +
        "        BIND(REPLACE(STR(?o), '([/#][^/#]*)$', '') AS ?namespaceO) " +
        "        BIND(CONCAT(?namespaceO, SUBSTR(STR(?o), STRLEN(?namespaceO) + 1, 1)) AS ?namespaceO1) " +
        "        BIND(IF(REGEX(STR(?o), '^http://purl.obolibrary.org/obo/[A-Za-z]+_\\\\d+$'), " +
        "            CONCAT('http://purl.obolibrary.org/obo', " +
        "            LCASE(SUBSTR(STR(?o), 31, STRLEN(STRBEFORE(SUBSTR(STR(?o), 31), '_')))),'.owl'), " +
        "            ?namespaceO1) AS ?namespaceFound) " +
        "      } " +
        "    } " +
        "    FILTER(STR(?namespaceFound) = STR(?namespace)) " +
        "  } " +
        "} " +
        " GROUP BY ?namespace ?order " +
        " ORDER BY ?order";


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
        // ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
        return ListResources;
    }
}