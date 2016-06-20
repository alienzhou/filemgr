/**
 * Created by zhouhongxuan on 2016/6/17.
 */
$(function () {
    $('#upload-form').on('submit', function (e) {
        e.preventDefault();
        if (!beforeSubmit()) {
            return;
        }
        $.ajax({
            timeout: 5000,
            url: 'check.do',
            type: 'POST',
            data: {
                number: $('#number').val()
            },
            async: true,
            success: function (data) {
                if (data == 1) {
                    mockSubmit();
                } else {
                    $('#up-info').text('密码错误');
                }
            }
        })
    });
});

function beforeSubmit() {
    if ($('#number').val() === null || $('#number').val() === '') {
        $('#up-info').text('密码为空');
        return false;
    }
    if ($('#file').val() === null || $('#file').val() === '') {
        $('#up-info').text('文件为空');
        return false;
    }
    return true;
}

function mockSubmit() {
    $('#bg').addClass('bg-animation');
    $('#percent').addClass('show-percent');
    var option = {
        target: '#up-info',
        type: 'post',
        success: function () {
            $('#bg').removeClass('bg-animation');
            $('#percent').removeClass('show-percent');
        }
    };
    $('#upload-form').ajaxSubmit(option);
    progress();
}

function progress() {
    setTimeout(requestProgress, 300);
}

var requestProgress = function () {
    $.ajax({
        timeout: 500,
        url: 'progress.do',
        type: 'POST',
        async: true,
        success: function (data) {
            $('#num').text(data);
            data = +data;
            $('#bar').css('width', data + "%");
            if (data !== 100) {
                progress();
            }
        },
        error: function () {
            progress();
        }
    });
}