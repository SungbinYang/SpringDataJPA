package me.sungbin.demospringdata;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
//        List<Post> posts = query.getResultList();
//
//        posts.forEach(System.out::println);

//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
//        Root<Post> root = query.from(Post.class);
//        query.select(root);
//
//        List<Post> resultList = entityManager.createQuery(query).getResultList();
//        resultList.forEach(System.out::println);

        List<Post> resultList = entityManager.createNativeQuery("SELECT * from Post", Post.class).getResultList();

        resultList.forEach(System.out::println);

    }
}
