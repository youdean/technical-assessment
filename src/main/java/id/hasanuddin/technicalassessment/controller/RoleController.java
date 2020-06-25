package id.hasanuddin.technicalassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import id.hasanuddin.technicalassessment.external.RoleRepository;
import id.hasanuddin.technicalassessment.model.Role;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository roleService;

    @GetMapping("/roles")
    public Iterable<Role> showAllRoles() {
        return roleService.findAll();
    }

}
