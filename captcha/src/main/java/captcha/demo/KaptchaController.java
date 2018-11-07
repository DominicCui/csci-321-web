package captcha.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
@RestController
public class KaptchaController {

    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService UserService;


    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView andView = new ModelAndView("index");
        return andView;
    }

    @RequestMapping("/register")
    public ModelAndView register(){
        ModelAndView andView = new ModelAndView("register");
        return andView;
    }

    @RequestMapping("/Register")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("rightCode", createText);
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @RequestMapping("/Verify")
    public ModelAndView imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest,
                                                         HttpServletResponse httpServletResponse) {
        ModelAndView andView = new ModelAndView();
        String rightCode = (String) httpServletRequest.getSession().getAttribute("rightCode");
        String tryCode = httpServletRequest.getParameter("tryCode");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        String password2 = httpServletRequest.getParameter("password2");

        if (!rightCode.equals(tryCode) || !password.equals(password2) ){
            andView.addObject("info", "the password does not match or wrong captcha code");
            andView.setViewName("register");
        } else {
//            User n = new User();
//            n.setUsername(username);
//            n.setPassword(password);
//            if (!UserService.findUserByUsername(username).equals(null)){
//                andView.addObject("info", "the user existed");
//                andView.setViewName("register");
//            }else{
//                UserService.add(username, password);
            andView.addObject("info", "successful");
                andView.setViewName("login");
//            }
        }

        return andView;
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView andView = new ModelAndView("login");
        return andView;
    }

    @RequestMapping("/verifyLogin")
    public ModelAndView verifyLogin (HttpServletRequest request)
    {
        ModelAndView andView = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //need to compare database data to verify the user and password
        if (username.equals("test") && password.equals("pass")) {
            request.getSession().setAttribute("loginName", "admin");
            andView.setViewName("success");
        } else {
            andView.setViewName("login");
        }
        return andView;
    }

//    @GetMapping(value = "/person/{name}")
//    public User findOne(@PathVariable("name") String name) {
//        return userRepository.findById().equals().findOne(name);
//    }

    @RequestMapping("/success")
    public ModelAndView success(){
        ModelAndView andView = new ModelAndView("success");
        return andView;
    }

    @RequestMapping(value="/success/{id}/{vuln}",method= RequestMethod.GET)
    public String sayHello(@PathVariable("id") Integer id, @PathVariable("vuln") String name){
        return "id:"+id+" vuln:"+name;
    }


}
