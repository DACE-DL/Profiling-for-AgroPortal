package agroPortalProfiling.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

public class MakeListLinks {
	
	// Création d'une liste des liens dans un triplet
	public static ArrayList<UriAndUriAndNumber> makeList(Model model, ArrayList<UriAndNumber> listPredicatUsageCount, ArrayList<UriAndString> listModelPrefixNameSpace) {
    
		String prefix = AgroPortalProfilingConf.queryPrefix;
	
		ArrayList<UriAndUriAndNumber> ListResources = new ArrayList<UriAndUriAndNumber>();
		Map<String, UriAndUriAndNumber> resourceMap = new HashMap<>();

		// Constitution d'une liste de NameSpace triée
		ArrayList<Uri> listNameSpace = new ArrayList<Uri>();
		for (UriAndString uriAndString : listModelPrefixNameSpace) {
			listNameSpace.add(new Uri(uriAndString.getUri().toString()));
		}	
		// Trier la liste des NameSpace (ordre décroissant)
        listNameSpace.sort(Comparator.comparing(Uri::toString).reversed());
	
		listPredicatUsageCount.forEach((property) -> {
			Query query2 = QueryFactory.create(prefix +
				" SELECT ?s (<" + property.getUri().toString() + "> AS ?property) ?o WHERE { " +
				" ?s <" + property.getUri().toString() + "> ?o " +
				" FILTER isIRI(?s) " +
				" } "
			);
	
			QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
			ResultSet result2 = qe2.execSelect();
			if (result2.hasNext()) {
				while (result2.hasNext()) {
					QuerySolution querySolution = result2.next();
					Resource subject = querySolution.getResource("s");
					RDFNode objectNode = querySolution.get("o");
	
					if (objectNode.isResource()) {
						Resource object = querySolution.getResource("o");
						if (!(subject.isAnon() || object.isAnon())) {
							String nameDomainSubject = normalizeNamespace(subject.getNameSpace(), subject.getURI(), listNameSpace);
							String nameDomainObject = normalizeNamespace(object.getNameSpace(), object.getURI(), listNameSpace);
	
							String key = nameDomainSubject + nameDomainObject;
							resourceMap.computeIfAbsent(key, k -> new UriAndUriAndNumber(nameDomainSubject, nameDomainObject, 0)).incrementNumber();
						}
					} else {
						if (!subject.isAnon()) {
							String nameDomainSubject = normalizeNamespace(subject.getNameSpace(), subject.getURI(), listNameSpace);
							String key = nameDomainSubject + "";
							resourceMap.computeIfAbsent(key, k -> new UriAndUriAndNumber(nameDomainSubject, "", 0)).incrementNumber();
						}
					}
				}
			}
		});
	
		ListResources.addAll(resourceMap.values());
		ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
		return ListResources;
	}

	private static String normalizeNamespace(String namespace, String uri, ArrayList<Uri> listNameSpace) {
		// Vérifiez si l'URI contient l'un des noms de domaine
        for (Uri nameSpace : listNameSpace) {
            if (uri.contains(nameSpace.getUri())) {
                return nameSpace.getUri();
            }
        }
		
		Pattern pat = Pattern.compile("^(((ftp|http|https):\\/\\/)?(www.)?[a-zA-Z0-9\\.-]*(\\/)?).*");
		Matcher mat = pat.matcher(namespace);
		if (mat.find()) {
			String normalized = mat.group(1);
			if (normalized.equals("http://http/") || normalized.equals("http://") || normalized.equals("https://") || normalized.equals("ftp://") || normalized.equals("www.")) {
				return uri;
			}
			return normalized;
		}
		return namespace;
	}



	////////////////////////////////////////////////
	// Sans controle namespace déclarés           //
	////////////////////////////////////////////////
	// Création d'une liste des liens dans un triplet
	public static ArrayList<UriAndUri> makeList(Model model, ArrayList<UriAndNumber> listPredicatUsageCount) {
		
		String prefix = AgroPortalProfilingConf.queryPrefix;

		ArrayList<UriAndUri> ListResources = new ArrayList<UriAndUri>();
		List<String> listResourcesString = new ArrayList<>();
		
		listPredicatUsageCount.forEach((property) -> {
			//System.out.println("property : " + property.getUri().toString());
			Query query2 = QueryFactory.create(prefix +
				" SELECT ?s (<" + property.getUri().toString() + "> AS ?property) ?o WHERE { " +
					" ?s <" + property.getUri().toString() + "> ?o " +
					" FILTER isIRI(?s) " +
					" FILTER isIRI(?o) " +
				" } "
			);
			
			QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
			ResultSet result2 = qe2.execSelect();
			if (result2.hasNext()) {
				//System.out.println("query OK !!!!!!!!!!!!!!!!!!");
				while (result2.hasNext()) {
					QuerySolution querySolution = result2.next();
					Resource subject = querySolution.getResource("s");
					Resource object = querySolution.getResource("o") ;
					//System.out.println("uri : " + object.getURI().toString());
					//System.out.println("split : " + object.getURI().substring(1, SplitIRI.splitpoint(object.getURI())) );
					if (!(subject.isAnon() || object.isAnon())) {		
						Pattern pat = Pattern.compile("^(((ftp|http|https):\\/\\/)?(www.)?[a-zA-Z0-9\\.-]*(\\/)?).*");
						String nameDomainSubject = subject.getNameSpace();
						String nameDomainObject = object.getNameSpace();
						
						Matcher matSubject = pat.matcher(subject.getNameSpace());
						if (matSubject.find() ){
							nameDomainSubject = matSubject.group(1);
						} 
						if (nameDomainSubject.equals("http://http/")) {
							nameDomainSubject = subject.getNameSpace();	
						}
						if (nameDomainSubject.equals("http://")) {
							nameDomainSubject = subject.getURI();	
						}
						
						Matcher matObject = pat.matcher(object.getNameSpace());
						if (matObject.find() ){
							nameDomainObject = matObject.group(1);
						}
						if (nameDomainObject.equals("http://http/")) {
								nameDomainObject = object.getNameSpace();	
						}
						if (nameDomainObject.equals("http://")) {
							nameDomainObject = object.getURI();	
						} 
						
						if (!nameDomainSubject.equals(nameDomainObject)) {
							String SubNsObNs = nameDomainSubject + nameDomainObject;
							if (!listResourcesString.contains(SubNsObNs)) { 
								listResourcesString.add(SubNsObNs);
								ListResources.add(new UriAndUri(nameDomainSubject, nameDomainObject)) ;
							}	
						}
					}	
				}
			}
		});
		
		return ListResources;
	}
}