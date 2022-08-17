package test1.com.test1.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class TestController {
    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World!";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String helloName(@PathVariable(value = "name") String name) {
        return String.format("Привет, %s!", name);
    }
}