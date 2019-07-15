$(function(){
    // 从cookie抓取账号，写入页面
    var user = {
        username : $.cookie('username'),
        password : $.cookie('password')
    }
    if (user != null) {
        $('#adminName').val(user.username);
        $('#adminPwd').val(user.password);
        $('.check-box input').attr('checked', true);
    }
    // 登录验证
    $('form').submit(function(){
        var flag = true;
        $(':input:visible').each(function(i){
            if ($.trim($(this).val()) == '') {
                flag = false;
                return false;
            }
        })
        if (flag == true && $('.check-box input').is(':checked')) {
            $.cookie('username', $('#adminName').val(), {
                expires : 30,
                path : '/'
            })
            $.cookie('password', $('#adminPwd').val(), {
                expires : 30,
                path : '/'
            })
        }
        return flag;
    })
})