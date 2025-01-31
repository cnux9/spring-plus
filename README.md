# 1. [spring-plus](https://github.com/f-api/spring-plus) 포크
리팩토링 및 요구사항 구현 과제

</br>

# 2. 수정한 부분
## **N+1 문제 해결**:
- FETCH JOIN을 사용하는 방법과 DTO에 필요한 필드를 쿼리문으로 불러오는 방법을 통해 N+1 문제를 해결
</br>

## **JWT + Spring Security**:
-  JWT Filter와 Security의 Authenication, 그리고 ArgumentResolver를 통해 인증인가를 처리
</br>

## **QueryDSL**:
- BooleanExpression을 활용해 여러가지 선택 조건들을 동적으로 처리
</br>

## **JPA 연관관계**:
- orphanRemoval과 cascade의 차이점을 알고 상황에 맞게 활용
</br>

## **Spring AOP**:
- Spring AOP를 활용해 비즈니스 로직의 트랜잭션 롤백 여부와 상관없이 로깅을 처리하고 횡단관심사를 분리

</br>

# 3. 기간
25/01/17 - 24/01/28

</br>

# 4. 만든 사람
### [cnux9](https://github.com/cnux9)
