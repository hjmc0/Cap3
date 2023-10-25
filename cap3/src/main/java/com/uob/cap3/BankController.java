package com.uob.cap3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
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
import com.uob.cap3.repo.RoleRepo;
import com.uob.cap3.repo.TellerRepo;
import com.uob.cap3.repo.TransactionRepo;
import com.uob.cap3.entities.Account;
import com.uob.cap3.repo.AccountRepo;

@Controller
public class BankController {
    boolean withdrawExceed = false;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    AccountRepo ar;

    @Autowired
    TransactionRepo tr;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    TellerRepo tellerRepo;

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
        m.addAttribute("withdrawExceed", withdrawExceed);
        return "transact";
    }

    @RequestMapping("/savetransact")
    public String transactionPage(@RequestParam(value = "id") long id,
            @RequestParam(value = "transType") String transType, @RequestParam(value = "amt") double amt) {
        Account acc = ar.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        double old_bal = acc.getBalance();
        if(transType.equalsIgnoreCase("withdraw") && amt>old_bal){
            withdrawExceed = true;
            return "redirect:/transact/"+id;
        }
        withdrawExceed = false;
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

    @RequestMapping("/transaction/{id}")
    public String viewTransactions(Model m, @PathVariable Long id) {
        m.addAttribute("transactions", tr.findByAccountId(id));
        return "transactions";
    }

    @RequestMapping("/createteller")
    public String createTeller(Model model, Teller teller, Principal principal) {
        model.addAttribute("teller", teller);
        List<Role> listRoles = (List<Role>) roleRepo.findAll();
        model.addAttribute("tellerRoles", listRoles);


        List<Teller> tellerList = (List<Teller>) tellerRepo.findAll();
        model.addAttribute("tellerList", tellerList);
        // model.addAttribute("tellerN", principal.getName());
        // model.addAttribute("principle", principal);
        return "createteller";
    }

    @RequestMapping("/createaccount")
    public String createAccount(Model model, Account account) {
        model.addAttribute("account", account);
        List<Account> accountList = (List<Account>) ar.findAll();
        model.addAttribute("accountList", accountList);
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
        tellerRepo.save(teller);
        return "view";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id, Model m) {
        Account acc = ar.findById(id).get();
        m.addAttribute("acc", acc);
        return "close";
    }
    
    @GetMapping("/close")
    public String closeAccount(@RequestParam(value = "dchoice") String dchoice, @RequestParam("accountId") Long id) {
        if (dchoice.equalsIgnoreCase("yes")) {
            Account acc = ar.findById(id).get();
            acc.setStatus("closed");
            ar.save(acc);
            System.out.println("here");
            return "redirect:/view";
        } else {
            return "redirect:/view";
        }
    }
    
    @GetMapping("/addaccount")
    public String addAccount(Account account){
        account.setBalance(0.00);
        account.setStatus("active");
        ar.save(account);
        return "redirect:/createaccount";
    }
}
