package com.ineuron.domain.nlp.test;

import com.ineuron.domain.nlp.service.NLPService;

public class Test {

	public static void main(String[] args){
		NLPService service = NLPService.getInstance();
		
		service.parseText("适合金属的耐黄变较好的磁漆");
		
		service.parseText("适合木器的具有防火功能的透明漆");	
		service.parseText("一个月后需要适合金属的耐黄变较好的磁漆1000L");
		service.parseText("我要55ml保温效果最好的外墙漆");
		service.parseText("无毒环保的水性乳胶漆");
		service.parseText("室内");
		service.parseText("竹炭净味漆");
		service.parseText("10天后需要适合木器的具有防火功能的透明漆1000L");
		service.parseText("10周后需要适合木器的具有防火功能的透明漆10吨");
		service.parseText("1个月后需要适合木器的具有防火功能的青漆100千克");
		service.parseText("1个月后需要适合木器的具有防火功能的底漆100千克");
		service.parseText("1个月后需要适合木器的具有防火功能的透明漆二千克");
		service.parseText("1个月后需要适合木器的具有防火功能的透明漆两千克");
		service.parseText("1个月后需要适合木器的具有防火功能的透明漆九千克");
		service.parseText("1个月后需要适合木器的具有防火功能的透明漆11千克");
		service.parseText("一个月后需要适合木器的具有防火功能的透明漆100克");
		service.parseText("八天后需要适合木器的具有防火功能的透明漆100公斤");
		service.parseText("我要500L保温效果最好的内墙漆");
		service.parseText("木器");	
		service.parseText("柔光亚光 ");
		service.parseText("适合家居的室内绿色水性乳胶漆");
		service.parseText("抗菌");
		service.parseText("外墙漆");
		service.parseText("买5桶无毒环保的水性乳胶漆");
		
	}
}
