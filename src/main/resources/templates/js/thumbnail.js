  document.getElementById('fileInput').addEventListener('change', handleFileSelect);

        function handleFileSelect(event) {
            const fileInput = event.target;
            const thumbnailImage = document.getElementById('thumbnail');

            const file = fileInput.files[0];
            const formData = new FormData();
            formData.append('file', file);

            // 파일 업로드를 위한 fetch API 사용
            fetch('/thumbnail/image', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('파일 업로드에 실패했습니다.');
                }
                return response.text();
            })
            .then(thumbnailPath => {
                // 서버에서 받아온 이미지 경로를 사용하여 이미지 썸네일 표시
                thumbnailImage.src = thumbnailPath;
                thumbnailImage.style.display = 'block';
            })
            .catch(error => {
                console.error(error);
            });
        }