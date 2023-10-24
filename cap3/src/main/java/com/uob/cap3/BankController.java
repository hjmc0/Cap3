package com.uob.cap3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
// import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uob.cap3.entities.Account;
import com.uob.cap3.entities.Role;
import com.uob.cap3.entities.Teller;
import com.uob.cap3.entities.Transaction;
import com.uob.cap3.repo.AccountRepo;

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
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/view")
    public String viewPage(Model m) {
        m.addAttribute("accounts", (List<Account>) ar.findAll());
        return "view";
    }

    @RequestMapping("/transact/{id}")
    public String transact(Model m, @PathVariable Long id) {
        Account accToEdit = ar.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        m.addAttribute("accToEdit", accToEdit);
        return "transact";
    }

    @RequestMapping("/savetransact")
    public String transactionPage(@RequestParam(value = "id") long id,
            @RequestParam(value = "transType") String transType, @RequestParam(value = "amt") double amt) {
        Account acc = ar.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        double old_bal = acc.getBalance();
        if(transType.equalsIgnoreCase("withdraw") && amt>old_bal){
            //toastify here

            return "redirect:/transact/"+id;
        }
        acc.setBalance(old_bal + (transType.equalsIgnoreCase("withdraw") ? -amt : amt));
        ar.save(acc);

        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(dateTime);
        Transaction trans = new Transaction();

        trans.setAccountId(id);
        trans.setAmount(amt);
        trans.setTransDate(ts);
        trans.setTransType(transType);
        tr.save(trans);
        return "redirect:/view";
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
