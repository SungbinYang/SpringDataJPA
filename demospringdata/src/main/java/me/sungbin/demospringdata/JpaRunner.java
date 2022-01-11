package me.sungbin.demospringdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Account account = new Account();
//        account.setUsername("sungbin");
//        account.setPassword("jpa");
//
//        Study study = new Study();
//        study.setName("Spring Data JPA");
////        study.setOwner(account);
//
//        account.addStudies(study);
//
//        Session session = entityManager.unwrap(Session.class);
//
//        session.save(account);
//        session.save(study);
//
//        Account sungbin = session.load(Account.class, account.getId()); // 1차 캐시
//        // Dirty Checking (객체의 변경사항을 계속해서 감지) and Write Behind (객체의 상태변화를 DB에 최대한 필요한 시점에 늦게 반영)
//        sungbin.setUsername("robert");
//        sungbin.setUsername("sungbin2");
//        sungbin.setUsername("sungbin");
//        System.out.println("=======================================");
//        System.out.println(sungbin.getUsername());

//        Post post = new Post();
//        post.setTitle("Spring Data JPA 빨리하자");
//
//        Comment comment = new Comment();
//        comment.setComment("QueryDsl이 뭐죠?");
//        post.addComment(comment);
//
//        Comment comment1 = new Comment();
//        comment1.setComment("곧 보여드릴께여");
//        post.addComment(comment1);

        Session session = entityManager.unwrap(Session.class);
        Post post = session.get(Post.class, 1L);

        session.delete(post);
    }
}
