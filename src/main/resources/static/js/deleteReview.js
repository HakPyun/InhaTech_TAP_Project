function deleteReview(rId) {
    // 해당 행의 데이터 가져오기
    var isConfirmed = window.confirm("삭제 하시겠습니까?");
    // Fetch를 사용하여 데이터 전송
    if(isConfirmed){
    fetch('/review/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            review_id:rId
        }),
    })
    .then(response => response.json())
    .then(data => {
        alert("삭제 완료");
          window.location.href = "/review/list";
    })
    .catch((error) => {
    alert("삭제 에러");
    });
    alert(name);
    }else{
    alert("취소");
    }
}