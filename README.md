
Description of the Mobile Programming Project
===

## [Trailer video](https://youtu.be/71wZpwm_JU0)
* If you want to watch our trailer video, please click on the image.

[![](http://img.youtube.com/vi/71wZpwm_JU0/0.jpg)](http://www.youtube.com/watch?v=71wZpwm_JU0 "")
 
## [Presentation material](https://github.com/JunHeon-Ch/Mobile-Programming-Project/blob/master/MP_Final%20Presentation.pdf)
If you want to watch our presentation material, please click on the image.
[![Presentation material](https://github.com/JunHeon-Ch/Mobile-Programming-Project/blob/master/presentation.png)](https://github.com/JunHeon-Ch/Mobile-Programming-Project/blob/master/MP_Final%20Presentation.pdf)

## [Title](https://github.com/JunHeon-Ch/MobileProgrammingProject/wiki/Title)
### Our Closet
![Logo](https://github.com/JunHeon-Ch/Mobile-Programming-Project/blob/master/wiki_image/loadingback.png)
* To share clothes that we don’t wear with users 
* To buy or borrow clothes from near by users.
* To check clothes without taking them out

## [Brief Description](https://github.com/JunHeon-Ch/MobileProgrammingProject/wiki/Brief-Description)
- You can check the clothes in the closet without taking them out.
- It can be handled by sharing clothes that you don't wear often or that you don't want to throw away.
- You can borrow clothes from a nearby user when you don't have clothes to wear in a special place.

**Make my own closet in my smart phone and share my coordinator and unused products with people.**

## [Contents](https://github.com/JunHeon-Ch/MobileProgrammingProject/wiki/Contents)
### Sign up
* If you want to use our program, you have to sign up according to the procedure.
* Enter your ID, password, name, age, and address.
* Store the user's information on the server database.
***
### Create Closet
* Once your subscription is complete, you can log in to the program.
* After logging in, you can save the clothes you want to save.
* Click the my closet button and press the additional button.
* Enter photos and closet information(item name, category, color, brand name, season, size, share) of clothes.
* Store the user's clothes in the server database.
***
### Search Clothes
* You can search the clothes in the closet by item name and brand name.
* Press the search button and enter the search term.
* Shows products that meet the search term on the screen.
***
### Filter Clothes
* You can filter the clothes in the closet by category, color, season, share.
* Press the filter button and select the desired criteria.
* Shows products that meet the filter criteria on the screen.
***
### Save Your Own Look
* Press the Look Book button.
* Select the desired clothes according to the category that appears on the screen.
* Enter look information(occasion, season).
* When all categories and information are selected, press the Save button.
* Save the look to the server database.
***
### Filter Looks
* You can filter the looks in the look book by occasion and season.
* Press the filter button and select the desired criteria.
* Shows looks that meet the filter criteria on the screen.
***
### Share Clothes
#### If you want to share your clothes
* Click on the clothes you want to share in the closet.
* Click the Share button.
#### If you want to borrow someone else's clothes,
* Click the Our Closet button.
* Enter the desired product name and brand name or select a category, color,and season.
* Show shared closets in the order closest to the user's location.

## Functions
### **메인**
✅ **로그인 여부 확인 `MainActivity`**

   -   FirebaseAuth 인스턴스을 이용해 현재 로그인된 정보를 확인함
        -   로그인 정보가 있을 경우
            -   Firestore에서 users 컬렉션 하위에 user uid로 설정된 User 도큐먼트를 가져옴
                -   도큐먼트가 있을 경우 → `ModeActivity`로 이동
                -   도큐먼트가 없을 경우 → `MemberInitActivity`로 이동
        -   로그인 정보가 없을 경우 → `SignUpActivity`로 이동

✅ **모드 선택 `ModeActivity`**

  ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/80b18c68-26bf-452a-8c26-b25c1e4557cd/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T075155Z&X-Amz-Expires=86400&X-Amz-Signature=33bd1f84994638984daf4d3dec8745d71d1aead9c7856719d75e4c4132de0cfb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   -   ModeActivity 하위에 `MyClosetFragment`, `LookbookFragment`, `OurClosetFragment`가 있음
   -   내옷장 버튼 클릭 시 → `MyClosetFragment`로 이동
   -   룩북 버튼 클릭 시 → `LookbookFragment`로 이동
   -   공유옷장 버튼 클릭 시 → `OurClosetFragment`로 이동
   -   로그아웃 버튼 클릭 시 → FirebaseAuth 인스턴스를 호출해 `signOut` 메서드 호출 후 `SignUpAcitivty`로 이동
***
### **회원가입/로그인**

✅ **회원가입 `SignUpActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/41e4e8a6-ca29-4950-9658-8d500aed6704/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T075525Z&X-Amz-Expires=86400&X-Amz-Signature=3efa9bcbfb498450e6629fa2060a05002f85a65f1068fcfe68bbd11c9a553c93&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   -   로그인 버튼 클릭 시 → `LoginAcitivty`로 이동
   -   회원가입 버튼 클릭 시 → Firebase Authentication에 입력한 이메일과 비밀번호로 사용자를 생성하고 `MemberInitActivity`로 이동

✅ **추가 정보 입력 `MemberInitActivity`**
    
![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7e8312d2-de08-432c-af5d-2ac6029becda/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T075723Z&X-Amz-Expires=86400&X-Amz-Signature=a61783473ef66ff56568bad26ed42708b379d0cc2961e1fe5bcacb72f0af0639&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   -   이름, 전화번호, 생년월일, 주소를 추가로 입력 받음
        -   주소: `Geocoder`를 사용하여 현재 GPS를 주소로 변환
   -   확인 버튼 클릭 시 → Firestore에 user 컬렉션 하위에 user uid의 도큐먼트를 생성하고 입력된 정보를 저장함

✅ **로그인 `LoginActivity`**
    
  ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0e082249-c295-4a96-a968-c0df6a30f1bc/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T075849Z&X-Amz-Expires=86400&X-Amz-Signature=182664434615df1522d04b3cd9e6af369ce3b72383fd20322d20b6b51477491f&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   -   이메일과 비밀번호를 사용해 로그인 후 `ModeAcitivty`로 이동
***
### **옷장**
    
✅ **메인 `MyClosetFragment`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/146d1e5b-9f1f-4e1f-b79f-af16e1271749/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T075951Z&X-Amz-Expires=86400&X-Amz-Signature=c28988285b514e29134039f06e9c797e50cd791ec3fb834fe78790d53397f064&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   **추가**
   
   -   Action Bar 메뉴 중 추가 선택 시 → `MyClosetAddActivity`로 이동
    
   **수정**
    
   -   아이템 클릭 후 수정 선택 시 → `MyClosetEditActivity`로 이동
    
   **삭제**
    
   -   아이템 클릭 후 삭제 선택 시 → Storage 내의 해당 이미지 삭제, Firestore 내의 해당 아이템 데이터 삭제
    
   **조회**
    
   -   **기본**: 사용자가 저장한 아이템 전체 출력
   -   **검색**: 키워드와 아이템 이름이나 브랜드 명이 같은 아이템 출력
   -   **필터**: 필터링 정보(카테고리, 색상, 시즌, 공유여부)로 필터링된 아이템 출력
    
   1.  `onStart` 메서드 호출 시 Firestore에서 사용자 아이템 전체 조회
   2.  기본, 검색, 필터에 따라 해당하는 아이템만 List에 저장
   3.  Storage에서 아이템 URL로 이미지 개별 조회 후 출력
    
   ❗❗ **검색, 필터에 따라 DB 조회 쿼리를 생성하지 않은 이유** 
   
   사용자가 해당 옵션을 선택할 때 초기 화면에 사용자가 가지고 있는 전체 아이템을 보여줘야 함. 어플리케이션 내에 전체 아이템을 저장하고 있기 때문에 굳이 네트워크 I/O를 사용하는 것은 비효율적이라고 판단함.
    
   ☑ **개선해야 할 부분**
   
   조회된 아이템을 화면에 출력할 때 이미지를 개별 조회 후 출력함 → 데이터 양이 많아질 경우 비효율적이기 때문에 조회된 아이템의 이미지를 **전체 조회**할 수 있는 방법이 필요함
    
✅ **아이템 추가 `MyClosetAddActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/6ea8ebf6-357a-4bdb-9d67-c8c29574f3b4/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080156Z&X-Amz-Expires=86400&X-Amz-Signature=c753d942c2188a679f4bf225df7eb79e0536418e1dca3ec5ad33a3595a80a087&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  카메라나 갤러리를 통해 아이템 이미지 업로드
   2.  아이템 정보(공유여부, 상품명, 카테고리, 컬러, 브랜드, 시즌, 사이즈, 가격) 입력
   3.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   4.  Action Bar 메뉴 중 확인 버튼 클릭 시 Storage에 아이템 이미지 저장, FireStore에 아이템 정보 저장
   5.  Activity 종료

✅ **아이템 수정 `MyClosetEditActivity`**
    
  ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9af35999-113f-4f76-ad9c-40d9d155a975/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080258Z&X-Amz-Expires=86400&X-Amz-Signature=be5f1d7d569d6ebe6fe032333f6b5fcc95c53d50f128ec489f4200ceb9184c1f&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  아이템 정보(공유여부, 상품명, 카테고리, 컬러, 브랜드, 시즌, 사이즈, 가격) 수정
   2.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   3.  Action Bar 메뉴 중 확인 버튼 클릭 시 FireStore에 아이템 정보 수정
   4.  Activity 종료

✅ **필터 `MyClosetFilterActivity`**
    
 ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7bdb42f4-b9b0-40b4-b6c3-21492ac53a76/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080321Z&X-Amz-Expires=86400&X-Amz-Signature=7db0ffbfadf56f9323ab96b816e290300b2d15821d2f0f5d7bde60df3e317e6b&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  필터링 정보(카테고리, 컬러, 시즌, 공유여부) 선택
   2.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   3.  Action Bar 메뉴 중 확인 버튼 클릭 시 `MyClosetFragment`로 필터링 List 전달
   4.  Activity 종료
***
### 룩북

✅ **메인 `LookbookFragment`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9ccfca11-bf05-46c2-8509-409eae3513ff/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080417Z&X-Amz-Expires=86400&X-Amz-Signature=3af3b9c36f15e697efc37585b4643b8b170825168dc0de70412629be9245ec20&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   **추가**
    
   -   Action Bar 메뉴 중 추가 선택 시 → `CoordinatorActivity`로 이동
    
   **수정**
    
   -   룩 클릭 후 수정 선택 시 → `CoordinatorEditActivity`로 이동
    
   **삭제**
    
   -   룩 클릭 후 삭제 선택 시 → Storage 내의 해당 이미지 삭제, Firestore 내의 해당 룩 데이터 삭제
    
   **조회**
    
   -   **기본**: 사용자가 저장한 룩 전체 출력
   -   **필터**: 필터링 정보(드레스코드, 시즌)로 필터링된 룩 출력
    
   1.  `onStart` 메서드 호출 시 Firestore에서 사용자 룩 전체 조회
   2.  기본, 필터에 따라 해당하는 룩만 List에 저장
   3.  Storage에서 룩 URL로 이미지 개별 조회 후 출력
    
   ❗❗ **필터에 따라 DB 조회 쿼리를 생성하지 않은 이유** 
   
   사용자가 해당 옵션을 선택할 때 초기 화면에 사용자가 가지고 있는 전체 룩을 보여줘야 함. 어플리케이션 내에 전체 룩을 저장하고 있기 때문에 굳이 네트워크 I/O를 사용하는 것은 비효율적이라고 판단함.
    
   ☑ **개선해야 할 부분**
    
   조회된 룩을 화면에 출력할 때 이미지를 개별 조회 후 출력함 → 데이터 양이 많아질 경우 비효율적이기 때문에 조회된 룩의 이미지를 **전체 조회**할 수 있는 방법이 필요함
    
✅ **룩 추가 `CoordinatorActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/a7c581ba-1f46-4228-a325-b6330bb06117/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080531Z&X-Amz-Expires=86400&X-Amz-Signature=04abc69c83cf0fb19b609ea12e4f9c6742b76a53479ff91daf740c8b022a3978&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  아이템 조회 버튼을 클릭하여 카테고리별로 아이템 조회
   2.  원하는 아이템 선택 후 위치 조정
   3.  룩 정보(드레스코드, 시즌) 입력
   4.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   5.  Action Bar 메뉴 중 확인 버튼 클릭 시 Storage에 룩 이미지 저장, FireStore에 룩 정보 저장
   6.  Activity 종료
    
   ☑ **개선해야 할 부분**
    
   룩에 저장할 아이템을 조회하기 위해 `onCreate` 메서드 실행 시 사용자가 저장한 모든 아이템을 조회함 → 카테고리를 선택했을 때 해당 카테고리의 아이템만 조회하는 로직으로 수정해야 함
    
✅ **룩 수정 `CoordinatorEditActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f71f8e1f-ab40-4efa-939a-f409a7d270bf/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080615Z&X-Amz-Expires=86400&X-Amz-Signature=c59a8185f57f3f9737c6eca3400ece8449d915c4675878950cba01ee3ed5539b&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  룩 정보(드레스코드, 시즌) 수정
   2.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   3.  Action Bar 메뉴 중 확인 버튼 클릭 시 FireStore에 룩 정보 수정
   4.  Activity 종료

✅ **필터 `LookbookFilterActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9ddb5b4d-277c-4e7f-b815-24d76de108df/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080641Z&X-Amz-Expires=86400&X-Amz-Signature=3bb8534884dfe6ae0d13d508f320d9306278fee2b7abbbc1dce6bed8eeee0f66&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  필터링 정보(드레스코드, 시즌) 선택
   2.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   3.  Action Bar 메뉴 중 확인 버튼 클릭 시 `LookbookFragment`로 필터링 List 전달
   4.  Activity 종료
***
### 공유옷장
    
✅ **메인 `OurClosetFragment`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/802d8adb-339c-4991-aa22-e06f13f50e8e/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080730Z&X-Amz-Expires=86400&X-Amz-Signature=b5c29045293b4c914bfa0176be6576b98d758951b0ecb614d0698236d7a844af&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   **공유 요청**
    
   -   아이템 클릭 후 공유 요청 보내기 선택 시 → `RequestActivity`로 이동
    
   **메시지**
    
   -   Action Bar 메뉴 중 메시지 선택 시 → 내가 보낸 메시지 or 내가 받은 메시지 확인 가능
        -   내가 보낸 메시지 → `RequestMessageActivity`로 이동
        -   내가 받은 메시지 → `ResponseMessageActivity`로 이동
    
   **개별 조회**
    
   -   아이템 클릭 후 정보 보기 선택 시 → `ViewClosetInfoActivity`로 이동하여 해당 아이템의 정보 출력
    
   **전체 조회**
    
   -   **기본**: 공유된 아이템 전체 출력
   -   **거리순**: 공유된 아이템를 나와 가까운 순서로 소팅하여 전체 출력
   -   **검색**: 키워드와 아이템 이름이나 브랜드 명이 같은 아이템 출력
   -   **필터**: 필터링 정보(카테고리, 색상, 시즌)로 필터링된 아이템 출력
    
   1.  `onStart` 메서드 호출 시 Firestore에서 사용자들이 공유한 아이템 전체 조회
   2.  기본, 거리순, 검색, 필터에 따라 해당하는 아이템만 List에 저장
   3.  Storage에서 아이템 URL로 이미지 개별 조회 후 출력
    
   ❗❗ **거리순, 검색, 필터에 따라 DB 조회 쿼리를 생성하지 않은 이유** 

사용자가 해당 옵션을 선택할 때 초기 화면에 사용자가 가지고 있는 전체 룩을 보여줘야 함. 어플리케이션 내에 전체 룩을 저장하고 있기 때문에 굳이 네트워크 I/O를 사용하는 것은 비효율적이라고 판단함.
    
   ☑ **개선해야 할 부분**
    
   조회된 룩을 화면에 출력할 때 이미지를 개별 조회 후 출력함 → 데이터 양이 많아질 경우 비효율적이기 때문에 조회된 룩의 이미지를 **전체 조회**할 수 있는 방법이 필요함
    
✅ **공유 요청 `RequestActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/33eecaa5-6917-4690-b191-e4943648be1e/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080831Z&X-Amz-Expires=86400&X-Amz-Signature=fe4033fcfa8afb49a66f1afd4f307e58dcb5bb9ea6566bfaad31f7445d350bb5&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  요청을 보내는 사용자의 정보 출력
   2.  요청 버튼 클릭 시
        1.  공유 요청하는 사용자의 정보로 DTO 생성 후 Firestore responses 컬렉션>공유 요청 받는 사용자 도큐먼트에 저장
        2.  공유 요청 받는 사용자의 정보로 DTO 생성 후 Firestore requests 컬렉션>공유 요청하는 사용자 도큐먼트에 저장
   3.  요청 메시지는 `OurClosetFragment`의 Action Bar 메뉴 중 메시지에서 확인할 수 있음

✅ **필터 `OurClosetFilterActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ee990a04-8cd8-4138-b34d-1e1d13531bb9/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080902Z&X-Amz-Expires=86400&X-Amz-Signature=647867119978eb883369a099fc9744e5bd47011a1b119694c7b9664412b78362&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   1.  필터링 정보(카테고리, 컬러, 시즌) 선택
   2.  Action Bar 메뉴 중 리셋 버튼 클릭 시 초기화
   3.  Action Bar 메뉴 중 확인 버튼 클릭 시 `OurClosetFragment`로 필터링 List 전달
   4.  Activity 종료

✅ **메시지 `RequestMessageActivity, ResponseMessageActivity`**
    
   ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/832aa8e5-1055-4dce-ae56-c83ebdd1bde6/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220226%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220226T080923Z&X-Amz-Expires=86400&X-Amz-Signature=47a0f220177799840d0cc71a0de7f864906aaa88ea23a26d04c1632b528603cb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
   -   내가 보낸 메시지: 내가 보낸 공유 요청 정보를 확인할 수 있음
   -   내가 받은 메시지: 다른 사용자가 보낸 요청 정보를 확인할 수 있음
    
   1.  아이템 이미지, 사용자 정보 출력
   2.  메시지 클릭 시 → 전화 걸기, 지도로 보기 선택
        1.  전화 걸기 클릭 시 → 상대 전화번호로 전화를 걸 수 있음
        2.  지도로 보기 선택 시 → 구글 맵으로 상대방의 위치와 나와 상대방 간의 거리를 확인할 수 있음

## [Key Features](https://github.com/JunHeon-Ch/MobileProgrammingProject/wiki/Key-Features)
### My Closet
* You can see the products I have at a glance.
* Specific products can be identified through search and filter.
***
### Look Book
* You can make your own lookbook easy to coordinate.
* Filter identifies specific looks.
***
### Shared Closet
* You can share products that you don't use with others.
* You can borrow the product you want from other users.
* Specific products can be identified through search and filter.
* You can check other users' products in order of distance in order of their location.

## [Differences from existing apps](https://github.com/JunHeon-Ch/MobileProgrammingProject/wiki/Differences-from-existing-apps)
* “Closet Share”: App that gives a certain amount of money to providers and borrow a certain amount money to consumers.
***
### Differences
* Make a connection between providers and consumers.
* When searching for shared clothes, show sharing users in the order that they are close to my location.
* Search by brand name, category, color, season, shared condition through search & filter function!
* Save my own look and see look made by me easily.

## [Technical Points](https://github.com/JunHeon-Ch/MobileProgrammingProject/wiki/Technical-Points)
>> ### DataBase
*  Implement server ***using Firebase*** to store member information and closets.
>> ### GPS
* Implementing for sharing closet that searches people around you ***using GPS***.
>> ### Open CV
* Implementing for removing image background ***Using OpenCV grabCut algorithm***.
>> ### UI
* ***Use Glide Library*** to quickly show pictures on screen.
* ***Use AlertDialog*** to enter information via pop-up screen.

## [Member Information & Role](https://github.com/JunHeon-Ch/Mobile-Programming-Project/wiki/Member-Information-&-Role)
||최준헌 | 이진원 | 이상운 |
|:-|:-:|:-:|:-:|
Student ID| 201533673 | 201433705 | 201635832 |
Email |chjh12100@gmail.com|wlsdnjs1120@naver.com|dltkddns79@gmail.com|
Role |Implementing Closet, Cordinator/Final Presentation|Implementing Sign up, Log in/Final Report|Implementing Share Closet/Proposal Presentation|
