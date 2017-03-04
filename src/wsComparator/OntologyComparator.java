package wsComparator;

import java.util.HashMap;
import org.mindswap.pellet.owlapi.Reasoner;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

public class OntologyComparator {

    private static Reasoner reasoner = null;
    private static String ontLocation = "File:./src/SUMO.owl";
    private static OWLOntologyManager manager = null;
    private static OWLOntology ontology = null;
    private static OntologyManager ontsum = null;
    
    public static double getScore(String clsName1, String clsName2) {
        HashMap<String, OWLClass> mapName_OWLClass = ontsum.loadClasses(reasoner);

        OWLClass cls1 = mapName_OWLClass.get(clsName1.toLowerCase());
        OWLClass cls2 = mapName_OWLClass.get(clsName2.toLowerCase());

        if (cls1 == null || cls2 == null) {
        }       
        else if (reasoner.isEquivalentClass(cls1, cls2)) {
            return 1.0f;
        } else if (reasoner.isSubClassOf(cls2, cls1)) {
            return 0.8f;
        } else if (reasoner.isSubClassOf(cls1, cls2)) {
            return 0.6f;
        } else if(ontsum.findRelationship(cls1, cls2,reasoner).size() > 0){
        	return 0.5f;
        } 
        
        return 0.0f;
    }

    public static void initializeOntology() {
        ontsum = new OntologyManager();
        manager = ontsum.initializeOntologyManager();
        ontology = ontsum.initializeOntology(manager, ontLocation);
        reasoner = ontsum.initializeReasoner(ontology, manager);
    }
}