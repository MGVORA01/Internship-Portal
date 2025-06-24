package com.jobportal.controller;

import com.jobportal.model.AdminUser;
import com.jobportal.model.Internship;
import com.jobportal.model.JobApplication;
import com.jobportal.model.User;
import com.jobportal.repository.AdminUserRepository;
import com.jobportal.repository.InternshipRepository;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InternshipRepository internshipRepo;

    @Autowired
    private JobApplicationRepository jobRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("internships", internshipRepo.findAll());
        return "home";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        user.setRole("USER");
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return user.getRole().equals("ADMIN") ? "redirect:/admin/dashboard" : "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<JobApplication> list = jobRepo.findByApplicantEmail(user.getEmail());
        model.addAttribute("applications", list);
        return "user_dashboard";
    }

    @GetMapping("/apply")
    public String applyForm() {
        return "apply";
    }

    @PostMapping("/apply")
    public String apply(JobApplication app, HttpSession session) {
        User user = (User) session.getAttribute("user");
        app.setApplicantEmail(user.getEmail());
        app.setStatus("PENDING");
        jobRepo.save(app);
        return "redirect:/dashboard";
    }

    @Autowired
    private AdminUserRepository adminRepo;

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin_login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password,
                             HttpSession session) {
        AdminUser admin = adminRepo.findByEmailAndPassword(email, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/login?error";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model,HttpSession session) {
        AdminUser admin = (AdminUser) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("applications", jobRepo.findAll());
        model.addAttribute("internships", internshipRepo.findAll());
        return "admin_dashboard";
    }

    @GetMapping("/admin/approve/{id}")
    public String approve(@PathVariable int id) {
        JobApplication app = jobRepo.findById(id).orElse(null);
        if (app != null) {
            app.setStatus("APPROVED");
            jobRepo.save(app);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/reject/{id}")
    public String reject(@PathVariable int id) {
        JobApplication app = jobRepo.findById(id).orElse(null);
        if (app != null) {
            app.setStatus("REJECTED");
            jobRepo.save(app);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/apply/{id}")
    public String applyInternship(@PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Internship internship = internshipRepo.findById(id).orElse(null);
        if (internship != null) {
            JobApplication app = new JobApplication();
            app.setTitle(internship.getTitle());
            app.setDescription(internship.getDescription());
            app.setApplicantEmail(user.getEmail());
            app.setStatus("PENDING");
            jobRepo.save(app);
        }

        return "redirect:/dashboard";
    }


    @GetMapping("/admin/internships")
    public String listInternships(Model model) {
        model.addAttribute("internships", internshipRepo.findAll());
        return "admin_internships";
    }

    @PostMapping("/admin/add-internship")
    public String addIntern(@RequestParam String title, @RequestParam String description) {
        Internship i = new Internship();
        i.setTitle(title);
        i.setDescription(description);
        internshipRepo.save(i);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/delete-internship/{id}")
    public String deleteIntern(@PathVariable int id) {
        internshipRepo.deleteById(id);
        return "redirect:/admin/dashboard";
    }

}