package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  UserService userService;

  @GetMapping("/register")
  public String showRegistrationPage(Model model){
    model.addAttribute("user", new User());
    return "registration";
  }

  @PostMapping("/register")
  public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){

    if(result.hasErrors()){
      return "registration";
    }

    else {
      user.setEnabled(true);
      userService.saveUser(user);
      model.addAttribute("message", "User Account Created");
    }
    return "redirect:/";
  }

  @RequestMapping("/login")
  public String login(){
    return "login";
  }

  @RequestMapping("/")
  public String listUser(Model model){
    model.addAttribute("users", userRepository.findAll());
    if(userService.getUser() != null) {
      model.addAttribute("user_id", userService.getUser().getId());
    }
    return "userlist";
  }


  @GetMapping("/adduser")
  public String UserForm(Model model) {
    model.addAttribute("users",userRepository.findAll());
    model.addAttribute("user", new User());
    return "userform";
  }

  @PostMapping("/processuser")
  public String processForm(@Valid User user, BindingResult result, Model model){
    if(result.hasErrors()){
      model.addAttribute("users", userRepository.findAll());
      return "userform";
    }

//    course.setUser(userService.getUser());
    userRepository.save(user);
    return "redirect:/";
  }

  @RequestMapping("/detail/{id}")
  public String showUser(@PathVariable("id") long id, Model model){
    model.addAttribute("user", userRepository.findById(id).get());
    return "courselist";
  }

  @RequestMapping("/update/{id}")
  public String updateUser(@PathVariable("id") long id, Model model){
    model.addAttribute("courses", courseRepository.findAll());
            model.addAttribute("user",userRepository.findById(id).get());
    return "userform";
  }

  @RequestMapping("/delete/{id}")
  public String delUser(@PathVariable("id") long id){
    userRepository.deleteById(id);
    return "redirect:/";
  }
//Refering to course
  @RequestMapping("/addcourse/{id}")
  public String addCourse(@PathVariable("id") long id, Model model) {
    model.addAttribute("course", courseRepository.findById(id).get());

    return "redirect:/addcourses";
  }
  @GetMapping("/addcourses")
  public String employeeForm(Model model) {
    model.addAttribute("course", courseRepository.findAll());
    model.addAttribute("course", new Course());

    return "courseform";
  }
  @PostMapping("/processcourse")
  public String processForm(@Valid Course course, BindingResult result) {
    if (result.hasErrors()) {
      return "courseform";
    }

    courseRepository.save(course);
    return "redirect:/";
  }
  @RequestMapping("/detail/course/{id}")
  public String showcourse(@PathVariable("id") long id, Model model){
//    model.addAttribute("user", userRepository.findAll());
    model.addAttribute("course", courseRepository.findById(id).get());

    return "courselist";
  }
  @RequestMapping("/update/course{id}")
  public String updatecourse(@PathVariable("id") long id, Model model){
    model.addAttribute("course", courseRepository.findById(id).get());
    return "courseform";

  }
  @RequestMapping("/delete/course{id}")
  public  String delcourse(@PathVariable("id") long id){
    courseRepository.deleteById(id);
    return "redirect:/";
  }
}
