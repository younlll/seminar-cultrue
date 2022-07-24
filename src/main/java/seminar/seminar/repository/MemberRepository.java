package seminar.seminar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seminar.seminar.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
