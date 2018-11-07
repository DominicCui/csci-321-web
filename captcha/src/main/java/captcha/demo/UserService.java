package captcha.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Component
public class UserService{
    @Autowired
    private UserRepository UserRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String hashedPassword;

    @Transactional
    public void add(String user, String pass){
        User n = new User();
        n.setUsername(user);
        String hashed = hash(pass);
        n.setPassword(hashed);
        UserRepository.save(n);

//        if(match(pass)){
//            System.out.println("the pass word is verified");
//        }
    }

    public String hash(String pass){
        hashedPassword = passwordEncoder.encode(pass);
        return hashedPassword;
    }

    public boolean match(String pass){
        boolean matches = passwordEncoder.matches(pass, hashedPassword);
        return matches;
    }
    public UserDetails findUserByUsername(String name) {
        //根据用户名从数据库查询对应记录
        UserDetails user = queryByUserName(name);
        return user;
    }

    private static UserDetails queryByUserName(String username) {
        return queryByUserName(username);

    }

}
