## 스프링 데이터 Common: 커스텀 리포지토리
- 쿼리 메소드(쿼리 생성과 쿼리 찾아쓰기)로 해결이 되지 않는 경우 직접 코딩으로 구현 가능.
    * 스프링 데이터 리포지토리 인터페이스에 기능 추가.
    * 스프링 데이터 리포지토리 기본 기능 덮어쓰기 가능.
    * 구현 방법
        * 커스텀 리포지토리 인터페이스 정의
        * 인터페이스 구현 클래스 만들기 (기본 접미어는 Impl)
        * 엔티티 리포지토리에 커스텀 리포지토리 인터페이스 추가
- 기능 추가하기
- 기본 기능 덮어쓰기
- 접미어 설정하기

## 스프링 데이터 Common: 기본 리포지토리 커스터마이징
- 모든 리포지토리에 공통적으로 추가하고 싶은 기능이 있거나 덮어쓰고 싶은 기본 기능이 있다면 
- JpaRepository를 상속 받는 인터페이스 정의
    * @NoRepositoryBean
- 기본 구현체를 상속 받는 커스텀 구현체 만들기
- @EnableJpaRepositories에 설정
    * repositoryBaseClass

```java
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    boolean contains(T entity);

}
```

```java
public class SimpleMyRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {

    private EntityManager entityManager;

    public SimpleMyRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }
}
```

```java
@EnableJpaRepositories(repositoryBaseClass = SimpleMyRepository.class)
```

```java
public interface PostRepository extends MyRepository<Post, Long> {
}
```

## 스프링 데이터 Common: 도메인 이벤트
- 도메인 관련 이벤트를 발생시키기
- 스프링 프레임워크의 이벤트 관련 기능
    * https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#context-functionality-events
    * ApplicationContext extends ApplicationEventPublisher
    * 이벤트: extends ApplicationEvent
    * 리스너
        * implements ApplicationListener<E extends ApplicationEvent>
        * @EventListener
- 스프링 데이터의 도메인 이벤트 Publisher
    * @DomainEvents
    * @AfterDomainEventPublication
    * extends AbstractAggregateRoot<E>
    * 현재는 save() 할 때만 발생 합니다.