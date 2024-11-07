package com.today_solution_server.demo.securityUser;

import com.today_solution_server.demo.entity.Users;
import com.today_solution_server.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public CustomUserDto loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = usersRepository.findOneByUserId(Integer.parseInt(userId))
                .orElseThrow(()-> new NullPointerException("No User"));

        CustomUserDto customUserDto = new CustomUserDto();
        customUserDto.setUserId(String.valueOf(user.getUserId()));
        customUserDto.setNickName(user.getNickName());

        return customUserDto;
    }
}
