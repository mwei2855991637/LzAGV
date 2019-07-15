$(function function_name() {
	$('body').on('click', '.left-nav #nav li', function() {
		if ($(this).hasClass('open')) {
			$(this).children('a:first').removeClass('open');
			$(this).children('a:first').find('.nav_right').addClass('icon-angle-right');
			$(this).children('a:first').find('.nav_right').removeClass('icon-angle-down');
			$(this).children('.sub-menu').stop().slideUp();
			$(this).siblings().children('.sub-menu').slideUp();
		} else {
			$(this).children('a:first').addClass('open');
			$(this).children('a:first').find('.nav_right').removeClass('icon-angle-right');
			$(this).children('a:first').find('.nav_right').addClass('icon-angle-down');
			$(this).children('.sub-menu').stop().slideDown();
			$(this).siblings().children('.sub-menu').stop().slideUp();
			$(this).siblings().removeClass('open');
		}
	})
})