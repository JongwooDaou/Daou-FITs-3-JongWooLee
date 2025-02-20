function myFunc() {
    //  jQuery 통해 Ajax 호출 실행
    let obj = {
        "name" : "hong",
        "age" : 20,
        "email" : "hong@example.com"
    }
    $.ajax({
        async: true,    // 비동기 방식 혹은 동기방식인지 선택, 기본값은 비동기
        url: "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json",
        type: "GET",
        data: {
            key: "790579020454919ad4b0aa0d4175d00f",
            targetDt: "20250217"
        },
        dataType: "json",
        success: function(result) {
            //  result 매개변수로 받은 JSON 문자열이 객체로 변환되어 매핑됨
            console.log("API 응답:", result); // 응답 데이터 확인

            if (!result || !result.boxOfficeResult || !result.boxOfficeResult.dailyBoxOfficeList) {
                console.error("박스오피스 데이터가 없습니다.");
                return;
            }
            $("h1").text(result.boxOfficeResult.dailyBoxOfficeList[1].movieNm)
            // alert("call success");
        },
        error: function() {
            alert("error");
        }


    })
}
