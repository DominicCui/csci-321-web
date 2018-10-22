package hello.demo;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.stereotype.Service;
        import javax.transaction.Transactional;


@Service
public class UserService {
    @Autowired
    private UserRepository UserRepository;

    @Transactional
    public void insertTwo(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User UserA = new User();
        UserA.setName("First");
        String passwordA = "password";
        String hashedPasswordA = passwordEncoder.encode(passwordA);
        UserA.setPassword(hashedPasswordA);
        UserRepository.save(UserA);

        User UserB = new User();
        UserB.setName("Second");
        String passwordB = "password";

        String hashedPasswordB = passwordEncoder.encode(passwordB);
        UserB.setPassword(hashedPasswordB);

        boolean matches = passwordEncoder.matches(passwordB, hashedPasswordB);
        System.out.println(matches);

        UserRepository.save(UserB);

    }
}
