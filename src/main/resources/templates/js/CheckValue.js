 function check_id() {
        var button=document.getElementById("idBtn")
        // 입력 필드에서 값을 가져옴
        button.addEventListener("click",function(){
        var valueId = document.getElementById("mId").value;
        if(valueId===""){
            document.getElementById("idMessage").textContent="아이디를 입력하세요.";
            return;
        }else if(valueId.length<6){
            document.getElementById("idMessage").textContent="아이디는 6자이상으로 만들어주세요.";
            return;
        }

        fetch("/check/id/"+valueId,{
        method:"GET"
        })
        .then(response=>response.json())
        .then(data=>{
            if(data){
                document.getElementById("idMessage").textContent="사용 가능한 아이디 입니다.";
            }else{
                document.getElementById("idMessage").textContent="이미 존재 하는 아이디 입니다..";

            }


        })
        .catch(error=>console.error("에러 발생",error));

});


    }
 function check_nickname() {
        var button=document.getElementById("NickBtn")
        // 입력 필드에서 값을 가져옴
        button.addEventListener("click",function(){
        var valueNickname = document.getElementById("mNickname").value;
        if(valueNickname===""){
            document.getElementById("nickMessage").textContent="닉네임을 입력하세요.";
            return;
        }else if(valueNickname.length<2||valueNickname.length>8){
            document.getElementById("nickMessage").textContent="닉네임은 2자이상 8자이하로 이루어집니다.";
            return;
        }

        fetch("/check/nick/"+valueNickname,{
        method:"GET"
        })
        .then(response=>response.json())
        .then(data=>{
            if(data){
                document.getElementById("nickMessage").textContent="사용 가능한 닉네임 입니다.";
            }else{
                document.getElementById("nickMessage").textContent="이미 존재 하는 닉네임 입니다..";

            }


        })
        .catch(error=>console.error("에러 발생",error));

});


    }
function deleteMember() {
        var id=document.getElementById("mId").value;
        var isConfirmed = window.confirm("정말 이 유저를 삭제 하시겠습니까?");
        const data={member_id:id};


                if (isConfirmed) {
                // 확인 버튼이 클릭된 경우
                // AJAX 요청을 보냄
                 fetch('/admin/member/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                    alert("삭제 완료");
                    window.location.href = "/admin/member/list";
            })
            .catch(error => {
                    alert("삭제 오류");
            });
                }

    }

    function check_nickname() {
        var button=document.getElementById("NickBtn")
        var nick_errorMessage = document.getElementById("nick-error-message");
        nick_errorMessage.innerHTML = "";
        var nickPattern =/^[\w\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$/;
        // 입력 필드에서 값을 가져옴
        button.addEventListener("click",function(){
        var valueNick = document.getElementById("mNickname").value;
        if(valueNick===""){
           nick_errorMessage.textContent="닉네임을 입력하세요.";
            return false;
        }else if(!nickPattern.test(valueNick)){
            nick_errorMessage.textContent="닉네임은 특수문자를 제외한 2~10자리여야 합니다.";
            return false;
        }
        var data={member_nickname:valueNick};
         fetch('/member/check/nickname', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(data)
                    })
                    .then(response => response.json())
                    .then(result => {
                           nick_errorMessage.textContent="사용 가능한 닉네임 입니다.";
                    })
                    .catch(error => {
                            nick_errorMessage.textContent="이미 존재 하는 닉네임 입니다..";
                    });

    });
    }

    const autoHyphen2 = (target) => {
    target.value = target.value
    .replace(/[^0-9]/g, '')
    .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
    }
            function update() {
    var nickname_value = document.getElementById("mNickname").value;
    var id_value = document.getElementById("mId").value;
    var phone_value = document.getElementById("mTell").value;
    var answer_value = document.getElementById("mAnswer").value;
    var question_value = document.getElementById("mStatus").value;
    var phone_errorMessage = document.getElementById("phone-error-message");
    var nick_errorMessage = document.getElementById("nick-error-message");
    var errorMessage = document.getElementById("error_message");
    errorMessage.innerHTML = "";
    nick_errorMessage.innerHTML = "";
    var nickPattern = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;

    if (nickname_value === "") {
        nick_errorMessage.textContent = "닉네임을 입력하세요.";
        return false;
    } else if (!nickPattern.test(nickname_value)) {
        nick_errorMessage.textContent = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.";
        return false;
    }

    if (phone_value === "") {
        phone_errorMessage.textContent = "전화번호를 입력하세요.";
        return false;
    }

    var isConfirmed = window.confirm("회원 정보를 수정하시겠습니까?");
    if (isConfirmed) {
        const data = {
            member_id: id_value,
            member_nickname: nickname_value,
            member_answer: answer_value,
            member_status: question_value,
            member_phone: phone_value
        };
        console.log(data);

        fetch('/mypage/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            alert("변경 완료");
            window.location.href = "/";
        })
        .catch(error => {
            alert("변경 실패");
            errorMessage.textContent = "전화번호 혹은 닉네임이 존재합니다.";
        });
    } else {
        alert("취소 버튼 클릭");
    }
    }

            function deleteMember() {
    var isConfirmed = window.confirm("정말로 삭제 하시겠습니까?");

    if (isConfirmed) {
        var data = {}; // 필요한 데이터를 이 객체에 추가하세요

        fetch('/mypage/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            alert("삭제 완료");
            window.location.href = "/logout";
        })
        .catch(error => {
            alert("삭제 실패");
        });
    } else {
        alert("사용자가 취소 버튼을 클릭했습니다.");
    }
    }