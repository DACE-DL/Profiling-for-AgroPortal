package agroPortalProfiling.launchScripts;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import org.apache.jena.rdf.model.Model;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import agroPortalProfiling.util.*;

public class RunTreatement {
	
	// On effectue les pré-traitements
	public static void treatements(Model model) throws JsonParseException, JsonMappingException, IOException {
		
		Instant start0 = Instant.now();

		////////////////////////////////////
		//// Pour le dataset en général ////
		////////////////////////////////////
		

		// Liste pour le nombre de triplets du graphe.
		String nameOfListGraphTripleSize = "listGraphTripleSize";
		ArrayList<UriAndNumber> listGraphTripleSize = new ArrayList<UriAndNumber>();
		listGraphTripleSize = MakeListGraphTripleSize.makeList(model);

		// Liste des predicats et de leur usage dans les triplets du graphe.
		String nameOfListPredicatUsageCount = "listPredicatUsageCount";
		ArrayList<UriAndNumber> listPredicatUsageCount = new ArrayList<UriAndNumber>();
		listPredicatUsageCount = MakeListPredicatUsageCount.makeList(model);

		// Liste des predicats et de leur usage dans les triplets du graphe sans éléments de
		//  la syntaxe RDF.
		String nameOfListPredicatNoRdfSyntaxUsageCount = "listPredicatNoRdfSyntaxUsageCount";
		ArrayList<UriAndNumber> listPredicatNoRdfSyntaxUsageCount = new ArrayList<UriAndNumber>();
		listPredicatNoRdfSyntaxUsageCount = MakeListPredicatNoRdfSyntaxUsageCount.makeList(model);
		
		// Liste des predicats et de leur usage dans les triplets du graphe sans éléments de
		//  la syntaxe RDF RDFS et OWL.
		String nameOfListPredicatNoRdfRdfsOwlUsageCount = "listPredicatNoRdfRdfsOwlUsageCount";
		ArrayList<UriAndNumber> listPredicatNoRdfRdfsOwlUsageCount = new ArrayList<UriAndNumber>();
		listPredicatNoRdfRdfsOwlUsageCount = MakeListPredicatNoRdfRdfsOwlUsageCount.makeList(model);
		
		// Liste des propriétés déclarées.
		String nameOfListPropertyDeclared = "listPropertyDeclared";
		ArrayList<UriAndUri> listPropertyDeclared = new ArrayList<UriAndUri>();
		listPropertyDeclared = MakeListPropertyDeclared.makeList(model);

		// Liste des classes déclarées.
		String nameOfListClassDeclared = "listClassDeclared";
		ArrayList<UriAndUri> listClassDeclared = new ArrayList<UriAndUri>();
		listClassDeclared = MakeListClassDeclared.makeList(model);

		// Liste des namespaces des propriétés déclarées.
		String nameOfListNameSpacePropertyDeclared = "listNameSpacePropertyDeclared";
		ArrayList<UriAndUriAndNumber> listNameSpacePropertyDeclared = new ArrayList<UriAndUriAndNumber>();
		listNameSpacePropertyDeclared = MakeListNameSpacePropertyDeclared.makeList(model);

		// Liste des namespaces des classes déclarées.
		String nameOfListNameSpaceClassDeclared = "listNameSpaceClassDeclared";
		ArrayList<UriAndUriAndNumber> listNameSpaceClassDeclared = new ArrayList<UriAndUriAndNumber>();
		listNameSpaceClassDeclared = MakeListNameSpaceClassDeclared.makeList(model);

		// Liste des propriétés et de leur domaine and range déclarés.
		String nameOfListPropertyDomainAndRange = "listPropertyDomainAndRange";
		ArrayList<UriAndX2UriAndUriListAndUriListAndUriList> listPropertyDomainAndRange = new ArrayList<UriAndX2UriAndUriListAndUriListAndUriList>();
		listPropertyDomainAndRange = MakeListPropertyDomainAndRange.makeList(model);

		// Liste des propriétés des propriétés (Transitive, reflective, ...).
		String nameOfListPropertyProperties = "listPropertyProperties";
		ArrayList<UriAndUri> listPropertyProperties = new ArrayList<UriAndUri>();
		listPropertyProperties = MakeListPropertyProperties.makeList(model);

		// Liste des ressources liées par une relation sameAs.
		String nameOfListResourcesSameAs = "listResourcesSameAs";
		ArrayList<UriAndUri> listResourcesSameAs = new ArrayList<UriAndUri>();
		listResourcesSameAs = MakeListResourcesSameAs.makeList(model);

		// Liste des ressources dépréciées.
		String nameOfListResourceDeprecated = "listResourceDeprecated";
		ArrayList<UriAndUri> listResourceDeprecated = new ArrayList<UriAndUri>();
		listResourceDeprecated = MakeListResourceDeprecated.makeList(model);
		
		// Uri de l'ontologie.
		String nameOfListUriOntology = "listUriOntology";
		ArrayList<Uri> listUriOntology = new ArrayList<Uri>();
		listUriOntology = MakeListUriOntology.makeList(model);

		// Liste des prefix des espaces de noms  .
		String nameOfListModelPrefixNameSpace = "listModelPrefixNameSpace";
		ArrayList<UriAndString> listModelPrefixNameSpace = new ArrayList<UriAndString>();
		listModelPrefixNameSpace = MakeListModelPrefixNameSpace.makeList(model);

		// Uri des imports de l'ontologie.
		String nameOfListUriImport = "listUriImport";
		ArrayList<Uri> listUriImport = new ArrayList<Uri>();
		listUriImport = MakeListUriImport.makeList(model);


		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe. 
		String nameOfListGraphNameSpace = "listGraphNameSpace";
		ArrayList<UriAndNumber> listGraphNameSpace = new ArrayList<UriAndNumber>();
		listGraphNameSpace = MakeListGraphNameSpace.makeList(model);
		
		// Création d'une liste avec les noms de domaine des sujets,
		//  des triplets du graphe. 
		String nameOfListTripleSubjectNameSpace = "listTripleSubjectNameSpace";
		ArrayList<UriAndNumber> listTripleSubjectNameSpace = new ArrayList<UriAndNumber>();
		listTripleSubjectNameSpace = MakeListTripleSubjectNameSpace.makeList(model);
		
		// Création d'une liste avec les noms de domaine des sujets,
		//  des triplets du graphe. 
		String nameOfListReduceTripleSubjectNameSpace = "listReduceTripleSubjectNameSpace";
		ArrayList<UriAndNumber> listReduceTripleSubjectNameSpace = new ArrayList<UriAndNumber>();
		listReduceTripleSubjectNameSpace = MakeListReduceTripleSubjectNameSpace.makeList(model);

		// Création d'une liste avec les noms de domaine des objets,
		//  des triplets du graphe. 
		String nameOfListTripleObjectNameSpace = "listTripleObjectNameSpace";
		ArrayList<UriAndNumber> listTripleObjectNameSpace = new ArrayList<UriAndNumber>();
		listTripleObjectNameSpace = MakeListTripleObjectNameSpace.makeList(model);

		// Création d'une liste avec les noms de domaine des predicats,
		//  des triplets du graphe. 
		String nameOfListTriplePredicatNameSpace = "listTriplePredicatNameSpace";
		ArrayList<UriAndNumber> listTriplePredicatNameSpace = new ArrayList<UriAndNumber>();
		listTriplePredicatNameSpace = MakeListTriplePredicatNameSpace.makeList(model);

		// Liste des namespaces des predicats et de leur usage dans les triplets du graphe sans éléments de
		//  la syntaxe RDF RDFS et OWL.
		String nameOfListTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount = "listTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount";
		ArrayList<UriAndNumber> listTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount = new ArrayList<UriAndNumber>();
		listTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount = MakeListTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount.makeList(model);

		
		// List of links between subjects and objects in the graphe.
		String nameOfListLinksSubjectObject = "listLinksSubjectObject";
		ArrayList<UriAndUriAndNumber> listLinksSubjectObject = new ArrayList<UriAndUriAndNumber>();
		listLinksSubjectObject = MakeListLinksSubjectObject.makeList(model);

		// List of links between subjects and objects in the graphe.
		String nameOfListLinksWithDefaultOntology = "listLinksWithDefaultOntology";
		ArrayList<UriAndUriAndNumber> listLinksWithDefaultOntology = new ArrayList<UriAndUriAndNumber>();
		listLinksWithDefaultOntology = MakeListLinksWithDefaultOntology.makeList(model);

		// List of links between subjects and objects in the graphe with rdf:type predicat.
		String nameOfListNameSpaceWithRdfTypePredicat = "listNameSpaceWithRdfTypePredicat";
		ArrayList<UriAndUriAndNumber> listNameSpaceWithRdfTypePredicat = new ArrayList<UriAndUriAndNumber>();
		listNameSpaceWithRdfTypePredicat = MakeListNameSpaceWithRdfTypePredicat.makeList(model);
		
		// List of links between subjects and objects in the graphe with rdfs:subClassOf predicat.
		String nameOfListNameSpaceWithRdfsSubClassOfPredicat = "listNameSpaceWithRdfsSubClassOfPredicat";
		ArrayList<UriAndUriAndNumber> listNameSpaceWithRdfsSubClassOfPredicat = new ArrayList<UriAndUriAndNumber>();
		listNameSpaceWithRdfsSubClassOfPredicat = MakeListNameSpaceWithRdfsSubClassOfPredicat.makeList(model);
		

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe. 
		String nameOfListTripleNameSpace = "listTripleNameSpace";
		ArrayList<UriAndUriAndUriAndNumber> listTripleNameSpace = new ArrayList<UriAndUriAndUriAndNumber>();
		listTripleNameSpace = MakeListTripleNameSpace.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe. 
		String nameOfListTripleNameSpaceWithDefaultOntology = "listTripleNameSpaceWithDefaultOntology";
		ArrayList<UriAndUriAndUriAndNumber> listTripleNameSpaceWithDefaultOntology = new ArrayList<UriAndUriAndUriAndNumber>();
		listTripleNameSpaceWithDefaultOntology = MakeListTripleNameSpaceWithDefaultOntology.makeList(model);
		
		// Création d'une liste avec les deux noms de domaine du sujet et du
		//  du predicat et de la nature de l'objet quand il s'agit d'un litéral. 
		String nameOfListTripleNameSpaceAndDataType = "listTripleNameSpaceAndDataType";
		ArrayList<UriAndUriAndUriAndNumber> listTripleNameSpaceAndDataType = new ArrayList<UriAndUriAndUriAndNumber>();
		listTripleNameSpaceAndDataType = MakeListTripleNameSpaceAndDataType.makeList(model);

	    // Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires de représentation des connaissances. 
		String nameOfListNsKnowledgeRepresentationVocabularies = "listNsKnowledgeRepresentationVocabularies";
		ArrayList<UriAndNumber> listNsKnowledgeRepresentationVocabularies = new ArrayList<UriAndNumber>();
		listNsKnowledgeRepresentationVocabularies = MakeListNsKnowledgeRepresentationVocabularies.makeList(model);
		
		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires réutilisés. 
		String nameOfListNsReusedVocabularies = "listNsReusedVocabularies";
		ArrayList<UriAndNumber> listNsReusedVocabularies = new ArrayList<UriAndNumber>();
		listNsReusedVocabularies = MakeListNsReusedVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les autres vocabulaires. 
		String nameOfListNsOtherVocabularies = "listNsOtherVocabularies";
		ArrayList<UriAndNumber> listNsOtherVocabularies = new ArrayList<UriAndNumber>();
		listNsOtherVocabularies = MakeListNsOtherVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires de représentation des connaissances. 
		String nameOfListNsSubjectKnowledgeRepresentationVocabularies = "listNsSubjectKnowledgeRepresentationVocabularies";
		ArrayList<UriAndNumber> listNsSubjectKnowledgeRepresentationVocabularies = new ArrayList<UriAndNumber>();
		listNsSubjectKnowledgeRepresentationVocabularies = MakeListNsSubjectKnowledgeRepresentationVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires réutilisés. 
		String nameOfListNsSubjectReusedVocabularies = "listNsSubjectReusedVocabularies";
		ArrayList<UriAndNumber> listNsSubjectReusedVocabularies = new ArrayList<UriAndNumber>();
		listNsSubjectReusedVocabularies = MakeListNsSubjectReusedVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les autres vocabulaires. 
		String nameOfListNsSubjectOtherVocabularies = "listNsSubjectOtherVocabularies";
		ArrayList<UriAndNumber> listNsSubjectOtherVocabularies = new ArrayList<UriAndNumber>();
		listNsSubjectOtherVocabularies = MakeListNsSubjectOtherVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires de représentation des connaissances. 
		String nameOfListNsPredicateKnowledgeRepresentationVocabularies = "listNsPredicateKnowledgeRepresentationVocabularies";
		ArrayList<UriAndNumber> listNsPredicateKnowledgeRepresentationVocabularies = new ArrayList<UriAndNumber>();
		listNsPredicateKnowledgeRepresentationVocabularies = MakeListNsPredicateKnowledgeRepresentationVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires réutilisés. 
		String nameOfListNsPredicateReusedVocabularies = "listNsPredicateReusedVocabularies";
		ArrayList<UriAndNumber> listNsPredicateReusedVocabularies = new ArrayList<UriAndNumber>();
		listNsPredicateReusedVocabularies = MakeListNsPredicateReusedVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les autres vocabulaires. 
		String nameOfListNsPredicateOtherVocabularies = "listNsPredicateOtherVocabularies";
		ArrayList<UriAndNumber> listNsPredicateOtherVocabularies = new ArrayList<UriAndNumber>();
		listNsPredicateOtherVocabularies = MakeListNsPredicateOtherVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires de représentation des connaissances. 
		String nameOfListNsObjectKnowledgeRepresentationVocabularies = "listNsObjectKnowledgeRepresentationVocabularies";
		ArrayList<UriAndNumber> listNsObjectKnowledgeRepresentationVocabularies = new ArrayList<UriAndNumber>();
		listNsObjectKnowledgeRepresentationVocabularies = MakeListNsObjectKnowledgeRepresentationVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires réutilisés. 
		String nameOfListNsObjectReusedVocabularies = "listNsObjectReusedVocabularies";
		ArrayList<UriAndNumber> listNsObjectReusedVocabularies = new ArrayList<UriAndNumber>();
		listNsObjectReusedVocabularies = MakeListNsObjectReusedVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les autres vocabulaires. 
		String nameOfListNsObjectOtherVocabularies = "listNsObjectOtherVocabularies";
		ArrayList<UriAndNumber> listNsObjectOtherVocabularies = new ArrayList<UriAndNumber>();
		listNsObjectOtherVocabularies = MakeListNsObjectOtherVocabularies.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe pour les vocabulaires de représentation des connaissances. 
		String nameOfListLiteralNsKnowledgeRepresentationVocabularies = "listLiteralNsKnowledgeRepresentationVocabularies";
		ArrayList<UriAndNumber> listLiteralNsKnowledgeRepresentationVocabularies = new ArrayList<UriAndNumber>();
		listLiteralNsKnowledgeRepresentationVocabularies = MakeListLiteralNsKnowledgeRepresentationVocabularies.makeList(model);

		//////////////////////////////////////////////
		// Transfert des listes dans fichers .json  //
		//////////////////////////////////////////////

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listGraphTripleSize, nameOfListGraphTripleSize + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listPredicatUsageCount, nameOfListPredicatUsageCount + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listPredicatNoRdfSyntaxUsageCount, nameOfListPredicatNoRdfSyntaxUsageCount + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listPredicatNoRdfRdfsOwlUsageCount, nameOfListPredicatNoRdfRdfsOwlUsageCount + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriFile(listPropertyDeclared, nameOfListPropertyDeclared + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriFile(listClassDeclared, nameOfListClassDeclared + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listNameSpacePropertyDeclared, nameOfListNameSpacePropertyDeclared + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listNameSpaceClassDeclared, nameOfListNameSpaceClassDeclared + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndX2UriAndUriListAndUriListAndUriListFile(listPropertyDomainAndRange, nameOfListPropertyDomainAndRange + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriFile(listPropertyProperties, nameOfListPropertyProperties + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriFile(listResourcesSameAs, nameOfListResourcesSameAs + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriFile(listResourceDeprecated, nameOfListResourceDeprecated + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriFile(listUriOntology, nameOfListUriOntology + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndStringFile(listModelPrefixNameSpace, nameOfListModelPrefixNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriFile(listUriImport, nameOfListUriImport + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listGraphNameSpace, nameOfListGraphNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listTripleSubjectNameSpace, nameOfListTripleSubjectNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listReduceTripleSubjectNameSpace, nameOfListReduceTripleSubjectNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listTripleObjectNameSpace, nameOfListTripleObjectNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listTriplePredicatNameSpace, nameOfListTriplePredicatNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount, nameOfListTriplePredicatNameSpaceNoRdfRdfsOwlUsageCount + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listLinksSubjectObject, nameOfListLinksSubjectObject + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listNameSpaceWithRdfTypePredicat, nameOfListNameSpaceWithRdfTypePredicat + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listNameSpaceWithRdfsSubClassOfPredicat, nameOfListNameSpaceWithRdfsSubClassOfPredicat + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listLinksWithDefaultOntology, nameOfListLinksWithDefaultOntology + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndUriAndNumberFile(listTripleNameSpace, nameOfListTripleNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndUriAndNumberFile(listTripleNameSpaceWithDefaultOntology, nameOfListTripleNameSpaceWithDefaultOntology + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndUriAndNumberFile(listTripleNameSpaceAndDataType, nameOfListTripleNameSpaceAndDataType + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsKnowledgeRepresentationVocabularies, nameOfListNsKnowledgeRepresentationVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsReusedVocabularies, nameOfListNsReusedVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsOtherVocabularies, nameOfListNsOtherVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsSubjectKnowledgeRepresentationVocabularies, nameOfListNsSubjectKnowledgeRepresentationVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsSubjectReusedVocabularies, nameOfListNsSubjectReusedVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsSubjectOtherVocabularies, nameOfListNsSubjectOtherVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsPredicateKnowledgeRepresentationVocabularies, nameOfListNsPredicateKnowledgeRepresentationVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsPredicateReusedVocabularies, nameOfListNsPredicateReusedVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsPredicateOtherVocabularies, nameOfListNsPredicateOtherVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsObjectKnowledgeRepresentationVocabularies, nameOfListNsObjectKnowledgeRepresentationVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsObjectReusedVocabularies, nameOfListNsObjectReusedVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listNsObjectOtherVocabularies, nameOfListNsObjectOtherVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listLiteralNsKnowledgeRepresentationVocabularies, nameOfListLiteralNsKnowledgeRepresentationVocabularies + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		Instant end0 = Instant.now();
		System.out.println("Runtime for treatments: " + AgroPortalProfilingUtil.getDurationAsString(Duration.between(start0, end0).toMillis()));
	}
}