function myFunction(){

    // 버튼 클릭 시 날짜를 YYYYMMDD형식으로 받아오기
    let selectedDate = $('#myDate').val().replace(/-/g, "");
    console.log(selectedDate);
    let movie;

    $.ajax({
        async: true,    // 비동기 방식 혹은 동기방식인지 선택, 기본값은 비동기
        url: "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json",
        type: "GET",
        data: {
            key: "790579020454919ad4b0aa0d4175d00f",
            targetDt: selectedDate
        },
        dataType: "json",
        success: function(result) {
            //  result 매개변수로 받은 JSON 문자열이 객체로 변환되어 매핑됨
            console.log("API 응답:", result); // 응답 데이터 확인

            if (!result || !result.boxOfficeResult || !result.boxOfficeResult.dailyBoxOfficeList) {
                console.error("박스오피스 데이터가 없습니다.");
                return;
            }
            movie = result.boxOfficeResult.dailyBoxOfficeList;



            $('tbody tr').each(function (index) {
                if(movie[index]) {
                    let row = $(this);

                    row.find(".rank").text(movie[index].rank)
                    row.find(".title").text(movie[index].movieNm)
                    row.find(".audiCnt").text(movie[index].audiAcc)
                    row.find(".year").text(movie[index].openDt)

                    // 영화 제목으로 이미지 검색 후 <img> src 업데이트
                    searchImage(movie[index].movieNm, function(imageUrl) {
                        if (imageUrl) {
                            row.find(".movieImg img").attr("src", imageUrl);
                        } else {
                            row.find(".movieImg img").attr("src", "img/no-image.png"); // 기본 이미지 설정 (이미지가 없을 경우)
                        }
                    });

                }


            })
            // alert("call success");
        },
        error: function() {
            alert("error");
        }


    })


}

function searchImage(keyword, callback) {
    $.ajax({
        async: true,
        url: "https://dapi.kakao.com/v2/search/image",
        type: "GET",
        headers: {
            "Authorization": "KakaoAK e26df093926993d34c40681d603f2b8f" // REST API 키
        },
        data: {
            query: keyword // 검색 키워드
        },
        dataType: "json",
        success: function(result) {
            if (result.documents.length > 0) {
                callback(result.documents[0].image_url); // 첫 번째 이미지 URL 반환
            } else {
                callback(null); // 이미지 없음
            }
        },
        error: function(xhr, status, error) {
            console.error("요청 실패:", status, error);
            callback(null);
        }
    });
}

function myDelete(btn){
    $(btn).closest("tr").remove();
}

$(document).on("click", ".deleteBtn", function() {
    myDelete(this); // 클릭한 버튼을 함수에 전달
});