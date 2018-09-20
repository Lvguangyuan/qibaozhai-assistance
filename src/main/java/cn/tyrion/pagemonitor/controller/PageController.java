package cn.tyrion.pagemonitor.controller;

import cn.tyrion.pagemonitor.model.User;
import cn.tyrion.pagemonitor.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greeting(
        @RequestParam(name = "name", required = false, defaultValue = "World") String name,
        Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    @GetMapping("/index")
    public String index(
        @RequestParam(name = "name", required = false, defaultValue = "World") String name,
        Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/add")
    // @ResponseBody means the returned String is the response, not a view name
    public @ResponseBody String user(
        @RequestParam(name = "userName", defaultValue = "Apple") String name,
        @RequestParam(name = "email", defaultValue = "apple@163.com") String email) {

        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        userRepository.save(user);

        return "Save successfully, " + name + " " + email;
    }

    @GetMapping("/findAll")
    public @ResponseBody Iterable<User> findAll() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

}
