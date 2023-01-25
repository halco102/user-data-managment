package com.user.data.management.bootstrap;

import com.user.data.management.dto.request.user.UserSignupRequest;
import com.user.data.management.model.Role;
import com.user.data.management.repository.RoleRepository;
import com.user.data.management.service.user.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitValuesOnStart implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {

/*        List<Role> roles = new ArrayList<>(
                Arrays.asList(new Role(null, "ROLE_ADMIN"), new Role(null, "ROLE_USER"))
        );

        var fetchAllRoles = roleRepository.findAll();

        if (fetchAllRoles == null || fetchAllRoles.isEmpty())
            roles.forEach(item -> roleRepository.save(item));


        //craete user
        userService.createUser(new UserSignupRequest("halco1002", "987456321aa", "ado.halilovic@outlook.com"));*/

    }


}
