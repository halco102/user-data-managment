package com.user.data.management.service.role;

import com.user.data.management.dto.request.role.AddRoleRequest;
import com.user.data.management.model.Role;

public interface IRole {

    Role addRole(AddRoleRequest request);

    Role getRoleById(Long id);

    Role getRoleByName(String name);

}
