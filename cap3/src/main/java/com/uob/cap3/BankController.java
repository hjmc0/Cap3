package com.uob.cap3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uob.cap3.entities.Account;
import com.uob.cap3.repo.AccountRepo;


@Controller
public class BankController {
    @Autowired
    AccountRepo ar;

    @RequestMapping("/")
    public String landing() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/view")
    public String viewPage(Model m) {
        m.addAttribute("accounts", (List<Account>) ar.findAll());
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
