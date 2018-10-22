package mile5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MilestoneController {

    @Autowired
    private UserService UserService;

    @GetMapping("/home")
    public String home() {
		return "home";   	
    }
    @GetMapping("/page1")
    public String page() {
		return "page1";   	
    }
    @GetMapping("/page2")
    public ModelAndView showForm() {
    	ModelAndView model = new ModelAndView("page2");
    	return model;
    }
    @PostMapping(value = "/postData")
    public ModelAndView submitAdmissionForm(@RequestParam("test") String t) {
    	ModelAndView model = new ModelAndView("postData");
    	model.addObject("msg", "POST DATA: " + t);
    	return model;
    }
    
    @PostMapping(value = "/postLog")
    public ModelAndView submitLogin(@RequestParam("user") String user, @RequestParam("pass") String pass) {
    	ModelAndView model = new ModelAndView("postData");
    	model.addObject("msg", "USER: " + user + " PASS: " + pass);

    	String u = user;
    	String p = pass;
        UserService.add(u, p);
    	return model;
    }
    
    @GetMapping("/login")
    public ModelAndView showLogin() {
    	ModelAndView model = new ModelAndView("login");
    	return model;
    }
    
    //using this page as reference https://www.oodlestechnologies.com/blogs/How-To-Download-A-File-Directly-From-URL-In-Spring-Boot
    @GetMapping(value = "/download")
    public InputStreamResource FileSystemResource(HttpServletResponse test) throws FileNotFoundException{
    	test.setContentType("application/txt");
    	test.setHeader("Content-Disposition", "attachment; filename=" + "test.txt");
    	InputStreamResource resource = new InputStreamResource(new FileInputStream ("/home/greg/test.txt"));
    	return resource;
    }
}