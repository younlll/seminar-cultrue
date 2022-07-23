package seminar.seminar.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import seminar.seminar.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByIdentificationCode(String identificationCode);
}
