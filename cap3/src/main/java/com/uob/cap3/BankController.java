package com.uob.cap3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import com.uob.cap3.service.AccountService;
import com.uob.cap3.service.TellerService;

@Controller
public class BankController {
    boolean withdrawExceed = false, accountClosed = false;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void resetState() {
        accountClosed = false;
        withdrawExceed = false;
    }

    @Autowired
    AccountRepo ar;

    @Autowired
    AccountService as;

    @Autowired
    TellerService ts;

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
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/view")
    public String viewPage(Model m, @RequestParam(value = "query", required = false) String query) {
        withdrawExceed = false;
        m.addAttribute("accounts", (List<Account>) ar.findAll());
        if (query != null && !query.trim().isEmpty()) {
            m.addAttribute("accounts", (List<Account>) as.searchAccounts(query));
        }
        m.addAttribute("accountClosed", accountClosed);
        return "view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model m, @PathVariable Long id) {
        accountClosed = false;
        Account acc = ar.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        m.addAttribute("account", acc);
        return "edit";
    }

    @RequestMapping("/saveEdit")
    public String saveEdit(@ModelAttribute("account") Account account,
            @RequestParam(name = "status", defaultValue = "inactive") String status) {
        account.setStatus(status.equalsIgnoreCase("on") ? "active" : "inactive");
        ar.save(account);
        return "redirect:/view";
    }

    @RequestMapping("/transact/{id}")
    public String transact(Model m, @PathVariable Long id) {
        Account accToEdit = ar.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        if (accToEdit.getStatus().equalsIgnoreCase("closed") || accToEdit.getStatus().equalsIgnoreCase("inactive")) {
            accountClosed = true;
            return "redirect:/view";
        }
        accountClosed = false;
        m.addAttribute("accToEdit", accToEdit);
        m.addAttribute("withdrawExceed", withdrawExceed);
        return "transact";
    }

    @RequestMapping("/savetransact")
    public String transactionPage(@RequestParam(value = "id") long id,
            @RequestParam(value = "transType") String transType, @RequestParam(value = "amt") double amt) {
        Account acc = ar.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account"));
        double old_bal = acc.getBalance();
        if (transType.equalsIgnoreCase("withdraw") && amt > old_bal) {
            withdrawExceed = true;
            return "redirect:/transact/" + id;
        }
        withdrawExceed = false;
        acc.setBalance(old_bal + (transType.equalsIgnoreCase("withdraw") ? -Math.abs(amt) : Math.abs(amt)));
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
        resetState();
        m.addAttribute("transactions", tr.findByAccountId(id));
        return "transactions";
    }

    @RequestMapping("/createteller")
    public String createTeller(Model m, Teller teller, Principal principal,
            @RequestParam(value = "query", required = false) String query) {
        resetState();
        m.addAttribute("teller", teller);
        List<Role> listRoles = (List<Role>) roleRepo.findAll();
        m.addAttribute("tellerRoles", listRoles);

        List<Teller> tellerList = (List<Teller>) tellerRepo.findAll();
        m.addAttribute("tellerList", tellerList);
        if (query != null && !query.trim().isEmpty()) {
            m.addAttribute("tellerList", (List<Teller>) ts.searchTellers(query));
        }
        return "createteller";
    }

    @RequestMapping("/createaccount")
    public String createAccount(Model model, Account account) {
        resetState();
        model.addAttribute("account", account);
        List<Account> accountList = (List<Account>) ar.findAll();
        model.addAttribute("accountList", accountList);
        return "createaccount";
    }

    @GetMapping("/adding")
    public String addTeller(Teller teller) {
        resetState();
        if (teller.getTellerId() == null) {
            teller.setTellerPass(passwordEncoder.encode(teller.getTellerPass()));
        } else {
            Teller teller2 = tellerRepo.findById(teller.getTellerId()).orElseThrow();
            if (teller.getTellerPass().equals(teller2.getTellerPass())) {
                teller.setTellerPass(teller2.getTellerPass());
            } else {
                teller.setTellerPass(passwordEncoder.encode(teller.getTellerPass()));
            }
        }
        tellerRepo.save(teller);
        return "redirect:/createteller";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id, Model m) {
        resetState();
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
            return "redirect:/view";
        } else {
            return "redirect:/view";
        }
    }

    @GetMapping("/addaccount")
    public String addAccount(Account account) {
        resetState();
        account.setBalance(0.00);
        account.setStatus("active");
        ar.save(account);
        return "redirect:/createaccount";
    }

    @GetMapping("deleteteller/{id}")
    public String deleteTeller(@PathVariable("id") Long id, Model m) {
        Teller teller = tellerRepo.findById(id).get();
        m.addAttribute("teller",teller);
        return "deleteteller";
    }

    @GetMapping("/deleteteller")
    public String deleteTellerConfirmation(@RequestParam(value = "tchoice") String tchoice, @RequestParam("tellerid") Long id) {
        if (tchoice.equalsIgnoreCase("yes")) {
            tellerRepo.deleteById(id);
            return "redirect:/createteller";
        } else {
            return "redirect:/createteller";
        }
    }


}
