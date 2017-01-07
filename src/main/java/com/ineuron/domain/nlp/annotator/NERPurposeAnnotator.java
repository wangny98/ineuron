package com.ineuron.domain.nlp.annotator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.ineuron.domain.nlp.annotator.annotation.ColorAnnotation;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.util.CoreMap;

public class NERPurposeAnnotator implements Annotator{

	public final static String COLOR = "color";
	
	public void annotate(Annotation annotation) {
		Set<String> colors = new HashSet<String>();
		CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
		for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {  
            // this is the text of the token  
            String word = token.get(CoreAnnotations.TextAnnotation.class);  
            // this is the POS tag of the token  
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);  
            // this is the NER label of the token  
            String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);  
            if(pos.equals("NN") && word.lastIndexOf("色") != -1){
            	colors.add(word);           	
            }
            sentence.set(ColorAnnotation.class, colors);
            System.out.println(word+"\t"+pos+"\t"+ne);  
            
        }  
		
	}

	public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
		return Collections.emptySet();
	}

	public Set<Class<? extends CoreAnnotation>> requires() {
		return Collections.emptySet();
	}


	 

}
