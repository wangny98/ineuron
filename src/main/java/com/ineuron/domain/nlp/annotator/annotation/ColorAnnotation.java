package com.ineuron.domain.nlp.annotator.annotation;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.ErasureUtils;

import java.util.Set;

/**
 * Annotation for the statements of a sentence.
 */
public class ColorAnnotation implements CoreAnnotation<Set<String>> {
    public Class<Set<String>> getType() {
        return ErasureUtils.<Class<Set<String>>>uncheckedCast(String.class);
    }
}
