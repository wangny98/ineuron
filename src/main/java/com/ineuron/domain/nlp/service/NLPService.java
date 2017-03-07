package com.ineuron.domain.nlp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	private static final String QUANTITY = "QUANTITY";
	private static final String FUTURE_DATE = "FUTURE_DATE";
	
	private static Set<String> PAINT_NAMES;

	private List<String> vvWhiteList;
	
	private List<String> synonymMatrix;
	
	private NLPService() {
		corenlp = new StanfordCoreNLP("com/ineuron/domain/nlp/nlp-chinese.properties");
		String[] paintNames = {"PAINT_BY_EFFECT_OF_SURFACE","PAINT_BY_FORM","PAINT_BY_FUNCTION_FORM","PAINT_BY_FUNCTION","PAINT_BY_PLACE"};
		PAINT_NAMES = new HashSet<String>(Arrays.asList(paintNames));
		vvWhiteList = readFileByLines("com/ineuron/domain/nlp/dict/vv-whitelist.dict");
		synonymMatrix = readFileByLines("com/ineuron/domain/nlp/dict/synonym-matrix.dict");
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

        Set<String> otherAttrs = new HashSet<String>();
        Set<IndexedWord> nodes = dependencies.descendants(root);
        nodes.add(root);
        String productName = null;
        
        for (IndexedWord child : nodes)
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
        		String scope = child.lemma();
        		if(scope.lastIndexOf("漆") != -1){
        			scope = scope.substring(0, scope.length() -1);
        		}
        		result.addScope(scope);
        		continue;
        	}else if(TYPE_BY_FUNCTION.equals(child.ner())){
        		result.addFunction(child.lemma());
        		continue;
        	}else if(FUTURE_DATE.equals(child.ner())){
            	result.addDateElement(child.lemma());
            	continue;
            }else if(QUANTITY.equals(child.ner())){
            	result.addQuantityElement(child.lemma());
            	continue;
            }else if(PAINT_NAMES.contains(child.ner())){
                productName = child.lemma();
                continue;
            }else if(child.lemma().lastIndexOf("色") != -1){
            	result.addColor(child.lemma());
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
            }else if("VV".equals(child.tag()) && isInVVWhiteList(child.lemma())){
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
        if(productName != null && productName.trim().length() > 1 && productName.lastIndexOf("漆") != -1){
        	String attribute = productName.substring(0, productName.length() - 1);
        	boolean matched = matchToKnownAttributes(result, attribute);
        	if(!matched){
        		otherAttrs.add(attribute);
        	}
        	
        }
        result.setOtherAttributes(otherAttrs);
        
        //Add the scope term which synonym is matched to the nlp parsed words;
        //currently, only search for functions and other attributes.
        List<String> parsedWords=new ArrayList<String>();
        if(result.getFunctions()!=null) parsedWords.addAll(result.getFunctions());
        if(result.getOtherAttributes()!=null) parsedWords.addAll(result.getOtherAttributes());
        
        if(parsedWords!=null){
          for (int i=0; i<synonymMatrix.size(); i++){
        	//System.out.println(synonymMatrix.get(i));
        	String[] synonymPair=synonymMatrix.get(i).split("=");
        	for(int j=0; j<parsedWords.size(); j++){
        		//System.out.println("s0: "+synonymPair[0]+" s1: "+synonymPair[1]+" nlp words: "+parsedWords.get(j));
        		if(synonymPair[1].equals(parsedWords.get(j))) {
        			result.addScope(synonymPair[0]);
        		}
        	}
          }
        }
        
		
		System.out.println(dependencies.toString());
		System.out.println(result.toString());
		System.out.println("----------------------------------------------");
		return result;

	}


	private boolean isInVVWhiteList(String tag) {
		if(vvWhiteList.contains(tag)){
			return true;
		}
		return false;
	}
	
	private List<String> readFileByLines(String fileName) {
		List<String> result = new ArrayList<String>();
		InputStream in = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                line++;
                result.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }

	private boolean matchToKnownAttributes(ProductSelection result, String attribute) {
		boolean matched = false;
		Annotation document = new Annotation(attribute);
		
		corenlp.annotate(document);
		SemanticGraph dependencies = parserOutput(document);
		IndexedWord root = dependencies.getFirstRoot();
		if(COLOR.equals(root.ner())){
    		result.addColor(root.lemma());
    		matched = true;
    	}else if(TYPE_BY_FORM.equals(root.ner())){
    		result.addForm(root.lemma());
    		matched = true;
    	}else if(QUALITY.equals(root.ner())){
    		result.addQuality(root.lemma());
    		matched = true;
    	}else if(TYPE_BY_SCOPE.equals(root.ner())){
    		result.addScope(root.lemma());
    		matched = true;
    	}else if(TYPE_BY_FUNCTION.equals(root.ner())){
    		result.addFunction(root.lemma());
    		matched = true;
    	}
		return matched;
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

