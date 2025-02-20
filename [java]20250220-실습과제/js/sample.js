//  JQuery 사용은 단순

//  selector
//  전체 선택자 : * , 모든 element
//  타입 선택자 : 원하는 tag 명을 이용해서 선택
//  아이디 선택자 : # ,원하는 id를 가진 element 선택( id 는 unique )
//  클래스 선택자 : . 원하는 class 를 가진 element 선택 ( class 는 unique 하지 않음 )
//  자식 선택자,  후손 선택자 : >, 공백으로 선택
//                          선택자는 합성해서 사용 가능
//  동위 선택자 : +, 바로 다음에 나오는 형제 1개
//              ~, 바로 다음에 나오는 형제 부터 모두

//  method
//  for 문 처리 대용으로 사용하는 method


function myFunc() {
    // list에서 선택된 항목 출력
    // //  입력 상자이기 때문에 text() 대신 val() 사용
    // //  인자 없이 val() 을 사용하면 입력상자 안의 값을 알아오는 기능
    // //  인자를 넣어서 val() 을 사용하면 인자값으로 입력상자를 채움
    // $("input").val($("select > option:selected").text())
    //
    // //  체크박스
    // // $("#region").val($("[type = checkbox]:checked + span").text())
    // $("[type=checkbox]:checked + span").each(function (idx, item) {
    //     // 반복
    //     console.log($(item).text() + "가 선택되었음.");
    // })


    // let newName = $('#name').val()
    // let li = $("<li></li>").text(newName)
    // //  원하는 위치에 붙이기
    // //  1. append() : 해당 element 의 마지막 자식에 붙이기
    // // $("ul").append(li)
    // //  2.prepend() : 해당 element 의 첫번째 자식에 붙이기
    // // $("ul").prepend(li)
    // //  3.after() : 바로 다음 형제에 붙이기
    // //  4.before() : 바로 앞 형제로 붙이기
    // $("ul > li:last").after(li)


    // 이미지 생성하기
    // <img src="img/car.jpg" width=200>
    // let myImg = $("<img /><br>").attr("src", "img/car.jpg").attr("width", "200");
    // $("#name").after(myImg);

    // 태그 찾기
    // $("h1")
    // 찾은 h1에 이벤트 등록
    $("h1").on("click", function() {
        // this 의미는 현재 사용되는 객체에 대한 reference
        // Javascript event 처리코드 내에서는 this 의 의미가 다름
        // event 소스에 대한 문서객체를 지칭
        alert($(this).text()+"clicked!");
    })
}




