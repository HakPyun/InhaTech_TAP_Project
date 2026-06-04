function updateMemberRank() {
        var name = document.getElementById("mName").value;
        var id=document.getElementById("mId").value;
        var tell=document.getElementById("mTell").value;
        var isConfirmed = window.confirm("승진 시키겠습니까?");
        const data={member_id:id};


                if (isConfirmed) {
                // 확인 버튼이 클릭된 경우
                // AJAX 요청을 보냄
                 fetch('/admin/member/check/promote', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                    alert("승진 완료");
                    window.location.href = "/admin/editor/check/list";
            })
            .catch(error => {
                    alert("변경 오류");
            });
                } else {
                var isConfirmed_cancel = window.confirm("학생증이 아닙니까?");
                if(isConfirmed_cancel){
                fetch('/admin/member/check/hold', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(data)
                            })
                            .then(response => response.json())
                            .then(result => {
                                    alert("업데이트 완료");
                                    window.location.href = "/admin/editor/check/list";
                            })
                            .catch(error => {
                                    alert("변경 오류");
                            });

                }else{
                    alert("취소");
                    return false;
                }
                }

    }

    function deleteMember() {
            var name = document.getElementById("mName").value;
            var id=document.getElementById("mId").value;
            var tell=document.getElementById("mTell").value;
            var isConfirmed = window.confirm("이 유저 정보를 삭제 시키겠습니까?");
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
                        alert("변경 오류");
                });
                    } else{
                    alert("취소하였습니다.");

                    }

        }

 function updateMember() {
            var name = document.getElementById("mName").value;
            var id = document.getElementById("mId").value;
            var role=document.getElementById("mRole").value;
            var isConfirmed = window.confirm("수정 하시겠습니까?");


                    if (isConfirmed) {
                    // 확인 버튼이 클릭된 경우
                    // AJAX 요청을 보냄
                     fetch('/admin/member/update', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({member_id:id,member_Role:role})
                })
                .then(response => response.json())
                .then(result => {
                        alert("수정 완료");
                        window.location.href = "/admin/member/list";
                })
                .catch(error => {
                        alert("수정 오류");
                        return ;
                });
                    } else{
                    alert("취소하였습니다.");

                    }

        }

