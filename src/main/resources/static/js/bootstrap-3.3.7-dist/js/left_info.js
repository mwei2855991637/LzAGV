			function dc(id){
			return document.getElementById(id);
			}
			var timer=null;
			dc('button1').addEventListener('click',function(e){
			
				$('button').css('color','#000');
				$('#button1').css('color','#AFD9EE');
			
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				form_query(1);
				//单击
			},250)
			},false);
			dc('button1').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=1', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			dc('button2').addEventListener('click',function(e){
			$('button').css('color','#000');
			$('#button2').css('color','#AFD9EE');
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				form_query(2);
				//单击
			},250)
			},false);
			dc('button2').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=2', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			dc('button3').addEventListener('click',function(e){
			$('button').css('color','#000');
			$('#button3').css('color','#AFD9EE');
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				//单击
				form_query(3);
			},250)
			},false);
			dc('button3').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=3', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			dc('button4').addEventListener('click',function(e){
			$('button').css('color','#000');
			$('#button4').css('color','#AFD9EE');
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				//单击
				form_query(4);
			},250)
			},false);
			dc('button4').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=4', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			dc('button5').addEventListener('click',function(e){
			$('button').css('color','#000');
			$('#button5').css('color','#AFD9EE');
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				//单击
				form_query(5);
			},250)
			},false);
			dc('button5').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=5', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			dc('button6').addEventListener('click',function(e){
			$('button').css('color','#000');
			$('#button6').css('color','#AFD9EE');
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				//单击
				form_query(6);
			},250)
			},false);
			dc('button6').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=6', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			
			dc('button7').addEventListener('click',function(e){
			$('button').css('color','#000');
			$('#button7').css('color','#AFD9EE');
			clearTimeout(timer);
			timer=setTimeout(function(){//初始化一个延时
				//单击
				form_query(7);
			},250)
			},false);
			dc('button7').addEventListener('dblclick',function(){//双击事件会先触发两次单击事件，然后再执行双击事件，使用清除定时器来达到双击只执行双击事件的目的
			clearTimeout(timer);
			//双击
			window.open('/carstatus?id=7', '', 'height=357, width=725, top=200, left=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			},false);
			
			$('#button1').mouseenter(function(){ $('#button1').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button1').css('border-color','#ddd'); }); 
			$('#button2').mouseenter(function(){ $('#button2').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button2').css('border-color','#ddd'); }); 
			$('#button3').mouseenter(function(){ $('#button3').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button3').css('border-color','#ddd'); }); 
			$('#button4').mouseenter(function(){ $('#button4').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button4').css('border-color','#ddd'); }); 
			$('#button5').mouseenter(function(){ $('#button5').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button5').css('border-color','#ddd'); }); 
			$('#button6').mouseenter(function(){ $('#button6').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button6').css('border-color','#ddd'); }); 
			$('#button7').mouseenter(function(){ $('#button7').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button7').css('border-color','#ddd'); }); 
			$('#button8').mouseenter(function(){ $('#button8').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button8').css('border-color','#ddd'); }); 
			$('#button9').mouseenter(function(){ $('#button9').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button9').css('border-color','#ddd'); }); 
			$('#button10').mouseenter(function(){ $('#button10').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button10').css('border-color','#ddd'); }); 
			$('#button11').mouseenter(function(){ $('#button11').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button11').css('border-color','#ddd'); }); 
			$('#button12').mouseenter(function(){ $('#button12').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button12').css('border-color','#ddd'); }); 
			$('#button13').mouseenter(function(){ $('#button13').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button13').css('border-color','#ddd'); }); 
			$('#button14').mouseenter(function(){ $('#button14').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button14').css('border-color','#ddd'); }); 
			$('#button15').mouseenter(function(){ $('#button15').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button15').css('border-color','#ddd'); }); 
			$('#button16').mouseenter(function(){ $('#button16').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button16').css('border-color','#ddd'); }); 
			$('#button17').mouseenter(function(){ $('#button17').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button17').css('border-color','#ddd'); }); 
			$('#button18').mouseenter(function(){ $('#button18').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button18').css('border-color','#ddd'); }); 
			$('#button19').mouseenter(function(){ $('#button19').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button19').css('border-color','#ddd'); }); 
			$('#button20').mouseenter(function(){ $('#button20').css('border-color','#AFD9EE');}).mouseleave(function(){ $('#button20').css('border-color','#ddd'); }); 