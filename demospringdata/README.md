## JPA 프로그래밍: 프로젝트 세팅
- 데이터베이스 실행
    * PostgreSQL 도커 컨테이너 재사용
    * docker start postgres_boot
- 스프링 부트
    * 스프링 부트 v2.*
    * 스프링 프레임워크 v5.*
- 스프링 부트 스타터 JPA
    * JPA 프로그래밍에 필요한 의존성 추가
        * JPA v2.*
        * Hibernate v5.*
    * 자동 설정: HibernateJpaAutoConfiguration
        * 컨테이너가 관리하는 EntityManager (프록시) 빈 설정
        * PlatformTransactionManager 빈 설정
- JDBC 설정
    * jdbc:postgresql://localhost:5432/springdata
    * sungbin
    * pass
- application.properties
    * spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
    * spring.jpa.hibernate.ddl-auto=create

## JPA 프로그래밍: 엔티티 맵핑
- @Entity
    * “엔티티”는 객체 세상에서 부르는 이름.
    * 보통 클래스와 같은 이름을 사용하기 때문에 값을 변경하지 않음.
    * 엔티티의 이름은 JQL에서 쓰임.
- @Table
    * “릴레이션" 세상에서 부르는 이름.
    * @Entity의 이름이 기본값.
    * 테이블의 이름은 SQL에서 쓰임.
- @Id
    * 엔티티의 주키를 맵핑할 때 사용.
    * 자바의 모든 primitive 타입과 그 랩퍼 타입을 사용할 수 있음
        * Date랑 BigDecimal, BigInteger도 사용 가능.
    * 복합키를 만드는 맵핑하는 방법도 있지만 그건 논외로..
- @GeneratedValue
    * 주키의 생성 방법을 맵핑하는 애노테이션
    * 생성 전략과 생성기를 설정할 수 있다.
        * 기본 전략은 AUTO: 사용하는 DB에 따라 적절한 전략 선택
        * TABLE, SEQUENCE, IDENTITY 중 하나.
- @Column
    * unique
    * nullable
    * length
    * columnDefinition
- @Temporal
    * JPA 2.1까지는 Date와 Calendar만 지원. 지금은 Period...등등 다양하게 지원
- @Transient
    * 컬럼으로 맵핑하고 싶지 않은 멤버 변수에 사용.
- application.properties
    * spring.jpa.show-sql=true
    * spring.jpa.properties.hibernate.format_sql=true

## JPA 프로그래밍: Value 타입 맵핑
- 엔티티 타입과 Value 타입 구분
  * 식별자가 있어야 하는가.
  * 독립적으로 존재해야 하는가.
- Value 타입 종류
  * 기본 타입 (String, Date, Boolean, ...)
  * Composite Value 타입
  * Collection Value 타입
    * 기본 타입의 콜렉션
    * 컴포짓 타입의 콜렉션
- Composite Value 타입 맵핑
  * @Embeddable
  * @Embedded
  * @AttributeOverrides
  * @AttributeOverride

```java
@Embeddable
public class Address {

    private String street;

    private String city;

    private String state;

    private String zipCode;

}
```

```java
@Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "home_street"))
    })
    private Address address;
```

## JPA 프로그래밍: 1대다 맵핑
- 관계에는 항상 두 엔티티가 존재 합니다.
  * 둘 중 하나는 그 관계의 주인(owning)이고
  * 다른 쪽은 종속된(non-owning) 쪽입니다.
  * 해당 관계의 반대쪽 레퍼런스를 가지고 있는 쪽이 주인.
- 단방향에서의 관계의 주인은 명확합니다.
  * 관계를 정의한 쪽이 그 관계의 주인입니다.
- 단방향 @ManyToOne
  * 기본값은 FK 생성
- 단방향 @OneToMany
  * 기본값은 조인 테이블 생성
- 양방향
  * FK 가지고 있는 쪽이 오너 따라서 기본값은 @ManyToOne 가지고 있는 쪽이 주인.
  * 주인이 아닌쪽(@OneToMany쪽)에서 mappedBy 사용해서 관계를 맺고 있는 필드를 설정해야 합니다.
- 양방향
  * @ManyToOne (이쪽이 주인)
  * @OneToMany(mappedBy)
  * 주인한테 관계를 설정해야 DB에 반영이 됩니다.

## JPA 프로그래밍: Cascade
- 엔티티의 상태 변화를 전파 시키는 옵션.
- 잠깐? 엔티티의 상태가 뭐지?
  * Transient: JPA가 모르는 상태
  * Persistent: JPA가 관리중인 상태 (1차 캐시, Dirty Checking, Write Behind, ...)
    * Dirty Checking (객체의 변경사항을 계속해서 감지) and Write Behind (객체의 상태변화를 DB에 최대한 필요한 시점에 늦게 반영)
  * Detached: JPA가 더이상 관리하지 않는 상태.
  * Removed: JPA가 관리하긴 하지만 삭제하기로 한 상태.

![](./img01.png)

## JPA 프로그래밍: Fetch
- 연관 관계의 엔티티를 어떻게 가져올 것이냐... 지금 (Eager)? 나중에(Lazy)?
  * @OneToMany의 기본값은 Lazy
  * @ManyToOne의 기본값은 Eager

## JPA 프로그래밍: Query
- JPQL (HQL)
  * Java Persistence Query Language / Hibernate Query Language
  * 데이터베이스 테이블이 아닌, 엔티티 객체 모델 기반으로 쿼리 작성.
  * JPA 또는 하이버네이트가 해당 쿼리를 SQL로 변환해서 실행함.
  * https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql

  ```java
          TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post As p", Post.class);
          List<Post> posts = query.getResultList();
  ```

- Criteria
  * 타입 세이프 쿼리
  * https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#criteria

  ```java
  CriteriaBuilder builder = entityManager.getCriteriaBuilder();
          CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
          Root<Post> root = criteria.from(Post.class);
          criteria.select(root);
          List<Post> posts = entityManager.createQuery(criteria).getResultList();
  ```

- Native Query
  * SQL 쿼리 실행하기
  * https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#sql

  ```java
  List<Post> posts = entityManager
                  .createNativeQuery("SELECT * FROM Post", Post.class)
                  .getResultList();
  ```

## 스프링 데이터 JPA 소개 및 원리
- JpaRepository<Entity, Id> 인터페이스
  * 매직 인터페이스
  * @Repository가 없어도 빈으로 등록해 줌.
- @EnableJpaRepositories
  * 매직의 시작은 여기서 부터
- 매직은 어떻게 이뤄지나?
  * 시작은 @Import(JpaRepositoriesRegistrar.class)
  * 핵심은 ImportBeanDefinitionRegistrar 인터페이스

## 핵심 개념 이해 정리
- 데이터베이스와 자바
- 패러다임 불일치
- ORM이란?
- JPA 사용법 (엔티티, 벨류 타입, 관계 맵핑)
- JPA 특징 (엔티티 상태 변화, Cascade, Fetch, 1차 캐시, ...)
- 주의할 점
  * 반드시 발생하는 SQL을 확인할 것.
  * 팁: “?”에 들어있는 값 출력하기
    * logging.level.org.hibernate.type.descriptor.sql=trace

## 스프링 데이터 JPA 활용 파트 소개

![](./img02.png)

|스프링 데이터|SQL & NoSQL 저장소 지원 프로젝트의 묶음.|
|------|---|
|스프링 데이터 Common|여러 저장소 지원 프로젝트의 공통 기능 제공.|
|스프링 데이터 REST|저장소의 데이터를 하이퍼미디어 기반 HTTP 리소스로(REST API로) 제공하는 프로젝트.|
|스프링 데이터 JPA|스프링 데이터 Common이 제공하는 기능에 JPA 관련 기능 추가.|

http://projects.spring.io/spring-data/

## 스프링 데이터 Common: Repository

![](./img03.png)

## 스프링 데이터 Common: Repository 인터페이스 정의하기
- Repository 인터페이스로 공개할 메소드를 직접 일일히 정의하고 싶다면
- 특정 리포지토리 당
  * @RepositoryDefinition
  
  ```java
  @RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
  public interface CommentRepository {
  
      Comment save(Comment comment);
  
      List<Comment> findAll();
  }
  ```
  
  * 공통 인터페이스 정의

  ```java
  @NoRepositoryBean
  public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID> {
  
      <E extends T> E save(E entity);
  
      List<T> findAll();
  
  }
  ```
  
## 스프링 데이터 Common: Null 처리하기
- 스프링 데이터 2.0 부터 자바 8의 Optional 지원.
  * Optional<Post> findById(Long id);
- 콜렉션은 Null을 리턴하지 않고, 비어있는 콜렉션을 리턴합니다.
- 스프링 프레임워크 5.0부터 지원하는 Null 애노테이션 지원.
  * @NonNullApi, @NonNull, @Nullable.
  * 런타임 체크 지원 함.
  * JSR 305 애노테이션을 메타 애노테이션으로 가지고 있음. (IDE 및 빌드 툴 지원)
- 인텔리J 설정
  * Build, Execution, Deployment
    * Compiler
      * Add runtime assertion for notnull-annotated methods and parameters

![](./img04.png)

## 스프링 데이터 Common: 쿼리 만들기 개요
- 스프링 데이터 저장소의 메소드 이름으로 쿼리 만드는 방법
  * 메소드 이름을 분석해서 쿼리 만들기 (CREATE)
  * 미리 정의해 둔 쿼리 찾아 사용하기 (USE_DECLARED_QUERY)
  * 미리 정의한 쿼리 찾아보고 없으면 만들기 (CREATE_IF_NOT_FOUND) (기본 전략)
- 쿼리 만드는 방법
  * 리턴타입 {접두어}{도입부}By{프로퍼티 표현식}(조건식)[(And|Or){프로퍼티 표현식}(조건식)]{정렬 조건} (매개변수)

| 접두어      | Find, Get, Query, Count, ... |
|----------|---------|
| 도입부      | Distinct, First(N), Top(N)    |
| 프로퍼티 표현식 | Person.Address.ZipCode => find(Person)ByAddress_ZipCode(...) |
| 정렬 조건    | OrderBy{프로퍼티}Asc Desc |
| 리턴 타입   | E, Optional<E>, List<E>, Page<E>, Slice<E>, Stream<E> |
| 매개변수   | Pageable, Sort|

- 쿼리 찾는 방법
  * 메소드 이름으로 쿼리를 표현하기 힘든 경우에 사용.
  * 저장소 기술에 따라 다름.
  * JPA: @Query @NamedQuery

## 스프링 데이터 Common: 쿼리 만들기 실습
- 기본 예제

```java
List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
// distinct
List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
// ignoring case
List<Person> findByLastnameIgnoreCase(String lastname);
// ignoring case
List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname)
```

- 정렬

```java
List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
```

- 페이징

```java
Page<User> findByLastname(String lastname, Pageable pageable);
Slice<User> findByLastname(String lastname, Pageable pageable);
List<User> findByLastname(String lastname, Sort sort);
List<User> findByLastname(String lastname, Pageable pageable);
```

- 스트리밍
  * try-with-resource 사용할 것. (Stream을 다 쓴다음에 close() 해야 함)

```java
Stream<User> readAllByFirstnameNotNull();
```

## 스프링 데이터 Common: 비동기 쿼리
- 비동기 쿼리
  * @Async Future<User> findByFirstname(String firstname); 
  * @Async CompletableFuture<User> findOneByFirstname(String firstname); 
  * @Async ListenableFuture<User> findOneByLastname(String lastname); 
    * 해당 메소드를 스프링 TaskExecutor에 전달해서 별도의 쓰레드에서 실행함.
    * Reactive랑은 다른 것임
- 권장하지 않는 이유
  * 테스트 코드 작성이 어려움.
  * 코드 복잡도 증가.
  * 성능상 이득이 없음. 
    * DB 부하는 결국 같고.
    * 메인 쓰레드 대신 백드라운드 쓰레드가 일하는 정도의 차이.
    * 단, 백그라운드로 실행하고 결과를 받을 필요가 없는 작업이라면 @Async를 사용해서 응답 속도를 향상 시킬 수는 있다.