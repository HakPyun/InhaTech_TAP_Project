$(document).ready(function () {
    var maxFields = 5; // 최대 필드 개수

    $('.btnAdd').click(function () {
        // 현재 필드의 개수 세기

        // 필드 추가
        var table = document.getElementById("menuTable").getElementsByTagName('tbody')[0];
        var newRow = table.insertRow(table.rows.length);
        if (table.rows.length > maxFields) {
            alert("최대 " + maxFields + "개까지만 추가할 수 있습니다.");
            return;
        }
        var cell1 = newRow.insertCell(0);
        var cell2 = newRow.insertCell(1);
        var cell3 = newRow.insertCell(2);
        var cell4 = newRow.insertCell(3);

        cell1.innerHTML = '<span>' + table.rows.length + '</span> ';
        cell2.innerHTML = '<input type="text" class="menu" name="menu" style="width: 100px;" >';
        cell3.innerHTML = '<input type="number" class="menu_price" name="menu_price" style="width: 100px;"> ';
        cell4.innerHTML = '<input type="button" class="btnRemove btn btn-danger" value="삭제">';

        // 삭제 버튼에 대한 이벤트 핸들러 추가
    });

    $(document).on('click', '.btnRemove', function () {
        var currentRow = $(this).closest('tr'); // 현재 행을 선택
        currentRow.remove(); // 행 제거
        updateMenuIndices(); // 메뉴 번호 업데이트
    });

    // 메뉴 번호 업데이트 함수
    function updateMenuIndices() {
        var table = document.getElementById("menuTable").getElementsByTagName('tbody')[0];
        var rows = table.getElementsByTagName('tr');

        // 각 행의 메뉴 번호 업데이트
        for (var i = 0; i < rows.length; i++) {
            var indexInput = rows[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0];
            indexInput.value = i + 1; // 메뉴 번호를 1부터 시작하도록 업데이트
        }
    }

    /////////////////////////////////////////여기까지 메뉴

    $('.btnImgAdd').click(function () {
        // 현재 필드의 개수 세기

        // 필드 추가
        var table = document.getElementById("imgTable").getElementsByTagName('tbody')[0];
        var newRow = table.insertRow(table.rows.length);
        if (table.rows.length > maxFields) {
            alert("최대 " + maxFields + "개까지만 추가할 수 있습니다.");
            return;
        }
        var cell1 = newRow.insertCell(0);
        var cell2 = newRow.insertCell(1);
        var cell3 = newRow.insertCell(2);

        cell1.innerHTML = '<span>' + table.rows.length + '</span>';
        cell2.innerHTML = '<input type="file" class="custom-file-input" name="itemImgFile">';
        cell3.innerHTML = '<input type="button" class="btnImgRemove btn btn-danger" value="삭제">';

    });

    $(document).on('click', '.btnImgRemove', function () {
        var currentRow = $(this).closest('tr'); // 현재 행을 선택
        currentRow.remove(); // 행 제거
        updateMenuIndices(); // 메뉴 번호 업데이트
    });

    // 메뉴 번호 업데이트 함수
    function updateMenuIndices() {
        var table = document.getElementById("imgTable").getElementsByTagName('tbody')[0];
        var rows = table.getElementsByTagName('tr');

        // 각 행의 메뉴 번호 업데이트
        for (var i = 0; i < rows.length; i++) {
            var indexInput = rows[i].getElementsByTagName('td')[0].getElementsByTagName('input')[0];
            indexInput.value = i + 1; // 메뉴 번호를 1부터 시작하도록 업데이트
        }
    }

    $('.btnAddUpdate').click(function () {
        // 현재 필드의 개수 세기

        // 필드 추가
        var table = document.getElementById("menuTable").getElementsByTagName('tbody')[0];
        var newRow = table.insertRow(table.rows.length);
        if (table.rows.length > maxFields) {
            alert("최대 " + maxFields + "개까지만 추가할 수 있습니다.");
            return;
        }
        var cell1 = newRow.insertCell(0);
        var cell2 = newRow.insertCell(1);
        var cell3 = newRow.insertCell(2);
cell1.innerHTML = '<input type="text" class="menu" name="menu" style="width: 100px;" >';
    cell2.innerHTML = '<input type="number" class="menu_price" name="menu_price" style="width: 100px;"> ';
    cell3.innerHTML = '<input type="button" class="btnRemove btn btn-danger" value="삭제">';
});






});