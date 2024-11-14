package utez.edu.mx.cleancheck.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import utez.edu.mx.cleancheck.model.role.Role;
import utez.edu.mx.cleancheck.service.role.RoleService;
import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor

public class InitialConfig implements CommandLineRunner {

    private final RoleService roleService;
    Logger logger = Logger.getLogger(InitialConfig.class.getName());

    @Value("${receptionist.name}")
    private String receptionistName;
    @Value("${maid.name}")
    private String maidName;
    @Value("${manager.name}")
    private String managerName;


    @Override
    public void run(String... args) {
        try {
            var receptionistRole = Role.builder()
                    .name(receptionistName)
                    .build();
            ApiResponse<Role> receptionistResponse = roleService.createReceptionist(receptionistRole);
            if (receptionistResponse.isError()) {
                logger.warning(receptionistResponse.getMessage());
                logger.warning("Role Receptionist ID: " + receptionistResponse.getData().getId());
            } else {
                logger.info(receptionistResponse.getMessage());
            }

            var maidRole = Role.builder()
                    .name(maidName)
                    .build();
            ApiResponse<Role> maidResponse = roleService.createMaid(maidRole);
            if (maidResponse.isError()) {
                logger.warning(maidResponse.getMessage());
                logger.warning("Role Maid ID: " + maidResponse.getData().getId());
            } else {
                logger.info(maidResponse.getMessage());
            }

            var managerRole = Role.builder()
                    .name(managerName)
                    .build();
            ApiResponse<Role> managerResponse = roleService.createManager(managerRole);
            if (managerResponse.isError()) {
                logger.warning(managerResponse.getMessage());
                logger.warning("Role Manager ID: " + managerResponse.getData().getId());
            } else {
                logger.info(managerResponse.getMessage());
            }
        } catch (Exception e) {
            logger.warning("Error al crear roles");
        }
    }
}
