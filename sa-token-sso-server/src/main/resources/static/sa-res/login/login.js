// sa
var sa = {};

// 打开loading
sa.loading = function (msg) {
    layer.closeAll();	// 开始前先把所有弹窗关了
    return layer.msg(msg, {icon: 16, shade: 0.3, time: 1000 * 20, skin: 'ajax-layer-load'});
};

// 隐藏loading
sa.hideLoading = function () {
    layer.closeAll();
};


// ----------------------------------- 登录事件 -----------------------------------

$('#login-btn').click(function () {
    let name = $('[name=username]').val();
    let pwd = $('[name=password]').val();
    let code = $('#code').val();
    if (name == null || name == '') {
        layer.msg("请输入账户名！", {anim: 6, icon: 2});
        return;
    }
    if (pwd == null || pwd == '') {
        layer.msg("请输入密码！", {anim: 6, icon: 2});
        return;
    }
    if (code == null || code == '') {
        layer.msg("请输入验证码！", {anim: 6, icon: 2});
        return;
    }
    sa.loading("正在登录...");
    // 开始登录
    setTimeout(function () {
        $.ajax({
            url: "sso/doLogin",
            type: "post",
            data: {
                name: name,
                pwd: pwd,
                code: code
            },
            dataType: 'json',
            success: function (res) {
                console.log('返回数据：', res);
                sa.hideLoading();
                if (res.code == 200) {
                    layer.msg('登录成功', {anim: 0, icon: 6});
                    setTimeout(function () {
                        location.reload();
                    }, 100)
                } else {
                    layer.msg(res.msg, {anim: 6, icon: 2});
                }
            },
            error: function (xhr, type, errorThrown) {
                sa.hideLoading();
                if (xhr.status == 0) {
                    return layer.alert('无法连接到服务器，请检查网络');
                }
                return layer.alert("异常：" + JSON.stringify(xhr));
            }
        });
    }, 400);
});

// 绑定回车事件
$('[name=username],[name=password]').bind('keypress', function (event) {
    if (event.keyCode == "13") {
        $('.login-btn').click();
    }
});

// 输入框获取焦点
$("[name=username]").focus();
