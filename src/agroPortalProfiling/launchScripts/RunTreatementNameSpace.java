package agroPortalProfiling.launchScripts;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import org.apache.jena.rdf.model.Model;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import agroPortalProfiling.util.*;

public class RunTreatementNameSpace {
	
	// On effectue les pré-traitements
	public static void treatements(Model model) throws JsonParseException, JsonMappingException, IOException {
		
		Instant start0 = Instant.now();

		////////////////////////////////////
		//// Pour le dataset en général ////
		////////////////////////////////////
		

		// Liste des propriétés et de leur usage.
		String nameOfListPropertyUsageCount = "listPropertyUsageCount";
		ArrayList<UriAndNumber> listPropertyUsageCount = new ArrayList<UriAndNumber>();
		listPropertyUsageCount = MakeListPropertyUsageCount.makeList(model);

		// Liste des propriétés déclarées.
		String nameOfListPropertyDeclared = "listPropertyDeclared";
		ArrayList<UriAndUri> listPropertyDeclared = new ArrayList<UriAndUri>();
		listPropertyDeclared = MakeListPropertyDeclared.makeList(model);

		// Liste des propriétés et leur domain and range déclarés.
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

		// Liste des prefix des espaces de noms  .
		String nameOfListModelPrefixNameSpace = "listModelPrefixNameSpace";
		ArrayList<UriAndString> listModelPrefixNameSpace = new ArrayList<UriAndString>();
		listModelPrefixNameSpace = MakeListModelPrefixNameSpace.makeList(model);

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

		
		// List of links between domain names between subjects and objects in the graphe.
		String nameOfListLinksSubjectObject = "listLinksSubjectObject";
		ArrayList<UriAndUriAndNumber> listLinksSubjectObject = new ArrayList<UriAndUriAndNumber>();
		listLinksSubjectObject = MakeListLinksSubjectObject.makeList(model);

		// Création d'une liste avec les trois noms de domaine du sujet,
		//  du predicat et de l'objet des triplets du graphe. 
		String nameOfListTripleNameSpace = "listTripleNameSpace";
		ArrayList<UriAndUriAndUriAndNumber> listTripleNameSpace = new ArrayList<UriAndUriAndUriAndNumber>();
		listTripleNameSpace = MakeListTripleNameSpace.makeList(model);
		
		//////////////////////////////////////////////////////////////////////////////////////
		// En comparant les deux listes, on peut observer la différence
		//  sur le nombre de propriétés declarées et celles utilisées dans des triplets
		//  OM versus IM
		//////////////////////////////////////////////////////////////////////////////////////

		// Liste des anomalies pour les propriétés dont le domain et le range sont déclarés.
		String nameOfListPropertyDomainAndRangeAnomalies = "listPropertyDomainAndRangeAnomalies";
		ArrayList<UriAndStringListAndStringList> listPropertyDomainAndRangeAnomalies = new ArrayList<UriAndStringListAndStringList>();
		listPropertyDomainAndRangeAnomalies = MakeListPropertyDomainAndRangeAnomalies.makeList(model, listPropertyDomainAndRange);

		//////////////////////////////////////////////
		// Transfert des listes dans fichers .json  //
		//////////////////////////////////////////////

		try {
			AgroPortalProfilingUtil.makeJsonUriAndNumberFile(listPropertyUsageCount, nameOfListPropertyUsageCount + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriFile(listPropertyDeclared, nameOfListPropertyDeclared + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndX2UriAndUriListAndUriListAndUriListFile(listPropertyDomainAndRange, nameOfListPropertyDomainAndRange + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			AgroPortalProfilingUtil.makeJsonUriAndStringListAndStringListFile(listPropertyDomainAndRangeAnomalies, nameOfListPropertyDomainAndRangeAnomalies + ".json");
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
			AgroPortalProfilingUtil.makeJsonUriAndStringFile(listModelPrefixNameSpace, nameOfListModelPrefixNameSpace + ".json");
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
			AgroPortalProfilingUtil.makeJsonUriAndUriAndNumberFile(listLinksSubjectObject, nameOfListLinksSubjectObject + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AgroPortalProfilingUtil.makeJsonUriAndUriAndUriAndNumberFile(listTripleNameSpace, nameOfListTripleNameSpace + ".json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Instant end0 = Instant.now();
		System.out.println("Runtime for treatments: " + AgroPortalProfilingUtil.getDurationAsString(Duration.between(start0, end0).toMillis()));
	}
}