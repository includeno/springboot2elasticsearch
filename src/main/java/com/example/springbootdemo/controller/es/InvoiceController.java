package com.example.springbootdemo.controller.es;

import com.example.springbootdemo.dao.es.ESInvoiceRepository;
import com.example.springbootdemo.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private ESInvoiceRepository repo;

    @GetMapping("/listInvoice")
    public String viewlistInvoicePage(Model model) throws IOException {
        model.addAttribute("listInvoiceDocuments",repo.getAllInvoices());
        return "listInvoice";
    }

    @PostMapping("/saveInvoice")
    public String saveInvoice(@ModelAttribute("invoice") Invoice invoice) throws IOException {
        repo.createOrUpdateInvoice(invoice);
        return "redirect:/invoice/listInvoice";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {
        Invoice invoice = repo.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "updateInvoice";
    }

    @GetMapping("/showNewInvoiceForm")
    public String showNewInvoiceForm(Model model) {
        // creating model attribute to bind form data
        Invoice invoice = new Invoice();
        model.addAttribute("invoice", invoice);
        return "addInvoice";
    }

    @GetMapping("/deleteInvoice/{id}")
    public String deleteInvoice(@PathVariable(value = "id") String id) throws IOException {
        this.repo.deleteInvoiceById(id);
        return "redirect:/invoice/listInvoice";
    }
}
