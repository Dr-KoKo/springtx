package hello.springtx.propagation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LogRepository {
    private final EntityManager em;

    @Transactional
    public void save(Log logMessage) {
        em.persist(logMessage);
        if (logMessage.getMessage().contains("로그예외")) {
            throw new RuntimeException("예외 발생");
        }
    }

    public Optional<Log> find(String message) {
        return em.createQuery("select l from Log l where l.message=:message")
                .setParameter("message", message)
                .getResultList().stream().findAny();
    }
}