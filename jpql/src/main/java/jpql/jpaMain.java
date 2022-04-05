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
            Team team = new Team();
            team.setName("team A");
            em.persist(team);

            Member member = new Member();
            member.setUserName("member1");
            member.setAge(10);
            member.cheangeTeam(team);
            member.setType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m, 'HELLO', true from Member m " +
                    "where m.type = :userType";
            List<Object[]> resultList = em.createQuery(query)
                            .setParameter("userType", jpql.MemberType.ADMIN)
                            .getResultList();
            resultList.forEach((x) -> System.out.println("x = " + x));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
