package agroPortalProfiling.util;

import java.util.ArrayList;

public class MakeListTripleNSwithDefaultNS {
	
	// Création d'une liste avec les trois noms de domaine du sujet,
	//  du predicat et de l'objet des triplets du graphe. 
	public static ArrayList<UriAndUriAndUriAndNumber> makeList(ArrayList<UriAndNumber> listTripleNameSpace, ArrayList<UriAndString> listModelPrefixNameSpace) {

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
				if (tripleNameSpace.getUri().toString().equals(defaultNS) ||
					tripleNameSpace.getUri().toString().equals(defaultNS) ||
					tripleNameSpace.getUri().toString().equals(defaultNS)) {
					
				}	
			});
		
			//ListResources.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
		}
		return ListResources;
	}

}