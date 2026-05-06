package com.dat.cnpm_btl.service.identity;

import com.dat.cnpm_btl.domain.identity.Role;
import com.dat.cnpm_btl.dao.identity.RoleDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDAO roleDAO;

    public Role getRoleById(String roleCode){
        return roleDAO.findById(roleCode)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleCode + " not found"));
    }

    public Role createRole(Role role){
        return roleDAO.save(role);
    }
}
