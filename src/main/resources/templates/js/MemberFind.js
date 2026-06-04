function find_id(){
       var btn=document.getElementById("find_id_btn");
       btn.addEventListener("click",function(){
       var valueName=document.getElementById("member_name").value;
       var valuePhone=document.getElementById("member_phone").value;
            if(valueId===""){
                document.getElementById("id_message").textContent="이름을 입력하세요";
                return;
            }else if(valuePhone===""){

                document.getElementById("phone_message").textContent="전화번호를 입력하세요";

                return;
            }
            var data={member_name:valueName,member_phone:valuePhone};
            fetch("/find/id", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data) // 객체를 JSON 문자열로 변환하여 전송
            })
            .then(response => response.json()) // 서버 응답을 JSON 형태로 파싱
            .then(data => {
                // 서버에서 받은 데이터 처리
                if(data){
                window.open()
                }
                else document.getElementById("result_message").textContent="정보와 일치 하는 아이디가 없습니다.";
            })
            .catch(error => {
                console.error("Error:", error);
            });

       })




}