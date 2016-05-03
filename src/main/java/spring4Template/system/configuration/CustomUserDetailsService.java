package spring4Template.system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring4Template.users.domain.entities.User;
import spring4Template.users.domain.entities.UserAuthority;
import spring4Template.users.domain.repositories.UserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if(user == null) {
            return null;
        }
        List<UserAuthority> authorities = user.getGroup().getAuthorities();
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(this::mapToSimpleGrantedAuthority)
                .collect(toList());
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }

    private SimpleGrantedAuthority mapToSimpleGrantedAuthority(UserAuthority a) {
        return new SimpleGrantedAuthority(a.getName());
    }

}