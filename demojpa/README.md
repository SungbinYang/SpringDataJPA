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

## 스프링 데이터 Common: QueryDSL
- findByFirstNameIngoreCaseAndLastNameStartsWithIgnoreCase(String firstName, String lastName) 
- 이런 복잡하고 긴 쿼리메소드를 어떻게하면 간단하게 할까?
- 여러 쿼리 메소드는 대부분 두 가지 중 하나
    * Optional<T> findOne(Predicate): 이런 저런 조건으로 무언가 하나를 찾는다.
    * List<T>|Page<T>|.. findAll(Predicate): 이런 저런 조건으로 무언가 여러개를 찾는다.
    * [QuerydslPredicateExecutor 인터페이스](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/querydsl/QuerydslPredicateExecutor.html)
- QueryDSL
    * http://www.querydsl.com/
    * 타입 세이프한 쿼리 만들 수 있게 도와주는 라이브러리
    * JPA, SQL, MongoDB, JDO, Lucene, Collection 지원
    * [QueryDSL JPA 연동 가이드](http://querydsl.com/static/querydsl/4.1.3/reference/html_single/#jpa_integration)
- 스프링 데이터 JPA + QueryDSL
    * 인터페이스: QuerydslPredicateExecutor<T>
    * 구현체: QuerydslPredicateExecutor<T> (@deprecated되었고 구현체를 제공하는 방식이 프레그먼트를 사용하도록 바뀌어서 SimpleRepository만 사용해도 가능)
- 연동 방법
    * 기본 리포지토리 커스터마이징 안 했을 때. 
    * 기본 리포지토리 커스타마이징 했을 때.
- 의존성 추가

```xml
<dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
        </dependency>
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-jpa</artifactId>
        </dependency>

<plugin>
<groupId>com.mysema.maven</groupId>
<artifactId>apt-maven-plugin</artifactId>
<version>1.1.3</version>
<executions>
    <execution>
        <goals>
            <goal>process</goal>
        </goals>
        <configuration>
            <outputDirectory>target/generated-sources/java</outputDirectory>
            <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
        </configuration>
    </execution>
</executions>
</plugin>
```

```java
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {
}
```

## 스프링 데이터 Common: Web 1부: 웹 지원 기능 소개
- 스프링 데이터 웹 지원 기능 설정
  * 스프링 부트를 사용하는 경우에.. 설정할 것이 없음. (자동 설정)
  * 스프링 부트 사용하지 않는 경우?

  ```java
  @Configuration
  @EnableWebMvc
  @EnableSpringDataWebSupport
  class WebConfiguration {}
  ```

- 제공하는 기능
  * 도메인 클래스 컨버터
  * @RequestHandler 메소드에서 Pageable과 Sort 매개변수 사용 
  * Page 관련 HATEOAS 기능 제공
    * PagedResourcesAssembler
    * PagedResoure
  * Payload 프로젝션
    * 요청으로 들어오는 데이터 중 일부만 바인딩 받아오기
    * @ProjectedPayload, @XBRead, @JsonPath
  * 요청 쿼리 매개변수를 QueryDSLdml Predicate로 받아오기
    * ?firstname=Mr&lastname=White => Predicate