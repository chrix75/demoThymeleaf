package com.example.controllers;

import com.example.dto.CompanyDTO;
import com.example.dto.DataTablesDTO;
import com.example.dto.NamedDTO;
import com.example.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Christian Sperandio on 30/07/2016.
 */
@Controller
public class GreetingController {
    private static Logger logger = LoggerFactory.getLogger(GreetingController.class);

    private List<CompanyDTO> companies;
    private List<UserDTO> users;


    @Autowired
    private HttpSession session; // Session test

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/companies")
    public String companies(Model model) {
        model.addAttribute("name", "Christian Sperandio");
        model.addAttribute("company", "IMS HEALTH CD");
        return "companylist";
    }

    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("name", "Christian Sperandio");
        model.addAttribute("company", "IMS HEALTH CD");
        return "userlist";
    }

    @RequestMapping("/supervisor")
    public String supervisor(Model model) {
        model.addAttribute("name", "Christian Sperandio");
        model.addAttribute("company", "IMS HEALTH CD");
        return "supervisor";
    }

    @RequestMapping(value = "/companylist", produces = "application/json")
    @ResponseBody
    public DataTablesDTO<NamedDTO> companyList(@RequestParam("draw") int draw,
                                                 @RequestParam("start") int start,
                                                 @RequestParam("length") int len,
                                                 @RequestParam("search[value]") String search) {


        DataTablesDTO<NamedDTO> dataTablesDTO = getDataTablesDTO(companies, draw, start, len, search);

        // ./ Session test
        if (session.getAttribute("counter") == null) {
            session.setAttribute("counter", 0);
        } else {
            int counter = (int) session.getAttribute("counter");
            session.setAttribute("counter", ++counter);
        }

        logger.info("Counter: " + session.getAttribute("counter"));
        // Session test ./

        return dataTablesDTO;
    }

    @RequestMapping(value = "/userlist", produces = "application/json")
    @ResponseBody
    public DataTablesDTO<NamedDTO> userList(@RequestParam("draw") int draw,
                                                 @RequestParam("start") int start,
                                                 @RequestParam("length") int len,
                                                 @RequestParam("search[value]") String search) {


        DataTablesDTO<NamedDTO> dataTablesDTO = getDataTablesDTO(users, draw, start, len, search);

        // ./ Session test
        if (session.getAttribute("counter") == null) {
            session.setAttribute("counter", 0);
        } else {
            int counter = (int) session.getAttribute("counter");
            session.setAttribute("counter", ++counter);
        }

        logger.info("Counter: " + session.getAttribute("counter"));
        // Session test ./

        return dataTablesDTO;
    }

    private DataTablesDTO<NamedDTO> getDataTablesDTO(List<? extends  NamedDTO> db, int draw, int start, int len, String search) {
        List<NamedDTO> selection;
        int filteredCount;

        if (search.isEmpty()) {
            selection = db.stream()
                                      .skip(start)
                                      .limit(len)
                                      .collect(Collectors.toList());

            filteredCount = db.size();
        } else {
            List<NamedDTO> completeSelection = db.stream()
                                                 .filter(cie -> cie.getName().contains(search.toUpperCase()))
                                                 .collect(Collectors.toList());
            selection = completeSelection.stream()
                                 .filter(cie -> cie.getName().contains(search.toUpperCase()))
                                 .skip(start)
                                 .limit(len)
                                 .collect(Collectors.toList());

            filteredCount = completeSelection.size();
        }


        return (DataTablesDTO<NamedDTO>) new DataTablesDTO<NamedDTO>(draw, db.size(), filteredCount, selection, "");
    }

    @PostConstruct
    public void loadData() throws IOException {
        logger.info("Loading the fake companies");

        companies = Files.readAllLines(new File("/Users/batman/IdeaProjects/demoThymeLeaf/src/main/resources/companies.csv").toPath())
                         .stream().map(s -> convertRecordToCompanyDTO(s)).collect(Collectors.toList());

        logger.info("Loading the fake users");

        users = Files.readAllLines(new File("/Users/batman/IdeaProjects/demoThymeLeaf/src/main/resources/users.csv").toPath())
                         .stream().map(s -> convertRecordToUserDTO(s)).collect(Collectors.toList());
    }

    private UserDTO convertRecordToUserDTO(String record) {
        String[] fields = record.split(";");
        String[] apps = fields[2].split(" ");
        return new UserDTO(fields[0], fields[1], Arrays.asList(apps));
    }

    private CompanyDTO convertRecordToCompanyDTO(String record) {
        String[] fields = record.split(";");

        String[] apps = fields[5].split(" ");

        return new CompanyDTO(fields[0], fields[1], fields[3],Arrays.asList(apps), Integer.parseInt(fields[6]));
    }


}