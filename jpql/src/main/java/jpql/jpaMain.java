package jpql;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import javax.persistence.*;
import java.util.List;

public class jpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team teamA = new Team();
            teamA.setName("team A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("team B");
            em.persist(teamB);

            Member member = new Member();
            member.setUserName("member1");
            member.setAge(10);
            member.cheangeTeam(teamA);

            Member member1 = new Member();
            member1.setUserName("member2");
            member1.setAge(15);
            member1.cheangeTeam(teamA);

            em.persist(member);
            em.persist(member1);

            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();
            System.out.println("resultCount = " + resultCount);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
