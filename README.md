# spring_api_client
Spring API 암/복호화 통신 - 클라이언트

- Spring API 암/복호화 통신 로직
1. 서비스 요청(client)
2. public key 요청(client)
3. public key 응답(server)
4. AES256 키 생성(client)
5. AES256 키로 input json data 문자열 전체 암호화(client)
6. AES256 키로 API 인증 토근 암호화(client)
7. public key로 AES256 키 암호화(client)
8. POST 요청(client)
9. 개인키로 AES256 키 복호화(server)
10. 복호화 된 AES256 키로 토큰 복호화 및 체크(server)
11. AES256 키로 input json data 복호화(server)
12. 비즈니스 로직 실행(server)
13. 결과값 AES256 키로 암호화(server)
14. 데이터 반환(server)
15. AES256 키로 결과 데이터 복호화(client)
16. 결과 데이터 서비스 요청단으로 반환(client)

- 클라이언트에서 실행된는 암/복호화 로직은 별도의 Util로 실행

- api 성능 테스트 결과 암호화 전 문자열 데이터 길이 최대 37만개 까지 가능 (환경에 따라 다를 수 있음)
![api_test](https://user-images.githubusercontent.com/92128277/150636597-23098e7c-9ec7-40a4-808c-4c9b4f13bd72.png)
