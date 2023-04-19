let index = {
    //let_this=this;
    init: function(){
        $("#btn-save").on("click", ()=>{ //function(){}대신 쓴 이유: this를 바인딩 하기 위해
            this.save();                 //function을 쓰려면 주석을 해제
        });
    },

    save: function(){
        //alert('user의 save함수 호출됨');
        let data = {
            memberEmail: $("#memberEmail").val(),
            memberPw: $("#memberPw").val(),
        };

        //console.log(data);

        //ajax호출시 default: 비동기호출
        $.ajax({
            //회원가입 수행 요청(100초 걸린다 가정)
            type:"POST",
            url:"/yummy/joinProc",
            data:JSON.stringify(data), //http body데이터
            contentType:"application/json; charset=utf-8", //body데이터가 어떤 타입인지(MIME)
            dataType:"json" //요청을 서러보해서 응답이 왔을 때 기본적으로 모든것이 Stirng(생긴게 json이라면)=>javascript오브젝트로 변경
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            location.href="/yummy";

        }).fail(function(error){
            alert(JSON.stringify(error));

        }); // ajax통신을 통해 3개의 데이터를 json으로 변경하여 insert요청


    }
}

index.init();