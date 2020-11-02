package org.balramjot.quiktraker.dao;

import org.balramjot.quiktraker.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for user information service class
 * @author BjSaini
 *
 */
@Repository
public interface UserInfoRepoIF extends JpaRepository<UserInfo, Long> {
	
}
