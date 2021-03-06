package controller;

import exception.*;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import path.PathApp;
import pdf.StatementWorker;
import service.FacultyService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.*;

@Controller
public class UserActionsController {
    private static final Logger LOG = LogManager.getLogger(UserActionsController.class);

    private UserService userService;
    private FacultyService facultyService;
    private StatementWorker statementWorker;

    @Autowired
    public UserActionsController(UserService userService, FacultyService facultyService, StatementWorker statementWorker) {
        this.userService = userService;
        this.facultyService = facultyService;
        this.statementWorker = statementWorker;
    }

    @RequestMapping(value = "/app/faculties")
    public ModelAndView showListOfFaculties(HttpServletRequest request) throws CantGetFacultiesException {
        ModelAndView modelAndView = new ModelAndView();

        String sort;

        //Logic of working with sorting
        if (request.getSession().getAttribute("sort") == null) {
            //default sorting - by alphabet
            sort = "sortAZ";
        } else if (request.getParameterMap().containsKey("sort")) {
            sort = request.getParameter("sort");
        } else {
            sort = (String) request.getSession().getAttribute("sort");
        }


        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        //realisation of pagination
        int pageFaculty;
        int facultyCountOnPage = 5;
        int startValue;
        if (!request.getParameterMap().containsKey("page")) {
            pageFaculty = 1;
            startValue = 0;
        } else {
            pageFaculty = Integer.parseInt(request.getParameter("page"));
            startValue = (pageFaculty - 1) * facultyCountOnPage;
        }

        int rows = facultyService.getCountOfFaculties();

        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {

            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<Faculty> faculties = facultyService.getFacultiesWithSorting(sort, startValue, facultyCountOnPage, locale);
        LOG.info("Getting faculties successful");
        request.setAttribute("facultiesList", faculties);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("pageFaculty", pageFaculty);
        //update sort attribute in session
        request.getSession().removeAttribute("sort");
        request.getSession().setAttribute("sort", sort);
        modelAndView.setViewName("app/faculties");
        return modelAndView;
    }

    @RequestMapping(value = "/app/add_mark")
    public ModelAndView addMark(@RequestParam(name = "mark") String mark,
                                @RequestParam(name = "marksSelect") String marksSelect,
                                HttpServletRequest request) throws EmptyParametersException {
        String userEmail = (String) request.getSession().getAttribute("email");
        ModelAndView modelAndView = new ModelAndView();
        userService.addUserMark(mark, marksSelect, userEmail);
        modelAndView.setViewName("redirect:/app/marks");
        return modelAndView;
    }

    @RequestMapping(value = "/app/marks")
    public ModelAndView showListOfMarks(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        LOG.info(request.getSession().getAttribute("email"));
        //getting user exams
        List<UserResult> results = userService.findUserResults((String) request.getSession().getAttribute("email"), locale);
        List<SubjectExam> exams = facultyService.getUserAvailableSubjects(locale, results);
        request.setAttribute("results", results);
        request.setAttribute("exams", exams);
        modelAndView.setViewName("app/marks");
        return modelAndView;
    }

    @RequestMapping(value = "/app/mark_del")
    public ModelAndView deleteMark(@RequestParam(name = "subjectid") String subjectid, HttpServletRequest request) throws WrongIdOfSubjectExamException {
        ModelAndView modelAndView = new ModelAndView();
        String userEmail = (String) request.getSession().getAttribute("email");
        userService.deleteUserMark(subjectid, userEmail);
        request.getSession().removeAttribute("admissions map");
        modelAndView.setViewName("redirect:/app/marks");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admissions")
    public ModelAndView showListOfAdmissions(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        User user = userService.findUser((String) request.getSession().getAttribute("email"));
        //getting all user admissions
        Map<String, Date> mapOfAdmissions = userService.findUserAdmissions(user, locale);

        HttpSession session = request.getSession();
        session.setAttribute("admissions map", mapOfAdmissions);
        modelAndView.setViewName("app/admissions");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admission_del")
    public ModelAndView deleteAdmission(@RequestParam(name = "faculty_name") String faculty_name, HttpServletRequest request) throws WrongFacultyException {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors

        Map<String, Date> mapOfAdmissions = userService.deleteUserAdmission(faculty_name, (String) request.getSession().getAttribute("email") ,locale);
        request.getSession().removeAttribute("admissions map");

        request.getSession().setAttribute("admissions map", mapOfAdmissions);
        modelAndView.setViewName("redirect:/app/admissions");
        return modelAndView;
    }

    @RequestMapping(value = "/app/faculty")
    public ModelAndView facultyAdmission(@RequestParam(name = "id") String id, HttpServletRequest request) throws WrongIdOfFacultyException, NoSuchFacultyException {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors


        Faculty faculty = facultyService.findFacultyByIdAndLocale(id, locale);

        Set<Integer> facultyDemends = facultyService.getFacultyDemends(String.valueOf(faculty.getId()));

        Set<Integer> userSubjects = userService.getUserSubjects((String) request.getSession().getAttribute("email"));
        faculty = facultyService.findFacultyById(String.valueOf(faculty.getId()), locale);

        request.setAttribute("faculty", faculty);
        if (userSubjects.containsAll(facultyDemends)) {
            request.setAttribute("able to apply", true);
            modelAndView.setViewName("app/faculty");
            return modelAndView;
        } else {
            request.setAttribute("able to apply", false);
            modelAndView.setViewName("app/faculty");
            return modelAndView;
        }

    }

    @RequestMapping(value = "/app/participate")
    public ModelAndView sendAdmission(@RequestParam(name = "faculty_id") String faculty_id, HttpServletRequest request) throws EmptyFacultyIdException, FacultyHaveNoDemendsException, AlreadySendedAmdissionException {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        Locale current = new Locale(locale);
        ResourceBundle rb = ResourceBundle.getBundle("resource", current);

        Map<String, Date> mapOfAdmissions = userService.participateUser(faculty_id, (String) request.getSession().getAttribute("email"),locale);
        //update map of admissions in session
        request.getSession().removeAttribute("admissions map");
        request.getSession().setAttribute("admissions map", mapOfAdmissions);
        modelAndView.setViewName("redirect:/app/admissions");
        return modelAndView;

    }

    @RequestMapping(value = "/app/personalInfo")
    public ModelAndView showPersonalInfo(HttpServletRequest request) throws NoSuchUserException {
        ModelAndView modelAndView = new ModelAndView();


        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        User user = userService.findUser((String) request.getSession().getAttribute("email"));

        //getting details for both locales: uk and en
        //we show all user details
        UserDetails userDetails1 = userService.findUserDetails(user, "uk");
        UserDetails userDetails2 = userService.findUserDetails(user, "en");

        //check if we have such user
        if (userDetails1 != null && userDetails2 != null) {
            request.setAttribute("user", user);
            request.setAttribute("details1", userDetails1);
            request.setAttribute("details2", userDetails2);
            modelAndView.setViewName("app/info");
            return modelAndView;
        } else {
            throw new NoSuchUserException();
        }
    }

    @RequestMapping(value = "/app/changeinfo")
    public ModelAndView changePersonalInfo(@RequestParam(name = "email") String email,
                                           @RequestParam(name = "pass1") String pass1,
                                           @RequestParam(name = "idn") String idn,
                                           @RequestParam(name = "name") String english_name,
                                           @RequestParam(name = "surname") String english_surname,
                                           @RequestParam(name = "patronymic") String english_patronymic,
                                           @RequestParam(name = "city") String english_city,
                                           @RequestParam(name = "region") String english_region,
                                           @RequestParam(name = "school_name") String english_school_name,
                                           @RequestParam(name = "average_certificate_point") String average_certificate_point,
                                           @RequestParam(name = "name_ua") String ukrainian_name,
                                           @RequestParam(name = "surname_ua") String ukrainian_surname,
                                           @RequestParam(name = "patronymic_ua") String ukrainian_patronymic,
                                           @RequestParam(name = "city_ua") String ukrainian_city,
                                           @RequestParam(name = "region_ua") String ukrainian_region,
                                           @RequestParam(name = "school_name_ua") String ukrainian_school_name,
                                           HttpServletRequest request) throws EmptyParametersException, SuchEmailExistException, SuchIdnExistException {
        ModelAndView modelAndView = new ModelAndView();
        String sessionUserEmail = ((String) request.getSession().getAttribute("email"));

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");

        String emailResult = userService.changeUserInfo(email, pass1, idn, english_name, english_surname, english_patronymic, english_city, english_region, english_school_name, average_certificate_point, ukrainian_name,ukrainian_surname,ukrainian_patronymic,ukrainian_city,ukrainian_region,ukrainian_school_name, locale, sessionUserEmail);
        request.getSession().removeAttribute("email");
        request.getSession().setAttribute("email", emailResult);
        modelAndView.setViewName("redirect:/app/personalInfo");
        return modelAndView;
    }

    @RequestMapping(value = "/app/statements")
    public ModelAndView showListOfStatements(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        request.setAttribute("files", statementWorker.getListOfStatements(PathApp.STATEMENTS_FOLDER));
        modelAndView.setViewName("app/all_statements");
        return modelAndView;
    }

    @RequestMapping(value = "/app/download_statement")
    public void downloadStatement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));


        String filename = request.getParameter("name");

        //find file with this name and return it to user
        OutputStream out = response.getOutputStream();
        response.setContentType("application/pdf");

        statementWorker.downloadStatement(PathApp.STATEMENTS_FOLDER + "/" + filename, out);
    }
}
