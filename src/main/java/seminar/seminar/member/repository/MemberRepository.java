package seminar.seminar.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seminar.seminar.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);
}
