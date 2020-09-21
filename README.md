# place-search
## Clone
```
git clone https://github.com/whize3/place-search
```

## Run
```
cd search
./gradlew bootRun
```  
※application.yml의 spring.api.kakao.rest-api-key를 입력해야 정상적인 실행이 가능

## Access
```
http://localhost:8080/view/main - 메인화면
```  
ID: whize3  
PW: 1234

## OpenSource
|OpenSource| 사용 목적|
|---|---|
|bootstrap|html 화면 구현  
|jquery| 화면 동작 구현  
|redis| API 결과 메모리 캐싱  
|h2| 로컬 테스트를 위한 메모리 RDB  
|yaml-resource-bundle| 메시징 처리  
|jjwt|JWT 구현  
|lombok| 개발 편의를 위한 annotation 처리  
|gson| JSON Parsing  
|swagger|API 테스팅
