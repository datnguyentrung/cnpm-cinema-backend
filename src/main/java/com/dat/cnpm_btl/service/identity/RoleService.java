package com.dat.cnpm_btl.service.identity;

import com.dat.cnpm_btl.domain.identity.Role;
import com.dat.cnpm_btl.repository.identity.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleById(String roleCode){
        return roleRepository.findById(roleCode)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleCode + " not found"));
    }

    public Role createRole(Role role){
        return roleRepository.save(role);
    }
}
