package agroPortalProfiling.util;

import java.util.ArrayList;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class MakeListPropertyAndSubproperty {
	
	// Création d'une liste des propriétés et de leur usage dans un triplet
	public static ArrayList<UriAndUri> makeList(Model model, String nameOfList) {
	
		String prefix = AgroPortalProfilingConf.queryPrefix;

		ArrayList<UriAndUri> ListResources = new ArrayList<UriAndUri>();
	
		// Integer n = 0;
		// Resource s = model.createResource(dsp + "sujet");
		// Property p = model.createProperty(dsp + "predicat");
		// Resource o = model.createResource(dsp + "objet");
		// Resource b = model.createResource();
		// Resource u1 = model.createResource(dsp + "property");
		// Resource u2 = model.createResource(dsp + "subproperty");
		// Property pu1 = model.createProperty(dsp + "asProperty");
		// Property pu2 = model.createProperty(dsp + "asSubproperty");
		
		Query query = QueryFactory.create(prefix + 
				"SELECT DISTINCT (?o AS ?property) (?s AS ?subproperty) " +
				" WHERE { " +
				" ?s rdfs:subPropertyOf ?o ." +
				" FILTER isIRI(?o) " +
				" FILTER isIRI(?s) " +
				" FILTER (?s !=<http://www.w3.org/2002/07/owl#bottomObjectProperty>) " +
				" FILTER (?s !=<http://www.w3.org/2002/07/owl#bottomDataProperty>) " +
				" FILTER (?o !=<http://www.w3.org/2002/07/owl#topObjectProperty>) " +
				" FILTER (?o !=<http://www.w3.org/2002/07/owl#topDataProperty>) " +
				" FILTER (?o != ?s) " +
				" } ORDER BY ?s ?o "
		);			
		QueryExecution qe = QueryExecutionFactory.create(query, model);		
		ResultSet result = qe.execSelect();
		if (result.hasNext()) {
			while( result.hasNext() ) {
				QuerySolution querySolution = result.next() ;
				ListResources.add(new UriAndUri(querySolution.getResource("property").toString(), querySolution.getResource("subproperty").toString())) ;
			}
		}

		// for (UriAndUri resource : ListResources) {
		// 	if (n == 0) {
		// 		s = model.createResource(dsp + nameOfList);
		// 		p = model.createProperty(rdf + "first");
				
		// 		b = model.createResource();
		// 		u1 = model.createResource(resource.getUri1().toString());
		// 		u2 = model.createResource(resource.getUri2().toString());
		// 		model.add(b, pu1, u1);
		// 		model.add(b, pu2, u2);

		// 		model.add(s, p, b);
		// 		n = n + 1;
		// 	} else {
		// 		s = model.createResource(dsp + nameOfList + n);
		// 		p = model.createProperty(rdf + "first");
				
		// 		b = model.createResource();
		// 		u1 = model.createResource(resource.getUri1().toString());
		// 		u2 = model.createResource(resource.getUri2().toString());
		// 		model.add(b, pu1, u1);
		// 		model.add(b, pu2, u2);

		// 		model.add(s, p, b);
		// 		if (n == 1) {
		// 			s = model.createResource(dsp + nameOfList);
		// 			p = model.createProperty(rdf + "rest");
		// 			o = model.createResource(dsp + nameOfList + n);
		// 			model.add(s, p, o);
		// 			n = n + 1;
		// 		} else {
		// 			s = model.createResource(dsp + nameOfList + (n - 1));
		// 			p = model.createProperty(rdf + "rest");
		// 			o = model.createResource(dsp + nameOfList + n);
		// 			model.add(s, p, o);
		// 			n = n + 1;
		// 		}
		// 	}
		// }

		// if (n > 0) {

		// 	if (n == 1) {
		// 		s = model.createResource(dsp + nameOfList);
		// 		p = model.createProperty(rdf + "rest");
		// 		o = model.createResource(rdf + "nil");
		// 		model.add(s, p, o);
		// 	} else {
		// 		s = model.createResource(dsp + nameOfList + (n - 1));
		// 		p = model.createProperty(rdf + "rest");
		// 		o = model.createResource(rdf + "nil");
		// 		model.add(s, p, o);
		// 	}
		// 	s = model.createResource(dsp + nameOfList);
		// 	p = model.createProperty(rdf + "type");
		// 	o = model.createResource(rdf + "List");
		// 	model.add(s, p, o);
		// }
		return ListResources;
	}
}