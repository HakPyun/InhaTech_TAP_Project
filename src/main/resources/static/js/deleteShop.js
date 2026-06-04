function adminDeleteShop(name) {
    // 해당 행의 데이터 가져오기
    var isConfirmed = window.confirm("삭제 하시겠습니까?");
    // Fetch를 사용하여 데이터 전송
    if(isConfirmed){
    fetch('/shop/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            sName:name
        }),
    })
    .then(response => response.json())
    .then(data => {
        alert("삭제 완료");
          window.location.href = "/admin/member/list";
    })
    .catch((error) => {
    alert("삭제 에러");
    });
    }else{
    alert("취소");
    }
}

function deleteRow(name) {
    // 해당 행의 데이터 가져오기
    var isConfirmed = window.confirm("삭제 하시겠습니까?");
    // Fetch를 사용하여 데이터 전송
    if(isConfirmed){
    fetch('/shop/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            sName:name
        }),
    })
    .then(response => response.json())
    .then(data => {
        alert("삭제 완료");
          window.location.href = "/shop/myShopList";
    })
    .catch((error) => {
    alert("삭제 에러");
    });
    }else{
    alert("취소");
    }
}
function adminDeleteShop(name) {
    // 해당 행의 데이터 가져오기
    var isConfirmed = window.confirm("삭제 하시겠습니까?");
    // Fetch를 사용하여 데이터 전송
    if(isConfirmed){
    fetch('/shop/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            sName:name
        }),
    })
    .then(response => response.json())
    .then(data => {
        alert("삭제 완료");
          window.location.href = "/admin/member/list";
    })
    .catch((error) => {
    alert("삭제 에러");
    });
    }else{
    alert("취소");
    }
}