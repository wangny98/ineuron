package com.ineuron.domain.nlp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ineuron.domain.nlp.valueobject.ProductSelection;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class NLPService {

	private StanfordCoreNLP corenlp;
	private static NLPService nlpService;
	
	private static final String COLOR = "COLOR";
	private static final String QUALITY = "QUALITY";
	private static final String TYPE_BY_FORM = "TYPE_BY_FORM";
	private static final String TYPE_BY_SCOPE = "TYPE_BY_SCOPE";
	private static final String TYPE_BY_FUNCTION = "TYPE_BY_FUNCTION";
	
	private static Set<String> PAINT_NAMES;


	private NLPService() {
		corenlp = new StanfordCoreNLP("com/ineuron/domain/nlp/nlp-chinese.properties");
		String[] paintNames = {"PAINT_BY_EFFECT_OF_SURFACE","PAINT_BY_FORM","PAINT_BY_FUNCTION_FORM","PAINT_BY_FUNCTION","PAINT_BY_PLACE"};
		PAINT_NAMES = new HashSet<String>(Arrays.asList(paintNames));
	}

	public static NLPService getInstance() {
		if (nlpService == null) {
			nlpService = new NLPService();
		}
		return nlpService;

	}

	public ProductSelection parseText(String text) {
		
		ProductSelection result = new ProductSelection();
		
		Annotation document = new Annotation(text);
		
		corenlp.annotate(document);
		SemanticGraph dependencies = parserOutput(document);
		IndexedWord root = dependencies.getFirstRoot();

        List<String> otherAttrs = new ArrayList<String>();
        Set<IndexedWord> children = dependencies.descendants(root);
        String productName = null;
        
        for (IndexedWord child : children)
        {
        	if(COLOR.equals(child.ner())){
        		result.addColor(child.lemma());
        		continue;
        	}else if(TYPE_BY_FORM.equals(child.ner())){
        		result.addForm(child.lemma());
        		continue;
        	}else if(QUALITY.equals(child.ner())){
        		result.addQuality(child.lemma());
        		continue;
        	}else if(TYPE_BY_SCOPE.equals(child.ner())){
        		result.addScope(child.lemma());
        		continue;
        	}else if(TYPE_BY_FUNCTION.equals(child.ner())){
        		result.addFunction(child.lemma());
        		continue;
        	}else if(PAINT_NAMES.contains(child.ner())){
                productName = child.lemma();
                continue;
            }
        	
        	//VA Predicative adjective
        	//JJ Noun-modifier other than nouns
        	//NN Common nouns
        	//NR Proper nouns
        	//NP Noun phrase
        	//ADJP Adjective phrase
        	else if("VA".equals(child.tag()) 
        			|| "JJ".equals(child.tag())
            		|| "NN".equals(child.tag())
            		|| "NR".equals(child.tag())
            		|| "NP".equals(child.tag())
            		|| "ADJP".equals(child.tag())){
            	otherAttrs.add(child.lemma());
            }
        	
		}
       
		if(productName == null){
		    IndexedWord product = getProductName(dependencies, root);
		    if(product != null){
		        productName = product.lemma();
		    }
		}
        result.setProductName(productName);
        if(productName != null && productName.lastIndexOf("漆") != -1){
        	otherAttrs.add(productName.substring(0, productName.length()-1));
        }
        result.setOtherAttributes(otherAttrs);
		
		System.out.println(dependencies.toString());
		System.out.println(result.toString());
		System.out.println("----------------------------------------------");
		return result;

	}


	private IndexedWord getProductName(SemanticGraph dependencies, IndexedWord root) {
		if(root.lemma().lastIndexOf("漆") != -1){
			return root;
		}
		Set<IndexedWord> children = dependencies.getChildren(root);
		for(IndexedWord child : children){
			//System.out.println("child = " + child);
			//System.out.println("child.ner() = " + child.ner());
			if(child.lemma().lastIndexOf("漆") != -1){
				return child;
			}
		}
		
		return null;
	}

	public SemanticGraph parserOutput(Annotation document) {
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		CoreMap sentence = document.get(CoreAnnotations.SentencesAnnotation.class).get(0);
		/*Set<String> colors = sentence.get(ColorAnnotation.class);
		for(String color : colors){
			System.out.println("color = " + color);
		}*/
		SemanticGraph dependencies = sentence
				.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class);
		
		Collection<CorefChain> corefs = document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values();
		for (CorefChain cc : corefs) {
		      System.out.println("\t CorefChain = " + cc);
		    }
		return dependencies;
	}

}

