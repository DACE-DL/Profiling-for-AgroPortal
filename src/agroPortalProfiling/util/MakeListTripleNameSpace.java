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

public class MakeListTripleNameSpace {
	
	// Création d'une liste avec les trois noms de domaine du sujet,
	//  du predicat et de l'objet des triplets du graphe. 
	public static ArrayList<UriAndUriAndUriAndNumber> makeList(Model model, ArrayList<UriAndNumber> listPropertyUsageCount, ArrayList<UriAndString> listModelPrefixNameSpace) {

		String prefix = AgroPortalProfilingConf.queryPrefix;
	
		ArrayList<UriAndUriAndUriAndNumber> ListResources = new ArrayList<>();
		Map<String, UriAndUriAndUriAndNumber> resourceMap = new HashMap<>();

		// Constitution d'une liste de NameSpace triée
		ArrayList<Uri> listNameSpace = new ArrayList<Uri>();
		for (UriAndString uriAndString : listModelPrefixNameSpace) {
			listNameSpace.add(new Uri(uriAndString.getUri().toString()));
		}	
		// Trier la liste des NameSpace (ordre décroissant)
        listNameSpace.sort(Comparator.comparing(Uri::toString).reversed());

		// listNameSpace.forEach(uri -> System.out.println(uri.toString()));

		listPropertyUsageCount.forEach((property) -> {
			// Créer un objet Resource à partir de l'URI de la propriété
			Resource propertyResource = ResourceFactory.createResource(property.getUri());
			// Extraction du namespace de la propriété
			String propertyNamespace = normalizeNamespace(propertyResource.getNameSpace(), property.getUri().toString(), listNameSpace);
	
			// SPARQL Query pour extraire les triplets
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
	
							// Clé pour le triplet (sujet, prédicat, objet)
							String key = nameDomainSubject + propertyNamespace + nameDomainObject;
							resourceMap.computeIfAbsent(key, k -> new UriAndUriAndUriAndNumber(nameDomainSubject, propertyNamespace, nameDomainObject, 0)).setNumber(resourceMap.get(key).getNumber() + 1);
						}
					} else {
						if (!subject.isAnon()) {
							String nameDomainSubject = normalizeNamespace(subject.getNameSpace(), subject.getURI(), listNameSpace);
							// Clé pour le triplet (sujet, prédicat, objet vide)
							String key = nameDomainSubject + propertyNamespace + "";
							resourceMap.computeIfAbsent(key, k -> new UriAndUriAndUriAndNumber(nameDomainSubject, propertyNamespace, "", 0)).setNumber(resourceMap.get(key).getNumber() + 1);
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
}