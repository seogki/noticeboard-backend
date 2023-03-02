package skh.noticeboard.member;

import skh.noticeboard.dto.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
