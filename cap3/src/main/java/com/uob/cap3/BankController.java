package com.uob.cap3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uob.cap3.entities.Account;
import com.uob.cap3.entities.Role;
import com.uob.cap3.entities.Teller;
import com.uob.cap3.repo.AccountRepo;
import com.uob.cap3.repo.RoleRepo;
import com.uob.cap3.repo.TellerRepo;
// import com.uob.cap3.repo.roleRepo;
// import com.uob.cap3.entities.Account;
// import com.uob.cap3.repo.AccountRepo;

@Controller
public class BankController {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    AccountRepo ar;
    
    @Autowired
    TellerRepo tellerRepo;
    @Autowired
    RoleRepo roleRepo; 


    @RequestMapping("/")
    public String landing() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/view")
    public String viewPage(Model m) {
        m.addAttribute("accounts", (List<Account>) ar.findAll());
        return "view";
    }

    @RequestMapping("/transact/{id}")
    public String transact(){
        return "transact";
    }

    @RequestMapping("/transaction/{id}")
    public String transactionPage(@PathVariable int id) {
        return "transaction";
    }

    @RequestMapping("/createteller")
    public String createTeller(Model model, Teller teller) {
        model.addAttribute("teller", teller);
        List<Role> listRoles = (List<Role>) roleRepo.findAll();
        model.addAttribute("listRoles", listRoles);
        return "createteller";
    }

    @RequestMapping("/createaccount")
    public String createAccount() {
        return "createaccount";
    }
    
    @GetMapping("/savetransact")
    public String saveTransact(@RequestParam("id") Long id, @RequestParam("choice") String choice, @RequestParam("amt")Long amt, Model m) {
        Account acc = new Account();
        double bal = ar.findbalance(id);
        String results = "";
        
        if(choice.equals("deposit")) {
                bal = bal + amt;
                acc.setBalance(bal);
                ar.save(acc);
                results = "redirect:/list";
        } else {
            boolean toContinue = true;
            while(toContinue) {
                if(amt > bal) {
                    results = "redirect:/transact";
                } else {
                    bal = bal - amt;
                    acc.setBalance(bal);
                    ar.save(acc);
                    results = "redirect:/list";
                    toContinue = false;
                }
            }
        }
        return results;
    }

    @GetMapping("/adding")
    public String addTeller(Teller teller){
        if (teller.getTellerId()==null){
            teller.setTellerPass(passwordEncoder.encode(teller.getTellerPass()));
        } else {
            Teller teller2 = tellerRepo.findById(teller.getTellerId()).orElseThrow();
            if(teller.getTellerPass().equals(teller2.getTellerPass())){
                teller.setTellerPass(teller2.getTellerPass());
            }
            else {
                teller.setTellerPass(passwordEncoder.encode(teller.getTellerPass()));
            }
        }
        return "redirect:/createteller";
    }
}
