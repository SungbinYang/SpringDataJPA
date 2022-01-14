package me.sungbin.demospringdata;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class JpaRunner implements ApplicationRunner {

    final PostRepository postRepository;

    final Sungbin sungbin;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        postRepository.findAll().forEach(System.out::println);

        System.out.println("-------------------------------");
        System.out.println(sungbin.getName());
    }
}
