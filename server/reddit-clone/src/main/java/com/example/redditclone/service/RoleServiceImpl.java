package com.example.redditclone.service;


import com.example.redditclone.Model.Role;
import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl  implements RoleService{


    public static final String ROLE_USER = "ROLE_USER";

    private final RoleRepository roleRepository;


    @Override
    public Role getUserRole() {
        try {
            return roleRepository.findByName(ROLE_USER).orElseThrow(
                    () -> new RedditCloneException("Role not found " + ROLE_USER)
            );
        }catch (RedditCloneException e){
            userRoleInit();
            return getUserRole();
        }
    }

    private void userRoleInit(){
        Role role = new Role();
        role.setName(ROLE_USER);
        roleRepository.save(role);
    }
}
