$(function() {
	/*
	 * 初次进入index页面，处理sizeInPage，从cookie中抓取，同时写入页面 后续如果有所变动的话，需要同步更新cookie和页面的值
	 * 但是，使用的时候，直接读取页面的值 <input type="hidden" value="1" class="_index_currPage" />
	 * <input type="hidden" class="_index_sizeInPage" />
	 */
	var sizeInPage = $.cookie('sizeInPage');
	if (sizeInPage == null) {
		sizeInPage = 10;
		updateSizeInPage(sizeInPage);
	}
	// 给页面的 input 赋值
	$('._index_sizeInPage').val(sizeInPage);
	// 定义一个 page 的data数据
	var page = {
		"page.currPage" : $('._index_currPage').val(),
		"page.sizeInPage" : $('._index_sizeInPage').val()
	};
	// 进入首页默认打开页面，同时，吧url写入 ._location_url
//	var url_index = "/JunAn/junan/applyRecord/preSave";
//	$('#main').load(url_index, page);
//	$('._location_url').val(url_index);
	// 点击左侧的导航栏，在右侧的 $('#main') 中加载url的内容
	$(document).on('click','._a_layer,#btn',function(){
		var href = $(this).attr("href");
		if (href != null) {
			// 置空currpage 的数据
			// $('_index_currPage').val("1");
			page = updateInputValueOfPage(page);
			// 把链接存至隐藏input
			$("._location_url").val(href);
			// 页面加载链接内容
			$('#main').empty();
			$("#main").load(href, page);
		}
		return false;
	})
	// 点击上方的菜单栏中的个人名称，弹出个人信息窗口
	$('._person_info').click(function() {
		layer.open({
			title : '个人信息',
			type : 2,
			area : [ '40%', '60%' ],
			fixed : false, // 不固定
			maxmin : true,
			shadeClose : true,
			content : $(this).attr('href'),
			end : function() {
			}
		});
		return false;
	})
});
function updateInputValueOfPage(page) {
	page["page.sizeInPage"] = $('._index_sizeInPage').val();
	page["page.currPage"] = 1;// $('._index_currPage').val();
	return page;
}