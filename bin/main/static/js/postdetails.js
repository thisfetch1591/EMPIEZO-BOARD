$(document).ready(function() {
    $('#comment-form').submit(function(event) {
        event.preventDefault();

        const content = $('#content').val();
        const currentUrl = $(location).attr('pathname');
        const jsonData = {
            "content" : content
        };

        $.ajax({
            url: currentUrl + '/comments',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(jsonData),
            success: function(response) {
                alert('댓글 작성 완료');
                $("#comments-section").load(window.location.href + " #comments-section");
            },
            error: function(error) {
                alert('게시글 작성 실패');
                console.log(error);
            }
        })
    });
    $('#like-button').click(function(event) {
        event.preventDefault();

        const currentUrl = $(location).attr('pathname');

        $.ajax({
            url: currentUrl + '/likes',
            type: 'POST',
            success: function(response) {
                $("#post-meta").load(window.location.href +" #post-meta");
            },
            error: function(error) {
                alert('추천에 실패하셨습니다.');
                console.log(error);
            }
        })
    })
})