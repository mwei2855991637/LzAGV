$(function() {
	// console.log("load page.js!")
	// 处理当前页码，如果是0，就变为1
	var $currPage = $('input[name="page.currPage"]');
	if ($currPage.val() == 0) {
		$currPage.val(1);
	}
	var sizeInPage = $.cookie('sizeInPage');
	$('._index_sizeInPage').val(sizeInPage);
	// 当 sizeInPage 数值变化时，同时更新缓存中的值
	$(document).on('change', 'input[name="page.sizeInPage"]', function() {
		//console.log("change size in page")
		var temp_val = parseInt($(this).val());
		sizeInPage = temp_val;
		if (sizeInPage - 3 < 0) {
			sizeInPage = 3;
		}
		updateSizeInPage(sizeInPage);
		load_date(1, sizeInPage);
	})
	// 定义4个按钮的id，first_page,last_page,prev_page,next_page
	// 用load和jQuery.form 来加载新的页面，在指定的div上
	// 给form表单指定一个样id form_page
	// '${page }' (currPage, sizeInPage, totalPage, totalRecord)
	var url = $('#form_page').attr("action");
	var currPage, maxPage;
	/* 分页按钮的点击事件 */
	$(document).on('click', '#first_page', function() {
		currPage = $('input[name="page.currPage"]').val();
		if (currPage - 1 > 0) {
			load_date(1, sizeInPage);
		}
	});
	$(document).on('click', '#last_page', function() {
		currPage = $('input[name="page.currPage"]').val();
		maxPage = $('input[name="page.totalPage"]').val();
		maxPage = maxPage == 0 ? 1 : maxPage;
		// console.log(sizeInPage)
		if (currPage - maxPage != 0) {
			load_date(maxPage, sizeInPage);
		}
	})
	$(document).on('click', '#prev_page', function() {
		currPage = $('input[name="page.currPage"]').val();
		if (--currPage > 0) {
			load_date(currPage, sizeInPage);
		}
	})
	$(document).on('click', '#next_page', function() {
		currPage = $('input[name="page.currPage"]').val();
		maxPage = $('input[name="page.totalPage"]').val();
		if (currPage++ < maxPage) {
			load_date(currPage, sizeInPage);
		}
	})
	$(document).on('change', 'input[name="page.currPage"]', function() {
		// 约束一下 currPage 的范围
		currPage = parseInt($(this).val());
		maxPage = $('input[name="page.totalPage"]').val();
		if (currPage > 0 && currPage <= maxPage) {
			load_date(currPage, sizeInPage);
		} else {
			load_date(1, sizeInPage);
		}
	})
})
function load_date(currPage, sizeInPage) {
	$('input[name="index_currPage"]').val(currPage);
	$currPage_val = $('input[name="page.currPage"]');
	$currPage_val.val(currPage);
	// 使用form表单上的url
	$('#main').empty().load($currPage_val.parents("form").attr("action"), {
		'page.currPage' : currPage,
		'page.sizeInPage' : sizeInPage
	})
	// 每次调用此方法，都更新一下index页面上的输入框的值
	$('._index_sizeInPage').val(sizeInPage);
	$('._index_currPage').val(currPage);
}

/*
 * 同步更新页面和cookie中的 sizeInPage 值 @param {Object} sizeInPage
 */
function updateSizeInPage(sizeInPage) {
	$.cookie('sizeInPage', sizeInPage, {
		expires : 30,
		path : '/'
	})
}