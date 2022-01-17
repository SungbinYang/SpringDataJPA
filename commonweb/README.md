## 스프링 데이터 Common: Web 2부: DomainClassConverter
- 스프링 Converter
    * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html
    * Formatter도 들어 본 것 같은데...

    ```java
    @GetMapping("/posts/{id}")
        public String getAPost(@PathVariable Long id) {
            Optional<Post> byId = postRepository.findById(id);
            Post post = byId.get();
            return post.getTitle();
        }
    ```
  
    ```java
    @GetMapping("/posts/{id}")
        public String getAPost(@PathVariable(“id”) Post post) {
            return post.getTitle();
        }
    ```

## 스프링 데이터 Common: Web 3부: Pageable과 Sort 매개변수
- 스프링 MVC HandlerMethodArgumentResolver
  * 스프링 MVC 핸들러 메소드의 매개변수로 받을 수 있는 객체를 확장하고 싶을 때 사용하는 인터페이스
  * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodArgumentResolver.html
- 페이징과 정렬 관련 매개변수
  * page: 0부터 시작.
  * size: 기본값 20.
  * sort: property,property(,ASC|DESC)
  * 예) sort=created,desc&sort=title (asc가 기본값)