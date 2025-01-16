package com.example.ivr_stand.controller;

import com.example.ivr_stand.model.Category;
import com.example.ivr_stand.model.Service;
import com.example.ivr_stand.repository.CategoryRepo;
import com.example.ivr_stand.repository.ServiceRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ServiceController {
    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public static String imgsize;
    private Integer category_id;
    private Integer service_id;

    @GetMapping("/getItems")
    public List<Service> getItems() {
        return serviceRepo.findAll();
    }

    @GetMapping("/service")
    public String showServicePage(Model model) {
        Optional<Service> service = serviceRepo.findById(service_id);
        model.addAttribute("service", service.get());
        model.addAttribute("imgsize", imgsize);
        return "service1ApplyForPension";
    }

    @GetMapping("/services")
    public String showServicesPage(Model model) {
        List<Service> services = serviceRepo.findByCategoryId(category_id);
        Optional<Category> category = categoryRepo.findById(category_id);
        model.addAttribute("services", services);
        model.addAttribute("category", category.get());
        model.addAttribute("imgsize", imgsize);
        return "services_template";
    }

    @GetMapping("/category")
    public String showCategoryPage(Model model){
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("imgsize", imgsize);
        return "category";
    }

    @PostMapping("/gesture")
    public String test_img(@RequestParam("buttonId") String buttonId){
        if (buttonId.equals("Text")){
            imgsize = "height: 0; width: 0;";
        }
        else{
            imgsize="";
        }
        return "redirect:/category";
    }

    @PostMapping("/category_id")
    public String set_category_id(@RequestParam("buttonId") String buttonId){
        String numericPart = buttonId.replaceAll("[^\\d]", "");
        category_id = Integer.parseInt(numericPart);
        return "redirect:/services";
    }

    @PostMapping("/service_id")
    public String set_service_id(@RequestParam("buttonId") String buttonId){
        service_id = Integer.parseInt(buttonId);
        return "redirect:/service";
    }

    @GetMapping("/")
    public String showMainPage(){
        return "index";
    }

    @GetMapping("/getItem/{id}")
    public ResponseEntity<Service> getItemById(@PathVariable Integer id) {
        Optional<Service> item = serviceRepo.findById(id);

        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
