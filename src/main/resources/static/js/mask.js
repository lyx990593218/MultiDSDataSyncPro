(function($){
    $.fn.extend({
        /**
         * 打开遮罩，并显示一段文字。
         * @param  {String} msg    [显示的文字]
         * @param  {String} imgsrc [图片的位置]
         * @return {void}       
         */
        openMask:function(msg, imgsrc){
//            var loadDiv=$("body").find("._mask_loadDiv");
            var loadDiv=this.find("._mask_loadDiv");
            if(!loadDiv || !loadDiv[0]){    // add Mask 
                var loadDiv=$("<div class='_mask_loadDiv' style='position:absolute; z-index:99999; height:40px; background:#FFF; border:1px solid #ACE'></div>");
                
                if(!imgsrc){    // 指定默认的图片
                    var scripts=document.getElementsByTagName("script");
                    for(var i=0; i<scripts.length; i++){
                        if(scripts[i].src.indexOf("mask")!=-1){
                            var scriptSrc=scripts[i].src;
                            var uri=scriptSrc.substring(0,scriptSrc.lastIndexOf("/"));
                            imgsrc=uri+"/images/mask_loading.gif";
                        }
                    }
                }

                var contentDiv=$("<div class='_mask_content' style='position:relative;text-align:center;' >");
                var fontsize=12;
                //loadDiv的宽度= msg的宽度+img的宽度
                var loadDiv_width=msg.length*fontsize+16+3;
                contentDiv.css("width",loadDiv_width);
                loadDiv.css("width",loadDiv_width);
                if(imgsrc){
                    contentDiv.append("<img src='"+imgsrc+"' alt='"+msg+"' style='width:16px; height:16px'>")
                            .append("<span style='font-size:"+fontsize+"px; margin-left:2px; vertical-align:text-top'>"+msg+"</span>");
                }
                this.append(loadDiv.append(contentDiv));
            //    $("body").append(loadDiv.append(contentDiv));
                /*
                loadDiv[0].style.top=this[0].offsetTop+(this[0].offsetHeight-loadDiv[0].offsetHeight)/2;
                loadDiv[0].style.left=this[0].offsetLeft+(this[0].offsetWidth-loadDiv[0].offsetWidth)/2;
                loadDiv[0].style.paddingTop=(loadDiv[0].offsetHeight-contentDiv[0].offsetHeight)/2;
                */
                loadDiv.css("top",this[0].offsetTop+(this[0].offsetHeight-loadDiv[0].offsetHeight)/2);
                loadDiv.css("left",this[0].offsetLeft+(this[0].offsetWidth-loadDiv[0].offsetWidth)/2);
                loadDiv.css("padding-top",(loadDiv[0].offsetHeight-contentDiv[0].offsetHeight)/2);
            }
            loadDiv.css("z-index",99999).css("display","block");
            return this;
        },
        closeMask:function(){
    //        var loadDiv=$("body").find("._mask_loadDiv");
            var loadDiv=this.find("._mask_loadDiv");
            if(loadDiv)
                loadDiv.css("display","none").css("z-index",-99999);
            return this;
        }
    });
})(jQuery);


/*



// 这个是遮罩层里信息展示框,这个会添加到 <body> 或者 target 元素中
<div class="_mask_loadDiv">
    <div class="_mask_content">
        <img src='imgsrc' alt='msg' >
        <span>msg</span>
    </div>
</div>


//这个是目标，要在它上显示遮罩层
<div id="target">

</div>

// 只需要下面的代码：
$("#target").openMask("数据加载中。。。");
// 隐藏对话框，只需要：
$("#target").closeMask();



*/