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
function menu_add(){
      var table = document.getElementById("menuTable").getElementsByTagName('tbody')[0];
                 var row = table.insertRow(table.rows.length);
                 var menuIndex = table.rows.length - 1;
                 var cell1 = row.insertCell(0);
                 var cell2 = row.insertCell(1);
                 cell1.innerHTML = '<input type="text" th:field="*{menus[__' + menuIndex + '__].name}" />';
                 cell2.innerHTML = '<input type="text" th:field="*{menus[__' + menuIndex + '__].price}" />';

        }