package com.ineuron.domain.nlp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.ineuron.domain.nlp.valueobject.ProductSelection;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
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
	private static final String TYPE_BY_FUNCTION = "TYPE_BY_FUNCTION";
	private static final String TYPE_BY_PURPOSE = "TYPE_BY_PURPOSE";
	
	private static final String TYPE_BY_EFFECT_OF_SURFACE = "TYPE_BY_EFFECT_OF_SURFACE";
	private static final String TYPE_BY_FUNCTION_FORM = "TYPE_BY_FUNCTION_FORM";
	private static final String TYPE_BY_PLACE = "TYPE_BY_PLACE";

	private NLPService() {
		corenlp = new StanfordCoreNLP("com/ineuron/domain/nlp/nlp-chinese.properties");
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
		for(IndexedWord child : children){
			
		
				switch (child.ner()){
					case COLOR:
						result.setColor(child.lemma());
						break;
					case TYPE_BY_FORM:
						result.setForm(child.lemma());
						break;
					case QUALITY:
						result.setQuality(child.lemma());
						break;
					case TYPE_BY_FUNCTION:
						result.setFunction(child.lemma());
						break;
					case TYPE_BY_PURPOSE:
						result.setPurpose(child.lemma());
						break;
					default:
						otherAttrs.add(child.lemma());
						break;
						
				}

			
		}
		
		result.setOtherAttributes(otherAttrs);
		IndexedWord productName = getProductName(dependencies, root);
		if(productName != null){
			result.setProductName(productName.lemma());
		}
		
		
		System.out.println(dependencies.toString());
		System.out.println(result.toString());
		System.out.println("----------------------------------------------");
		return null;

	}


	private IndexedWord getProductName(SemanticGraph dependencies, IndexedWord root) {
		System.out.println("getProductName。root.tag() = " + root.tag());
		if(root.lemma().lastIndexOf("漆") != -1){
			return root;
		}
		Set<IndexedWord> children = dependencies.getChildren(root);
		for(IndexedWord child : children){
			System.out.println("child = " + child);
			System.out.println("child.ner() = " + child.ner());
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
