package com.ineuron.domain.nlp.test;

import com.ineuron.domain.nlp.service.NLPService;

public class Test {

	public static void main(String[] args){
		NLPService service = NLPService.getInstance();
		
		service.parseText("适合金属的耐黄变较好的磁漆");
		service.parseText("适合家居的室内绿色水性乳胶漆");
		service.parseText("适合木器的具有防火功能的透明漆");	
		service.parseText("一个月后需要适合金属的耐黄变较好的磁漆1000L");
		service.parseText("我要500L保温效果最好的外墙漆");
		service.parseText("水性乳胶漆这一个月内的产量");
	}
}