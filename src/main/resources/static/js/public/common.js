$(function() {
	// 处理文件下载，restful风格的超链接有中文字符，切割url，封装一个form表单来提交
	// 用样式 （_a_download_file）来控制
	$('._a_download_file').each(function() {
		var url = $(this).attr('href');
		var flag = url.lastIndexOf('\/');
		$(this).click(function() {
			var fileName = url.substring(flag + 1, url.length);
			var path = url.substring(0, flag);
			var form = $("<form></form>").attr("action", path).attr("method", "post");
			form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
			form.appendTo('body').submit().remove();
			return false;
		})
	})
	// 给删除的操作添加确认框，删除操作，失败后的回调提示
	$(document).on('click', 'a._delete_button', function() {
		// console.log("delete button click ...")
		var $a = $(this);
		// if ($a.hasClass('_delete_button')) {
		layer.confirm('请确定是否继续此操作？', {
			icon : 3,
			title : '提示'
		}, function(index) {
			/* do something */
			$.get($a.attr('href'), function(data) {
				if (data == "false") {
					layer.msg("操作失败！", {
						time : 800
					})
				} else {
					/* 页面加载链接内容 */
					$('#main').empty();
					$("#main").load($("._location_url").val(), {
						"page.currPage" : $('._index_currPage').val(),
						"page.sizeInPage" : $('._index_sizeInPage').val()
					});
				}
			})
			layer.close(index);
		});
		return false;
		// }
	});
	// 校验input的val，输入的数字大于0
	$("body").on('change', 'input[type="number"]', function() {
		$input = $(this);
		if ($input.val() < 0) {
			$input.val("0");
		}
		var maxlength = $input.attr("maxlength");
		// 判断是否为 phone 的input
		if ($input.is('._input_phone')) {
			$input.val($input.val().substring(0, 11));
		} else {
			if ($input.val() > Math.pow(10, maxlength)) {
				$input.val(Math.pow(10, maxlength) - 1)
			}
		}
		/*
		 * if (typeof ($(this).attr("max")) != "undefined") { if ($(this).val() - $(this).attr("max") > 0) { $(this).val($(this).attr("max")) } }
		 */
	})
	// 给所有的input输入框去空格，除去文件上传（文件上传的输入框的值不能改变，如果用js操作，会报错）
	$("body").on('change', 'input:not([type="file"])', function() {
		var input_val = $(this).val();
		input_val = $(this).val().replace(/\s+/g, "");
		// 如果包含样式（_car_name），则转为大写
		if ($(this).hasClass('_car_name')) {
			input_val = input_val.toUpperCase();
			var re = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
			if (input_val.search(re) == -1) {
				input_val = "";
				$(this).attr('placeholder', '车牌不合法！！');
			}
			// console.log(input_val)
		}
		$(this).val(input_val);
	})
	// 下拉框选中目标值
	$('select option').each(function() {
		if ($(this).val() == $(this).parent().next().val()) {
			$(this).attr("selected", true);
		}
	});
	// 为样式赋值（_check_value_goto_backStage)，到后台校验使用了此样式的input的值，自定义属性
	// targetUrl="url"
	// $("._check_value_goto_backStage").change(function() {
	$("body").on('change', '._check_value_goto_backStage', function() {
		// console.log('--> 异步请求后台数据 ...');
		var $input = $(this);
		var input_val = $input.val();
		var input_url = $input.attr("targetUrl");
		// 当输入的值不为默认值
		if ($(this)[0].defaultValue != input_val) {
			if (checkTargetName(input_val, input_url) != null) {
				$input.val("").attr("placeholder", input_val + "  已经存在，请重新输入！");
				$(this).val("")
			} else {
				// 修改回默认提示
				$input.attr("placeholder", "请勿重复录入！");
			}
		}
	})
})
/**
 * 遍历处理数组，保留指定位数的小数
 * 
 * @param {Object}
 *            array
 * @param {Object}
 *            number
 * @return {TypeName}
 */
function dealArray(array, number) {
	var fun_array = new Array();
	for (var i = 0; i < array.length; i++) {
		fun_array.push(parseFloat(array[i]).toFixed(number))
	}
	return fun_array;
}
/**
 * 输入需要校验的name和目标url，如果存在，函数返回去空格后的name，否则返回null <br>
 * 如果需要查找的name值存在，后台需要返回 "find" 字符串
 * 
 * @param name
 * @param url
 * @return String 后台接收中午乱码，排查为默认使用get提交，替换为post就好了
 */
function checkTargetName(name, url) {
	var msg = null;
	if (name != "") {
		$.ajax({
			url : url,
			async : false,
			type : 'post',
			data : {
				name : name
			},
			dataType : 'text',
			success : function(data) {
				// console.log("return data : " + data)
				if (data == "find") {
					msg = name;
				}
			}
		});
	}
	return msg;
}
/**
 * 通用的弹窗函数，简易的一级判断弹窗，使用自己的title属性作为弹窗的提示ming
 * 
 * @param {Object}
 *            title
 * @param {Object}
 *            width
 * @param {Object}
 *            height
 * @memberOf {TypeName}
 * @return {TypeName}
 */
function generalLayerUIForListJsp(width, height) {
	$('tr td a:not(._delete_button)').click(function() {
		// console.log("currPage : " + $('._index_currPage').val() + ",
		// sizeInPage : " + $('._index_sizeInPage').val());
		layer.open({
			title : $(this).attr('title'),
			type : 2,
			area : [ width, height ],
			fixed : false, // 不固定
			maxmin : true,
			shadeClose : true,
			content : $(this).attr('href'),
			end : function() {
				$('#main').load($('._location_url').val(), {
					"page.currPage" : $('._index_currPage').val(),
					"page.sizeInPage" : $('._index_sizeInPage').val()
				});
			}
		});
		return false;
	})
}
// 返回明天的日期，例：2018-10-01
function dateAdd() {
	var now = new Date();
	now.setDate(now.getDate() + 1);
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	month = month < 10 ? "0" + month : month;
	date = date < 10 ? "0" + date : date;
	var string = year + "-" + month + "-" + date;
	$('input[type="date"]').val(string)
}
function testFunction() {
	console.log("已经成功引入aus.js")
}
// 计算字符串长度
function getByteLen(val) {
	var len = 0;
	for (var i = 0; i < val.length; i++) {
		var a = val.charAt(i);
		if (a.match(/[^\x00-\xff]/ig) != null) {
			len += 2;
		} else {
			len += 1;
		}
	}
	return len;
}
// 获取当前时间的字符串，格式"20171201"
function getTimeTemp() {
	var now = new Date();
	return timeTemp = now.getFullYear() + ((now.getMonth() + 1) < 10 ? "0" : "") + (now.getMonth() + 1) + (now.getDate() < 10 ? "0" : "")
			+ now.getDate();
}
// 给input type=file 元素的紧邻的 input type=hidden 元素填充文件名，fileNameLength表示限定的长度
function getFileName(jqObj, timeTemp, fileNameLength) {
	var array = new Array();
	array = jqObj.val().split("\\");
	if (array.length == 0) {
		jqObj.next().val(timeTemp + "_" + jqObj.val());
	} else {
		if (getByteLen(array[array.length - 1]) > fileNameLength) {
			alert("文件名称过长，请修改！")
			jqObj.val("");
		} else if (array[array.length - 1].length == 0) {
			jqObj.next().val("");
		} else {
			jqObj.next().val(timeTemp + "_" + array[array.length - 1]);
		}
	}
};
// 测试common.js 文件能否正常运行
function test() {
	console.log("common.js !!!")
}