package com.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.service.ServiceA;

@RestController
public class ServiceAController {
	//@Autowired
	//ServiceA sa;
	@RequestMapping("/info")
    public String test() {
        return "hello I am service A"; //测试代码直接返回一个字符串，不再调用service层等等。
    }
	@GetMapping("/apia")
    public String apia() {
        return "API A"; //测试代码直接返回一个字符串，不再调用service层等等。
    }
	@RequestMapping("/apib")
    public String apib() {
        return "API B"; //测试代码直接返回一个字符串，不再调用service层等等。
    }
	@RequestMapping("/apic")
    public String apic() {
        return "API C"; //测试代码直接返回一个字符串，不再调用service层等等。
    }
}
