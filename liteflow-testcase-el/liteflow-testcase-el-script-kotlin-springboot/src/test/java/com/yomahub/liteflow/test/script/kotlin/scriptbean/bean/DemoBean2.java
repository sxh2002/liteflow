package com.yomahub.liteflow.test.script.kotlin.scriptbean.bean;

import org.springframework.stereotype.Component;

@Component
public class DemoBean2 {

	public String getDemoStr2(String name) {
		return "hello," + name;
	}

}