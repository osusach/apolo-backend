package osusach.apolo.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user/testing")
public class TestingController {

    @GetMapping("/test01")
    public String test01() {
        return "Test 01 from TestingController in user.controllers";
    }

    @GetMapping("/test02")
    public String test02() {
        return "Test 02 from TestingController in user.controllers protected";
    }

}
