package io.spin.status.service.admin;

import io.spin.status.domain.dynamoDB.admin.Admin;
import io.spin.status.dto.DynamoDBScanPageResult;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.dto.Result;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService {

    Admin getAdmin(String id);
    Result signupAdmin(Admin admin);
    Result updateAdmin(String id, String body);
    Result deleteAdmin(String id);

    Result changePassword(String newPassword);

    DynamoDBScanPageResult adminList(DynamoDBSearchParam dynamoDBSearchParam);

}
