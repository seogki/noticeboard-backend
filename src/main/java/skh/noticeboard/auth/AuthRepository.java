package skh.noticeboard.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import skh.noticeboard.dto.Member;

public interface AuthRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberEmail(String memberEmail);
    Member findByMemberPassword(String memberPassword);
    // Member findByMemberEmail(String memberEmail);
    boolean existsByMemberEmail(String memberEmail);

    boolean existsByMemberNickname(String memberNickname);
}
