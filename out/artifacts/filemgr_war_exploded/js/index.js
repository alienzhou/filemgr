/**
 * Created by zhouhongxuan on 2016/6/17.
 */
$(function () {
    $('#upload-form').on('submit', function (e) {
        $('#bg').addClass('bg-animation');
        $('#percent').addClass('show-percent');
        e.preventDefault();
        var option = {
            target: '#up-info',
            type: 'post',
            success: function () {
                $('#bg').removeClass('bg-animation');
                $('#percent').removeClass('show-percent');
            }
        };
        $(this).ajaxSubmit(option);
        progress();
    });
});

function progress() {
    setTimeout(requestProgress, 200);
}

var requestProgress = function () {
    $.ajax({
        url: 'progress.do',
        type: 'POST',
        success: function (data) {
            $('#num').text(data);
            data = +data;
            $('#bar').css('width', data + "%");
            if (data !== 100) {
                progress();
            }
        }
    });
}