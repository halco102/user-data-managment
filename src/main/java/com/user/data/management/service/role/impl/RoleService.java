package com.user.data.management.service.role.impl;

import com.user.data.management.dto.request.role.AddRoleRequest;
import com.user.data.management.exception.DuplicateException;
import com.user.data.management.exception.NotFoundException;
import com.user.data.management.mapper.RoleMapper;
import com.user.data.management.model.Role;
import com.user.data.management.repository.RoleRepository;
import com.user.data.management.service.role.IRole;
import com.user.data.management.service.user.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService implements IRole {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private IUser iUser;

    @Override
    public Role addRole(AddRoleRequest request) {
        var checkIfRoleExists = roleRepository.findRoleByName(request.getName());

        if (checkIfRoleExists.isPresent())
            throw new DuplicateException("The role already exists in db");

        return roleRepository.save(roleMapper.requestToEntity(request));
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("The role does not exist"));
    }

    @Override
    public Role getRoleByName(String name) {
        var findRole = roleRepository.findRoleByName(name).orElseThrow(() -> new NotFoundException("Role not found"));
        return findRole;
    }


}
