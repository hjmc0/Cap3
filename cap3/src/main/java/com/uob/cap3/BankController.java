package com.uob.cap3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BankController {

    @RequestMapping("/")
    public String landing() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/view")
    public String viewPage() {
        return "view";
    }

    @RequestMapping("/withdraw/{id}")
    public String withdrawPage(@PathVariable int id) {
        return "inout";
    }

    @RequestMapping("/deposit/{id}")
    public String depositPage(@PathVariable int id) {
        return "inout";
    }

    @RequestMapping("/transaction/{id}")
    public String transactionPage(@PathVariable int id) {
        return "transaction";
    }

    @RequestMapping("/createteller")
    public String createTeller() {
        return "createteller";
    }

    @RequestMapping("/createaccount")
    public String createAccount() {
        return "createaccount";
    }
}
