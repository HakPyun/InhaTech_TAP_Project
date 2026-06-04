 function validatePassword() {
          var password = document.getElementById("member_pw").value;
          var id = document.getElementById("member_id").value;
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
                  const data={member_id:id,member_pw:password};
                  // 확인 버튼이 클릭된 경우
                  // AJAX 요청을 보냄
                   fetch('/member/update/pw/search', {
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

      const autoHyphen2 = (target) => {
              target.value = target.value
             .replace(/[^0-9]/g, '')
            .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
              }