  function editor_register(){
      var id = document.getElementById("member_id").value;
      var name=document.getElementById("member_name").value;
      var errorMessage = document.getElementById("error-message");
      errorMessage.innerHTML = "";

       var isConfirmed = window.confirm("에디터 신청 하시겠습니까??");
              if (isConfirmed) {
              data={}
              // 확인 버튼이 클릭된 경우
              // AJAX 요청을 보냄
               fetch('/member/register/editor', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify(data)
          })
          .then(response => response.json())
          .then(result => {
                alert("신청 완료");
                  window.location.href = "/mypage";
          })
          .catch(error => {
                  alert("신청 실패");

          });
              } else {
              // 취소 버튼이 클릭된 경우
              alert("사용자가 취소 버튼을 클릭했습니다.");
              }

  }



const autoHyphen2 = (target) => {
    target.value = target.value
   .replace(/[^0-9]/g, '')
  .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
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
               function update_member() {
       var nickname_value = document.getElementById("mNickname").value;
       var id_value = document.getElementById("mId").value;
       var phone_value = document.getElementById("mTell").value;
       var answer_value = document.getElementById("mAnswer").value;
       var question_value = document.getElementById("mStatus").value;
       var errorMessage = document.getElementById("error_message");
       var nickPattern = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;

       if (nickname_value === "") {
       alert("닉네임을 입력해주세요");
           return false;
       } else if (!nickPattern.test(nickname_value)) {
       alert("닉네임은 특수문자를 제외한 2~10자리여야 합니다");
           return false;
       }

       if (phone_value === "") {

       alert("전화번호 값은 필수입니다.");
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
               alert("전화번호 또는 닉네임이 존재합니다.");
           });
       } else {
           alert("변경이 취소되었습니다.");
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

      function validatePassword() {
              var password = document.getElementById("member_pw").value;
              var checkPassword=document.getElementById("check_pw").value;
              var errorMessage = document.getElementById("error-message");
              errorMessage.innerHTML = "";

              // 유효성 검사 규칙을 정의
              var passwordPattern = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}$/;
              if (!passwordPattern.test(password)) {
                  // 비밀번호가 유효성 검사를 통과하지 못한 경우
                  errorMessage.innerHTML = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용해야 합니다.";
                  return false;
                  }
              if(password == checkPassword){
               var isConfirmed = window.confirm("비밀번호 변경하시겠습니가??");
                      if (isConfirmed) {
                      const data={member_pw:password};
                      // 확인 버튼이 클릭된 경우
                      // AJAX 요청을 보냄
                       fetch('/member/update/pw', {
                      method: 'POST',
                      headers: {
                          'Content-Type': 'application/json'
                      },
                      body: JSON.stringify(data)
                  })
                  .then(response => response.json())
                  .then(result => {
                        alert("변경완료");
                          window.location.href = "/";
                  })
                  .catch(error => {
                          alert("변경 실패");

                  });
                      } else {
                      // 취소 버튼이 클릭된 경우
                      alert("사용자가 취소 버튼을 클릭했습니다.");
                      }
                  }else{
              errorMessage.innerHTML="비밀번호가 일치하지 않습니다."
              return false;
              }
              // 유효성 검사를 통과한 경우
              // 폼을 제출하거나 다른 처리를 수행할 수 있음
          }
 function check_id() {
        var button=document.getElementById("idBtn")
        var id_errorMessage = document.getElementById("id-error-message");
                  id_errorMessage.innerHTML = "";
        // 입력 필드에서 값을 가져옴
        button.addEventListener("click",function(){
        var valueId = document.getElementById("mId").value;
        if(valueId===""){
           id_errorMessage.textContent="아이디를 입력하세요.";
            return false;
        }else if(valueId.length<6){
            id_errorMessage.textContent="아이디는 6자이상으로 만들어주세요.";
            return false;
        }
        var data={member_id:valueId};
         fetch('/member/check/id', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(data)
                    })
                    .then(response => response.json())
                    .then(result => {
                           id_errorMessage.textContent="사용 가능한 아이디 입니다.";
                    })
                    .catch(error => {
                            id_errorMessage.textContent="이미 존재 하는 아이디 입니다..";
                    });
});
    }

